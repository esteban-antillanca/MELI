package com.eantillanca.melimasterdetailexample.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Esteban Antillanca on 2020-01-08.
 */
public final class Item implements Parcelable {

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

    public Item(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
        this.price = in.readString();
        this.thumbnail = in.readString();
        this.sellerName = in.readString();
        this.qtySells = in.readString();
        this.condition = in.readString();
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.getTitle());
        dest.writeString(this.getID());
        dest.writeString(this.getPrice());
        dest.writeString(this.getThumbnail());
        dest.writeString(this.getSellerName());
        dest.writeString(this.getQtySells());
        dest.writeString(this.getCondition());

    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
