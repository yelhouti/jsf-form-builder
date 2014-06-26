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

import at.reppeitsolutions.formbuilder.model.FormData;
import at.reppeitsolutions.formbuilder.model.Form;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@ManagedBean
@ApplicationScoped
public class ModelApplicationBean implements Serializable {
    
    private Map<String, FormBuilderAttributesContainer> models = new HashMap<>();
    private Map<String, FormFillerAttributesContainer> datamodels = new HashMap<>();
    private Map<String, FormDataResultAttributesContainer> resultmodels = new HashMap<>();
    
    public FormBuilderAttributesContainer getForm(String uuid) {
        return models.get(uuid);
    }
    
    public FormFillerAttributesContainer getFormData(String uuid) {
        return datamodels.get(uuid);
    }
    
    public FormDataResultAttributesContainer getFormDataResult(String uuid) {
        return resultmodels.get(uuid);
    }
    
    public void putForm(String uuid, FormBuilderAttributesContainer container) {
        destroyForm(uuid);
        models.put(uuid, container);
    }
    
    public void putFormData(String uuid, FormFillerAttributesContainer container) {
        destroyFormData(uuid);
        datamodels.put(uuid, container);
    }
    
    public void putFormDataResult(String uuid, FormDataResultAttributesContainer container) {
        destroyFormData(uuid);
        resultmodels.put(uuid, container);
    }
    
    public void destroyForm(String uuid) {
        if(models.containsKey(uuid)) {
            models.remove(uuid);
        }
    }
    
    public void destroyFormData(String uuid) {
        if(datamodels.containsKey(uuid)) {
            datamodels.remove(uuid);
        }
    }
    
    public void destroyFormDataResult(String uuid) {
        if(resultmodels.containsKey(uuid)) {
            resultmodels.remove(uuid);
        }
    }
    
    public static ModelApplicationBean getInstance() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        return (ModelApplicationBean) ctx.getApplication().evaluateExpressionGet(ctx, "#{modelApplicationBean}", ModelApplicationBean.class);
    }
    
}
