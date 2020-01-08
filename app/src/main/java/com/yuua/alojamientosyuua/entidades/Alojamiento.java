package com.yuua.alojamientosyuua.entidades;

public class Alojamiento {

	int id;
	String tipo;
	String nombre;
	String descripcion;
	int telefono;
	String web;
	String email;
	int capacidad;

	Localizacion localizacion;

	protected Alojamiento() {
	}

	public Alojamiento(String tipo, String nombre, String descripcion, int telefono, String web, String email, int capacidad, Localizacion localizacion) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.telefono = telefono;
		this.web = web;
		this.email = email;
		this.capacidad = capacidad;
		this.localizacion = localizacion;
	}

	

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
