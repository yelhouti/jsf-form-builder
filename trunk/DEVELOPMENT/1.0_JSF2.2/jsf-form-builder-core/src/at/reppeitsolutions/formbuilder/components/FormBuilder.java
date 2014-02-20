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

import at.reppeitsolutions.formbuilder.components.html.HtmlIFrame;
import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormBuilderRenderer;
import java.util.UUID;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.html.HtmlCommandButton;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE, tagName = "formBuilder")
@ResourceDependencies(value = {
    @ResourceDependency(library = "javax.faces", name = "jsf.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery-1.9.1.js"),
    @ResourceDependency(library = "formbuilder", name = "formbuilderiframe.css"),
    @ResourceDependency(library = "formbuilder", name = "js/iframe.js")
})
public class FormBuilder extends FormBuilderComponentBase  {
    
    private HtmlCommandButton callbackButton;
    private AjaxBehavior ajax;
    
    public FormBuilder() {
        setRendererType(FormBuilderRenderer.RENDERTYPE);
        
        iframe = new HtmlIFrame();
        iframe.setStyle("width: 1040px;");
        iframe.setBorder(0);
        iframe.setScrolling(false);
        iframe.setId("iframe" + UUID.randomUUID().toString());
        getChildren().add(iframe);
               
        callbackButton = new HtmlCommandButton();
        callbackButton.setId("callbackbutton");
        ajax = new AjaxBehavior();
        callbackButton.addClientBehavior("action", ajax);
        callbackButton.setStyle("display:none;");
        getChildren().add(callbackButton);
    }

    public HtmlCommandButton getCallbackButton() {
        return callbackButton;
    }

    public AjaxBehavior getAjax() {
        return ajax;
    }

    public void setAjax(AjaxBehavior ajax) {
        this.ajax = ajax;
    }

    @Override
    public String getFamily() {
        return FormBuilderRenderer.FAMILY;
    }
    
}
