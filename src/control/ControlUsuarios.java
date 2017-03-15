package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import entidad.UbicacionUsuario;
import entidad.Usuario;

public class ControlUsuarios implements Serializable {

	private static final long serialVersionUID = -2152389656664659476L;
	private EntityManagerFactory emf;
	private EntityManager em;

	private String nombre;
	private String clave;
	private boolean logeado = false;

	private MapModel emptyModel;
	private String title;
	private double lat;
	private double lng;

	private Usuario usuario;
	private UbicacionUsuario ubicacion;

	private List<Usuario> usuarios;
	private List<UbicacionUsuario> ubicaciones;

	public ControlUsuarios() {

		emf = Persistence.createEntityManagerFactory("UsuariosLocalizacion");
		em = emf.createEntityManager();
		emptyModel = new DefaultMapModel();
		
		this.usuario = new Usuario();
		this.ubicacion = new UbicacionUsuario();
		this.usuarios = cargarUsuarios();
		this.ubicaciones = cargarUbicaciones();
	}

	public boolean comprobarClave(String login, String clave) {
		boolean comprobar = false;
		for (Usuario usuario : this.usuarios) {
			if (usuario.getLogin().equals(login) && usuario.getClave().equals(clave)) {
				comprobar = true;
				this.usuario = usuario;
			}
		}
		return comprobar;
	}

	public void login(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		if (nombre != null && clave != null && this.comprobarClave(nombre, clave)) {
			logeado = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.usuario.getNombre());
		} else {
			logeado = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no válidas");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("estaLogeado", logeado);
		if (logeado)
			context.addCallbackParam("view", "paginas/mapa.xhtml");
	}

	public void logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		logeado = false;
	}

	public List<Usuario> cargarUsuarios() {
		List<Usuario> tempUsuarios;
		String jpql = " SELECT cat FROM Usuario cat";
		Query query = em.createQuery(jpql);
		tempUsuarios = query.getResultList();
		return tempUsuarios;
	}

	public List<UbicacionUsuario> cargarUbicaciones() {
		List<UbicacionUsuario> tempUbicaciones = new ArrayList<UbicacionUsuario>();
		if (this.usuario != null) {
			String jpql = " SELECT cat FROM UbicacionUsuario cat WHERE cat.usuario = :usuario";
			Query query = em.createQuery(jpql);
			query.setParameter("usuario", this.usuario);
			tempUbicaciones = query.getResultList();
		}
		for (UbicacionUsuario ubicacionUsuario : tempUbicaciones) {
			Marker marker = new Marker(new LatLng(ubicacionUsuario.getLatitud(), ubicacionUsuario.getLongitud()), ubicacionUsuario.getDescripcion());
			emptyModel.addOverlay(marker);
		}
		return tempUbicaciones;
	}



	public void addMarker() {
		Marker marker = new Marker(new LatLng(lat, lng), title);
		UbicacionUsuario ubicacion = new UbicacionUsuario(title, lat, lng, this.usuario);

		this.em.getTransaction().begin();
		this.em.persist(ubicacion);
		this.em.getTransaction().commit();

		ubicaciones.add(ubicacion);
		emptyModel.addOverlay(marker);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
		this.lat = 0;
		this.lng = 0;
		this.title = null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UbicacionUsuario getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionUsuario ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UbicacionUsuario> getUbicaciones() {
		return ubicaciones;
	}

	public void setUbicaciones(List<UbicacionUsuario> ubicaciones) {
		this.ubicaciones = ubicaciones;
	}

	public MapModel getEmptyModel() {
		this.ubicaciones = this.cargarUbicaciones();
		return emptyModel;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public boolean estaLogeado() {
		return logeado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
