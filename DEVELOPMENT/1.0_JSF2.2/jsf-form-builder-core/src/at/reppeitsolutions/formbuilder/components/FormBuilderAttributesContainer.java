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
import at.reppeitsolutions.formbuilder.components.helper.MetaDataDescription;
import at.reppeitsolutions.formbuilder.model.ConstraintClient;
import at.reppeitsolutions.formbuilder.model.Form;
import at.reppeitsolutions.formbuilder.model.WorkflowState;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormBuilderAttributesContainer implements Serializable {
    
    private Form form;
    private List<WorkflowState> workflowStates;
    private List<ConstraintClient> constraintClients;
    private List<MetaDataDescription> metaDataDescriptions;
    private Object metaDataObject;
    private IMetaDataFetcher metaDataFetcher;

    public FormBuilderAttributesContainer() {
        
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<WorkflowState> getWorkflowStates() {
        return workflowStates;
    }

    public void setWorkflowStates(List<WorkflowState> workflowStates) {
        this.workflowStates = workflowStates;
    }

    public List<ConstraintClient> getConstraintClients() {
        return constraintClients;
    }

    public void setConstraintClients(List<ConstraintClient> constraintClients) {
        this.constraintClients = constraintClients;
    }

    public List<MetaDataDescription> getMetaDataDescriptions() {
        return metaDataDescriptions;
    }

    public void setMetaDataDescriptions(List<MetaDataDescription> metaDataDescriptions) {
        this.metaDataDescriptions = metaDataDescriptions;
    }

    public IMetaDataFetcher getMetaDataFetcher() {
        return metaDataFetcher;
    }
    
    public void setMetaDataFetcher(IMetaDataFetcher metaDataFetcher) {
        this.metaDataFetcher = metaDataFetcher;
    }

    public Object getMetaDataObject() {
        return metaDataObject;
    }

    public void setMetaDataObject(Object metaDataObject) {
        this.metaDataObject = metaDataObject;
    }
    
}
