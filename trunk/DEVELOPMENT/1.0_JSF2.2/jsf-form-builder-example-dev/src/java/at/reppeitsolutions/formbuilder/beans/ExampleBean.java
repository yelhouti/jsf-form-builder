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
import model.Form;
import at.reppeitsolutions.formbuilder.model.controller.FormJpaController;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.NonexistentEntityException;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.PreexistingEntityException;
import at.reppeitsolutions.formbuilder.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@ManagedBean(name = "exampleBean")
@ViewScoped
public class ExampleBean implements Serializable {

    private Form form;
    @PersistenceContext
    protected EntityManager em;
    @Resource
    private UserTransaction utx;
    @ManagedProperty("#{request.getParameter('formid')}")
    private Long formid;
    private FormJpaController formJpaController;

    /**
     * Creates a new instance of ExampleBean
     */
    public ExampleBean() {
    }

    @PostConstruct
    public void init() {
        formJpaController = new FormJpaController(utx, em);
        if (formid != null) {
            if(formid == -1) {
                form = new Form();
            } else {
                form = formJpaController.findForm(formid);
            }
        }
    }

    public void save() {
        try {
            formJpaController.edit(form);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(ExampleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ExampleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExampleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newForm() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?formid=-1");
    }
    
    public void deleteForm() {
        try {
            formJpaController.destroy(form.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ExampleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ExampleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExampleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        form = null;
    }

    public List<Form> getForms() {
        return formJpaController.findFormEntities();
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Long getFormid() {
        return formid;
    }

    public void setFormid(Long formid) {
        this.formid = formid;
    }
}
