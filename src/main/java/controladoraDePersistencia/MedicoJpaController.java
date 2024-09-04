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
import Logica.Turno;
import java.util.ArrayList;
import java.util.List;
import Logica.Especialidad;
import Logica.Medico;
import controladoraDePersistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author brizu
 */
public class MedicoJpaController implements Serializable {

    public MedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
public MedicoJpaController() {
        emf = Persistence.createEntityManagerFactory("UnidadPersistenciaPU");
    }
    public void create(Medico medico) {
        if (medico.getTurnos() == null) {
            medico.setTurnos(new ArrayList<Turno>());
        }
        if (medico.getEspecialidades() == null) {
            medico.setEspecialidades(new ArrayList<Especialidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Turno> attachedTurnos = new ArrayList<Turno>();
            for (Turno turnosTurnoToAttach : medico.getTurnos()) {
                turnosTurnoToAttach = em.getReference(turnosTurnoToAttach.getClass(), turnosTurnoToAttach.getIdTurno());
                attachedTurnos.add(turnosTurnoToAttach);
            }
            medico.setTurnos(attachedTurnos);
            List<Especialidad> attachedEspecialidades = new ArrayList<Especialidad>();
            for (Especialidad especialidadesEspecialidadToAttach : medico.getEspecialidades()) {
                especialidadesEspecialidadToAttach = em.getReference(especialidadesEspecialidadToAttach.getClass(), especialidadesEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidades.add(especialidadesEspecialidadToAttach);
            }
            medico.setEspecialidades(attachedEspecialidades);
            em.persist(medico);
            for (Turno turnosTurno : medico.getTurnos()) {
                Medico oldMedicoOfTurnosTurno = turnosTurno.getMedico();
                turnosTurno.setMedico(medico);
                turnosTurno = em.merge(turnosTurno);
                if (oldMedicoOfTurnosTurno != null) {
                    oldMedicoOfTurnosTurno.getTurnos().remove(turnosTurno);
                    oldMedicoOfTurnosTurno = em.merge(oldMedicoOfTurnosTurno);
                }
            }
            for (Especialidad especialidadesEspecialidad : medico.getEspecialidades()) {
                especialidadesEspecialidad.getMedicos().add(medico);
                especialidadesEspecialidad = em.merge(especialidadesEspecialidad);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medico medico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medico persistentMedico = em.find(Medico.class, medico.getId());
            List<Turno> turnosOld = persistentMedico.getTurnos();
            List<Turno> turnosNew = medico.getTurnos();
            List<Especialidad> especialidadesOld = persistentMedico.getEspecialidades();
            List<Especialidad> especialidadesNew = medico.getEspecialidades();
            List<Turno> attachedTurnosNew = new ArrayList<Turno>();
            for (Turno turnosNewTurnoToAttach : turnosNew) {
                turnosNewTurnoToAttach = em.getReference(turnosNewTurnoToAttach.getClass(), turnosNewTurnoToAttach.getIdTurno());
                attachedTurnosNew.add(turnosNewTurnoToAttach);
            }
            turnosNew = attachedTurnosNew;
            medico.setTurnos(turnosNew);
            List<Especialidad> attachedEspecialidadesNew = new ArrayList<Especialidad>();
            for (Especialidad especialidadesNewEspecialidadToAttach : especialidadesNew) {
                especialidadesNewEspecialidadToAttach = em.getReference(especialidadesNewEspecialidadToAttach.getClass(), especialidadesNewEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidadesNew.add(especialidadesNewEspecialidadToAttach);
            }
            especialidadesNew = attachedEspecialidadesNew;
            medico.setEspecialidades(especialidadesNew);
            medico = em.merge(medico);
            for (Turno turnosOldTurno : turnosOld) {
                if (!turnosNew.contains(turnosOldTurno)) {
                    turnosOldTurno.setMedico(null);
                    turnosOldTurno = em.merge(turnosOldTurno);
                }
            }
            for (Turno turnosNewTurno : turnosNew) {
                if (!turnosOld.contains(turnosNewTurno)) {
                    Medico oldMedicoOfTurnosNewTurno = turnosNewTurno.getMedico();
                    turnosNewTurno.setMedico(medico);
                    turnosNewTurno = em.merge(turnosNewTurno);
                    if (oldMedicoOfTurnosNewTurno != null && !oldMedicoOfTurnosNewTurno.equals(medico)) {
                        oldMedicoOfTurnosNewTurno.getTurnos().remove(turnosNewTurno);
                        oldMedicoOfTurnosNewTurno = em.merge(oldMedicoOfTurnosNewTurno);
                    }
                }
            }
            for (Especialidad especialidadesOldEspecialidad : especialidadesOld) {
                if (!especialidadesNew.contains(especialidadesOldEspecialidad)) {
                    especialidadesOldEspecialidad.getMedicos().remove(medico);
                    especialidadesOldEspecialidad = em.merge(especialidadesOldEspecialidad);
                }
            }
            for (Especialidad especialidadesNewEspecialidad : especialidadesNew) {
                if (!especialidadesOld.contains(especialidadesNewEspecialidad)) {
                    especialidadesNewEspecialidad.getMedicos().add(medico);
                    especialidadesNewEspecialidad = em.merge(especialidadesNewEspecialidad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = medico.getId();
                if (findMedico(id) == null) {
                    throw new NonexistentEntityException("The medico with id " + id + " no longer exists.");
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
            Medico medico;
            try {
                medico = em.getReference(Medico.class, id);
                medico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medico with id " + id + " no longer exists.", enfe);
            }
            List<Turno> turnos = medico.getTurnos();
            for (Turno turnosTurno : turnos) {
                turnosTurno.setMedico(null);
                turnosTurno = em.merge(turnosTurno);
            }
            List<Especialidad> especialidades = medico.getEspecialidades();
            for (Especialidad especialidadesEspecialidad : especialidades) {
                especialidadesEspecialidad.getMedicos().remove(medico);
                especialidadesEspecialidad = em.merge(especialidadesEspecialidad);
            }
            em.remove(medico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medico> findMedicoEntities() {
        return findMedicoEntities(true, -1, -1);
    }

    public List<Medico> findMedicoEntities(int maxResults, int firstResult) {
        return findMedicoEntities(false, maxResults, firstResult);
    }

    private List<Medico> findMedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medico.class));
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

    public Medico findMedico(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medico.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medico> rt = cq.from(Medico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
