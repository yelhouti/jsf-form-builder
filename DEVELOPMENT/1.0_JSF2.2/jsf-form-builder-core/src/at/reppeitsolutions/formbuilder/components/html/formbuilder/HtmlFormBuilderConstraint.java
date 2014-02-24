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
package at.reppeitsolutions.formbuilder.components.html.formbuilder;

import at.reppeitsolutions.formbuilder.components.html.HtmlDiv;
import at.reppeitsolutions.formbuilder.messages.Messages;
import at.reppeitsolutions.formbuilder.model.Constraint;
import at.reppeitsolutions.formbuilder.model.ConstraintClient;
import at.reppeitsolutions.formbuilder.model.ConstraintType;
import at.reppeitsolutions.formbuilder.model.WorkflowState;
import java.util.List;
import java.util.UUID;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectOneMenu;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class HtmlFormBuilderConstraint extends HtmlFormBuilderItem {

    private List<WorkflowState> workflowStates;
    private List<ConstraintClient> constraintClients;
    private List<Constraint> constraints;

    public HtmlFormBuilderConstraint() {
    }

    public HtmlFormBuilderConstraint(List<WorkflowState> workflowStates,
            List<ConstraintClient> constraintClients,
            List<Constraint> constraints) {
        this.workflowStates = workflowStates;
        this.constraintClients = constraintClients;
        this.constraints = constraints;
    }

    @Override
    public void renderView() {
        HtmlDiv constraintEditorDiv = new HtmlDiv();
        constraintEditorDiv.setId("constraintEditor" + UUID.randomUUID().toString());
        HtmlDiv constraintHeader = new HtmlDiv();
        constraintHeader.setStyle("text-align: center; border-bottom: 1px solid black;");
        HtmlOutputText output = new HtmlOutputText();
        output.setValue(properties.getValues());
        output.setTransient(true);
        constraintHeader.getChildren().add(output);
        getChildren().add(constraintHeader);
        HtmlOutputText show = new HtmlOutputText();
        show.setEscape(false);
        String showStyle = "";
        if (getProperties().getMaximise()) {
            showStyle = "display:none;";
        }
        String showLinkId = "link" + UUID.randomUUID().toString();
        String hideLinkId = "link" + UUID.randomUUID().toString();
        show.setValue("<a style=\"" + showStyle + "\" class=\"max\" "
                + "id=\"" + showLinkId + "\" href=\"#\" onclick=\"$('#" + constraintEditorDiv.getId() + "').show("
                + "{complete:function(){"
                + "$('#" + showLinkId + "').hide();"
                + "$('#" + hideLinkId + "').show();"
                + "}});\"></a>");
        String hideStyle = "display:none;";
        if (getProperties().getMaximise()) {
            hideStyle = "";
        }
        HtmlOutputText hide = new HtmlOutputText();
        hide.setEscape(false);
        hide.setValue("<a style=\"" + hideStyle + "\" class=\"min\""
                + "id=\"" + hideLinkId + "\" href=\"#\" onclick=\"$('#" + constraintEditorDiv.getId() + "').hide("
                + "{complete:function(){"
                + "$('#" + showLinkId + "').show();"
                + "$('#" + hideLinkId + "').hide();"
                + "}});\"></a>");
        getChildren().add(show);
        getChildren().add(hide);
        constraintEditorDiv.setStyle("text-align:center;font-size:10pt;" + hideStyle);
        output = new HtmlOutputText();
        output.setEscape(false);
        output.setValue(Messages.getStringJSF("constraint.info.new") + "<br />");
        constraintEditorDiv.getChildren().add(output);
        HtmlPanelGrid panel = new HtmlPanelGrid();
        panel.setStyle("font-size:10pt;text-align:left;");
        panel.setColumns(2);
        if (workflowStates != null) {
            HtmlOutputText outputWorkflowState = new HtmlOutputText();
            outputWorkflowState.setValue(Messages.getStringJSF("constraint.info.workflowState"));
            panel.getChildren().add(outputWorkflowState);
            HtmlSelectOneMenu select = new HtmlSelectOneMenu();
            UISelectItem item = new UISelectItem();
            item.setItemValue(null);
            item.setItemLabel(Messages.getStringJSF("constraint.workflowstate.all"));
            select.getChildren().add(item);
            select.setId("workflowState" + getItemUuid());
            for (WorkflowState workflowState : workflowStates) {
                item = new UISelectItem();
                item.setItemValue(workflowState.getUuid());
                item.setItemLabel(workflowState.getDisplayName());
                select.getChildren().add(item);
            }
            panel.getChildren().add(select);
        }
        HtmlOutputText outputConstraintType = new HtmlOutputText();
        outputConstraintType.setValue(Messages.getStringJSF("constraint.info.constraintType"));
        panel.getChildren().add(outputConstraintType);
        HtmlSelectOneMenu select = new HtmlSelectOneMenu();
        select.setId("constraintType" + getItemUuid());
        for (ConstraintType constraintType : ConstraintType.values()) {
            if (constraintType != ConstraintType.DEFAULT) {
                UISelectItem item = new UISelectItem();
                item.setItemValue(constraintType);
                item.setItemLabel(Messages.getStringJSF("ConstraintType." + constraintType.name()));
                select.getChildren().add(item);
            }
        }
        panel.getChildren().add(select);
        if (constraintClients != null) {
            HtmlOutputText outputConstraintClient = new HtmlOutputText();
            outputConstraintClient.setValue(Messages.getStringJSF("constraint.info.constraintClient"));
            panel.getChildren().add(outputConstraintClient);
            select = new HtmlSelectOneMenu();
            select.setId("constraintClient" + getItemUuid());
            UISelectItem item = new UISelectItem();
            item.setItemValue(null);
            item.setItemLabel(Messages.getStringJSF("constraint.constraintclient.all"));
            select.getChildren().add(item);
            for (ConstraintClient constraintClient : constraintClients) {
                item = new UISelectItem();
                item.setItemValue(constraintClient.getUuid());
                item.setItemLabel(constraintClient.getDisplayName());
                select.getChildren().add(item);
            }
            panel.getChildren().add(select);
        }
        HtmlOutputText empty = new HtmlOutputText();
        panel.getChildren().add(empty);
        HtmlOutputText save = new HtmlOutputText();
        save.setEscape(false);
        save.setValue("<button href=\"#\" onclick=\"addConstraint('" + getItemUuid() + "','" + getItemUuid() + "');\">"
                + Messages.getStringJSF("constraint.button.add")
                + "</button>");
        panel.getChildren().add(save);
        constraintEditorDiv.getChildren().add(panel);
        HtmlPanelGrid listPanel = new HtmlPanelGrid();
        listPanel.setStyle("font-size:10pt;text-align:left;");
        int columns = 7;
        if (workflowStates == null) {
            columns = columns - 2;
        }
        if (constraintClients == null) {
            columns = columns - 2;
        }
        listPanel.setColumns(columns);
        if (constraints != null) {
            if (!constraints.isEmpty()) {
                output = new HtmlOutputText();
                output.setEscape(false);
                output.setValue("<br />" + Messages.getStringJSF("constraint.info.current") + "<br />");
                constraintEditorDiv.getChildren().add(output);
            }
            for (Constraint constraint : constraints) {
                if (workflowStates != null) {
                    HtmlOutputText tmpOutput = new HtmlOutputText();
                    if (constraint.getWorkflowState() != null) {
                        tmpOutput.setValue(constraint.getWorkflowState().getDisplayName() + ",");
                    } else {
                        tmpOutput.setValue(Messages.getStringJSF("constraint.workflowstate.all") + ",");
                    }
                    HtmlOutputText outputWorkflowState = new HtmlOutputText();
                    outputWorkflowState.setValue(Messages.getStringJSF("constraint.info.workflowState"));
                    listPanel.getChildren().add(outputWorkflowState);
                    listPanel.getChildren().add(tmpOutput);
                }
                HtmlOutputText tmpOutput = new HtmlOutputText();
                String comma = ",";
                if(constraintClients == null) {
                    comma = "";
                }
                tmpOutput.setValue(Messages.getStringJSF("ConstraintType." + constraint.getConstraintType().name()) + comma);
                outputConstraintType = new HtmlOutputText();
                outputConstraintType.setValue(Messages.getStringJSF("constraint.info.constraintType"));
                listPanel.getChildren().add(outputConstraintType);
                listPanel.getChildren().add(tmpOutput);
                if (constraintClients != null) {
                    tmpOutput = new HtmlOutputText();
                    if (constraint.getConstraintClient() != null) {
                        tmpOutput.setValue(constraint.getConstraintClient().getDisplayName());
                    } else {
                        tmpOutput.setValue(Messages.getStringJSF("constraint.constraintclient.all"));
                    }
                    HtmlOutputText outputConstraintClient = new HtmlOutputText();
                    outputConstraintClient.setValue(Messages.getStringJSF("constraint.info.constraintClient"));
                    listPanel.getChildren().add(outputConstraintClient);
                    listPanel.getChildren().add(tmpOutput);
                }
                tmpOutput = new HtmlOutputText();
                tmpOutput.setEscape(false);
                tmpOutput.setValue("<div class=\"deleteConstraint\" onclick=\"deleteConstraint('" + constraint.hashCode() + "','" + getItemUuid() + "');\"></div>");
                listPanel.getChildren().add(tmpOutput);
            }
        }
        constraintEditorDiv.getChildren().add(listPanel);
        getChildren().add(constraintEditorDiv);

    }
}
