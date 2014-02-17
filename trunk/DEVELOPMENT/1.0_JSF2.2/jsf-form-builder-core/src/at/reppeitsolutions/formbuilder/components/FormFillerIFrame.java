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
import com.sun.faces.taglib.html_basic.CommandButtonTag;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UICommand;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.faces.el.MethodBinding;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE, tagName = "formFiller")
@ResourceDependencies(value = {
    @ResourceDependency(library = "formbuilder", name = "js/jquery-1.9.1.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery-ui-1.10.3.custom.min.js"),
    @ResourceDependency(library = "formbuilder", name = "css/smoothness/jquery-ui-1.10.3.custom.min.css"),})
public class FormFillerIFrame extends UICommand {
    
    public static final String MODE_VIEW = "view";
    public static final String MODE_FILL = "fill";
    
    private HtmlIFrame iframe;
    
    public FormFillerIFrame() {
        setRendererType(FormFillerIFrameRenderer.RENDERTYPE);
        iframe = new HtmlIFrame();
        iframe.setStyle("width: 795px; height: 610px;");
        iframe.setBorder(0);
        iframe.setScrolling(false);
        iframe.setId("iframe" + UUID.randomUUID().toString());
        
        HtmlCommandButton submit = new HtmlCommandButton();
        submit.setStyleClass("btn");
        submit.setStyle("display:none;");
        
        getChildren().add(iframe);
        getChildren().add(submit);
    }
    
    @PostConstruct
    public void init() {
        setMode(MODE_FILL);
    }
    
    public HtmlIFrame getIFrame() {
        return iframe;
    }
    
    public FormData getModel() {
        return (FormData) getStateHelper().eval("model");
    }

    public void setModel(FormData model) {
        getStateHelper().put("model", model);
    }
    
    public MethodExpression getSave() {
        return (MethodExpression) getStateHelper().eval("save");
    }
    public void setSave(MethodExpression action) {
        getStateHelper().put("save", action);
    }
    
    public String getMode() {
        return (String) getStateHelper().eval("mode");
    }
    
    public void setMode(String mode) {
        getStateHelper().put("mode", mode);
    }

    @Override
    public String getFamily() {
        return FormFillerIFrameRenderer.FAMILY;
    }
    
}
