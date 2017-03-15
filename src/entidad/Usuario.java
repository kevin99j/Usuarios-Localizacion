package entidad;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String login;

	private String clave;

	private String nombre;

	//bi-directional many-to-one association to UbicacionUsuario
	@OneToMany(mappedBy="usuario")
	private List<UbicacionUsuario> ubicacionUsuarios;

	public Usuario() {
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<UbicacionUsuario> getUbicacionUsuarios() {
		return this.ubicacionUsuarios;
	}

	public void setUbicacionUsuarios(List<UbicacionUsuario> ubicacionUsuarios) {
		this.ubicacionUsuarios = ubicacionUsuarios;
	}

	public UbicacionUsuario addUbicacionUsuario(UbicacionUsuario ubicacionUsuario) {
		getUbicacionUsuarios().add(ubicacionUsuario);
		ubicacionUsuario.setUsuario(this);

		return ubicacionUsuario;
	}

	public UbicacionUsuario removeUbicacionUsuario(UbicacionUsuario ubicacionUsuario) {
		getUbicacionUsuarios().remove(ubicacionUsuario);
		ubicacionUsuario.setUsuario(null);

		return ubicacionUsuario;
	}

}