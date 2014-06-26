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

import at.reppeitsolutions.formbuilder.components.helper.FormDataStringResult;
import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormDataResultStringRenderer;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = false, namespace = Constants.NAMESPACE, tagName = "formDataResultString")
/*
 * Custom JSF component to render the result in HTML.
 */
public class FormDataResultString extends UIComponentBase {

    /*
     * Standard constructor
     * Sets the renderer of the component.
     */
    public FormDataResultString() {
        setRendererType(FormDataResultStringRenderer.RENDERTYPE);
    }

    /*
     * Defines the component family.
     */
    @Override
    public String getFamily() {
        return FormDataResultStringRenderer.FAMILY;
    }

    //Simple getter and setter for the component attributes
    
    /*
     * FormDataStringResult to render.
     */
    public FormDataStringResult getStringFormDataResult() {
        return (FormDataStringResult) getStateHelper().eval("stringFormDataResult");
    }

    public void setStringFormDataResult(FormDataStringResult stringFormDataResult) {
        getStateHelper().put("stringFormDataResult", stringFormDataResult);
    }
    
}
