package com.eantillanca.melimasterdetailexample.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Esteban Antillanca on 4/7/21.
 * POJO for the detail of a given Item, to populate the Item Detail View. Some of the fields have are redundant with other in the
 * Item list class, but they correspond to different endpoints so they are mapped in different classes for simplicity
 */

public final class ItemDetail {
    @SerializedName("title")
    private final String title;

    @SerializedName("condition")
    private final String condition;

    @SerializedName("price")
    private final String price;


    @SerializedName("pictures")
    private final String[] pictures;

    public ItemDetail(String title, String condition, String price, String[] pictures) {
        this.title = title;
        this.condition = condition;
        this.price = price;
        this.pictures = pictures;
    }

    public String getTitle() {
        return title;
    }

    public String getCondition() {
        return condition;
    }

    public String getPrice() {
        return price;
    }

    public String[] getPictures() {
        return pictures;
    }

}
