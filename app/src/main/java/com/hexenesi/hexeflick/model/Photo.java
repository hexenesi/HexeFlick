package com.hexenesi.hexeflick.model;


import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.RushObject;

public class Photo extends RushObject {

    private String id;
    private String owner;
    private String secret;
    private String server;
    private long farm;
    private String title;
    private long ispublic;
    private long isfriend;
    private long isfamily;

    /**
     * No args constructor for use in serialization
     */
    public Photo() {
    }

    /**
     * @param isfamily
     * @param farm
     * @param id
     * @param title
     * @param ispublic
     * @param owner
     * @param secret
     * @param server
     * @param isfriend
     */
    public Photo(String id, String owner, String secret, String server, long farm, String title, long ispublic, long isfriend, long isfamily) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.ispublic = ispublic;
        this.isfriend = isfriend;
        this.isfamily = isfamily;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Photo withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Photo withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    /**
     * @return The secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret The secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Photo withSecret(String secret) {
        this.secret = secret;
        return this;
    }

    /**
     * @return The server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server The server
     */
    public void setServer(String server) {
        this.server = server;
    }

    public Photo withServer(String server) {
        this.server = server;
        return this;
    }

    /**
     * @return The farm
     */
    public long getFarm() {
        return farm;
    }

    /**
     * @param farm The farm
     */
    public void setFarm(long farm) {
        this.farm = farm;
    }

    public Photo withFarm(long farm) {
        this.farm = farm;
        return this;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Photo withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return The ispublic
     */
    public long getIspublic() {
        return ispublic;
    }

    /**
     * @param ispublic The ispublic
     */
    public void setIspublic(long ispublic) {
        this.ispublic = ispublic;
    }

    public Photo withIspublic(long ispublic) {
        this.ispublic = ispublic;
        return this;
    }

    /**
     * @return The isfriend
     */
    public long getIsfriend() {
        return isfriend;
    }

    /**
     * @param isfriend The isfriend
     */
    public void setIsfriend(long isfriend) {
        this.isfriend = isfriend;
    }

    public Photo withIsfriend(long isfriend) {
        this.isfriend = isfriend;
        return this;
    }

    /**
     * @return The isfamily
     */
    public long getIsfamily() {
        return isfamily;
    }

    /**
     * @param isfamily The isfamily
     */
    public void setIsfamily(long isfamily) {
        this.isfamily = isfamily;
    }

    public Photo withIsfamily(long isfamily) {
        this.isfamily = isfamily;
        return this;
    }

    @Override
    public void save() {
        RushCore.getInstance().registerObjectWithId(this, id);
        super.save();
    }
}