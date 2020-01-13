package com.yuua.alojamientosyuua.entidades;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;


public class Usuario implements Serializable {

	private String idDni;
	private String nombre;
	private String apellidos;
	private String tipoUsuario;
	private String nombreUsuario;
	private String contrasena;
	private Date fechaNacimiento;
	private String correo;
	private long telefono;
	private Uri imageUrl;

	protected Usuario() {

	}

	public Usuario(String idDni, String nombre, String apellidos, String tipoUsuario, String nombreUsuario, String contrasena, Date fechaNacimiento, String correo, long telefono,Uri imageUrl) {
		super();
		this.idDni = idDni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.tipoUsuario = tipoUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.correo = correo;
		this.telefono = telefono;
		this.imageUrl=imageUrl;
	}

	public String getIdDni() {
		return idDni;
	}

	public void setIdDni(String idDni) {
		this.idDni = idDni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public void setImageUrl(Uri imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Uri getImageUrl() {
		return imageUrl;
	}

}
