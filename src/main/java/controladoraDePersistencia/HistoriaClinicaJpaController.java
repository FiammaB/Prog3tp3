/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladoraDePersistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Paciente;
import Logica.DetalleHistoriaClinica;
import Logica.HistoriaClinica;
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
public class HistoriaClinicaJpaController implements Serializable {

    public HistoriaClinicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
 public HistoriaClinicaJpaController() {
         emf = Persistence.createEntityManagerFactory("UnidadPersistenciaPU");
    }
 
    public void create(HistoriaClinica historiaClinica) {
        if (historiaClinica.getDetalleHistoriasClinicas() == null) {
            historiaClinica.setDetalleHistoriasClinicas(new ArrayList<DetalleHistoriaClinica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente paciente = historiaClinica.getPaciente();
            if (paciente != null) {
                paciente = em.getReference(paciente.getClass(), paciente.getId());
                historiaClinica.setPaciente(paciente);
            }
            List<DetalleHistoriaClinica> attachedDetalleHistoriasClinicas = new ArrayList<DetalleHistoriaClinica>();
            for (DetalleHistoriaClinica detalleHistoriasClinicasDetalleHistoriaClinicaToAttach : historiaClinica.getDetalleHistoriasClinicas()) {
                detalleHistoriasClinicasDetalleHistoriaClinicaToAttach = em.getReference(detalleHistoriasClinicasDetalleHistoriaClinicaToAttach.getClass(), detalleHistoriasClinicasDetalleHistoriaClinicaToAttach.getIdDetalleHC());
                attachedDetalleHistoriasClinicas.add(detalleHistoriasClinicasDetalleHistoriaClinicaToAttach);
            }
            historiaClinica.setDetalleHistoriasClinicas(attachedDetalleHistoriasClinicas);
            em.persist(historiaClinica);
            if (paciente != null) {
                HistoriaClinica oldHistoriaClinicaOfPaciente = paciente.getHistoriaClinica();
                if (oldHistoriaClinicaOfPaciente != null) {
                    oldHistoriaClinicaOfPaciente.setPaciente(null);
                    oldHistoriaClinicaOfPaciente = em.merge(oldHistoriaClinicaOfPaciente);
                }
                paciente.setHistoriaClinica(historiaClinica);
                paciente = em.merge(paciente);
            }
            for (DetalleHistoriaClinica detalleHistoriasClinicasDetalleHistoriaClinica : historiaClinica.getDetalleHistoriasClinicas()) {
                HistoriaClinica oldHistoriaClinicaOfDetalleHistoriasClinicasDetalleHistoriaClinica = detalleHistoriasClinicasDetalleHistoriaClinica.getHistoriaClinica();
                detalleHistoriasClinicasDetalleHistoriaClinica.setHistoriaClinica(historiaClinica);
                detalleHistoriasClinicasDetalleHistoriaClinica = em.merge(detalleHistoriasClinicasDetalleHistoriaClinica);
                if (oldHistoriaClinicaOfDetalleHistoriasClinicasDetalleHistoriaClinica != null) {
                    oldHistoriaClinicaOfDetalleHistoriasClinicasDetalleHistoriaClinica.getDetalleHistoriasClinicas().remove(detalleHistoriasClinicasDetalleHistoriaClinica);
                    oldHistoriaClinicaOfDetalleHistoriasClinicasDetalleHistoriaClinica = em.merge(oldHistoriaClinicaOfDetalleHistoriasClinicasDetalleHistoriaClinica);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoriaClinica historiaClinica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriaClinica persistentHistoriaClinica = em.find(HistoriaClinica.class, historiaClinica.getIdHistoriClinica());
            Paciente pacienteOld = persistentHistoriaClinica.getPaciente();
            Paciente pacienteNew = historiaClinica.getPaciente();
            List<DetalleHistoriaClinica> detalleHistoriasClinicasOld = persistentHistoriaClinica.getDetalleHistoriasClinicas();
            List<DetalleHistoriaClinica> detalleHistoriasClinicasNew = historiaClinica.getDetalleHistoriasClinicas();
            if (pacienteNew != null) {
                pacienteNew = em.getReference(pacienteNew.getClass(), pacienteNew.getId());
                historiaClinica.setPaciente(pacienteNew);
            }
            List<DetalleHistoriaClinica> attachedDetalleHistoriasClinicasNew = new ArrayList<DetalleHistoriaClinica>();
            for (DetalleHistoriaClinica detalleHistoriasClinicasNewDetalleHistoriaClinicaToAttach : detalleHistoriasClinicasNew) {
                detalleHistoriasClinicasNewDetalleHistoriaClinicaToAttach = em.getReference(detalleHistoriasClinicasNewDetalleHistoriaClinicaToAttach.getClass(), detalleHistoriasClinicasNewDetalleHistoriaClinicaToAttach.getIdDetalleHC());
                attachedDetalleHistoriasClinicasNew.add(detalleHistoriasClinicasNewDetalleHistoriaClinicaToAttach);
            }
            detalleHistoriasClinicasNew = attachedDetalleHistoriasClinicasNew;
            historiaClinica.setDetalleHistoriasClinicas(detalleHistoriasClinicasNew);
            historiaClinica = em.merge(historiaClinica);
            if (pacienteOld != null && !pacienteOld.equals(pacienteNew)) {
                pacienteOld.setHistoriaClinica(null);
                pacienteOld = em.merge(pacienteOld);
            }
            if (pacienteNew != null && !pacienteNew.equals(pacienteOld)) {
                HistoriaClinica oldHistoriaClinicaOfPaciente = pacienteNew.getHistoriaClinica();
                if (oldHistoriaClinicaOfPaciente != null) {
                    oldHistoriaClinicaOfPaciente.setPaciente(null);
                    oldHistoriaClinicaOfPaciente = em.merge(oldHistoriaClinicaOfPaciente);
                }
                pacienteNew.setHistoriaClinica(historiaClinica);
                pacienteNew = em.merge(pacienteNew);
            }
            for (DetalleHistoriaClinica detalleHistoriasClinicasOldDetalleHistoriaClinica : detalleHistoriasClinicasOld) {
                if (!detalleHistoriasClinicasNew.contains(detalleHistoriasClinicasOldDetalleHistoriaClinica)) {
                    detalleHistoriasClinicasOldDetalleHistoriaClinica.setHistoriaClinica(null);
                    detalleHistoriasClinicasOldDetalleHistoriaClinica = em.merge(detalleHistoriasClinicasOldDetalleHistoriaClinica);
                }
            }
            for (DetalleHistoriaClinica detalleHistoriasClinicasNewDetalleHistoriaClinica : detalleHistoriasClinicasNew) {
                if (!detalleHistoriasClinicasOld.contains(detalleHistoriasClinicasNewDetalleHistoriaClinica)) {
                    HistoriaClinica oldHistoriaClinicaOfDetalleHistoriasClinicasNewDetalleHistoriaClinica = detalleHistoriasClinicasNewDetalleHistoriaClinica.getHistoriaClinica();
                    detalleHistoriasClinicasNewDetalleHistoriaClinica.setHistoriaClinica(historiaClinica);
                    detalleHistoriasClinicasNewDetalleHistoriaClinica = em.merge(detalleHistoriasClinicasNewDetalleHistoriaClinica);
                    if (oldHistoriaClinicaOfDetalleHistoriasClinicasNewDetalleHistoriaClinica != null && !oldHistoriaClinicaOfDetalleHistoriasClinicasNewDetalleHistoriaClinica.equals(historiaClinica)) {
                        oldHistoriaClinicaOfDetalleHistoriasClinicasNewDetalleHistoriaClinica.getDetalleHistoriasClinicas().remove(detalleHistoriasClinicasNewDetalleHistoriaClinica);
                        oldHistoriaClinicaOfDetalleHistoriasClinicasNewDetalleHistoriaClinica = em.merge(oldHistoriaClinicaOfDetalleHistoriasClinicasNewDetalleHistoriaClinica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = historiaClinica.getIdHistoriClinica();
                if (findHistoriaClinica(id) == null) {
                    throw new NonexistentEntityException("The historiaClinica with id " + id + " no longer exists.");
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
            HistoriaClinica historiaClinica;
            try {
                historiaClinica = em.getReference(HistoriaClinica.class, id);
                historiaClinica.getIdHistoriClinica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaClinica with id " + id + " no longer exists.", enfe);
            }
            Paciente paciente = historiaClinica.getPaciente();
            if (paciente != null) {
                paciente.setHistoriaClinica(null);
                paciente = em.merge(paciente);
            }
            List<DetalleHistoriaClinica> detalleHistoriasClinicas = historiaClinica.getDetalleHistoriasClinicas();
            for (DetalleHistoriaClinica detalleHistoriasClinicasDetalleHistoriaClinica : detalleHistoriasClinicas) {
                detalleHistoriasClinicasDetalleHistoriaClinica.setHistoriaClinica(null);
                detalleHistoriasClinicasDetalleHistoriaClinica = em.merge(detalleHistoriasClinicasDetalleHistoriaClinica);
            }
            em.remove(historiaClinica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistoriaClinica> findHistoriaClinicaEntities() {
        return findHistoriaClinicaEntities(true, -1, -1);
    }

    public List<HistoriaClinica> findHistoriaClinicaEntities(int maxResults, int firstResult) {
        return findHistoriaClinicaEntities(false, maxResults, firstResult);
    }

    private List<HistoriaClinica> findHistoriaClinicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriaClinica.class));
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

    public HistoriaClinica findHistoriaClinica(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriaClinica.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriaClinicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriaClinica> rt = cq.from(HistoriaClinica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
