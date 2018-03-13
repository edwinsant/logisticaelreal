package com.inventario.acreal.floatbutton.UI.ViewHolder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.inventario.acreal.floatbutton.Models.Product;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Utils.BaseViewHolder;
import com.inventario.acreal.floatbutton.Utils.OnRecyclerObjectClickListener;

/**
 * Created by amelara on 26/10/2017.
 */

public class ProductViewHolder extends BaseViewHolder<Product, OnRecyclerObjectClickListener<Product>> {

    private TextView name;

    public ProductViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
    }

    @Override
    public void onBind(Product item, @Nullable OnRecyclerObjectClickListener<Product> listener) {
        name.setText(item.getProducto());
    }
}
