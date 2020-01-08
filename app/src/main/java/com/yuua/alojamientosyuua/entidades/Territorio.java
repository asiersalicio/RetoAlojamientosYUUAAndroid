package com.yuua.alojamientosyuua.entidades;

import java.util.Set;



public class Territorio {
	public char[] id;

	public String nombre;

	private Set<Localizacion> tlocalizacion;

	public Territorio() {

	}
	

	public Territorio(char[] id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public Territorio recrearTerritorio() {
		return new Territorio(this.id, this.nombre);
	}
	

	public Set<Localizacion> getTlocalizacion() {
		return tlocalizacion;
	}

	public void setTlocalizacion(Set<Localizacion> tlocalizacion) {
		this.tlocalizacion = tlocalizacion;
	}

	public char[] getId() {
		return id;
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
