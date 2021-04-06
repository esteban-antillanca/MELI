package com.eantillanca.melimasterdetailexample.itemList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.eantillanca.melimasterdetailexample.R;
import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.itemDetail.ItemDetailActivity;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.eantillanca.melimasterdetailexample.Constants.CURRENT_ITEM;

/**
 * Created by Esteban Antillanca on 2021-03-29.
 */
public class ItemListFragment extends Fragment implements ItemListContract.View {

    private ItemListContract.Presenter mPresenter;

    private ItemAdapter mListAdapter;

    private RelativeLayout itemsL;

    private RelativeLayout noItemsL;

    private RecyclerView listView;

    private EditText searchEditText;

    private ProgressBar spinner;

    private RelativeLayout firstLoad;

    public ItemListFragment(){
        //Empty constructor
    }

    /**
     * Listener for clicks on tasks in the ListView.
     */
    ItemsListener mItemListener = new ItemsListener() {

        @Override
        public void onItemClick(Item item) {
            mPresenter.openItemDetail(item);
        }
    };

    public static ItemListFragment newInstance() {
        return new ItemListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new ItemAdapter(new ArrayList<>(0), getContext(), mItemListener);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(ItemListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.item_list_frag, container, false);

        itemsL = root.findViewById(R.id.itemsL);

        noItemsL = root.findViewById(R.id.no_items_l);

        listView = root.findViewById(R.id.items_list);
        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(llManager);
        listView.setAdapter(mListAdapter);

        spinner = root.findViewById(R.id.loading_spinner);

        firstLoad = root.findViewById(R.id.rlay_start);

        searchEditText = root.findViewById(R.id.search_edit_text);
        searchEditText.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                mPresenter.searchItems(searchEditText.getText().toString());
                return true;
            }
            return false;
        });


        return root;

    }

    @Override
    public void showItems(List<Item> items) {
        mListAdapter.replaceData(items);
        itemsL.setVisibility(View.VISIBLE);
        noItemsL.setVisibility(View.GONE);

    }

    @Override
    public void showLoadingItemsError() {

        Snackbar.make(getActivity().findViewById(R.id.contentFrame), getActivity().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void showNoItems() {
        itemsL.setVisibility(View.GONE);
        noItemsL.setVisibility(View.VISIBLE);

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
    public void showItemDetail(Item item) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra(CURRENT_ITEM, item);
        startActivity(intent);

    }

    @Override
    public void showNoEmptySearchString() {

        Snackbar.make(getActivity().findViewById(R.id.contentFrame), getActivity().getString(R.string.no_empty_search), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void setFirstImageVisible(Boolean visible) {

        if(visible) firstLoad.setVisibility(View.VISIBLE);
        else firstLoad.setVisibility(View.GONE);

    }


    private static class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Item> mItems;
        private Context context;
        private ItemsListener mListener;

        public ItemAdapter(List<Item> items , Context context, ItemsListener mListener) {
            setList(items);
            this.context = context;
            this.mListener = mListener;
        }

        private void replaceData(List<Item> items) {
            setList(items);
            notifyDataSetChanged();
        }

        private void setList(List<Item> items) {
            mItems = checkNotNull(items);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Item item = mItems.get(position);
            holder.itemTitle.setText(item.getTitle());
            holder.itemPrice.setText(item.getPrice());
            holder.cardView.setOnClickListener(v -> mListener.onItemClick(item));
            holder.cardView.setTag(holder);
            Glide
                    .with(context)
                    .load(item.getThumbnail().trim())
                    .placeholder(R.drawable.fogg_640)
                    .centerCrop()
                    .into(holder.thumbnail);

        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

    }

    //To better handle click listeners in the Recycler View
    public interface ItemsListener {

        void onItemClick(Item item);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTitle;

        private TextView itemPrice;

        private ImageView thumbnail;

        private CardView cardView;


        public ViewHolder(@NonNull View view) {
            super(view);
            this.itemTitle = view.findViewById(R.id.item_title);
            this.itemPrice = view.findViewById(R.id.item_price);
            this.thumbnail = view.findViewById(R.id.item_thumbnail);
            this.cardView = view.findViewById(R.id.cardViewitem);
        }

    }
}
