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

import at.reppeitsolutions.formbuilder.components.helper.IMetaDataFetcher;
import at.reppeitsolutions.formbuilder.model.FormData;
import at.reppeitsolutions.formbuilder.model.ConstraintClient;
import at.reppeitsolutions.formbuilder.model.WorkflowState;
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
    
    public WorkflowState getWorkflowState() {
        return (WorkflowState) getStateHelper().eval("workflowState");
    }

    public void setWorkflowState(List<WorkflowState> workflowState) {
        getStateHelper().put("workflowState", workflowState);
    }

    public ConstraintClient getConstraintClient() {
        return (ConstraintClient) getStateHelper().eval("constraintClient");
    }

    public void setConstraintClient(ConstraintClient constraintClient) {
        getStateHelper().put("constraintClient", constraintClient);
    }
    
    public String getMode() {
        return (String) getStateHelper().eval("mode");
    }
    
    public void setMode(String mode) {
        getStateHelper().put("mode", mode);
    }
    
    public IMetaDataFetcher getMetaDataFetcher() {
        return (IMetaDataFetcher) getStateHelper().eval("metaDataFetcher");
    }
    
    public void setMetaDataFetcher(IMetaDataFetcher metaDataFetcher) {
        getStateHelper().put("metaDataFetcher", metaDataFetcher);
    }
    
    public Object getMetaDataObject() {
        return getStateHelper().eval("metaDataObject");
    }

    public void setMetaDataObject(Object metaDataObject) {
        getStateHelper().put("metaDataObject", metaDataObject);
    }
    
}
