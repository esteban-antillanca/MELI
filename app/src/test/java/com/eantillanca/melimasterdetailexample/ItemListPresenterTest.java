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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Esteban Antillanca on 2021-04-06.
 */
public class ItemListPresenterTest {

    @Mock
    private ItemRemoteDataSource dataSource;

    @Mock
    private ItemListContract.View view;

    private ItemListPresenter presenter;

    @Captor
    private ArgumentCaptor<ItemDataSource.LoadItemsCallback> argumentCaptor;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new ItemListPresenter(dataSource,view);
    }

    @Test
    public void fetchValidDataShouldLoadIntoView(){
        presenter.searchItems("avengers");
        verify(dataSource,times(1)).getItems(argumentCaptor.capture(), eq("avengers"));
        argumentCaptor.getValue().onItemsLoaded(getList());
        ArgumentCaptor<List> entityArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).showItems(entityArgumentCaptor.capture());
        assertTrue(entityArgumentCaptor.getValue().size()==6);


    }

    @Test
    public void emptyListShouldShowError(){
        presenter.searchItems("test");;
        verify(dataSource,times(1)).getItems(argumentCaptor.capture(), eq("test"));
        argumentCaptor.getValue().onItemsLoaded(new ArrayList<>());
        verify(view).showNoItems();
    }

    /**
     *
     */
    @Test
    public void noConnectionShouldShowError(){
        presenter.searchItems("test");
        verify(dataSource,times(1)).getItems(argumentCaptor.capture(),eq("test"));
        argumentCaptor.getValue().onDataNotAvailable();
        verify(view).showLoadingItemsError();
    }

    /**
     * Helper method to create a basic list of items to show on the main list
     * @return A list with dummy data
     */
    public List<Item> getList(){

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Thor Odinson","1","300","http://www.test.com/image1.jpg","12345"));
        items.add(new Item("Captain America", "2", "2500","http://www.test.com/image2.jpg","67890"));
        items.add(new Item("Iron Man", "3", "150","http://www.test.com/image3.jpg","09876"));
        items.add(new Item("Black Widow", "4", "1010","http://www.test.com/image4.jpg","54321"));
        items.add(new Item("Hulk", "5", "10000","http://www.test.com/image5.jpg","10293"));
        items.add(new Item("Hawkeye", "6", "645","http://www.test.com/image6.jpg","564738"));
        return items;

    }


}
