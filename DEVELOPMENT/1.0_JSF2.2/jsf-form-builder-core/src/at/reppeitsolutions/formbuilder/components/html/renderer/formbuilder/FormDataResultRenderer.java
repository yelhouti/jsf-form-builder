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
package at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder;

import at.reppeitsolutions.formbuilder.components.Constants;
import at.reppeitsolutions.formbuilder.components.FormBuilder;
import at.reppeitsolutions.formbuilder.components.FormBuilderAttributesContainer;
import at.reppeitsolutions.formbuilder.components.FormDataResult;
import at.reppeitsolutions.formbuilder.components.FormDataResultAttributesContainer;
import at.reppeitsolutions.formbuilder.components.ModelApplicationBean;
import java.io.IOException;
import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(componentFamily = FormDataResultRenderer.FAMILY, rendererType = FormDataResultRenderer.RENDERTYPE)
public class FormDataResultRenderer extends Renderer {

    public static final String RENDERTYPE = "FormDataResultRenderer";
    public static final String FAMILY = "at.rits.formbuilder";

    public FormDataResultRenderer() {
    }

    @Override
    public void encodeBegin(FacesContext ctx,
            UIComponent component) throws IOException {
        FormDataResult formDataResult = (FormDataResult) component;
        String uuid = UUID.randomUUID().toString();
        FormDataResultAttributesContainer container = new FormDataResultAttributesContainer();
        container.setFormDatas(formDataResult.getFormDatas());
        ModelApplicationBean.getInstance().putFormDataResult(uuid, container);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        formDataResult.getIFrame().setSrc(request.getContextPath() + "/pages/formresult.xhtml?uuid=" + uuid);
    }

    @Override
    public void encodeEnd(FacesContext ctx,
            UIComponent component) throws IOException {
        FormDataResult formDataResult = (FormDataResult) component;
        ResponseWriter writer = ctx.getResponseWriter();
        writer.write("<script type=\"text/javascript\">"
                + "$(function(){regIframe('" + formDataResult.getIFrame().getId() + "');});"
                + "</script>");
    }
}
