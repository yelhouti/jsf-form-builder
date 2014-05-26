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

import at.reppeitsolutions.formbuilder.model.FormData;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.NonexistentEntityException;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.PreexistingEntityException;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormDataJpaController implements Serializable {

    public FormDataJpaController(UserTransaction utx, EntityManager em) {
        this.utx = utx;
        this.em = em;
    }
    
    private UserTransaction utx = null;
    private EntityManager em = null;

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(FormData formData) throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(formData);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFormData(formData.getId()) != null) {
                throw new PreexistingEntityException("FormData " + formData + " already exists.", ex);
            }
            throw ex;
        }
    }

    public void edit(FormData formData) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            formData = em.merge(formData);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = formData.getId();
                if (findFormData(id) == null) {
                    throw new NonexistentEntityException("The form with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            FormData formData;
            try {
                formData = em.getReference(FormData.class, id);
                formData.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formData with id " + id + " no longer exists.", enfe);
            }
            em.remove(formData);
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

    public List<FormData> findFormDataEntities() {
        return findFormDataEntities(true, -1, -1);
    }

    public List<FormData> findFormDataEntities(int maxResults, int firstResult) {
        return findFormDataEntities(false, maxResults, firstResult);
    }

    private List<FormData> findFormDataEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FormData.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public FormData findFormData(Long id) {
        return em.find(FormData.class, id);
    }

    public int getFormDataCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<FormData> rt = cq.from(FormData.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
