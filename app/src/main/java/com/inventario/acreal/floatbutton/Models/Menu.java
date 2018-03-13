package com.inventario.acreal.floatbutton.Models;

/**
 * Created by amelara on 31/10/2017.
 */

public class Menu {
    public int Id;
    public String Name;
    public int Image;

    public Menu(int id, String name, int image) {
        Id = id;
        Name = name;
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
