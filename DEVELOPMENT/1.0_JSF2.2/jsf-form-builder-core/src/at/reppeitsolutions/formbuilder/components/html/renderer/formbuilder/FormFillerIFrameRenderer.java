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
import at.reppeitsolutions.formbuilder.components.FormBuilderIFrame;
import at.reppeitsolutions.formbuilder.components.FormFillerIFrame;
import at.reppeitsolutions.formbuilder.components.ModelApplicationBean;
import at.reppeitsolutions.formbuilder.messages.Messages;
import java.io.IOException;
import java.util.UUID;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(componentFamily = FormFillerIFrameRenderer.FAMILY, rendererType = FormFillerIFrameRenderer.RENDERTYPE)
public class FormFillerIFrameRenderer extends Renderer {

    public static final String RENDERTYPE = "FormFillerIframeRenderer";
    public static final String FAMILY = "at.rits.formbuilder";

    public FormFillerIFrameRenderer() {
    }

    @Override
    public void encodeBegin(FacesContext ctx,
            UIComponent component) throws IOException {
        FormFillerIFrame formFillerIFrame = (FormFillerIFrame) component;
        String uuid = UUID.randomUUID().toString();
        ModelApplicationBean.getInstance().putModelData(uuid, formFillerIFrame.getModel());
        formFillerIFrame.getIFrame().setSrc("pages/formfiller.xhtml?uuid=" + uuid);
    }

    @Override
    public void encodeEnd(FacesContext ctx,
            UIComponent component) throws IOException {
        FormFillerIFrame formFillerIFrame = (FormFillerIFrame) component;
        if (formFillerIFrame.getMode() != null &&
            formFillerIFrame.getMode().equals(FormFillerIFrame.MODE_FILL)) {
            ResponseWriter writer = ctx.getResponseWriter();
            String btnid = "btn-" + UUID.randomUUID().toString();
            writer.write("<br />");
            writer.write("<button id=\"" + btnid + "\" onclick=\"submitForm();\">" + Messages.getStringJSF("formfiller.submit") + "</button>");
            writer.write("<script type=\"text/javascript\">"
                    + "$('#" + btnid + "').button();"
                    + "function submitForm() {"
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
        FormFillerIFrame formFillerIFrame = (FormFillerIFrame) component;
        MethodBinding action = formFillerIFrame.getAction();
        action.invoke(ctx, null);
    }
}
