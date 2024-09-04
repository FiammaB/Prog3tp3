/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladoraDePersistencia;

import Logica.DetalleHistoriaClinica;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.HistoriaClinica;
import controladoraDePersistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author brizu
 */
public class DetalleHistoriaClinicaJpaController implements Serializable {

    public DetalleHistoriaClinicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DetalleHistoriaClinicaJpaController() {
         emf = Persistence.createEntityManagerFactory("UnidadPersistenciaPU");
    }
    

    public void create(DetalleHistoriaClinica detalleHistoriaClinica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriaClinica historiaClinica = detalleHistoriaClinica.getHistoriaClinica();
            if (historiaClinica != null) {
                historiaClinica = em.getReference(historiaClinica.getClass(), historiaClinica.getIdHistoriClinica());
                detalleHistoriaClinica.setHistoriaClinica(historiaClinica);
            }
            em.persist(detalleHistoriaClinica);
            if (historiaClinica != null) {
                historiaClinica.getDetalleHistoriasClinicas().add(detalleHistoriaClinica);
                historiaClinica = em.merge(historiaClinica);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleHistoriaClinica detalleHistoriaClinica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleHistoriaClinica persistentDetalleHistoriaClinica = em.find(DetalleHistoriaClinica.class, detalleHistoriaClinica.getIdDetalleHC());
            HistoriaClinica historiaClinicaOld = persistentDetalleHistoriaClinica.getHistoriaClinica();
            HistoriaClinica historiaClinicaNew = detalleHistoriaClinica.getHistoriaClinica();
            if (historiaClinicaNew != null) {
                historiaClinicaNew = em.getReference(historiaClinicaNew.getClass(), historiaClinicaNew.getIdHistoriClinica());
                detalleHistoriaClinica.setHistoriaClinica(historiaClinicaNew);
            }
            detalleHistoriaClinica = em.merge(detalleHistoriaClinica);
            if (historiaClinicaOld != null && !historiaClinicaOld.equals(historiaClinicaNew)) {
                historiaClinicaOld.getDetalleHistoriasClinicas().remove(detalleHistoriaClinica);
                historiaClinicaOld = em.merge(historiaClinicaOld);
            }
            if (historiaClinicaNew != null && !historiaClinicaNew.equals(historiaClinicaOld)) {
                historiaClinicaNew.getDetalleHistoriasClinicas().add(detalleHistoriaClinica);
                historiaClinicaNew = em.merge(historiaClinicaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = detalleHistoriaClinica.getIdDetalleHC();
                if (findDetalleHistoriaClinica(id) == null) {
                    throw new NonexistentEntityException("The detalleHistoriaClinica with id " + id + " no longer exists.");
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
            DetalleHistoriaClinica detalleHistoriaClinica;
            try {
                detalleHistoriaClinica = em.getReference(DetalleHistoriaClinica.class, id);
                detalleHistoriaClinica.getIdDetalleHC();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleHistoriaClinica with id " + id + " no longer exists.", enfe);
            }
            HistoriaClinica historiaClinica = detalleHistoriaClinica.getHistoriaClinica();
            if (historiaClinica != null) {
                historiaClinica.getDetalleHistoriasClinicas().remove(detalleHistoriaClinica);
                historiaClinica = em.merge(historiaClinica);
            }
            em.remove(detalleHistoriaClinica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleHistoriaClinica> findDetalleHistoriaClinicaEntities() {
        return findDetalleHistoriaClinicaEntities(true, -1, -1);
    }

    public List<DetalleHistoriaClinica> findDetalleHistoriaClinicaEntities(int maxResults, int firstResult) {
        return findDetalleHistoriaClinicaEntities(false, maxResults, firstResult);
    }

    private List<DetalleHistoriaClinica> findDetalleHistoriaClinicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleHistoriaClinica.class));
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

    public DetalleHistoriaClinica findDetalleHistoriaClinica(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleHistoriaClinica.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleHistoriaClinicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleHistoriaClinica> rt = cq.from(DetalleHistoriaClinica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
