package com.inventario.acreal.floatbutton.Utils;

/**
 * Created by amelara on 26/10/2017.
 */

public interface OnRecyclerObjectClickListener<T> extends BaseRecyclerListener {

    void onItemClicked(T item);
}
