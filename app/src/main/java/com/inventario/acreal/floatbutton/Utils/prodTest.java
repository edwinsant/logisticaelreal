package com.inventario.acreal.floatbutton.Utils;

import com.inventario.acreal.floatbutton.Models.Product;

import java.util.ArrayList;

/**
 * Created by amelara on 26/10/2017.
 */

public class prodTest {
    ArrayList<Product> products;

    public ArrayList<Product> getProducts(){
        products = new ArrayList<>();

        for (int i = 1; i<15; i++){
            products.add(new Product("Producto"+" "+i,"Nueva presentaciòn."));
        }

        return products;
    }
}
