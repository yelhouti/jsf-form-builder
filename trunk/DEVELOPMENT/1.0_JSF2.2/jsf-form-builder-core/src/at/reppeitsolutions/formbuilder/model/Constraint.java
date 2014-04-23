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

import at.reppeitsolutions.formbuilder.components.Constants;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity
@Table(name = Constants.TABLE_PREFIX + "formitem_constraintclient")
public class Constraint implements Serializable {

    @Id
    private String uuid = UUID.randomUUID().toString();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "formBuilderItemUuid")
    private FormBuilderItemBase formBuilderItem;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "constraintClientUuid")
    private ConstraintClient constraintClient;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private WorkflowState workflowState;
    @Enumerated(EnumType.STRING)
    private ConstraintType constraintType = ConstraintType.DEFAULT;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Constraint other = (Constraint) obj;
        if (!Objects.equals(this.constraintClient, other.constraintClient)) {
            return false;
        }
        if (!Objects.equals(this.workflowState, other.workflowState)) {
            return false;
        }
        if (this.constraintType != other.constraintType) {
            return false;
        }
        return true;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.constraintClient);
        hash = 59 * hash + Objects.hashCode(this.workflowState);
        hash = 59 * hash + Objects.hashCode(this.constraintType);
        return hash;
    }

}
