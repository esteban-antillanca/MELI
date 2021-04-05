package com.eantillanca.melimasterdetailexample.itemDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.eantillanca.melimasterdetailexample.R;
import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.itemList.ItemListFragment;
import com.google.android.material.snackbar.Snackbar;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Esteban Antillanca on 4/3/21.
 */
public class ItemDetailFragment extends Fragment implements ItemDetailContract.View{

    private ItemDetailContract.Presenter mPresenter;
    private TextView condition;
    private TextView title;
    private TextView price;
    private TextView seller;
    private TextView qtySell;
    private ImageView image;
    private Button btn;



    public static ItemDetailFragment newInstance() {
        return new ItemDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.item_detail_frag, container, false);

        condition = root.findViewById(R.id.condition);
        title = root.findViewById(R.id.product_title);
        price = root.findViewById(R.id.item_price);
        seller = root.findViewById(R.id.seller_name);
        qtySell = root.findViewById(R.id.qty_sells);
        btn = root.findViewById(R.id.button);
        image = root.findViewById(R.id.imageView);
        btn.setOnClickListener(v -> showPaymentAction());

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(ItemDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void showPaymentAction() {

        Snackbar.make(getView(),getString(R.string.coming_soon_message), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showItem(Item item) {

        condition.setText(item.getCondition());
        title.setText(item.getTitle());
        price.setText(item.getPrice());
        seller.setText(item.getSellerName());
        qtySell.setText(item.getQtySells());
        Glide
                .with(this)
                .load(item.getThumbnail())
                .placeholder(R.drawable.fogg_640)
                .into(image);

    }

}
