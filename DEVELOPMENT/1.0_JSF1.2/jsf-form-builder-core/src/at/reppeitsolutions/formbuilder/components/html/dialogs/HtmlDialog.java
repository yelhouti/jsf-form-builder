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
package at.reppeitsolutions.formbuilder.components.html.dialogs;

import at.reppeitsolutions.formbuilder.components.Constants;
import at.reppeitsolutions.formbuilder.components.annotations.IgnorePropertyInDialog;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItem;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemProperties;
import at.reppeitsolutions.formbuilder.components.html.HtmlPanelGrid;
import at.reppeitsolutions.formbuilder.components.html.HtmlTextarea;
import at.reppeitsolutions.formbuilder.components.html.renderer.HtmlBaseComponentRenderer;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import at.reppeitsolutions.formbuilder.messages.Messages;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE, tagName = "htmlDialog")
public class HtmlDialog extends UIComponentBase {

    private String uuid;
    private FormBuilderItem item;
    private String style = "";

    public HtmlDialog(FormBuilderItem item) {
        setRendererType(HtmlBaseComponentRenderer.RENDERTYPE);
        this.uuid = "diag:" + item.getId();
        this.item = item;

        HtmlPanelGrid grid = new HtmlPanelGrid();
        grid.setColumns(2);
        grid.setColumnClasses("evenColumn, oddColumn");
        getChildren().add(grid);

        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(item.getClass()).getPropertyDescriptors()) {
                if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                    setProperty(pd, grid, item.getId(), item);
                }
            }
            for (PropertyDescriptor pd : Introspector.getBeanInfo(FormBuilderItemProperties.class).getPropertyDescriptors()) {
                if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                    setProperty(pd, grid, item.getId(), item.getProperties());
                }
            }
        } catch (IntrospectionException ex) {
            Logger.getLogger(HtmlDialog.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (item.getSpecialProperties() != null && !item.getSpecialProperties().isEmpty()) {
            style = "width: 200px;";
        }

        HtmlCommandButton save = new HtmlCommandButton();
        save.setStyleClass("commandButton");
        save.setValue(Messages.getStringJSF("dialog.save"));
        save.setOnclick("saveProperties('" + item.getId() + "');");

        grid.getChildren().add(save);
    }

    private void setProperty(PropertyDescriptor pd, HtmlPanelGrid grid, String itemid, Object object) {
        try {
            Object val = pd.getReadMethod().invoke(object);
            if (val != null) {
                if ((pd.getReadMethod().getReturnType() == String.class
                        || pd.getReadMethod().getReturnType() == Integer.class
                        || pd.getReadMethod().getReturnType() == Boolean.class)
                        && !pd.getReadMethod().isAnnotationPresent(IgnorePropertyInDialog.class)) {
                    String property = pd.getDisplayName();

                    //Label
                    String labelText = property;
                    if (item.getPropertyTranslations().containsKey(property)) {
                        labelText = item.getPropertyTranslations().get(property);
                    }
                    HtmlOutputText label = new HtmlOutputText();
                    label.setValue(labelText);
                    grid.getChildren().add(label);

                    //Value
                    UIComponentBase comp = null;
                    if (item.getValueTranslations().containsKey(property)) {
                        comp = new HtmlSelectOneMenu();
                        for (Object value : item.getValueTranslations().get(property).keySet()) {
                            String translation = (String) item.getValueTranslations().get(property).get(value);
                            UISelectItem selectItem = new UISelectItem();
                            selectItem.setItemLabel(translation);
                            selectItem.setItemValue(value);
                            comp.getChildren().add(selectItem);
                        }
                        ((HtmlSelectOneMenu) comp).setValue(val);
                    } else {
                        if (!item.getSpecialProperties().containsKey(property)) {
                            if (pd.getReadMethod().getReturnType() == String.class
                                    || pd.getReadMethod().getReturnType() == Integer.class) {
                                comp = new HtmlInputText();
                                ((HtmlInputText) comp).setValue(val);
                            } else if(pd.getReadMethod().getReturnType() == Boolean.class) {
                                comp = new HtmlSelectBooleanCheckbox();
                                UISelectItem box = new UISelectItem();
                                if((Boolean)val) {
                                    ((HtmlSelectBooleanCheckbox)comp).setSelected(Boolean.TRUE);
                                } else {
                                    ((HtmlSelectBooleanCheckbox)comp).setSelected(Boolean.FALSE);
                                }
                                comp.getChildren().add(box);
                            }
                        } else {
                            if (item.getSpecialProperties().get(property) == FormBuilderItemBase.SPECIALPROPERTY.TEXTAREA) {
                                comp = new HtmlTextarea();
                                HtmlTextarea textarea = ((HtmlTextarea) comp);
                                textarea.setValue((String) val);
                                textarea.setRows(4);
                                textarea.setCols(40);
                            }
                        }
                    }
                    if (comp != null) {
                        comp.getPassThroughAttributes().put("itemid", itemid);
                        comp.getPassThroughAttributes().put("class", "property-" + itemid);
                        comp.getPassThroughAttributes().put("method", property);
                        if (val != null) {
                            grid.getChildren().add(comp);
                        }
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HtmlDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(HtmlDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(HtmlDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("<div id=\"" + getUuid() + "\" class=\"dialog\" style=\"" + style + "\">");
    }

    @Override
    public void encodeEnd(FacesContext ctx) throws IOException {
        ResponseWriter writer = ctx.getResponseWriter();
        writer.write("</div>");
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getFamily() {
        return HtmlBaseComponentRenderer.FAMILY;
    }
}
