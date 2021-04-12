package com.eantillanca.melimasterdetailexample.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Esteban Antillanca on 4/8/21.
 * POJO for storing basic seller data, to enrich the info given in the item detail view.
 */
public final class Seller {
    @SerializedName("nickname")
    private final String nickname;

    @SerializedName("total")
    private final String total;

    public Seller(String nickname, String total) {
        this.nickname = nickname;
        this.total = total;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTotalTransactions() {
        return total;
    }
}


