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
package beans;

import at.reppeitsolutions.formbuilder.components.helper.MetaDataDescription;
import at.reppeitsolutions.formbuilder.components.pdf.helper.FormBuilderPdfFactory;
import at.reppeitsolutions.formbuilder.model.ConstraintClient;
import at.reppeitsolutions.formbuilder.model.ConstraintType;
import at.reppeitsolutions.formbuilder.model.Form;
import at.reppeitsolutions.formbuilder.model.FormData;
import at.reppeitsolutions.formbuilder.model.WorkflowState;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@ManagedBean
@SessionScoped
public class FormBuilderBean implements Serializable {

    private Form form;
    private FormData formData;
    private List<WorkflowState> workflowStates = new ArrayList<>();
    private List<ConstraintClient> constraintClients = new ArrayList<>();
    private WorkflowState workflowState;
    private ConstraintClient constraintClient;
    private ConstraintType constraintType;
    private boolean fillConstraint = false;
    private String constraintClientUuid;
    private String workflowStateUuid;
    private MetaData metaData = new MetaData();
    private List<MetaDataDescription> metaDataDescriptions = new ArrayList<>();
    
    public static final WorkflowState INIT = new WorkflowState("INIT", UUID.randomUUID().toString());
    public static final WorkflowState FILL = new WorkflowState("FILL", UUID.randomUUID().toString());
    public static final WorkflowState FINISH = new WorkflowState("FINISH", UUID.randomUUID().toString());
    public static final ConstraintClient REPPE = new ConstraintClient("REPPE", UUID.randomUUID().toString());
    public static final ConstraintClient JONACH = new ConstraintClient("JONACH", UUID.randomUUID().toString());
    public static final ConstraintClient ERTLER = new ConstraintClient("ERTLER", UUID.randomUUID().toString());

    public FormBuilderBean() {
        workflowStates.add(INIT);
        workflowStates.add(FILL);
        workflowStates.add(FINISH);
        
        constraintClients.add(REPPE);
        constraintClients.add(JONACH);
        constraintClients.add(ERTLER);
        
        metaData.setTitle("Testform");
        MetaDataDescription desc = new MetaDataDescription();
        desc.setDescription("Titel");
        desc.setGetter("title");
        metaDataDescriptions.add(desc);
        
    }

    public void save() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("view.xhtml");
    }

    public void buffer() {
        System.out.println("FormBuilderBean.buffer");
    }

    public void reset() {
        form = null;
        formData = null;
    }

    public Form getForm() {
        if (form == null) {
            form = new Form();
        }
        return form;
    }
    
    public String fillConstraint() {
        formData = null;
        fillConstraint = true;
        return "fill";
    }

    public String fill() {
        formData = null;
        fillConstraint = false;
        return "fill";
    }

    public String pdf() {
        formData = null;
        return "pdf";
    }

    public void exportPdf() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset();
        ec.setResponseContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + UUID.randomUUID().toString() + ".pdf\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        OutputStream output = ec.getResponseOutputStream();
        formData = null;
        FormBuilderPdfFactory.getPdf(output, getFormData());

        fc.responseComplete();
    }

    public FormData getFormData() {
        if (formData == null) {
            formData = new FormData(getForm());
        }
        return formData;
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

    public WorkflowState getWorkflowState() {
        if(workflowStateUuid != null) {
            for(WorkflowState tmpWorkflowState : workflowStates) {
                if(tmpWorkflowState.getUuid().equals(workflowStateUuid)) {
                    workflowState = tmpWorkflowState;
                    break;
                }
            }
        }
        return workflowState;
    }

    public void setWorkflowState(WorkflowState workflowState) {
        this.workflowState = workflowState;
    }

    public ConstraintClient getConstraintClient() {
        if(constraintClientUuid != null) {
            for(ConstraintClient tmpConstraintClient : constraintClients) {
                if(tmpConstraintClient.getUuid().equals(constraintClientUuid)) {
                    constraintClient = tmpConstraintClient;
                    break;
                }
            }
        }
        return constraintClient;
    }

    public void setConstraintClient(ConstraintClient constraintClient) {
        this.constraintClient = constraintClient;
    }

    public boolean isFillConstraint() {
        return fillConstraint;
    }

    public void setFillConstraint(boolean fillConstraint) {
        this.fillConstraint = fillConstraint;
    }

    public String getConstraintClientUuid() {
        return constraintClientUuid;
    }

    public void setConstraintClientUuid(String constraintClientUuid) {
        this.constraintClientUuid = constraintClientUuid;
    }

    public String getWorkflowStateUuid() {
        return workflowStateUuid;
    }

    public void setWorkflowStateUuid(String workflowStateUuid) {
        this.workflowStateUuid = workflowStateUuid;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public List<MetaDataDescription> getMetaDataDescriptions() {
        return metaDataDescriptions;
    }

    public void setMetaDataDescriptions(List<MetaDataDescription> metaDataDescriptions) {
        this.metaDataDescriptions = metaDataDescriptions;
    }
    
}
