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
import javax.persistence.Entity;
import at.reppeitsolutions.formbuilder.messages.Messages;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity
public class FormBuilderItemTextarea extends FormBuilderItemBase {
    
    public FormBuilderItemTextarea() {
        formbuildertype = FormBuilderItemFactory.TYPE_TEXTAREA;
        properties = new FormBuilderItemProperties();
        properties.setLabel(Messages.getStringJSF("textarea.default.label"));
        properties.setRows(4);
        properties.setValues("");
        properties.setOnelinedescription(Boolean.FALSE);
        properties.setDescription("");
        
        addPropertyTranslation("onelinedescription", Messages.getStringJSF("onelinedescription"));
        addPropertyTranslation("label", Messages.getStringJSF("textarea.label"));
        addPropertyTranslation("rows", Messages.getStringJSF("textarea.rows"));
        addPropertyTranslation("cols", Messages.getStringJSF("textarea.cols"));
        addPropertyTranslation("values", Messages.getStringJSF("textarea.values"));
        addPropertyTranslation("onelinedescription", Messages.getStringJSF("onelinedescription"));
        addPropertyTranslation("description", Messages.getStringJSF("description"));
    }
    
    public FormBuilderItemTextarea(boolean renderDescription) {
        this();
        properties.setRenderDescription(renderDescription);
    }
    
}
