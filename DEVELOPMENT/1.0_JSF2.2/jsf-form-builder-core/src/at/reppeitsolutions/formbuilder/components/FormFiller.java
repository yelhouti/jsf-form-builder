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
package at.reppeitsolutions.formbuilder.components;

import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormData;
import at.reppeitsolutions.formbuilder.components.html.HtmlDiv;
import at.reppeitsolutions.formbuilder.components.html.HtmlUnorderedList;
import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormFillerRenderer;
import at.reppeitsolutions.formbuilder.messages.Messages;
import javax.annotation.PostConstruct;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlInputHidden;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE, tagName = "formFillerInternal")
@ResourceDependencies(value = {
    @ResourceDependency(library = "javax.faces", name = "jsf.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery-1.9.1.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery-ui-1.10.3.custom.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery-ui-1.10.3.custom.min.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery.timepicker.min.js"),
    @ResourceDependency(library = "formbuilder", name = "css/smoothness/jquery-ui-1.10.3.custom.css"),
    @ResourceDependency(library = "formbuilder", name = "css/smoothness/jquery-ui-1.10.3.custom.min.css"),
    @ResourceDependency(library = "formbuilder", name = "jquery.listAttributes.js"),
    @ResourceDependency(library = "formbuilder", name = "jquery.timepicker.css"),
    @ResourceDependency(library = "formbuilder", name = "formbuilder.js"),
    @ResourceDependency(library = "formbuilder", name = "formbuilder.css"),})
public class FormFiller extends FormComponent {
    
    public FormFiller() {
        setRendererType(FormFillerRenderer.RENDERTYPE);
        
        HtmlInputHidden formActionString = new HtmlInputHidden();
        formActionString.setValue("");
        formActionString.setId(FormBuilder.FORMACTIONSTRING);

        HtmlInputHidden formContentString = new HtmlInputHidden();
        formContentString.setValue("");
        formContentString.setId(FormBuilder.FORMCONTENTSTRING);
        
        HtmlInputHidden propDialogHeader = new HtmlInputHidden();
        propDialogHeader.setValue(Messages.getStringJSF("dialog.header"));
        propDialogHeader.setId("prop-dialog-header");
        
        HtmlDiv holder = new HtmlDiv(); 
        holder.setId("holderfiller");
        
        holder.getChildren().add(formActionString);
        holder.getChildren().add(formContentString);
        
        formContent = new HtmlUnorderedList();
        formContent.setClassString("sortable2");
        
        HtmlDiv contentHolder = new HtmlDiv();
        contentHolder.setId("contentHolder");
        contentHolder.getChildren().add(formContent);
        
        holder.getChildren().add(contentHolder);
        
        getChildren().add(holder);
        
        HtmlDiv div = new HtmlDiv();
        div.setStyle("clear:left;");        
        getChildren().add(div);
    }

    public FormData getModel() {
        return (FormData) getStateHelper().eval("model");
    }

    public void setModel(FormData model) {
        getStateHelper().put("model", model);
    }

    @Override
    public String getFamily() {
        return FormFillerRenderer.FAMILY;
    }
}
