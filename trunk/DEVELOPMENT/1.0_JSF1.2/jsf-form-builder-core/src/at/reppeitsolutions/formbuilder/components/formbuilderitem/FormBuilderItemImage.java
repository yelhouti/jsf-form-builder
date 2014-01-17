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
import java.io.Serializable;
import javax.persistence.Entity;
import at.reppeitsolutions.formbuilder.messages.Messages;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity
public class FormBuilderItemImage extends FormBuilderItemBase implements Serializable {
    
    public FormBuilderItemImage() {
        formbuildertype = FormBuilderItemFactory.TYPE_IMAGE;
        properties = new FormBuilderItemProperties();
        properties.setWidthproperty(100);
        
        addPropertyTranslation("widthproperty", Messages.getStringJSF("image.label.width"));
    }
    
    public FormBuilderItemImage(boolean renderDescription) {
        this();
        properties.setRenderDescription(renderDescription);
    }
    
    @Override
    public boolean getSkipRendering() {
        if(properties.getFile() == null) {
            return true;
        }
        return false;
    }
    
}
