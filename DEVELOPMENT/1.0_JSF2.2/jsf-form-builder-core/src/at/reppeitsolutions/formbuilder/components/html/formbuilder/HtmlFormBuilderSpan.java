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

import at.reppeitsolutions.formbuilder.components.annotations.IgnoreProperty;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemProperties;
import at.reppeitsolutions.formbuilder.components.html.renderer.HtmlFormBuilderSpanRenderer;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class HtmlFormBuilderSpan extends HtmlFormBuilderItem {

    FormBuilderItemBase fbitem;

    public HtmlFormBuilderSpan(FormBuilderItemBase fbitem) {
        this.fbitem = fbitem;
    }

    @Override
    public String getFamily() {
        return HtmlFormBuilderSpanRenderer.FAMILY;
    }

    private String getProperties(String properties, String prefix, PropertyDescriptor[] pds, Object item) {
        for (PropertyDescriptor pd : pds) {
            if (item != null && pd.getReadMethod() != null
                    && !"class".equals(pd.getName())
                    && !"properties".equals(pd.getName())
                    && !pd.getReadMethod().isAnnotationPresent(IgnoreProperty.class)) {
                try {
                    properties += prefix + pd.getDisplayName() + "=\"" + pd.getReadMethod().invoke(item) + "\" ";
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(HtmlFormBuilderSpan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(HtmlFormBuilderSpan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(HtmlFormBuilderSpan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return properties;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        try {
            ResponseWriter writer = context.getResponseWriter();
            String props = getProperties("", "", Introspector.getBeanInfo(FormBuilderItemBase.class).getPropertyDescriptors(), fbitem);
            props = getProperties(props, "properties_", Introspector.getBeanInfo(FormBuilderItemProperties.class).getPropertyDescriptors(), fbitem.getProperties());
            writer.write("<span " + props + ">");
        } catch (IntrospectionException ex) {
            Logger.getLogger(HtmlFormBuilderSpan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("</span>");
    }

    @Override
    public void renderView() {
        //do nothing
    }

    @Override
    public String getWidth() {
        if(fbitem.getProperties() != null && fbitem.getProperties().getRenderDescription()) {
            return "175px";
        } else {
            return fbitem.getWidth();
        }
    }
}
