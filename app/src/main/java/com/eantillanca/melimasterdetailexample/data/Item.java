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

    @SerializedName("count")
    private int count = 0;

    public Item(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getID(){
        return this.id;
    }

    public int getCount(){
        return count;
    }


}
