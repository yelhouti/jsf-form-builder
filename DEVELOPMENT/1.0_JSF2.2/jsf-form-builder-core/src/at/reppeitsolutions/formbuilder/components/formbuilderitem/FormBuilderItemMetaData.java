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

import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemFactory;
import at.reppeitsolutions.formbuilder.components.helper.MetaDataDescription;
import java.io.Serializable;
import javax.persistence.Entity;
import at.reppeitsolutions.formbuilder.messages.Messages;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity
public class FormBuilderItemMetaData extends FormBuilderItemBase implements Serializable {

    public FormBuilderItemMetaData() {
        formbuildertype = FormBuilderItemFactory.TYPE_METADATA;
        properties = new FormBuilderItemProperties();
    }
    
    public FormBuilderItemMetaData(MetaDataDescription description, Object object) {
        this();
        try {
            for(PropertyDescriptor pd : Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors()) {
                if (pd.getReadMethod() != null && description.getGetter().equals(pd.getName())) {
                    properties.setValues((String)pd.getReadMethod().invoke(object));
                    break;
                }
            }
        } catch (IntrospectionException ex) {
            Logger.getLogger(FormBuilderItemMetaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FormBuilderItemMetaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FormBuilderItemMetaData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FormBuilderItemMetaData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public FormBuilderItemMetaData(boolean renderDescription, MetaDataDescription metaDataDescription) {
        this();
        properties.setMetadatadescription(metaDataDescription.getDescription());
        properties.setMetadatagetter(metaDataDescription.getGetter());
        properties.setRenderDescription(renderDescription);
    }
    
}
