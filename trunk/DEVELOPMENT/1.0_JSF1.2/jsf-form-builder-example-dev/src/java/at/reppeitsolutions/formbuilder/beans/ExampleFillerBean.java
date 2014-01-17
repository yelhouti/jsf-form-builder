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
package at.reppeitsolutions.formbuilder.beans;

import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormData;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import at.reppeitsolutions.formbuilder.model.controller.FormDataJpaController;
import at.reppeitsolutions.formbuilder.model.controller.FormJpaController;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.NonexistentEntityException;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@ManagedBean(name = "exampleFillerBean")
@ViewScoped
public class ExampleFillerBean implements Serializable {

    private FormData form;
    @PersistenceContext
    protected EntityManager em;
    @Resource
    private UserTransaction utx;
    @ManagedProperty("#{request.getParameter('formfillid')}")
    private Long formfillid;
    @ManagedProperty("#{request.getParameter('formdataid')}")
    private Long formdataid;
    private FormJpaController formJpaController;
    private FormDataJpaController formDataJpaController;

    /**
     * Creates a new instance of ExampleBean
     */
    public ExampleFillerBean() {
    }

    @PostConstruct
    public void init() {
        formJpaController = new FormJpaController(utx, em);
        formDataJpaController = new FormDataJpaController(utx, em);
        if (formfillid != null && formdataid == null) {
            form = new FormData(formJpaController.findForm(formfillid));
        } else if(formdataid != null && formfillid == null) {
            form = formDataJpaController.findFormData(formdataid);
        }
    }
    
    public void submit() throws IOException {
        try {
            formDataJpaController.edit(form);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ExampleFillerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ExampleFillerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExampleFillerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }
    
    public List<FormData> getFormDatas() {
        return formDataJpaController.findFormDataEntities();
    }

    public FormData getFormData() {
        return form;
    }

    public void setFormData(FormData form) {
        this.form = form;
    }

    public Long getFormfillid() {
        return formfillid;
    }

    public void setFormfillid(Long formfillid) {
        this.formfillid = formfillid;
    }

    public Long getFormdataid() {
        return formdataid;
    }

    public void setFormdataid(Long formdataid) {
        this.formdataid = formdataid;
    }
}
