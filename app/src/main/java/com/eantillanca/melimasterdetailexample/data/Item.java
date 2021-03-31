package com.eantillanca.melimasterdetailexample.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Esteban Antillanca on 2020-01-08.
 */
public final class Item {

    @SerializedName("id")
    private final String id;

    @SerializedName("title")
    private final String title;

    @SerializedName("price")
    private final String price;

    @SerializedName("thumbnail")
    private final String thumbnail;

    public Item(String title, String id, String price, String thumbnail) {
        this.title = title;
        this.id = id;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public String getTitle(){
        return this.title;
    }

    public String getID(){
        return this.id;
    }

    public String getPrice() { return price; }

    public String getThumbnail() { return thumbnail; }


}
