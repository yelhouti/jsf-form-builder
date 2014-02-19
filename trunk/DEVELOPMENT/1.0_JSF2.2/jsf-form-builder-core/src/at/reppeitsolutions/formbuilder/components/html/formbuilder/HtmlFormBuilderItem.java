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

import at.reppeitsolutions.formbuilder.components.FormFiller;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemProperties;
import at.reppeitsolutions.formbuilder.components.html.HtmlDiv;
import at.reppeitsolutions.formbuilder.components.html.renderer.HtmlBaseComponentRenderer;
import at.reppeitsolutions.formbuilder.components.html.renderer.multipart.File;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlOutputText;
import at.reppeitsolutions.formbuilder.messages.Messages;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public abstract class HtmlFormBuilderItem extends UIComponentBase {

    /*
     * builds up the UIComponents
     */
    protected abstract void renderView();
    protected String value;
    protected Float numberValue;
    protected File file;
    protected String dataUuid;
    protected String itemUuid;
    protected boolean renderDescription = false;
    protected FormBuilderItemProperties properties = new FormBuilderItemProperties();
    public final static String DATA_UUID_PREFIX = "DATA";
    private String mode;

    public void render() {
        if (properties.getRenderDescription()) {
            renderDescription();
        } else {
            renderView();
        }
    }

    protected void renderDescription() {
        HtmlOutputText output = new HtmlOutputText();
        output.setValue(Messages.getStringJSF(this.getClass().getSimpleName()));
        output.setTransient(true);
        getChildren().add(output);
    }
    
    protected void addLabeledComponent(HtmlOutputText label, UIComponent output, String style) {
        if (properties == null
                || properties.getLabelLength() == -1) {
            getChildren().add(label);
            getChildren().add(output);
        } else {
            HtmlDiv div = new HtmlDiv();
            if(properties.getOnelinedescription() != null && 
               properties.getOnelinedescription()) {
                div.setStyle("width: 100%;");
            } else {
                div.setStyle("width: " + properties.getLabelLength() + "ex; float: left;");
            }
            
            String labelString = (String)label.getValue();
            if(labelString != null && labelString.trim().isEmpty()) {
                HtmlOutputText span = new HtmlOutputText();
                span.setValue("&nbsp");
                span.setEscape(false);
                div.getChildren().add(span);
            } else {
                div.getChildren().add(label);
            }

            getChildren().add(div);

            div = new HtmlDiv();
            div.setStyle("overflow: hidden;");
            div.getChildren().add(output);
            output.getPassThroughAttributes().put("style", style);
            getChildren().add(div); 
        }
    }

    protected void addLabeledComponent(HtmlOutputText label, UIComponent output) {
        addLabeledComponent(label, output, "width: 100%; margin-right: 10px;");
    }
    
    protected boolean isDisabled() {
        if (getMode() == null ||
            getMode().equals(FormFiller.MODE_VIEW) ||
            (getMode().equals(FormFiller.MODE_FILL) && getProperties().getLocked())) {
            return true;
        }
        return false;
    }

    @Override
    public String getFamily() {
        return HtmlBaseComponentRenderer.FAMILY;
    }

    public String getWidth() {
        return "600px";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Float getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Float numberValue) {
        this.numberValue = numberValue;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getDataUuid() {
        return dataUuid;
    }

    public void setDataUuid(String dataUuid) {
        if (!dataUuid.startsWith(DATA_UUID_PREFIX)) {
            this.dataUuid = DATA_UUID_PREFIX + dataUuid;
        } else {
            this.dataUuid = dataUuid;
        }
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
