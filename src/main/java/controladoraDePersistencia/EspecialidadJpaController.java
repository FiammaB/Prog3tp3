/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladoraDePersistencia;

import Logica.Especialidad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Medico;
import controladoraDePersistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author brizu
 */
public class EspecialidadJpaController implements Serializable {

    public EspecialidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public EspecialidadJpaController() {
        emf = Persistence.createEntityManagerFactory("UnidadPersistenciaPU");
    }
    public void create(Especialidad especialidad) {
        if (especialidad.getMedicos() == null) {
            especialidad.setMedicos(new ArrayList<Medico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Medico> attachedMedicos = new ArrayList<Medico>();
            for (Medico medicosMedicoToAttach : especialidad.getMedicos()) {
                medicosMedicoToAttach = em.getReference(medicosMedicoToAttach.getClass(), medicosMedicoToAttach.getId());
                attachedMedicos.add(medicosMedicoToAttach);
            }
            especialidad.setMedicos(attachedMedicos);
            em.persist(especialidad);
            for (Medico medicosMedico : especialidad.getMedicos()) {
                medicosMedico.getEspecialidades().add(especialidad);
                medicosMedico = em.merge(medicosMedico);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especialidad especialidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidad persistentEspecialidad = em.find(Especialidad.class, especialidad.getIdEspecialidad());
            List<Medico> medicosOld = persistentEspecialidad.getMedicos();
            List<Medico> medicosNew = especialidad.getMedicos();
            List<Medico> attachedMedicosNew = new ArrayList<Medico>();
            for (Medico medicosNewMedicoToAttach : medicosNew) {
                medicosNewMedicoToAttach = em.getReference(medicosNewMedicoToAttach.getClass(), medicosNewMedicoToAttach.getId());
                attachedMedicosNew.add(medicosNewMedicoToAttach);
            }
            medicosNew = attachedMedicosNew;
            especialidad.setMedicos(medicosNew);
            especialidad = em.merge(especialidad);
            for (Medico medicosOldMedico : medicosOld) {
                if (!medicosNew.contains(medicosOldMedico)) {
                    medicosOldMedico.getEspecialidades().remove(especialidad);
                    medicosOldMedico = em.merge(medicosOldMedico);
                }
            }
            for (Medico medicosNewMedico : medicosNew) {
                if (!medicosOld.contains(medicosNewMedico)) {
                    medicosNewMedico.getEspecialidades().add(especialidad);
                    medicosNewMedico = em.merge(medicosNewMedico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = especialidad.getIdEspecialidad();
                if (findEspecialidad(id) == null) {
                    throw new NonexistentEntityException("The especialidad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidad especialidad;
            try {
                especialidad = em.getReference(Especialidad.class, id);
                especialidad.getIdEspecialidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especialidad with id " + id + " no longer exists.", enfe);
            }
            List<Medico> medicos = especialidad.getMedicos();
            for (Medico medicosMedico : medicos) {
                medicosMedico.getEspecialidades().remove(especialidad);
                medicosMedico = em.merge(medicosMedico);
            }
            em.remove(especialidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especialidad> findEspecialidadEntities() {
        return findEspecialidadEntities(true, -1, -1);
    }

    public List<Especialidad> findEspecialidadEntities(int maxResults, int firstResult) {
        return findEspecialidadEntities(false, maxResults, firstResult);
    }

    private List<Especialidad> findEspecialidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especialidad.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Especialidad findEspecialidad(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especialidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspecialidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especialidad> rt = cq.from(Especialidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
