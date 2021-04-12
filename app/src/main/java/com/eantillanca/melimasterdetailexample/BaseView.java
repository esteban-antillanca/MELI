package com.eantillanca.melimasterdetailexample;

/**
 * Created by Esteban Antillanca on 2021-03-29.
 * Base View for MVP implementation
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

}