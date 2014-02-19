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

import at.reppeitsolutions.formbuilder.components.FormFiller;
import at.reppeitsolutions.formbuilder.components.ModelApplicationBean;
import java.io.IOException;
import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(componentFamily = FormFillerRenderer.FAMILY, rendererType = FormFillerRenderer.RENDERTYPE)
public class FormFillerRenderer extends Renderer {

    public static final String RENDERTYPE = "FormFillerRenderer";
    public static final String FAMILY = "at.rits.formfiller";

    public FormFillerRenderer() {
    }

    @Override
    public void encodeBegin(FacesContext ctx,
            UIComponent component) throws IOException {
        FormFiller formFillerIFrame = (FormFiller) component;
        String uuid = UUID.randomUUID().toString();
        ModelApplicationBean.getInstance().putFormData(uuid, formFillerIFrame.getFormData());
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        formFillerIFrame.getIFrame().setSrc(request.getContextPath() + "/pages/formfiller.xhtml?uuid=" + uuid + "&mode=" + formFillerIFrame.getMode());

        formFillerIFrame.addLoadImage();
    }

    @Override
    public void encodeEnd(FacesContext ctx,
            UIComponent component) throws IOException {
        FormFiller formFillerIFrame = (FormFiller) component;
        if (formFillerIFrame.getMode() != null
                && formFillerIFrame.getMode().equals(FormFiller.MODE_FILL)) {
            ResponseWriter writer = ctx.getResponseWriter();
            writer.write("<script type=\"text/javascript\">");
            if (formFillerIFrame.getSubmitButtonId() != null) {
                writer.write("$(function(){"
                        + "$(\"#" + formFillerIFrame.getSubmitButtonId() + "\").attr(\"onclick\",\"submitForm();\");"
                        + "});");
            }
            writer.write("function submitForm() {"
                    + "  var iframe = document.getElementById('" + formFillerIFrame.getIFrame().getId() + "');"
                    + "  var innerDoc = iframe.contentDocument || iframe.contentWindow.document;"
                    + "  $(innerDoc).find(\".btn\").click();"
                    + "} "
                    + "function submitParentForm() {"
                    + "  $(\".btn\").click();"
                    + "}"
                    + "</script>");
        }
    }

    @Override
    public void decode(FacesContext ctx, UIComponent component) {
        FormFiller formFillerIFrame = (FormFiller) component;
        MethodBinding action = formFillerIFrame.getAction();
        action.invoke(ctx, null);
    }
}
