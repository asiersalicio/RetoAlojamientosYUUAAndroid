package com.yuua.alojamientosyuua.entidades;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {

	private int id;
	private Date fechaEntrada;
	private Date fechaSalida;
    private Alojamiento alojamiento;
    private Usuario usuario;
	
	protected Reserva() {
		
	}

	public Reserva(int id, Date fechaEntrada, Date fechaSalida, Alojamiento alojamiento, Usuario usuario) {
		super();
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.alojamiento = alojamiento;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Alojamiento getAlojamiento() {
		return alojamiento;
	}

	public void setAlojamiento(Alojamiento alojamiento) {
		this.alojamiento = alojamiento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
