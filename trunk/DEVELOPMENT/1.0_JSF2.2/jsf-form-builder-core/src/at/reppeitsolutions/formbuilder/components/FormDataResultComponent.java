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

import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormDataResultRenderer;
import at.reppeitsolutions.formbuilder.model.FormData;
import java.util.List;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE, tagName = "formDataResult")
@ResourceDependencies(value = {
    @ResourceDependency(library = "formbuilder", name = "js/jquery-1.9.1.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery.flot.js"),
    @ResourceDependency(library = "formbuilder", name = "js/jquery.flot.pie.js")
})
public class FormDataResultComponent extends UIComponentBase {

    public FormDataResultComponent() {
        setRendererType(FormDataResultRenderer.RENDERTYPE);
    }

    @Override
    public String getFamily() {
        return FormDataResultRenderer.FAMILY;
    }

    public List<FormData> getFormDatas() {
        return (List<FormData>) getStateHelper().eval("formDatas");
    }

    public void setFormDatas(List<FormData> formDatas) {
        getStateHelper().put("formDatas", formDatas);
    }
    
}
