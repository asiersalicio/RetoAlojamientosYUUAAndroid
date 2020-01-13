package com.yuua.alojamientosyuua.entidades;

import java.io.Serializable;
import java.util.Set;

public class Municipio implements Serializable {
	public char[] id;
	public String nombre;
	private transient Set<Localizacion> tlocalizacion;

	public Municipio() { }

	public Municipio(char[] id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Municipio recrearMunicipio() {
		return new Municipio(this.id, this.nombre);
	}

	public char[] getId() {
		return id;
	}

	public Set<Localizacion> getTlocalizacion() {
		return tlocalizacion;
	}

	public void setTlocalizacion(Set<Localizacion> tlocalizacion) {
		this.tlocalizacion = tlocalizacion;
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
