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

import at.reppeitsolutions.formbuilder.model.ConstraintClient;
import at.reppeitsolutions.formbuilder.model.FormData;
import at.reppeitsolutions.formbuilder.model.WorkflowState;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormFillerAttributesContainer {
    
    private FormData formData;
    private WorkflowState workflowState;
    private ConstraintClient constraintClient;
    private Object metaDataObject;

    public FormData getFormData() {
        return formData;
    }

    public void setFormData(FormData formData) {
        this.formData = formData;
    }

    public WorkflowState getWorkflowState() {
        return workflowState;
    }

    public void setWorkflowState(WorkflowState workflowState) {
        this.workflowState = workflowState;
    }

    public ConstraintClient getConstraintClient() {
        return constraintClient;
    }

    public void setConstraintClient(ConstraintClient constraintClient) {
        this.constraintClient = constraintClient;
    }

    public Object getMetaDataObject() {
        return metaDataObject;
    }

    public void setMetaDataObject(Object metaDataObject) {
        this.metaDataObject = metaDataObject;
    }
    
}
