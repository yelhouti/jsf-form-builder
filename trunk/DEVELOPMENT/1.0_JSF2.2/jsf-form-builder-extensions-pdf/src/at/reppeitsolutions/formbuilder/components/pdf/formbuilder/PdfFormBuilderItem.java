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
package at.reppeitsolutions.formbuilder.components.pdf.formbuilder;

import at.reppeitsolutions.formbuilder.components.FormFiller;
import com.lowagie.text.Element;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemProperties;
import at.reppeitsolutions.formbuilder.components.html.renderer.multipart.File;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public abstract class PdfFormBuilderItem {

    /*
     * builds up the IText component
     */
    public abstract Element render();
    private String value;
    protected File file;
    protected String dataUuid;
    protected String itemUuid;
    protected boolean renderDescription = false;
    protected FormBuilderItemProperties properties = new FormBuilderItemProperties();
    private String mode;

    public String getValue() {
        return value;
    }
    
    public String[] getValueArray() {
        if(value != null) {
            return value.split(";");
        }
        return null;
    }
    
    protected boolean isDisabled() {
        if (getMode() == null ||
            getMode().equals(FormFiller.MODE_VIEW) ||
            (getMode().equals(FormFiller.MODE_FILL) && getProperties().getLocked())) {
            return true;
        }
        return false;
    }
    
    public String getMandatoryString() {
        String mandatory = "";
        if(getProperties().getMandatory()) {
            mandatory = "*";
        }
        return mandatory;
    } 

    public void setValue(String value) {
        this.value = value;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getItemUuid() {
        return itemUuid;
    }

    public void setItemUuid(String itemUuid) {
        this.itemUuid = itemUuid;
    }

    public FormBuilderItemProperties getProperties() {
        return properties;
    }

    public void setProperties(FormBuilderItemProperties properties) {
        this.properties = properties;
    }
    
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
}
