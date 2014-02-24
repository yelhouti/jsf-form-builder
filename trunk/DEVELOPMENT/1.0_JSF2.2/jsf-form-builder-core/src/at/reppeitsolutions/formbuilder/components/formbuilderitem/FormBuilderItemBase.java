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
package at.reppeitsolutions.formbuilder.components.formbuilderitem;

import at.reppeitsolutions.formbuilder.components.Constants;
import at.reppeitsolutions.formbuilder.components.annotations.IgnoreProperty;
import at.reppeitsolutions.formbuilder.components.annotations.IgnorePropertyInDialog;
import at.reppeitsolutions.formbuilder.model.Constraint;
import at.reppeitsolutions.formbuilder.model.ConstraintClient;
import at.reppeitsolutions.formbuilder.model.ConstraintType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import at.reppeitsolutions.formbuilder.model.Form;
import at.reppeitsolutions.formbuilder.model.WorkflowState;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity(name = Constants.TABLE_PREFIX + "formitem")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class FormBuilderItemBase implements Comparable, Serializable {

    public static final String FULLWIDTH = "600px";
    public static final String HALFWIDTH = "300px";
    @Id
    private String uuid = UUID.randomUUID().toString();
    protected int position = -1;
    protected String formbuildertype;
    protected String className;
    protected String diagid;
    protected String width = FULLWIDTH;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "form_id")
    protected Form form;
    @Embedded
    protected FormBuilderItemProperties properties;
    @OneToMany(mappedBy = "formBuilderItem")
    private List<Constraint> constraints = new ArrayList<>();
    @Transient
    private String constraintsstring;
    @Transient
    private Map<String, Map> valueTranslations = new HashMap<>();
    @Transient
    private Map<String, String> propertyTranslations = new HashMap<>();
    @Transient
    private Map<String, FormBuilderItemBase.SPECIALPROPERTY> specialProperties = new HashMap<>();
    @Transient
    private FormBuilderItemBase brother;

    public enum SPECIALPROPERTY {

        TEXTAREA
    }

    protected void addValueTranslation(String property, String value, String translation) {
        if (valueTranslations.containsKey(property)) {
            valueTranslations.get(property).put(value, translation);
        } else {
            Map translations = new HashMap();
            translations.put(value, translation);
            valueTranslations.put(property, translations);
        }
    }

    protected void addPropertyTranslation(String property, String translation) {
        propertyTranslations.put(property, translation);
    }

    protected void addSpecialProperty(String property, SPECIALPROPERTY special) {
        specialProperties.put(property, special);
    }

    public void addConstraintClient(ConstraintClient constraintClient, WorkflowState workflowState, ConstraintType constraintType) {
        Constraint constraint = new Constraint();
        constraint.setFormBuilderItem(this);
        constraint.setConstraintClient(constraintClient);
        constraint.setFormBuilderItemUuid(this.getId());
        constraint.setConstraingClientId(constraintClient.getId());
        constraint.setWorkflowState(workflowState);
        constraint.setConstraintType(constraintType);

        boolean alreadyAdded = false;
        for (Constraint tmpConstraint : constraints) {
            if (tmpConstraint.equals(constraint)) {
                alreadyAdded = true;
                break;
            }
        }
        if (!alreadyAdded) {
            this.constraints.add(constraint);
            constraintClient.getConstraints().add(constraint);
        }
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @IgnoreProperty
    public Form getForm() {
        return form;
    }

    @IgnorePropertyInDialog
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @IgnorePropertyInDialog
    public String getFormbuildertype() {
        return formbuildertype;
    }

    public void setFormbuildertype(String formbuildertype) {
        this.formbuildertype = formbuildertype;
    }

    @IgnorePropertyInDialog
    public String getClassname() {
        if (className == null) {
            return getClass().getName();
        }
        return className;
    }

    public void setClassname(String className) {
        this.className = className;
    }

    public int compareTo(Object t) {
        FormBuilderItemBase item = (FormBuilderItemBase) t;
        if (getPosition() > item.getPosition()) {
            return 1;
        } else if (getPosition() < item.getPosition()) {
            return -1;
        }
        return 0;
    }

    @IgnorePropertyInDialog
    public String getId() {
        return uuid;
    }

    public void setId(String uuid) {
        this.uuid = uuid;
    }

    @IgnorePropertyInDialog
    public String getDiagid() {
        if (diagid == null) {
            return "diag:" + uuid;
        }
        return diagid;
    }

    public void setDiagid(String diagid) {
        this.diagid = diagid;
    }

    @IgnorePropertyInDialog
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public FormBuilderItemProperties getProperties() {
        return properties;
    }

    public void setProperties(FormBuilderItemProperties properties) {
        this.properties = properties;
    }

    public Map<String, Map> getValueTranslations() {
        return valueTranslations;
    }

    public Map<String, String> getPropertyTranslations() {
        return propertyTranslations;
    }

    public Map<String, FormBuilderItemBase.SPECIALPROPERTY> getSpecialProperties() {
        return specialProperties;
    }

    public boolean getSkipRendering() {
        return false;
    }

    @IgnoreProperty
    public FormBuilderItemBase getBrother() {
        return brother;
    }

    public void setBrother(FormBuilderItemBase brother) {
        this.brother = brother;
    }

    @IgnoreProperty
    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }
    
    private static final String sep1 = "<sep1>";
    private static final String sep2 = "<sep2>";

    public void initConstraintsFromConstraintsString(List<WorkflowState> workflowStates,
            List<ConstraintClient> constraintClients) {
        if (constraintsstring != null && !"null".equals(constraintsstring)) {
            String[] constraintsArray = constraintsstring.split(sep2);
            for (String constraintString : constraintsArray) {
                String[] constraintUuids = constraintString.split(sep1);
                WorkflowState workflowState = null;
                ConstraintClient constraintClient = null;
                ConstraintType constraintType = null;
                for (WorkflowState tmpWorkflowState : workflowStates) {
                    if (tmpWorkflowState.getUuid().equals(constraintUuids[0])) {
                        workflowState = tmpWorkflowState;
                        break;
                    }
                }
                for (ConstraintClient tmpConstraintClient : constraintClients) {
                    if (tmpConstraintClient.getUuid().equals(constraintUuids[1])) {
                        constraintClient = tmpConstraintClient;
                        break;
                    }
                }
                for (ConstraintType tmpConstraintType : ConstraintType.values()) {
                    if (tmpConstraintType.name().equals(constraintUuids[2])) {
                        constraintType = tmpConstraintType;
                        break;
                    }
                }
                addConstraintClient(constraintClient, workflowState, constraintType);
            }
        }
    }

    @IgnorePropertyInDialog
    public String getConstraintsstring() {
        if ((constraintsstring == null || "null".equals(constraintsstring))
                && constraints != null && !constraints.isEmpty()) {
            constraintsstring = "";
            for (Constraint constraint : constraints) {
                constraintsstring += constraint.getWorkflowState().getUuid() + sep1;
                constraintsstring += constraint.getConstraintClient().getUuid() + sep1;
                constraintsstring += constraint.getConstraintType().name() + sep2;
            }
            constraintsstring = constraintsstring.substring(0, constraintsstring.length() - sep2.length());
        }
        return constraintsstring;
    }

    public void setConstraintsstring(String constraintsstring) {
        this.constraintsstring = constraintsstring;
    }
}
