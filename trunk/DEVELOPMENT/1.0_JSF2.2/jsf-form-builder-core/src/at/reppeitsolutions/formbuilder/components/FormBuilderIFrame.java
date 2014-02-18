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
import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormBuilderIFrameRenderer;
import at.reppeitsolutions.formbuilder.model.Form;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputHidden;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE, tagName = "formBuilder")
@ResourceDependencies(value = {
    @ResourceDependency(library = "javax.faces", name = "jsf.js")
})
public class FormBuilderIFrame extends FormComponent {
    
    private HtmlIFrame iframe;
    private HtmlCommandButton callbackButton;
    private AjaxBehavior ajax;
    private HtmlInputHidden inputhidden;
    
    public FormBuilderIFrame() {
        setRendererType(FormBuilderIFrameRenderer.RENDERTYPE);
        iframe = new HtmlIFrame();
        iframe.setStyle("width: 1040px; height: 620px;");
        iframe.setBorder(0);
        iframe.setScrolling(false);
        getChildren().add(iframe);
               
        callbackButton = new HtmlCommandButton();
        callbackButton.setId("callbackbutton");
        ajax = new AjaxBehavior();
        callbackButton.addClientBehavior("action", ajax);
        callbackButton.setStyle("display:none;");
        getChildren().add(callbackButton);
    }
    
    public HtmlIFrame getIFrame() {
        return iframe;
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
    
    public Form getModel() {
        return (Form) getStateHelper().eval("model");
    }

    public void setModel(Form model) {
        getStateHelper().put("model", model);
    }

    @Override
    public String getFamily() {
        return FormBuilderIFrameRenderer.FAMILY;
    }
    
}
