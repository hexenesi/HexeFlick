package com.hexenesi.hexeflick.events;

import com.hexenesi.hexeflick.model.Photos;

/**
 * Created by juanpcazarez on 11/18/15.
 */
public class SearchEvent {
    public String busqueda;
    public Photos photos;

    public SearchEvent(String busqueda) {
        this.photos=null;
        this.busqueda = busqueda;
    }
    public SearchEvent(Photos photos){
        this.busqueda=null;
        this.photos=photos;
    }

}
