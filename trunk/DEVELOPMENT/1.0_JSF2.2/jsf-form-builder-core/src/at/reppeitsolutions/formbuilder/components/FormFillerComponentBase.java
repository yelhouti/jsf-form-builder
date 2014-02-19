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

import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormData;
import at.reppeitsolutions.formbuilder.model.IUser;
import at.reppeitsolutions.formbuilder.model.IWorkflowState;
import java.util.List;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public abstract class FormFillerComponentBase extends BuilderFillerComponent {
    
    public FormData getFormData() {
        return (FormData) getStateHelper().eval("formData");
    }

    public void setFormData(FormData formData) {
        getStateHelper().put("formData", formData);
    }
    
    public IWorkflowState getWorkflowState() {
        return (IWorkflowState) getStateHelper().eval("workflowState");
    }

    public void setWorkflowState(List<IWorkflowState> workflowState) {
        getStateHelper().put("workflowState", workflowState);
    }

    public IUser getUser() {
        return (IUser) getStateHelper().eval("user");
    }

    public void setUser(IUser user) {
        getStateHelper().put("user", user);
    }
    
    public String getMode() {
        return (String) getStateHelper().eval("mode");
    }
    
    public void setMode(String mode) {
        getStateHelper().put("mode", mode);
    }
    
}
