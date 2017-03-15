package entidad;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ubicacion_usuario database table.
 * 
 */
@Entity
@Table(name="ubicacion_usuario")
@NamedQuery(name="UbicacionUsuario.findAll", query="SELECT u FROM UbicacionUsuario u")
public class UbicacionUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob
	private String descripcion;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private double latitud;

	private double longitud;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="login")
	private Usuario usuario;

	public UbicacionUsuario() {
	}
	
	

	public UbicacionUsuario(String descripcion, double latitud, double longitud, Usuario usuario) {

		this.descripcion = descripcion;
		this.latitud = latitud;
		this.fecha = new Date();
		this.longitud = longitud;
		this.usuario = usuario;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getLatitud() {
		return this.latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return this.longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}