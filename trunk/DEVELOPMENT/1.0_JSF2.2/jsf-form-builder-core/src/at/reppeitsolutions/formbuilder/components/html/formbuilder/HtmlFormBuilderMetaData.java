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

import at.reppeitsolutions.formbuilder.components.annotations.SkipDialog;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.helper.MetaDataDescription;
import at.reppeitsolutions.formbuilder.components.html.HtmlCustomOutputLabel;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@SkipDialog
public class HtmlFormBuilderMetaData extends HtmlFormBuilderItem {

    private Object metaDataObject;

    public HtmlFormBuilderMetaData() {
    }

    public HtmlFormBuilderMetaData(Object metaDataObject) {
        this.metaDataObject = metaDataObject;
    }

    @Override
    public void renderView() {
        HtmlOutputText outputValue = new HtmlOutputText();
        outputValue.setEscape(false);

        if (metaDataObject != null) {
            try {
                for (PropertyDescriptor pd : Introspector.getBeanInfo(metaDataObject.getClass()).getPropertyDescriptors()) {
                    if (pd.getReadMethod() != null && properties.getMetadatagetter().equals(pd.getName())) {
                        try {
                            outputValue.setValue((String) pd.getReadMethod().invoke(metaDataObject));
                            break;
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(HtmlFormBuilderMetaData.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(HtmlFormBuilderMetaData.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(HtmlFormBuilderMetaData.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (IntrospectionException ex) {
                Logger.getLogger(HtmlFormBuilderMetaData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        properties.setLabel(properties.getMetadatadescription());
        HtmlCustomOutputLabel output = new HtmlCustomOutputLabel(properties);

        addLabeledComponent(output, outputValue, null);
    }
}
