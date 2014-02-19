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
package at.reppeitsolutions.formbuilder.model;

import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity
@Table(name = "formitem_constraintclient")
@IdClass(ConstraintId.class)
public class Constraint implements Serializable {

    @Id
    private String formBuilderItemUuid;
    @Id
    private Long constraingClientId;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "formBuilderItemUuid", referencedColumnName = "uuid")
    private FormBuilderItemBase formBuilderItem;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ConstraintClientId", referencedColumnName = "id")
    private ConstraintClient constraintClient;
    @OneToOne
    private WorkflowState workflowState;
    @Enumerated(EnumType.STRING)
    private ConstraintType constraintType = ConstraintType.DEFAULT;

    public String getFormBuilderItemUuid() {
        return formBuilderItemUuid;
    }

    public void setFormBuilderItemUuid(String formBuilderItemUuid) {
        this.formBuilderItemUuid = formBuilderItemUuid;
    }
    
    public Long getConstraingClientId() {
        return constraingClientId;
    }

    public void setConstraingClientId(Long constraingClientId) {
        this.constraingClientId = constraingClientId;
    }

    public FormBuilderItemBase getFormBuilderItem() {
        return formBuilderItem;
    }

    public void setFormBuilderItem(FormBuilderItemBase formBuilderItem) {
        this.formBuilderItem = formBuilderItem;
    }

    public ConstraintClient getConstraintClient() {
        return constraintClient;
    }

    public void setConstraintClient(ConstraintClient constraintClient) {
        this.constraintClient = constraintClient;
    }

    public WorkflowState getWorkflowState() {
        return workflowState;
    }

    public void setWorkflowState(WorkflowState workflowState) {
        this.workflowState = workflowState;
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(ConstraintType constraintType) {
        this.constraintType = constraintType;
    }

}
