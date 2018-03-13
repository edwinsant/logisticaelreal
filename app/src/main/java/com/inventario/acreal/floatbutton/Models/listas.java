package com.inventario.acreal.floatbutton.Models;

public class listas {
	String nombre;
	 String puntos;
	 Integer image_id;
	String color;
	 public listas()
	 {
		 super();
	 }
	 public listas(String nom, String puntos, String color)
	 {
		 super();
		 this.color = color;
		 this.nombre = nom;
		 this.puntos = puntos;
	 }
	 public void setima(Integer imag)
	 {
		 this.image_id = imag;
	 }
	 public void setnombre(String nombre)
	 {
		 this.nombre = nombre;
	 }
	 public void setpuntos(String puntos)
	 {
		 this.puntos = puntos;
	 }
	 public void setColor(String color){ this.color = color;}
	 
	 public Integer getima()
	 {
		return this.image_id;
	 }
	 public String Getnombre()
	 {
		return this.nombre; 
	 }
	 public String getpuntos()
	 {
		return this.puntos;
	 }
	 public String getColor(){ return this.color;}
}
