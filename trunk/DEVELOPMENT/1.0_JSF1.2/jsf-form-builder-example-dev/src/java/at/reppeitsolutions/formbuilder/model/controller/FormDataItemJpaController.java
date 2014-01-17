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
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormBuilderItemData;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormDataItemJpaController implements Serializable {

    public FormDataItemJpaController(UserTransaction utx, EntityManager em) {
        this.em = em;
    }
    
    private EntityManager em = null;

    public EntityManager getEntityManager() {
        return em;
    }

    public List<FormBuilderItemData> findFormDataEntities() {
        return findFormDataEntities(true, -1, -1);
    }

    public List<FormBuilderItemData> findFormDataEntities(int maxResults, int firstResult) {
        return findFormDataEntities(false, maxResults, firstResult);
    }

    private List<FormBuilderItemData> findFormDataEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FormBuilderItemData.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public FormBuilderItemData findFormData(Long id) {
        return em.find(FormBuilderItemData.class, id);
    }
    
}
