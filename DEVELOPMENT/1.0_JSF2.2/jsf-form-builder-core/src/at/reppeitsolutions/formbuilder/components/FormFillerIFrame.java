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
import at.reppeitsolutions.formbuilder.components.html.HtmlIFrame;
import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormFillerIFrameRenderer;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE, tagName = "formFiller")
@ResourceDependencies(value = {
    @ResourceDependency(library = "formbuilder", name = "js/jquery-1.9.1.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery-ui-1.10.3.custom.min.js"),
    @ResourceDependency(library = "formbuilder", name = "formbuilderiframe.css")
})
public class FormFillerIFrame extends IFrameComponent {
    
    public static final String MODE_VIEW = "view";
    public static final String MODE_FILL = "fill";

    private HtmlForm form;
    
    public FormFillerIFrame() {
        setRendererType(FormFillerIFrameRenderer.RENDERTYPE);
        iframe = new HtmlIFrame();
        iframe.setStyle("width: 795px; height: 610px;");
        iframe.setBorder(0);
        iframe.setScrolling(false);
        iframe.setId("iframe" + UUID.randomUUID().toString());
        
        form = new HtmlForm();
        form.setEnctype("multipart/form-data");
        
        HtmlCommandButton submit = new HtmlCommandButton();
        submit.setStyleClass("btn");
        submit.setStyle("display:none;");
        submit.setValue("Submit out of IFrame");
        
        form.getChildren().add(iframe);
        form.getChildren().add(submit);
        
        getChildren().add(form);
    }
    
    @PostConstruct
    public void init() {
        setMode(MODE_FILL);
    }

    public HtmlForm getForm() {
        return form;
    }
    
    public FormData getModel() {
        return (FormData) getStateHelper().eval("model");
    }

    public void setModel(FormData model) {
        getStateHelper().put("model", model);
    }
    
    public String getMode() {
        return (String) getStateHelper().eval("mode");
    }
    
    public void setMode(String mode) {
        getStateHelper().put("mode", mode);
    }
    
    public String getButtonid() {
        return (String) getStateHelper().eval("buttonid");
    }
    
    public void setButtonid(String buttonid) {
        getStateHelper().put("buttonid", buttonid);
    }    
    
    public String getTarget() {
        return (String) getStateHelper().eval("target");
    }
    
    public void setTarget(String target) {
        getStateHelper().put("target", target);
    }
    

    @Override
    public String getFamily() {
        return FormFillerIFrameRenderer.FAMILY;
    }
    
}
