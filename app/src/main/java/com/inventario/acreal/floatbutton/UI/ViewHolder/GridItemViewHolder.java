package com.inventario.acreal.floatbutton.UI.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inventario.acreal.floatbutton.Models.Menu;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.UI.OnItemClickListener;

/**
 * Created by amelara on 31/10/2017.
 */

public class GridItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvItem;
    public ImageView ivItem;
    public CardView cview;

    Menu mItem;
    OnItemClickListener<Menu> mClickListener;

    public GridItemViewHolder(View itemView,OnItemClickListener<Menu> listener) {
        super(itemView);
        mClickListener = listener;
        init(itemView);
    }

    private void init(View itemView) {
        tvItem = (TextView) itemView.findViewById(R.id.tv_menu_item);
        ivItem = (ImageView) itemView.findViewById(R.id.iv_menu_item);
        cview = (CardView) itemView.findViewById(R.id.cv_item);
        itemView.setOnClickListener(this);
    }

    public void setItem(Menu item) {
        mItem = item;
    }

    @Override
    public void onClick(View view) {
        mClickListener.onItemClick(view, mItem);
    }
}
