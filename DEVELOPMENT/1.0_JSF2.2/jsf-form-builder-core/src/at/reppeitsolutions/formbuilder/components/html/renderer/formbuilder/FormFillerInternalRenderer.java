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
package at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder;

import at.reppeitsolutions.formbuilder.components.FormFillerInternal;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemConstraint;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemNumber;
import at.reppeitsolutions.formbuilder.model.FormBuilderItemData;
import at.reppeitsolutions.formbuilder.model.FormData;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderContainer;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemFactory;
import at.reppeitsolutions.formbuilder.components.html.HtmlDiv;
import at.reppeitsolutions.formbuilder.components.html.HtmlListItem;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderItem;
import at.reppeitsolutions.formbuilder.components.html.renderer.multipart.File;
import at.reppeitsolutions.formbuilder.components.html.renderer.multipart.MultipartRequest;
import at.reppeitsolutions.formbuilder.model.Constraint;
import at.reppeitsolutions.formbuilder.model.ConstraintType;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(componentFamily = FormFillerInternalRenderer.FAMILY, rendererType = FormFillerInternalRenderer.RENDERTYPE)
public class FormFillerInternalRenderer extends Renderer {

    public static final String RENDERTYPE = "FormFillerInternalRenderer";
    public static final String FAMILY = "at.rits.formfillerinternal";

    @Override
    public void encodeBegin(FacesContext ctx,
            UIComponent component) throws IOException {
        FormFillerInternal formFiller = (FormFillerInternal) component;
        ResponseWriter writer = ctx.getResponseWriter();
        HtmlForm form = FormBuilderInternalRenderer.getHtmlForm(formFiller);
        form.setTransient(true);
        formFiller.getFormContent().setTransient(true);
        formFiller.getFormContent().setId(form.getId() + "formContent" + UUID.randomUUID().toString());

        FormData formModel = formFiller.getFormData();

        if (formModel != null) {
            List<FormBuilderContainer> components = new ArrayList<>();

            boolean mandatoryError = false;
            FormBuilderItemConstraint activeConstraint = null;

            if (formModel.getData() != null) {
                for (FormBuilderItemData item : formModel.getData()) {
                    Boolean visible = null;
                    Boolean mandatory = null;
                    Boolean locked = null;
                    if (item.getFormBuilderItem() instanceof FormBuilderItemConstraint && activeConstraint == null) {
                        activeConstraint = (FormBuilderItemConstraint) item.getFormBuilderItem();
                    } else if (item.getFormBuilderItem() instanceof FormBuilderItemConstraint) {
                        activeConstraint = null;
                        visible = null;
                        mandatory = null;
                        locked = null;
                    } else if (activeConstraint != null) {
                        for (Constraint constraint : activeConstraint.getConstraints()) {
                            boolean workflowMatch = constraint.getWorkflowState() != null && constraint.getWorkflowState().equals(formFiller.getWorkflowState());
                            boolean constraintClientMatch = constraint.getConstraintClient() != null && constraint.getConstraintClient().equals(formFiller.getConstraintClient());
                            boolean workflowNull = constraint.getWorkflowState() == null;
                            boolean constraintClientNull = constraint.getConstraintClient()== null;
                            if ((workflowMatch && constraintClientMatch)
                                    || (constraintClientNull && workflowNull)
                                    || (workflowMatch && constraintClientNull)
                                    || (constraintClientMatch && workflowNull)) {
                                if (constraint.getConstraintType() == ConstraintType.INVISIBLE) {
                                    visible = false;
                                    mandatory = false;
                                    locked = false;
                                } else if (constraint.getConstraintType() == ConstraintType.MANDATORY) {
                                    visible = true;
                                    mandatory = true;
                                    locked = false;
                                } else if (constraint.getConstraintType() == ConstraintType.READONLY) {
                                    visible = true;
                                    mandatory = false;
                                    locked = true;
                                }
                            }
                        }
                    }
                    if (visible != null & mandatory != null && locked != null) {
                        item.getFormBuilderItem().getProperties().setVisible(visible);
                        item.getFormBuilderItem().getProperties().setMandatory(mandatory);
                        item.getFormBuilderItem().getProperties().setLocked(locked);
                    } else {
                        item.getFormBuilderItem().getProperties().resetConstraintVariables();
                    }
                    if (!item.getFormBuilderItem().getSkipRendering()
                            && item.getFormBuilderItem().getProperties().getVisible()) {
                        components.add(new FormBuilderContainer(item.getFormBuilderItem(), FormBuilderItemFactory.getUIComponent(item, formFiller.getMode(), formFiller)));
                        if (item.getFormBuilderItem().getProperties().getMandatoryError()) {
                            mandatoryError = true;
                            item.getFormBuilderItem().getProperties().setMandatoryError(false);
                        }
                    }
                }
            }

            if (mandatoryError) {
                writer.write("<script type=\"text/javascript\">$(function(){$(\"#mandatoryError\").show();});</script>");
            } else {
                writer.write("<script type=\"text/javascript\">$(function(){$(\"#mandatoryError\").hide();});</script>");
            }

            for (FormBuilderContainer comp : components) {
                HtmlDiv icons = new HtmlDiv();
                icons.setClassString("icons");

                addInfoIcon(comp, icons, writer);

                HtmlListItem li = new HtmlListItem();
                li.getChildren().add(icons);
                li.setClassString("ui-state-default box-runde-ecken");
                li.setStyle("width:" + comp.getHtmlfbitem().getWidth() + ";");
                li.getChildren().add(comp.getHtmlfbitem());
                li.setTransient(true);
                li.setStyle("position: relative;width:" + comp.getHtmlfbitem().getWidth() + ";");
                formFiller.getFormContent().getChildren().add(li);
            }
        } else {
            HtmlOutputText output = new HtmlOutputText();
            output.setValue("ERROR: Model of form builder is null.");
            formFiller.getChildren().add(output);
        }
    }

    @Override
    public void encodeEnd(FacesContext ctx,
            UIComponent component) throws IOException {
        ResponseWriter writer = ctx.getResponseWriter();
        FormFillerInternal formFiller = (FormFillerInternal) component;
        HtmlForm form = FormBuilderInternalRenderer.getHtmlForm(formFiller);
        writer.write("<script type=\"text/javascript\">"
                + "$(function(){"
                + "initFormFiller(\"" + form.getClientId() + "\","
                + "\"" + FormBuilderInternalRenderer.getFormActionStringId(component) + "\","
                + "\"" + FormBuilderInternalRenderer.getFormContentStringId(component) + "\""
                + ");"
                + "});"
                + "</script>");

        if (formFiller.getInvokeCallback()) {
            writer.write("<script type=\"text/javascript\">"
                    + "$(function(){"
                    + "parent.submitParentForm();"
                    + "});"
                    + "</script>");
        }
    }

    public static String extractDataUuid(String key) {
        return key.substring(key.indexOf(HtmlFormBuilderItem.DATA_UUID_PREFIX) + HtmlFormBuilderItem.DATA_UUID_PREFIX.length(), key.length());
    }

    @Override
    public void decode(FacesContext ctx, UIComponent component) {
        FormFillerInternal formFiller = (FormFillerInternal) component;
        boolean fillError = false;
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();

        String formContentString = ctx.getExternalContext().getRequestParameterMap().get(FormBuilderInternalRenderer.getFormContentStringId(component));
        String formActionString = ctx.getExternalContext().getRequestParameterMap().get(FormBuilderInternalRenderer.getFormActionStringId(component));

        boolean formSubmit = true;

        if (formActionString != null) {
            switch (formActionString) {
                case "download":
                    if (formFiller.getFormData().getData() != null) {
                        for (FormBuilderItemData data : formFiller.getFormData().getData()) {
                            if (data.getUuid().equals(formContentString)) {
                                HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                                prepareResponseFor(response, data.getFile());
                                try {
                                    streamFileTo(response, data.getFile());
                                } catch (IOException ex) {
                                    Logger.getLogger(FormFillerInternalRenderer.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    formSubmit = false;
                    break;
            }
        }

        if (formFiller.getFormData().getData() != null && formSubmit) {
            MultipartRequest multiRequest = new MultipartRequest(request);
            for (String key : multiRequest.getFiles().keySet()) {
                if (key.contains(HtmlFormBuilderItem.DATA_UUID_PREFIX)) {
                    String dataUuid = extractDataUuid(key);
                    for (FormBuilderItemData data : formFiller.getFormData().getData()) {
                        if (data.getUuid().equals(dataUuid)) {
                            data.setFile(multiRequest.getFiles().get(key));
                        }
                    }
                }
            }
            for (String key : multiRequest.getParameterMaps().keySet()) {
                if (key.contains(HtmlFormBuilderItem.DATA_UUID_PREFIX)) {
                    String dataUuid = extractDataUuid(key);
                    for (FormBuilderItemData data : formFiller.getFormData().getData()) {
                        if (data.getUuid().equals(dataUuid)) {
                            List<String> result = multiRequest.getParameterMaps().get(key);
                            if (data.getFormBuilderItem() instanceof FormBuilderItemNumber) {
                                if (result.size() == 1) {
                                    try {
                                        if (!"".equals(result.get(0).trim())) {
                                            data.setNumberValue(Float.parseFloat(result.get(0).replaceAll(",", ".")));
                                        }
                                    } catch (NumberFormatException ex) {
                                        throw new NumberFormatException("Internal error with number component. Number not parseable.");
                                    }
                                }
                                if (data.getFormBuilderItem().getProperties().getMandatory()
                                        && data.getNumberValue() == null) {
                                    fillError = true;
                                    data.getFormBuilderItem().getProperties().setMandatoryError(true);
                                }
                            } else {
                                if (result.size() == 1) {
                                    data.setValue(result.get(0));
                                } else {
                                    String tmp = "";
                                    for (String str : result) {
                                        tmp += str;
                                        tmp += ";";
                                    }
                                    data.setValue(tmp.substring(0, tmp.length() - 1));
                                }
                                if (data.getFormBuilderItem().getProperties().getMandatory()
                                        && (data.getValue() == null
                                        || "".equals(data.getValue().trim()))) {
                                    fillError = true;
                                    data.getFormBuilderItem().getProperties().setMandatoryError(true);
                                }
                            }
                        }
                    }
                }
            }
            //Check every field if it is filled
        }

        if (!fillError) {
            formFiller.setInvokeCallback(true);
        }
    }

    public void setTransient(FacesContext ctx, UIComponent comp) throws IOException {
        for (UIComponent compRekursion : comp.getChildren()) {
            comp.setTransient(true);
            setTransient(ctx, compRekursion);
        }
    }

    private void prepareResponseFor(HttpServletResponse response, File file) {
        StringBuilder type = new StringBuilder("attachment; filename=\"" + file.getFilename() + "\"");
        response.setContentLength((int) file.getFilesize());
        response.setContentType(file.getFiletype());
        response.setHeader("Content-Disposition", type.toString());
    }

    private void streamFileTo(HttpServletResponse response, File file) throws IOException {
        OutputStream os = response.getOutputStream();
        os.write(file.getFile());
        os.flush();
    }

    public static void addInfoIcon(FormBuilderContainer comp, HtmlDiv icons, ResponseWriter writer) throws IOException {
        if (comp.getFbitem().getProperties().getDescription() != null
                && !"".equals(comp.getFbitem().getProperties().getDescription())) {
            HtmlDiv info = new HtmlDiv();
            String infoUuid = "info" + UUID.randomUUID().toString();
            info.setClassString("info");
            info.setId(infoUuid);
            icons.getChildren().add(info);

            writer.write("<script type=\"text/javascript\">"
                    + "$(function(){"
                    + "$(\"#" + infoUuid + "\").tooltip({"
                    + "items : \"div\","
                    + "content : \"<span style='font-size: 10pt;'>" + comp.getFbitem().getProperties().getDescription() + "</span>\""
                    + "});"
                    + "});"
                    + "</script>");
        }
    }
}
