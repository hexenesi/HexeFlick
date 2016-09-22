package com.hexenesi.hexeflick.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.hexenesi.hexeflick.R;

public class ParallaxPageTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {

        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        View view1 = view.findViewById(R.id.imagen);
        float scale = position < 0 ? 1f + position : 1f - position;
        view1.setScaleX(scale);
        view1.setScaleY(scale);

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(1);

        } else if (position <= 1) { // [-1,1]


            view1.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
            view1.setTranslationY(-Math.abs(position) * (pageHeight / 2));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(1);
        }


    }
}