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

    @SerializedName("nick_name")
    private final String sellerName;

    @SerializedName("completed")
    private final String qtySells;

    @SerializedName("condition")
    private final String condition;

    public Item(String title, String id, String price, String thumbnail, String sellerName, String qtySells, String condition) {
        this.title = title;
        this.id = id;
        this.price = price;
        this.thumbnail = thumbnail;
        this.sellerName = sellerName;
        this.qtySells = qtySells;
        this.condition = condition;
    }

    public String getTitle(){
        return this.title;
    }

    public String getID(){
        return this.id;
    }

    public String getPrice() { return price; }

    public String getThumbnail() { return thumbnail; }

    public String getSellerName() { return sellerName; }

    public String getQtySells() { return qtySells; }

    public String getCondition() { return condition; }


}
