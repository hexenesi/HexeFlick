package com.hexenesi.hexeflick;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hexenesi.hexeflick.events.MessageEvent;
import com.hexenesi.hexeflick.events.ReloadEvent;
import com.hexenesi.hexeflick.events.SearchEvent;
import com.hexenesi.hexeflick.model.Photo;
import com.hexenesi.hexeflick.model.Photos;
import com.hexenesi.hexeflick.requests.FlickrSearches;
import com.hexenesi.hexeflick.transformers.ParallaxPageTransformer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.uk.rushorm.core.RushSearch;
import de.greenrobot.event.EventBus;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class FullPhotoFragment extends Fragment {

    private FlickrSearches search;
    private ViewPager pager;
    private ToggleButton favorite;
    private DisplayImageOptions options;
    private List<Photo> images;
    private ImageAdapter adapter;

    private CompoundButton.OnCheckedChangeListener listener;
    private final Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            if (msg.what != -1) {
                adapter.notifyDataSetChanged();
                pager.setCurrentItem(0);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_full_photo);
        View root = inflater.inflate(R.layout.activity_full_photo, container, false);
        search=new FlickrSearches();
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        pager = (ViewPager) root.findViewById(R.id.pager);
        favorite = (ToggleButton) root.findViewById(R.id.favorite);

        listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    images.get(pager.getCurrentItem()).save();
                } else {
                    images.get(pager.getCurrentItem()).delete();
                    images.remove(pager.getCurrentItem());
                    adapter.notifyDataSetChanged();
                }
            }
        };
        favorite.setOnCheckedChangeListener(listener);

        images = new ArrayList<>();

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Photo currentPhoto = images.get(position);
                ((MainActivity) getActivity()).setName(currentPhoto.getTitle());
                Photo photo = new RushSearch().whereId(currentPhoto.getId()).findSingle(Photo.class);
                favorite.setOnCheckedChangeListener(null);
                if (photo != null) {
                    favorite.setChecked(true);
                } else {
                    favorite.setChecked(false);
                }
                favorite.setOnCheckedChangeListener(listener);
//                loadPrevNext(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter = new ImageAdapter(getActivity());
        if (!ImageLoader.getInstance().isInited()) {
            MainActivity.initImageLoader(getActivity());
        }
        //imageDisplayer(imagenGaleria.getUrluser(), usrImg);
        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        pager.setAdapter(adapter);
        pager.setPageTransformer(false, new ParallaxPageTransformer());
        search.get(FlickrSearches.RECENTS, Photos.class);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // Mostrar los favoritos
    public void onEvent(MessageEvent event) {
        images.clear();
        images.addAll(new RushSearch().find(Photo.class));
        adapter.notifyDataSetChanged();
        pager.setCurrentItem(images.size() - 1, true);
        pager.setCurrentItem(0);
    }

    // Mostrar los recientes
    public void onEvent(ReloadEvent event) {
        Toast.makeText(getActivity(), event.message, Toast.LENGTH_SHORT).show();
        reload();
    }

    public void onEvent(SearchEvent event) {
        if(event.photos==null) {
            String busqueda = String.format(Locale.getDefault(), FlickrSearches.SEARCHTAG, event.busqueda);
            Log.d("Busqueda tag", busqueda);
            search.get(busqueda, Photos.class);
        } else {
            images.clear();
            images.addAll(event.photos.getPhotos().getPhoto());
            handler.sendEmptyMessage(0);
        }
    }

    private void reload() {
        search.get(FlickrSearches.RECENTS, Photos.class);
    }

    private void imageDisplayer(String url, ImageView view, final ProgressBar loading) {
        ImageLoader.getInstance().displayImage(url, view, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (loading != null) {
                    loading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        break;
                }
                //Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                if (loading != null) {
                    loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loading != null) {
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }


    private class ImageAdapter extends PagerAdapter {

        private final LayoutInflater inflater;

        ImageAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_galeria_zoom, view, false);

            ImageViewTouch imageTouch = (ImageViewTouch) imageLayout.findViewById(R.id.imagen);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
            imageTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

            //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
            String img_url;//=DOWNLOAD;
            Photo image = images.get(position);


            Resources res = getResources();
            img_url = String.format(res.getString(R.string.download), image.getFarm(), image.getServer(), image.getId(), image.getSecret(), "h");

            Log.d("Peticion", img_url);
            imageDisplayer(img_url, imageTouch, spinner);


            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}
