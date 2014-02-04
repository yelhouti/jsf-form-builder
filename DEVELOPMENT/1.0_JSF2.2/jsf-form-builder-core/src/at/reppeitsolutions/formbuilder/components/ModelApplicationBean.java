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

import at.reppeitsolutions.formbuilder.model.Form;
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
public class ModelApplicationBean {
    
    private Map<String, Form> models = new HashMap<>();
    
    public Form getModel(String uuid) {
        return models.get(uuid);
    }
    
    public void putModel(String uuid, Form model) {
        destroyModel(uuid);
        models.put(uuid, model);
    }
    
    public void destroyModel(String uuid) {
        if(models.containsKey(uuid)) {
            models.remove(uuid);
        }
    }
    
    public static ModelApplicationBean getInstance() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        return (ModelApplicationBean) ctx.getApplication().evaluateExpressionGet(ctx, "#{modelApplicationBean}", ModelApplicationBean.class);
    }
    
}
