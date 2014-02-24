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

import at.reppeitsolutions.formbuilder.components.helper.MetaDataDescription;
import at.reppeitsolutions.formbuilder.model.Form;
import at.reppeitsolutions.formbuilder.model.ConstraintClient;
import at.reppeitsolutions.formbuilder.model.WorkflowState;
import java.util.List;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public abstract class FormBuilderComponentBase extends BuilderFillerComponent {

    public Form getForm() {
        return (Form) getStateHelper().eval("form");
    }

    public void setForm(Form form) {
        getStateHelper().put("form", form);
    }

    public List<WorkflowState> getWorkflowStates() {
        return (List<WorkflowState>) getStateHelper().eval("workflowStates");
    }

    public void setWorkflowStates(List<WorkflowState> workflowStates) {
        getStateHelper().put("workflowStates", workflowStates);
    }

    public List<ConstraintClient> getConstraintClients() {
        return (List<ConstraintClient>) getStateHelper().eval("constraintClients");
    }

    public void setConstraintClients(List<ConstraintClient> constraintClients) {
        getStateHelper().put("constraintClients", constraintClients);
    }

    public Object getMetaDataObject() {
        return getStateHelper().eval("metaDataObject");
    }

    public void setMetaDataObject(Object metaDataObject) {
        getStateHelper().put("metaDataObject", metaDataObject);
    }
    
    public List<MetaDataDescription> getMetaDataDescriptions() {
        return (List<MetaDataDescription>) getStateHelper().eval("metaDataDescriptions");
    }
    
    public void setMetaDataDescriptions(List<MetaDataDescription> metaDataDescriptions) {
        getStateHelper().put("metaDataDescriptions", metaDataDescriptions);
    }
}
