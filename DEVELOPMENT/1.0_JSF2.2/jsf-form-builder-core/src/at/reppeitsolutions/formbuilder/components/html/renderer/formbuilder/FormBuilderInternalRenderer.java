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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import at.reppeitsolutions.formbuilder.components.Constants;
import at.reppeitsolutions.formbuilder.components.FormBuilderInternal;
import at.reppeitsolutions.formbuilder.components.annotations.SkipDialog;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBaseHelper;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemConstraint;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemDownload;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemFormatArea;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemImage;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderContainer;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemAddConstraint;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemDeleteConstraint;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemFactory;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemUpdate;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemUpdateHolder;
import at.reppeitsolutions.formbuilder.components.html.HtmlDiv;
import at.reppeitsolutions.formbuilder.components.html.HtmlListItem;
import at.reppeitsolutions.formbuilder.components.html.HtmlUnorderedList;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderItem;
import at.reppeitsolutions.formbuilder.components.html.renderer.multipart.MultipartRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import at.reppeitsolutions.formbuilder.messages.Messages;
import at.reppeitsolutions.formbuilder.model.Constraint;
import at.reppeitsolutions.formbuilder.model.ConstraintClient;
import at.reppeitsolutions.formbuilder.model.ConstraintType;
import at.reppeitsolutions.formbuilder.model.Form;
import at.reppeitsolutions.formbuilder.model.WorkflowState;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(componentFamily = FormBuilderInternalRenderer.FAMILY, rendererType = FormBuilderInternalRenderer.RENDERTYPE)
public class FormBuilderInternalRenderer extends Renderer {

    public static final String RENDERTYPE = "FormBuilderInternalRenderer";
    public static final String FAMILY = "at.rits.formbuilderinternal";

    @Override
    public void encodeBegin(FacesContext ctx,
            UIComponent component) throws IOException {
        ResponseWriter writer = ctx.getResponseWriter();
        FormBuilderInternal formBuilder = (FormBuilderInternal) component;
        HtmlForm form = getHtmlForm(formBuilder);
        form.setTransient(true);
        for (HtmlUnorderedList palette : formBuilder.getPalettes()) {
            palette.setTransient(true);
            palette.setId(form.getId() + "palette" + UUID.randomUUID().toString());
        }
        formBuilder.getFormContent().setTransient(true);
        formBuilder.getFormContent().setId(form.getId() + "formContent" + UUID.randomUUID().toString());

        Form formModel = formBuilder.getForm();

        if (formModel != null) {
            List<FormBuilderContainer> components = new ArrayList<>();

            if (formModel.getItems() != null) {
                String activeConstraint = null;
                for (FormBuilderItemBase item : formModel.getItems()) {
                    HtmlFormBuilderItem htmlItem = null;
                    if (item instanceof FormBuilderItemConstraint && activeConstraint == null) {
                        htmlItem = FormBuilderItemFactory.getUIComponentWithDialog(item, formBuilder);
                        activeConstraint = item.getId();
                    } else {
                        htmlItem = FormBuilderItemFactory.getUIComponentWithDialog(item);
                        if (item instanceof FormBuilderItemConstraint) {
                            activeConstraint = null;
                        }
                    }
                    components.add(new FormBuilderContainer(item, htmlItem));
                }
                if (formModel.getItems().isEmpty()) {
                    addPlaceholder(formBuilder);
                }
            }

            for (FormBuilderContainer comp : components) {
                HtmlListItem li = new HtmlListItem();
                HtmlDiv icons = new HtmlDiv();
                icons.setClassString("icons");

                addSettingsIcon(comp, icons);
                addDeleteIcon(comp, icons);
                FormFillerInternalRenderer.addInfoIcon(comp, icons, writer);

                li.getChildren().add(icons);
                li.setClassString("ui-state-default box-runde-ecken");
                li.setStyle("width:" + comp.getHtmlfbitem().getWidth() + ";");
                li.getChildren().add(comp.getHtmlfbitem());
                li.setTransient(true);
                formBuilder.getFormContent().getChildren().add(li);
            }
        } else {
            formBuilder.getChildren().clear();
            HtmlOutputText output = new HtmlOutputText();
            output.setValue("ERROR: Model of form builder is null.");
            formBuilder.getChildren().add(output);
        }
    }

    @Override
    public void encodeEnd(FacesContext ctx,
            UIComponent component) throws IOException {
        ResponseWriter writer = ctx.getResponseWriter();
        FormBuilderInternal formBuilder = (FormBuilderInternal) component;
        HtmlForm form = getHtmlForm(formBuilder);

        String paletteIds = "";
        for (HtmlUnorderedList palette : formBuilder.getPalettes()) {
            paletteIds += palette.getId() + ";";
        }
        paletteIds = paletteIds.substring(0, paletteIds.length() - 1);

        writer.write("<script type=\"text/javascript\">"
                + "$(function(){"
                + "initFormBuilder(\"" + form.getClientId() + "\","
                + "\"" + paletteIds + "\","
                + "\"" + formBuilder.getFormContent().getId() + "\","
                + "\"" + getFormActionStringId(component) + "\","
                + "\"" + getFormContentStringId(component) + "\","
                + "\"" + getFormActiveTabStringId(component) + "\""
                + ");"
                + "});"
                + "</script>");

        if (formBuilder.getInvokeCallback()) {
            writer.write("<script type=\"text/javascript\">"
                    + "$(function(){"
                    + "parent.buffer();"
                    + "});"
                    + "</script>");
        }
    }

    public static UIComponent getForm(UIComponent comp) {
        if (comp.getParent() == null) {
            return null;
        } else if (comp.getParent() instanceof HtmlForm) {
            return comp.getParent();
        } else {
            return getForm(comp.getParent());
        }
    }

    @Override
    public void decode(FacesContext ctx, UIComponent component) {
        FormBuilderInternal formBuilder = (FormBuilderInternal) component;
        formBuilder.setInvokeCallback(true);
        String formContentString = ctx.getExternalContext().getRequestParameterMap().get(getFormContentStringId(component));
        String formActionString = ctx.getExternalContext().getRequestParameterMap().get(getFormActionStringId(component));

        if (formActionString != null && !"".equals(formActionString)) {
            ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            switch (formActionString) {
                case "update":
                    String[] contents = formContentString.split(Constants.STRINGSEPERATOR);
                    List<FormBuilderItemBase> list = new ArrayList<>();
                    Map<String, FormBuilderItemBase> cachedObjects = new HashMap<>();
                    String activeFormatArea = null;
                    String activeConstraint = null;
                    try {
                        int positionOffset = 0;
                        for (String content : contents) {
                            FormBuilderItemBaseHelper item = mapper.readValue(content, FormBuilderItemBaseHelper.class);
                            try {
                                Class<FormBuilderItemBase> cls = (Class<FormBuilderItemBase>) Class.forName(item.getClassname());
                                Object o = cls.cast(mapper.readValue(content, cls));
                                //Start image specific code
                                if (o instanceof FormBuilderItemImage || o instanceof FormBuilderItemDownload) {
                                    FormBuilderItemBase object = (FormBuilderItemBase) o;
                                    for (FormBuilderItemBase tmpItem : formBuilder.getForm().getItems()) {
                                        if (tmpItem.getId().equals(object.getId())) {
                                            cachedObjects.put(tmpItem.getId(), tmpItem);
                                        }
                                    }
                                }
                                //End image specific code
                                FormBuilderItemBase itemBase = (FormBuilderItemBase) o;
                                itemBase.setPosition(itemBase.getPosition() + positionOffset);
                                itemBase.initConstraintsFromConstraintsString(formBuilder.getWorkflowStates(), formBuilder.getConstraintClients());
                                list.add((FormBuilderItemBase) o);
                                //Start format area specific code
                                if (o instanceof FormBuilderItemFormatArea || o instanceof FormBuilderItemConstraint) {
                                    FormBuilderItemBase area = (FormBuilderItemBase) o;
                                    if (area.getProperties().getBrother() == null) {
                                        HashMap<String, String> areasMap = new HashMap<>();
                                        HashMap<String, String> constraintsMap = new HashMap<>();
                                        for (FormBuilderItemBase tmpItem : formBuilder.getForm().getItems()) {
                                            if (tmpItem instanceof FormBuilderItemFormatArea
                                                    && !areasMap.containsKey(tmpItem.getProperties().getFormatareauuid())) {
                                                areasMap.put(tmpItem.getProperties().getFormatareauuid(), null);
                                            }
                                            if (tmpItem instanceof FormBuilderItemConstraint
                                                    && !areasMap.containsKey(tmpItem.getProperties().getFormatareauuid())) {
                                                constraintsMap.put(tmpItem.getProperties().getFormatareauuid(), null);
                                            }
                                        }
                                        int areas = 1 + areasMap.size();
                                        int constrains = 1 + constraintsMap.size();
                                        FormBuilderItemBase brother = null;
                                        if (o instanceof FormBuilderItemFormatArea) {
                                            brother = new FormBuilderItemFormatArea();
                                        } else {
                                            brother = new FormBuilderItemConstraint();
                                        }
                                        brother.getProperties().setBrother(area.getId());
                                        brother.setBrother(area);
                                        brother.setPosition(area.getPosition() + 1);
                                        area.getProperties().setBrother(brother.getId());
                                        area.setBrother(brother);
                                        list.add(brother);
                                        String formatAreaUuid = UUID.randomUUID().toString();
                                        area.getProperties().setFormatareauuid(formatAreaUuid);
                                        brother.getProperties().setFormatareauuid(formatAreaUuid);
                                        if (o instanceof FormBuilderItemFormatArea) {
                                            area.getProperties().setValues(area.getProperties().getValues() + " " + areas);
                                            brother.getProperties().setValues(brother.getProperties().getValues() + " " + areas);
                                        } else {
                                            area.getProperties().setValues(area.getProperties().getValues() + " " + constrains);
                                            brother.getProperties().setValues(brother.getProperties().getValues() + " " + constrains);
                                        }
                                        positionOffset++;
                                    }
                                    if (o instanceof FormBuilderItemFormatArea) {
                                        if (activeFormatArea == null) {
                                            activeFormatArea = area.getProperties().getFormatareauuid();
                                        } else if (activeFormatArea.equals(area.getProperties().getFormatareauuid())) {
                                            activeFormatArea = null;
                                        } else {
                                            throw new RuntimeException(Messages.getStringJSF("formatarea.error"));
                                        }
                                    } else {
                                        if (activeConstraint == null) {
                                            activeConstraint = area.getProperties().getFormatareauuid();
                                        } else if (activeConstraint.equals(area.getProperties().getFormatareauuid())) {
                                            activeConstraint = null;
                                        } else {
                                            throw new RuntimeException(Messages.getStringJSF("constraint.error"));
                                        }
                                    }
                                }
                                //End format area specific code
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(FormBuilderInternalRenderer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FormBuilderInternalRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    formBuilder.getForm().getItems().clear();
                    formBuilder.getForm().addItems(list);
                    //Start image specific code
                    if (!cachedObjects.isEmpty()) {
                        for (FormBuilderItemBase tmpItem : formBuilder.getForm().getItems()) {
                            if ((tmpItem instanceof FormBuilderItemImage || tmpItem instanceof FormBuilderItemDownload)
                                    && cachedObjects.containsKey(tmpItem.getId())) {
                                tmpItem.getProperties().setFile(cachedObjects.get(tmpItem.getId()).getProperties().getFile());
                            }
                        }
                    }
                    HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
                    MultipartRequest multiRequest = new MultipartRequest(request);
                    for (String key : multiRequest.getFiles().keySet()) {
                        for (FormBuilderItemBase item : formBuilder.getForm().getItems()) {
                            if (key.contains(item.getId())) {
                                item.getProperties().setFile(multiRequest.getFiles().get(key));
                            }
                        }
                    }
                    //End image specific code                    
                    break;
                case "edit":
                    try {
                        FormBuilderItemUpdateHolder updateHolder = mapper.readValue(formContentString, FormBuilderItemUpdateHolder.class);
                        List<FormBuilderItemBase> items = formBuilder.getForm().getItems();
                        for (FormBuilderItemBase item : items) {
                            if (item.getId().equals(updateHolder.getItemId())) {
                                FormBuilderItemFactory.updateFormBuilderItem(item, updateHolder.getUpdates());
                                if (item instanceof FormBuilderItemFormatArea || item instanceof FormBuilderItemConstraint) {
                                    for (FormBuilderItemBase item2 : items) {
                                        if (item2.getProperties().getBrother() != null
                                                && (item2 instanceof FormBuilderItemFormatArea || item2 instanceof FormBuilderItemConstraint)
                                                && item2.getProperties().getBrother().equals(item.getId())) {
                                            Iterator<FormBuilderItemUpdate> updateIter = updateHolder.getUpdates().iterator();
                                            while (updateIter.hasNext()) {
                                                FormBuilderItemUpdate update = updateIter.next();
                                                if (update.getMethod().equals("brother")) {
                                                    updateIter.remove();
                                                }
                                            }
                                            FormBuilderItemFactory.updateFormBuilderItem(item2, updateHolder.getUpdates());
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FormBuilderInternalRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "delete":
                    List<FormBuilderItemBase> items = formBuilder.getForm().getItems();
                    Iterator<FormBuilderItemBase> itemIter = items.iterator();
                    String brotherToDelete = null;
                    while (itemIter.hasNext()) {
                        FormBuilderItemBase item = itemIter.next();
                        if (item.getId().equals(formContentString)) {
                            if (item instanceof FormBuilderItemFormatArea || item instanceof FormBuilderItemConstraint) {
                                brotherToDelete = item.getProperties().getBrother();
                            }
                            itemIter.remove();
                            break;
                        }
                    }
                    if (brotherToDelete != null) {
                        itemIter = items.iterator();
                        while (itemIter.hasNext()) {
                            FormBuilderItemBase item = itemIter.next();
                            if (item.getId().equals(brotherToDelete)) {
                                itemIter.remove();
                                break;
                            }
                        }
                    }
                    break;
                case "addconstraint":
                    try {
                        FormBuilderItemAddConstraint addConstraint = mapper.readValue(formContentString, FormBuilderItemAddConstraint.class);
                        for (FormBuilderItemBase tmpItem : formBuilder.getForm().getItems()) {
                            if (tmpItem.getId().equals(addConstraint.getItemUuid())) {
                                WorkflowState workflowState = null;
                                ConstraintClient constraintClient = null;
                                ConstraintType constraintType = null;
                                if (formBuilder.getWorkflowStates() != null) {
                                    for (WorkflowState tmpWorkflowState : formBuilder.getWorkflowStates()) {
                                        if (tmpWorkflowState.getUuid().equals(addConstraint.getWorkflowState())) {
                                            workflowState = tmpWorkflowState;
                                            break;
                                        }
                                    }
                                }
                                if (formBuilder.getConstraintClients() != null) {
                                    for (ConstraintClient tmpConstraintClient : formBuilder.getConstraintClients()) {
                                        if (tmpConstraintClient.getUuid().equals(addConstraint.getConstraintClient())) {
                                            constraintClient = tmpConstraintClient;
                                            break;
                                        }
                                    }
                                }
                                for (ConstraintType tmpConstraintType : ConstraintType.values()) {
                                    if (tmpConstraintType.name().equals(addConstraint.getConstraintType())) {
                                        constraintType = tmpConstraintType;
                                        break;
                                    }
                                }
                                tmpItem.addConstraintClient(constraintClient, workflowState, constraintType);
                                tmpItem.getProperties().setMaximise(Boolean.TRUE);
                                break;
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FormBuilderInternalRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "deleteconstraint":
                    try {
                        FormBuilderItemDeleteConstraint deleteConstraint = mapper.readValue(formContentString, FormBuilderItemDeleteConstraint.class);
                        for (FormBuilderItemBase tmpItem : formBuilder.getForm().getItems()) {
                            if (tmpItem.getId().equals(deleteConstraint.getItemUuid())) {
                                boolean deleted = false;
                                for (Iterator<Constraint> it = tmpItem.getConstraints().iterator(); it.hasNext();) {
                                    Constraint constraint = it.next();
                                    if (constraint.hashCode() == Integer.parseInt(deleteConstraint.getHashCode())) {
                                        it.remove();
                                        tmpItem.getProperties().setMaximise(Boolean.TRUE);
                                        deleted = true;
                                        break;
                                    }
                                }
                                if (deleted) {
                                    break;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FormBuilderInternalRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        }
    }

    public void setTransient(FacesContext ctx, UIComponent comp) throws IOException {
        for (UIComponent compRekursion : comp.getChildren()) {
            comp.setTransient(true);
            setTransient(ctx, compRekursion);
        }
    }

    public static HtmlForm getHtmlForm(UIComponent formBuilder) throws IOException {
        HtmlForm form = (HtmlForm) getForm(formBuilder);
        if (form == null) {
            throw new IOException("formBuilder must be inside a h:form tag");
        }
        return form;
    }

    public static String getFormActiveTabStringId(UIComponent component) {
        return getForm(component).getId() + Constants.sep + FormBuilderInternal.FORMACTIVETABSTRING;
    }

    public static String getFormActionStringId(UIComponent component) {
        return getForm(component).getId() + Constants.sep + FormBuilderInternal.FORMACTIONSTRING;
    }

    public static String getFormContentStringId(UIComponent component) {
        return getForm(component).getId() + Constants.sep + FormBuilderInternal.FORMCONTENTSTRING;
    }

    private void addPlaceholder(FormBuilderInternal formBuilder) {
        HtmlOutputText placeholderText = new HtmlOutputText();
        placeholderText.setValue(Messages.getStringJSF("emptyform.info"));

        HtmlListItem placeholder = new HtmlListItem();
        placeholder.setClassString("placeholder");
        placeholder.getChildren().add(placeholderText);
        placeholder.setTransient(true);
        placeholder.setStyle("border: 0px solid black;font-size: 10pt;");

        formBuilder.getFormContent().getChildren().add(placeholder);
    }

    public void addSettingsIcon(FormBuilderContainer comp, HtmlDiv icons) {
        if (!comp.getHtmlfbitem().getChildren().get(0).getClass().isAnnotationPresent(SkipDialog.class)) {
            HtmlDiv settings = new HtmlDiv();
            settings.setClassString("settings");
            settings.setOnClick("openDialog('" + comp.getFbitem().getId() + "');");
            icons.getChildren().add(settings);
        }
    }

    public void addDeleteIcon(FormBuilderContainer comp, HtmlDiv icons) {
        HtmlDiv delete = new HtmlDiv();
        delete.setClassString("delete");
        delete.setOnClick("deleteItem('" + comp.getFbitem().getId() + "');");
        icons.getChildren().add(delete);
    }
}
