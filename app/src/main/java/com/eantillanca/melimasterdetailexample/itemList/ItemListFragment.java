package com.eantillanca.melimasterdetailexample.itemList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.eantillanca.melimasterdetailexample.R;
import com.eantillanca.melimasterdetailexample.data.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Esteban Antillanca on 2021-03-29.
 */
public class ItemListFragment extends Fragment implements ItemListContract.View {

    private ItemListContract.Presenter mPresenter;

    private CounterAdapter mListAdapter;

    private RelativeLayout itemsL;

    private RelativeLayout noItemsL;

    private RecyclerView listView;

    public ItemListFragment(){
        //Empty constructor
    }

    public static ItemListFragment newInstance() {
        return new ItemListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new CounterAdapter(new ArrayList<>(0), getContext());

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

        View root = inflater.inflate(R.layout.counter_list_frag, container, false);

        itemsL = root.findViewById(R.id.countersL);

        noItemsL = root.findViewById(R.id.no_items_l);

        listView = root.findViewById(R.id.counters_list);
        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(llManager);
        listView.setAdapter(mListAdapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(listView.getContext(),llManager.getOrientation());
        listView.addItemDecoration(mDividerItemDecoration);


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


    private static class CounterAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Item> mItems;
        private Context context;
        private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


        public CounterAdapter(List<Item> items , Context context) {
            setList(items);
            this.context = context;
        }

        private void replaceData(List<Item> items) {
            setList(items);
            notifyDataSetChanged();
        }

        private void addItem(Item item){
            mItems.add(item);
        }

        private void removeItem(int position){
            mItems.remove(position);
        }

        private int getDataSetSize(){
            return mItems.size();
        }

        private void setItemAtPosition(int position, Item item){
            mItems.set(position, item);
        }

        private List<Item> getData(){
            return mItems;
        }


        private void setList(List<Item> items) {
            mItems = checkNotNull(items);
        }



        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.counter_item, parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Item item = mItems.get(position);
            viewBinderHelper.setOpenOnlyOne(true);
            viewBinderHelper.bind(holder.swipeRevealLayout, item.getID());
            holder.counterTV.setText(item.getTitle());
            holder.counterNumber.setText(item.getCount() + "");

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

    public interface CounterItemListener {

        void onIncCLick(Item incItem, int position);
        void onDecClick(Item decItem, int position);
        void onDelClick(Item delItem, int position);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView counterTV;
        private TextView counterNumber;
        private SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.counterTV = view.findViewById(R.id.counter_label);
            this.counterNumber = view.findViewById(R.id.counter_number);
            this.swipeRevealLayout = view.findViewById(R.id.swipeRevealLayout);
        }
    }
}
