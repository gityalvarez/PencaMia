package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import beans.interfaces.GrupoPersistenceLocal;
import beans.interfaces.GrupoPersistenceRemote;
import entidades.Fase;
import entidades.Grupo;
import entidades.Torneo;

/**
 * Session Bean implementation class GrupoPersistence
 */
@Stateless
public class GrupoPersistence implements GrupoPersistenceRemote, GrupoPersistenceLocal {

	@PersistenceContext(unitName = "PencaUyCentral-persistencia")
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public GrupoPersistence() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crearGrupo(String nombre, int fase) {
		if (em.createQuery("Select d from " + Grupo.class.getSimpleName() + " d where d.nombre = '" + nombre
				+ "' and d.fase =" + fase).getResultList().isEmpty()) {
			Grupo g = new Grupo();
			Fase f = em.find(Fase.class, fase);
			g.setFase(f);
			g.setNombre(nombre);
			em.persist(g);
			return true;
		} else {
			System.out.println("Ya existe un grupo con ese nombre");
			return false;
		}

	}

	public Grupo obtenerGrupo(int id) {

		return em.find(Grupo.class, id);
	}

	@SuppressWarnings("unchecked")
	public Grupo obtenerGrupoPorNombreYFase(String nombre, int id) {
		List<Grupo> list = new ArrayList<>();
		list = (List<Grupo>) em.createQuery("Select g from " + Grupo.class.getSimpleName()+ " g where g.fase = " + id + " and g.nombre ='" + nombre + "'").getResultList();
		if (!(list.isEmpty())) {
			return list.get(0);
		} else
			return null;
	}
	
    @SuppressWarnings("unchecked")
    public List<Grupo> obtenerGruposPorFase(int id){
    	List<Grupo> list= new ArrayList();
    	list =(List<Grupo>)em.createQuery( "Select g from "+ Grupo.class.getSimpleName()+" g where g.fase = "+id ).getResultList();
    	return list;
    }
    
    @Override
	public boolean eliminarGrupo(int id) {
    	System.out.println("voy a buscar el grupo a eliminar");
		Grupo g = em.find(Grupo.class, id);
		System.out.println("Encontre el grupo "+g.getNombre());
		em.remove(g);
		System.out.println("pude eliminar");
		return true;
	}

}
