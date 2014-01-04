/*
 * Copyright (C) 2014 Mathias Reppe <mathias.reppe@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.reppeitsolutions.formbuilder.model.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Form;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.NonexistentEntityException;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.PreexistingEntityException;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormJpaController implements Serializable {

    public FormJpaController(UserTransaction utx, EntityManager em) {
        this.utx = utx;
        this.em = em;
    }
    private UserTransaction utx = null;
    private EntityManager em = null;

    public void create(Form form) throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(form);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findForm(form.getId()) != null) {
                throw new PreexistingEntityException("Form " + form + " already exists.", ex);
            }
            throw ex;
        }
    }

    public void edit(Form form) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            form = em.merge(form);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = form.getId();
                if (findForm(id) == null) {
                    throw new NonexistentEntityException("The form with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Form form;
            try {
                form = em.getReference(Form.class, id);
                form.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The form with id " + id + " no longer exists.", enfe);
            }
            em.remove(form);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<Form> findFormEntities() {
        return findFormEntities(true, -1, -1);
    }

    public List<Form> findFormEntities(int maxResults, int firstResult) {
        return findFormEntities(false, maxResults, firstResult);
    }

    private List<Form> findFormEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Form.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Form findForm(Long id) {
        return em.find(Form.class, id);
    }

    public int getFormCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Form> rt = cq.from(Form.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
