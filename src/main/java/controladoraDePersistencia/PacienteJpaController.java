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
import Logica.HistoriaClinica;
import Logica.Paciente;
import Logica.Turno;
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
public class PacienteJpaController implements Serializable {

    public PacienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
public PacienteJpaController() {
        emf = Persistence.createEntityManagerFactory("UnidadPersistenciaPU");
    }
    public void create(Paciente paciente) {
        if (paciente.getTurnos() == null) {
            paciente.setTurnos(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriaClinica historiaClinica = paciente.getHistoriaClinica();
            if (historiaClinica != null) {
                historiaClinica = em.getReference(historiaClinica.getClass(), historiaClinica.getIdHistoriClinica());
                paciente.setHistoriaClinica(historiaClinica);
            }
            List<Turno> attachedTurnos = new ArrayList<Turno>();
            for (Turno turnosTurnoToAttach : paciente.getTurnos()) {
                turnosTurnoToAttach = em.getReference(turnosTurnoToAttach.getClass(), turnosTurnoToAttach.getIdTurno());
                attachedTurnos.add(turnosTurnoToAttach);
            }
            paciente.setTurnos(attachedTurnos);
            em.persist(paciente);
            if (historiaClinica != null) {
                Paciente oldPacienteOfHistoriaClinica = historiaClinica.getPaciente();
                if (oldPacienteOfHistoriaClinica != null) {
                    oldPacienteOfHistoriaClinica.setHistoriaClinica(null);
                    oldPacienteOfHistoriaClinica = em.merge(oldPacienteOfHistoriaClinica);
                }
                historiaClinica.setPaciente(paciente);
                historiaClinica = em.merge(historiaClinica);
            }
            for (Turno turnosTurno : paciente.getTurnos()) {
                Paciente oldPacienteOfTurnosTurno = turnosTurno.getPaciente();
                turnosTurno.setPaciente(paciente);
                turnosTurno = em.merge(turnosTurno);
                if (oldPacienteOfTurnosTurno != null) {
                    oldPacienteOfTurnosTurno.getTurnos().remove(turnosTurno);
                    oldPacienteOfTurnosTurno = em.merge(oldPacienteOfTurnosTurno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paciente paciente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente persistentPaciente = em.find(Paciente.class, paciente.getId());
            HistoriaClinica historiaClinicaOld = persistentPaciente.getHistoriaClinica();
            HistoriaClinica historiaClinicaNew = paciente.getHistoriaClinica();
            List<Turno> turnosOld = persistentPaciente.getTurnos();
            List<Turno> turnosNew = paciente.getTurnos();
            if (historiaClinicaNew != null) {
                historiaClinicaNew = em.getReference(historiaClinicaNew.getClass(), historiaClinicaNew.getIdHistoriClinica());
                paciente.setHistoriaClinica(historiaClinicaNew);
            }
            List<Turno> attachedTurnosNew = new ArrayList<Turno>();
            for (Turno turnosNewTurnoToAttach : turnosNew) {
                turnosNewTurnoToAttach = em.getReference(turnosNewTurnoToAttach.getClass(), turnosNewTurnoToAttach.getIdTurno());
                attachedTurnosNew.add(turnosNewTurnoToAttach);
            }
            turnosNew = attachedTurnosNew;
            paciente.setTurnos(turnosNew);
            paciente = em.merge(paciente);
            if (historiaClinicaOld != null && !historiaClinicaOld.equals(historiaClinicaNew)) {
                historiaClinicaOld.setPaciente(null);
                historiaClinicaOld = em.merge(historiaClinicaOld);
            }
            if (historiaClinicaNew != null && !historiaClinicaNew.equals(historiaClinicaOld)) {
                Paciente oldPacienteOfHistoriaClinica = historiaClinicaNew.getPaciente();
                if (oldPacienteOfHistoriaClinica != null) {
                    oldPacienteOfHistoriaClinica.setHistoriaClinica(null);
                    oldPacienteOfHistoriaClinica = em.merge(oldPacienteOfHistoriaClinica);
                }
                historiaClinicaNew.setPaciente(paciente);
                historiaClinicaNew = em.merge(historiaClinicaNew);
            }
            for (Turno turnosOldTurno : turnosOld) {
                if (!turnosNew.contains(turnosOldTurno)) {
                    turnosOldTurno.setPaciente(null);
                    turnosOldTurno = em.merge(turnosOldTurno);
                }
            }
            for (Turno turnosNewTurno : turnosNew) {
                if (!turnosOld.contains(turnosNewTurno)) {
                    Paciente oldPacienteOfTurnosNewTurno = turnosNewTurno.getPaciente();
                    turnosNewTurno.setPaciente(paciente);
                    turnosNewTurno = em.merge(turnosNewTurno);
                    if (oldPacienteOfTurnosNewTurno != null && !oldPacienteOfTurnosNewTurno.equals(paciente)) {
                        oldPacienteOfTurnosNewTurno.getTurnos().remove(turnosNewTurno);
                        oldPacienteOfTurnosNewTurno = em.merge(oldPacienteOfTurnosNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = paciente.getId();
                if (findPaciente(id) == null) {
                    throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.");
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
            Paciente paciente;
            try {
                paciente = em.getReference(Paciente.class, id);
                paciente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.", enfe);
            }
            HistoriaClinica historiaClinica = paciente.getHistoriaClinica();
            if (historiaClinica != null) {
                historiaClinica.setPaciente(null);
                historiaClinica = em.merge(historiaClinica);
            }
            List<Turno> turnos = paciente.getTurnos();
            for (Turno turnosTurno : turnos) {
                turnosTurno.setPaciente(null);
                turnosTurno = em.merge(turnosTurno);
            }
            em.remove(paciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paciente> findPacienteEntities() {
        return findPacienteEntities(true, -1, -1);
    }

    public List<Paciente> findPacienteEntities(int maxResults, int firstResult) {
        return findPacienteEntities(false, maxResults, firstResult);
    }

    private List<Paciente> findPacienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paciente.class));
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

    public Paciente findPaciente(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paciente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paciente> rt = cq.from(Paciente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
