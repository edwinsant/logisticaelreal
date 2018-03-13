package com.inventario.acreal.floatbutton.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inventario.acreal.floatbutton.Models.Menu;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.UI.OnItemClickListener;
import com.inventario.acreal.floatbutton.UI.ViewHolder.GridItemViewHolder;

import java.util.ArrayList;

/**
 * Created by amelara on 31/10/2017.
 */

public class menuAdapter extends RecyclerView.Adapter<GridItemViewHolder> {

    public static final int LAYOUT = R.layout.item_main_menu;

    Context mCtx;
    ArrayList<Menu> mItemsData;
    OnItemClickListener<Menu> mClickListener;

    public menuAdapter(Context context, ArrayList<Menu> itemData, OnItemClickListener<Menu> listener) {
        mCtx = context;
        mItemsData = itemData;
        mClickListener = listener;
    }

    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT, parent, false);
        return new GridItemViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(GridItemViewHolder holder, int position) {
        Menu items = mItemsData.get(position);
        holder.setItem(items);
        setData(items, holder);
    }

    private void setData(Menu item, GridItemViewHolder holder) {
        if (item == null) {
            return;
        }
        if (holder.tvItem != null) {
            holder.tvItem.setText(item.getName());
        }
        if (holder.ivItem != null) {
            holder.ivItem.setImageResource(item.getImage());
        }
    }

    @Override
    public int getItemCount() {
        return mItemsData.size();
    }
}
