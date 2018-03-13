package com.inventario.acreal.floatbutton.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.inventario.acreal.floatbutton.Models.Product;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.UI.ViewHolder.ProductViewHolder;
import com.inventario.acreal.floatbutton.Utils.BaseAdapter;
import com.inventario.acreal.floatbutton.Utils.OnRecyclerObjectClickListener;

/**
 * Created by amelara on 26/10/2017.
 */

public class TestAdapter extends BaseAdapter<Product,OnRecyclerObjectClickListener<Product>,ProductViewHolder> {

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(inflate(R.layout.item_prod_test,parent));
    }
}
