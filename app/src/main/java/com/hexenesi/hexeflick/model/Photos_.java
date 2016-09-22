package com.hexenesi.hexeflick.model;

import java.util.ArrayList;
import java.util.List;


public class Photos_ {

    private long page;
    private long pages;
    private long perpage;
    private String total;
    private List<Photo> photo = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     */
    public Photos_() {
    }

    /**
     * @param total
     * @param page
     * @param pages
     * @param photo
     * @param perpage
     */
    public Photos_(long page, long pages, long perpage, String total, List<Photo> photo) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photo = photo;
    }

    /**
     * @return The page
     */
    public long getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(long page) {
        this.page = page;
    }

    public Photos_ withPage(long page) {
        this.page = page;
        return this;
    }

    /**
     * @return The pages
     */
    public long getPages() {
        return pages;
    }

    /**
     * @param pages The pages
     */
    public void setPages(long pages) {
        this.pages = pages;
    }

    public Photos_ withPages(long pages) {
        this.pages = pages;
        return this;
    }

    /**
     * @return The perpage
     */
    public long getPerpage() {
        return perpage;
    }

    /**
     * @param perpage The perpage
     */
    public void setPerpage(long perpage) {
        this.perpage = perpage;
    }

    public Photos_ withPerpage(long perpage) {
        this.perpage = perpage;
        return this;
    }

    /**
     * @return The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    public Photos_ withTotal(String total) {
        this.total = total;
        return this;
    }

    /**
     * @return The photo
     */
    public List<Photo> getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public Photos_ withPhoto(List<Photo> photo) {
        this.photo = photo;
        return this;
    }

}