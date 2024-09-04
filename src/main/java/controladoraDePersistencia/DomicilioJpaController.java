/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladoraDePersistencia;

import Logica.Domicilio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Paciente;
import controladoraDePersistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author brizu
 */
public class DomicilioJpaController implements Serializable {

    public DomicilioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
      public DomicilioJpaController() {
        emf = Persistence.createEntityManagerFactory("UnidadPersistenciaPU");
    }

    public void create(Domicilio domicilio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente paciente = domicilio.getPaciente();
            if (paciente != null) {
                paciente = em.getReference(paciente.getClass(), paciente.getId());
                domicilio.setPaciente(paciente);
            }
            em.persist(domicilio);
            if (paciente != null) {
                Domicilio oldDomicilioOfPaciente = paciente.getDomicilio();
                if (oldDomicilioOfPaciente != null) {
                    oldDomicilioOfPaciente.setPaciente(null);
                    oldDomicilioOfPaciente = em.merge(oldDomicilioOfPaciente);
                }
                paciente.setDomicilio(domicilio);
                paciente = em.merge(paciente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Domicilio domicilio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Domicilio persistentDomicilio = em.find(Domicilio.class, domicilio.getId());
            Paciente pacienteOld = persistentDomicilio.getPaciente();
            Paciente pacienteNew = domicilio.getPaciente();
            if (pacienteNew != null) {
                pacienteNew = em.getReference(pacienteNew.getClass(), pacienteNew.getId());
                domicilio.setPaciente(pacienteNew);
            }
            domicilio = em.merge(domicilio);
            if (pacienteOld != null && !pacienteOld.equals(pacienteNew)) {
                pacienteOld.setDomicilio(null);
                pacienteOld = em.merge(pacienteOld);
            }
            if (pacienteNew != null && !pacienteNew.equals(pacienteOld)) {
                Domicilio oldDomicilioOfPaciente = pacienteNew.getDomicilio();
                if (oldDomicilioOfPaciente != null) {
                    oldDomicilioOfPaciente.setPaciente(null);
                    oldDomicilioOfPaciente = em.merge(oldDomicilioOfPaciente);
                }
                pacienteNew.setDomicilio(domicilio);
                pacienteNew = em.merge(pacienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = domicilio.getId();
                if (findDomicilio(id) == null) {
                    throw new NonexistentEntityException("The domicilio with id " + id + " no longer exists.");
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
            Domicilio domicilio;
            try {
                domicilio = em.getReference(Domicilio.class, id);
                domicilio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The domicilio with id " + id + " no longer exists.", enfe);
            }
            Paciente paciente = domicilio.getPaciente();
            if (paciente != null) {
                paciente.setDomicilio(null);
                paciente = em.merge(paciente);
            }
            em.remove(domicilio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Domicilio> findDomicilioEntities() {
        return findDomicilioEntities(true, -1, -1);
    }

    public List<Domicilio> findDomicilioEntities(int maxResults, int firstResult) {
        return findDomicilioEntities(false, maxResults, firstResult);
    }

    private List<Domicilio> findDomicilioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Domicilio.class));
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

    public Domicilio findDomicilio(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Domicilio.class, id);
        } finally {
            em.close();
        }
    }

    public int getDomicilioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Domicilio> rt = cq.from(Domicilio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
