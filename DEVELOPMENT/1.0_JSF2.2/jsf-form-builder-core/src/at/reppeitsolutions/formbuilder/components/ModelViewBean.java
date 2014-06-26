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
package at.reppeitsolutions.formbuilder.components;

import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@ManagedBean
@ViewScoped
public class ModelViewBean implements Serializable {

    private String uuid;
    private String mode;
    @ManagedProperty(value = "#{modelApplicationBean}")
    ModelApplicationBean modelApplicationBean;
    
    public ModelViewBean() {
        
    }
    
    @PreDestroy
    public void preDestroy() {
        modelApplicationBean.destroyForm(uuid);
        modelApplicationBean.destroyFormData(uuid);
        modelApplicationBean.destroyFormDataResult(uuid);
    }
    
    public void save() {
        
    }
    
    public FormBuilderAttributesContainer getFormBuilderAttributesContainer() {
        return modelApplicationBean.getForm(uuid);
    } 
    
    //Just for ide to restore session after deploy
    private void setFormBuilderAttributesContainer(FormBuilderAttributesContainer container) {
        modelApplicationBean.putForm(uuid, container);
    }
    
    public FormFillerAttributesContainer getFormFillerAttributesContainer() {
        return modelApplicationBean.getFormData(uuid);
    }
    
    //Just for ide to restore session after deploy
    private void setFormFillerAttributesContainer(FormFillerAttributesContainer container) {
        modelApplicationBean.putFormData(uuid, container);
    }
    
    public FormDataResultAttributesContainer getFormDataResultAttributesContainer() {
        return modelApplicationBean.getFormDataResult(uuid);
    }
    
    //Just for ide to restore session after deploy
    private void setFormDataResultAttributesContainer(FormDataResultAttributesContainer container) {
        modelApplicationBean.putFormDataResult(uuid, container);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ModelApplicationBean getModelApplicationBean() {
        return modelApplicationBean;
    }

    public void setModelApplicationBean(ModelApplicationBean modelApplicationBean) {
        this.modelApplicationBean = modelApplicationBean;
    }
    
}
