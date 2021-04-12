package com.eantillanca.melimasterdetailexample.itemDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.eantillanca.melimasterdetailexample.R;
import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.data.ItemDetail;
import com.eantillanca.melimasterdetailexample.data.Seller;
import com.eantillanca.melimasterdetailexample.itemList.ItemListFragment;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Esteban Antillanca on 4/3/21.
 * This class represent the View in the MVP pattern for the Item Detail feature. It will show the details of the product
 * selected in the Item List feature, passing user interaction data to the companion presenter.
 */
public class ItemDetailFragment extends Fragment implements ItemDetailContract.View{

    private ItemDetailContract.Presenter mPresenter;
    private TextView condition;
    private TextView title;
    private TextView price;
    private TextView seller;
    private TextView qtySell;
    private Button btn;
    private ProgressBar spinner;
    private SliderView slideView;



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
        spinner = root.findViewById(R.id.loading_spinner);
        slideView = root.findViewById(R.id.slideView);

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
    public void showItem(ItemDetail item, Seller mseller) {


        condition.setText(item.getCondition());
        title.setText(item.getTitle());
        price.setText(item.getPrice());
        seller.setText(mseller.getNickname());
        qtySell.setText(mseller.getTotalTransactions());

        SliderAdapter adapter = new SliderAdapter(getContext(), item.getPictures());
        slideView.setSliderAdapter(adapter);
        slideView.setAutoCycle(false);

    }

    @Override
    public void showLoadingIndicator(Boolean loading) {

        if (loading) {
            spinner.setVisibility(View.VISIBLE);
        } else {
            spinner.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void showLoadingItemDetailError() {
        Snackbar.make(getActivity().findViewById(R.id.contentFrame), getActivity().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    /**
     * This adapter enable this view to use a Sliding View, to show multiple images of the given item in a carousel-like view
     */
    public static class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

        // list for storing urls of images.
        private final List<String> mSliderItems;

        // Constructor
        public SliderAdapter(Context context, String[] pictures) {

            this.mSliderItems = new ArrayList<String>();

            for (int i = 0; i < pictures.length; i++)
            {
                mSliderItems.add(pictures[i]);
            }
        }

        // We are inflating the slider_layout
        // inside on Create View Holder method.
        @Override
        public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
            return new SliderAdapterViewHolder(inflate);
        }

        // Inside on bind view holder we will
        // set data to item of Slider View.
        @Override
        public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

            final String sliderItem = mSliderItems.get(position);

            // Glide is use to load image
            // from url in your imageview.
            Glide.with(viewHolder.itemView)
                    .load(sliderItem)
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
        }

        // this method will return
        // the count of our list.
        @Override
        public int getCount() {
            return mSliderItems.size();
        }

        static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
            // Adapter class for initializing
            // the views of our slider view.
            View itemView;
            ImageView imageViewBackground;

            public SliderAdapterViewHolder(View itemView) {
                super(itemView);
                imageViewBackground = itemView.findViewById(R.id.myimage);
                this.itemView = itemView;
            }
        }
    }

}
