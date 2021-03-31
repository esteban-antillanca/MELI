package com.eantillanca.melimasterdetailexample;

import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.itemList.ItemListContract;
import com.eantillanca.melimasterdetailexample.itemList.ItemListPresenter;
import com.eantillanca.melimasterdetailexample.data.ItemDataSource;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Esteban Antillanca on 2020-01-13.
 */
public class ItemListPresenterTest {

    @Mock
    private ItemRemoteDataSource dataSource;

    @Mock
    private ItemListContract.View view;

    private ItemListPresenter presenter;

    @Captor
    private ArgumentCaptor<ItemDataSource.LoadItemsCallback> argumentCaptor;

    @Captor
    private ArgumentCaptor<Item> counterCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        presenter = new ItemListPresenter(dataSource,view);


    }

    @Test
    public void fetchValidDataShouldLoadIntoView(){
        presenter.searchItems();
        verify(dataSource,times(1)).getItems(argumentCaptor.capture());
        argumentCaptor.getValue().onItemsLoaded(getList());

        ArgumentCaptor<List> entityArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(view).showItems(entityArgumentCaptor.capture());

        assertTrue(entityArgumentCaptor.getValue().size()==5);


    }

    @Test
    public void emptyListShouldShowError(){
        presenter.searchItems();;
        verify(dataSource,times(1)).getItems(argumentCaptor.capture());
        argumentCaptor.getValue().onItemsLoaded(new ArrayList<>());
        verify(view).showNoItems();
    }

    @Test
    public void noConnectionShouldShowError(){
        presenter.searchItems();
        verify(dataSource,times(1)).getItems(argumentCaptor.capture());
        argumentCaptor.getValue().onDataNotAvailable();
        verify(view).showLoadingItemsError();
    }
/*
    @Test
    public void duplicatedCounterShouldShowError(){
        presenter.createCounter("Kids",getList());
        verify(view).showDuplicatedNameErr();
    }

    @Test
    public void modifiedDataShouldUpdateView(){

        //Decrease
        presenter.decCounter(counterCaptor.capture(), eq(1));
        verify(dataSource,times(1)).decCounter(counterCaptor.capture(),argumentCaptor.capture());
        argumentCaptor.getValue().onCounterDecreased(getList());
        verify(view).showCounterDec(counterCaptor.capture(),anyInt());

        //Increase
        presenter.incCounter(counterCaptor.capture(), eq(1));
        verify(dataSource,times(1)).incCounter(counterCaptor.capture(),argumentCaptor.capture());
        argumentCaptor.getValue().onCounterIncreased(getList());
        verify(view).showCounterInc(counterCaptor.capture(),anyInt());

        //Deleted
        presenter.deleteCounter(any(Item.class), eq(1));
        verify(dataSource,times(1)).delCounter(counterCaptor.capture(),argumentCaptor.capture());
        argumentCaptor.getValue().onCounterDeleted(getList());
        verify(view).showCounterRemoved(anyInt());
    }
 */
    public List<Item> getList(){

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Glasses of water","1"));
        items.add(new Item("White Shirts","2"));
        items.add(new Item("Ex-Girlfriends","3"));
        items.add(new Item("Kids","4"));
        items.add(new Item("Nintendo Switch games","5"));

        return items;

    }


}
