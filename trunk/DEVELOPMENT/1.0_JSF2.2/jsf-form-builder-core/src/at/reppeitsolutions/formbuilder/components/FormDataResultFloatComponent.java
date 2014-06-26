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

import at.reppeitsolutions.formbuilder.components.helper.FormDataFloatResult;
import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormDataResultFloatRenderer;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = false, namespace = Constants.NAMESPACE, tagName = "floatFormDataResult")
/*
 * Custom JSF component to render the result in HTML.
 */
public class FormDataResultFloatComponent extends UIComponentBase {

    /*
     * Standard constructor
     * Sets the renderer of the component.
     */
    public FormDataResultFloatComponent() {
        setRendererType(FormDataResultFloatRenderer.RENDERTYPE);
    }

    /*
     * Defines the component family.
     */
    @Override
    public String getFamily() {
        return FormDataResultFloatRenderer.FAMILY;
    }

    //Simple getter and setter for the component attributes
    
    /*
     * FormDataFloatResult to render.
     */
    public FormDataFloatResult getFloatFormDataResult() {
        return (FormDataFloatResult) getStateHelper().eval("floatFormDataResult");
    }

    public void setFloatFormDataResult(FormDataFloatResult floatFormDataResult) {
        getStateHelper().put("floatFormDataResult", floatFormDataResult);
    }
    
}
