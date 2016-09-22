package com.hexenesi.hexeflick.model;


public class Photos {

    private Photos_ photos;
    private String stat;

    /**
     * No args constructor for use in serialization
     */
    public Photos() {
    }

    /**
     * @param photos
     * @param stat
     */
    public Photos(Photos_ photos, String stat) {
        this.photos = photos;
        this.stat = stat;
    }

    /**
     * @return The photos
     */
    public Photos_ getPhotos() {
        return photos;
    }

    /**
     * @param photos The photos
     */
    public void setPhotos(Photos_ photos) {
        this.photos = photos;
    }

    public Photos withPhotos(Photos_ photos) {
        this.photos = photos;
        return this;
    }

    /**
     * @return The stat
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat The stat
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

    public Photos withStat(String stat) {
        this.stat = stat;
        return this;
    }

}