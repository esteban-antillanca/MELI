package com.eantillanca.melimasterdetailexample;

import com.eantillanca.melimasterdetailexample.data.ItemDataSource;
import com.eantillanca.melimasterdetailexample.data.ItemDetail;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;
import com.eantillanca.melimasterdetailexample.data.Seller;
import com.eantillanca.melimasterdetailexample.itemDetail.ItemDetailContract;
import com.eantillanca.melimasterdetailexample.itemDetail.ItemDetailPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Esteban Antillanca on 4/10/21.
 */
public class ItemDetailPresenterTest {

    @Mock
    private ItemRemoteDataSource dataSource;

    @Mock
    private ItemDetailContract.View view;

    private ItemDetailPresenter presenter;

    @Captor
    private ArgumentCaptor<ItemDataSource.LoadItemDetailCallback> itemDetailArgumentCaptor;

    @Captor
    private ArgumentCaptor<ItemDataSource.LoadSellerCallback> sellerArgumentCaptor;




    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void fetchValidDataShouldLoadIntoView(){

        presenter = new ItemDetailPresenter(view, dataSource, "MLA897752664", "90205574");
        presenter.start();

        //Fist we fetch de detail of the item

        verify(dataSource,times(1)).getItemDetail(itemDetailArgumentCaptor.capture(), eq("MLA897752664"));
        itemDetailArgumentCaptor.getValue().onItemDetailLoaded(getItemDetail());
        ArgumentCaptor<ItemDetail> entityArgumentCaptor1 = ArgumentCaptor.forClass(ItemDetail.class);


        //Then we fetch some insights of the seller
        verify(dataSource,times(1)).getSeller(sellerArgumentCaptor.capture(), eq("90205574"));
        ArgumentCaptor<Seller> entityArgumentCaptor2 = ArgumentCaptor.forClass(Seller.class);
        sellerArgumentCaptor.getValue().onSellerLoaded(getSeller());

        verify(view).showItem(entityArgumentCaptor1.capture(), entityArgumentCaptor2.capture());
    }

    @Test
    public void noItemDetailDataShouldShowError(){
        presenter = new ItemDetailPresenter(view, dataSource, "test", "test");
        presenter.start();

        verify(dataSource,times(1)).getItemDetail(itemDetailArgumentCaptor.capture(), eq("test"));
        itemDetailArgumentCaptor.getValue().onDataNotAvailable();
        verify(view).showLoadingItemDetailError();
    }

    @Test
    public void noSellerDataShouldShowError(){
        presenter = new ItemDetailPresenter(view, dataSource, "test", "test");
        presenter.start();

        verify(dataSource,times(1)).getItemDetail(itemDetailArgumentCaptor.capture(), eq("test"));
        itemDetailArgumentCaptor.getValue().onItemDetailLoaded(getItemDetail());

        verify(dataSource,times(1)).getSeller(sellerArgumentCaptor.capture(), eq("test"));
        sellerArgumentCaptor.getValue().onDataNotAvailable();
        verify(view).showLoadingItemDetailError();
    }

    /**
     * Helper method to create mock ItemDetail, with hardcoded data, in order to use in the rest of the tests.
     * @return an ItemDetail object
     */
    public ItemDetail getItemDetail(){
        String[] pictures = new String[]{"https://wwww.mercadolibre.cl/imagen1.png", "https://wwww.mercadolibre.cl/imagen2.png", "https://wwww.mercadolibre.cl/imagen3.png", "https://wwww.mercadolibre.cl/imagen4.png"};
        return new ItemDetail("Item de prueba", "Nuevo","$12345", pictures);

    }

    /**
     * Mock of Seller object, loosely asociated to a product.
     * @return A seller object
     */
    public Seller getSeller(){

        return new Seller("Vendedor1","3450");

    }
}
