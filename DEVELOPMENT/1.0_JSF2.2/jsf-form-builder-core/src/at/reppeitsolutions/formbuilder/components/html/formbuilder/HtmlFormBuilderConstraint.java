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

    public HtmlFormBuilderConstraint() {
    }

    public HtmlFormBuilderConstraint(List<WorkflowState> workflowStates, List<ConstraintClient> constraintClients) {
        this.workflowStates = workflowStates;
        this.constraintClients = constraintClients;
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
        if (workflowStates != null && constraintClients != null) {
            HtmlOutputText show = new HtmlOutputText();
            show.setEscape(false);
            String showLinkId = "link" + UUID.randomUUID().toString();
            String hideLinkId = "link" + UUID.randomUUID().toString();
            show.setValue("<a class=\"max\" "
                    + "id=\"" + showLinkId + "\" href=\"#\" onclick=\"$('#" + constraintEditorDiv.getId() + "').show("
                    + "{complete:function(){"
                    + "$('#" + showLinkId + "').hide();"
                    + "$('#" + hideLinkId + "').show();"
                    + "}});\"></a>");
            HtmlOutputText hide = new HtmlOutputText();
            hide.setEscape(false);
            hide.setValue("<a style=\"display:none;\" class=\"min\""
                    + "id=\"" + hideLinkId + "\" href=\"#\" onclick=\"$('#" + constraintEditorDiv.getId() + "').hide("
                    + "{complete:function(){"
                    + "$('#" + showLinkId + "').show();"
                    + "$('#" + hideLinkId + "').hide();"
                    + "}});\"></a>");
            getChildren().add(show);
            getChildren().add(hide);
            constraintEditorDiv.setStyle("text-align:center;font-size:10pt;display:none;");
            output = new HtmlOutputText();
            output.setEscape(false);
            output.setValue(Messages.getStringJSF("constraint.info.new") + "<br />");
            constraintEditorDiv.getChildren().add(output);
            HtmlPanelGrid panel = new HtmlPanelGrid();
            panel.setStyle("font-size:10pt;text-align:left;");
            panel.setColumns(2);
            output = new HtmlOutputText();
            output.setValue(Messages.getStringJSF("constraint.info.workflowState"));
            panel.getChildren().add(output);
            HtmlSelectOneMenu select = new HtmlSelectOneMenu();
            for (WorkflowState workflowState : workflowStates) {
                UISelectItem item = new UISelectItem();
                item.setItemValue(workflowState.getUuid());
                item.setItemLabel(workflowState.getDisplayName());
                select.getChildren().add(item);
            }
            panel.getChildren().add(select);
            output = new HtmlOutputText();
            output.setEscape(false);
            output.setValue(Messages.getStringJSF("constraint.info.constraintType"));
            panel.getChildren().add(output);
            select = new HtmlSelectOneMenu();
            for (ConstraintType constraintType : ConstraintType.values()) {
                if (constraintType != ConstraintType.DEFAULT) {
                    UISelectItem item = new UISelectItem();
                    item.setItemValue(constraintType);
                    item.setItemLabel(Messages.getStringJSF("ConstraintType." + constraintType.name()));
                    select.getChildren().add(item);
                }
            }
            panel.getChildren().add(select);
            output = new HtmlOutputText();
            output.setEscape(false);
            output.setValue(Messages.getStringJSF("constraint.info.constraintClient"));
            panel.getChildren().add(output);
            select = new HtmlSelectOneMenu();
            for (ConstraintClient constraintClient : constraintClients) {
                UISelectItem item = new UISelectItem();
                item.setItemValue(constraintClient.getUuid());
                item.setItemLabel(constraintClient.getDisplayName());
                select.getChildren().add(item);
            }
            panel.getChildren().add(select);
            constraintEditorDiv.getChildren().add(panel);
            output = new HtmlOutputText();
            output.setEscape(false);
            output.setValue("<br />" + Messages.getStringJSF("constraint.info.current") + "<br />");
            constraintEditorDiv.getChildren().add(output);
            getChildren().add(constraintEditorDiv);
        }
    }
}
