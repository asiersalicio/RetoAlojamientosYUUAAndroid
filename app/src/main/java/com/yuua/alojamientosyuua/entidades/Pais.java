package com.yuua.alojamientosyuua.entidades;

import java.io.Serializable;
import java.util.Set;

public class Pais implements Serializable {
	public char[] id;
	public String nombre;
	private transient Set<Localizacion> tlocalizacion;

	public Pais() { }

	public char[] getId() {
		return id;
	}

	public Pais(char[] id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Pais recrearPais() {
		return new Pais(this.id, this.nombre);
	}

	public void setId(char[] id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
