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
import at.reppeitsolutions.formbuilder.components.FormFiller;
import at.reppeitsolutions.formbuilder.components.FormFillerAttributesContainer;
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
        FormFiller formFiller = (FormFiller) component;
        String uuid = UUID.randomUUID().toString();
        FormFillerAttributesContainer container = new FormFillerAttributesContainer();
        container.setFormData(formFiller.getFormData());
        container.setWorkflowState(formFiller.getWorkflowState());
        container.setConstraintClient(formFiller.getConstraintClient());
        container.setMetaDataObject(formFiller.getMetaDataObject());
        container.setMetaDataFetcher(formFiller.getMetaDataFetcher());
        ModelApplicationBean.getInstance().putFormData(uuid, container);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        formFiller.getIFrame().setSrc(request.getContextPath() + "/pages/formfiller.xhtml?uuid=" + uuid + "&mode=" + formFiller.getMode());

        formFiller.getCallbackButton().setActionExpression(formFiller.getActionExpression());
    }

    @Override
    public void encodeEnd(FacesContext ctx,
            UIComponent component) throws IOException {
        FormFiller formFiller = (FormFiller) component;
        ResponseWriter writer = ctx.getResponseWriter();
        writer.write("<script type=\"text/javascript\">");
        if (formFiller.getMode() != null
                && formFiller.getMode().equals(FormFiller.MODE_FILL)) {
            if (formFiller.getSubmitButtonId() != null) {
                writer.write("$(function(){"
                        + "$(\"#" + formFiller.getSubmitButtonId() + "\").attr(\"onclick\",\"submitForm();\");"
                        + "});");
            }
            writer.write("function submitForm() {"
                    + "  var iframe = document.getElementById('" + formFiller.getIFrame().getId() + "');"
                    + "  var innerDoc = iframe.contentDocument || iframe.contentWindow.document;"
                    + "  $(innerDoc).find(\".btn\").click();"
                    + "} "
                    + "function submitParentForm() {"
                    + "  document.getElementById(\"" + FormBuilderInternalRenderer.getHtmlForm(component).getId() + Constants.sep + formFiller.getCallbackButton().getId() + "\").click();"
                    + "}");
        }
        writer.write("$(function(){regIframe('" + formFiller.getIFrame().getId() + "');});"
                + "</script>");
    }

    @Override
    public void decode(FacesContext ctx, UIComponent component) {
        FormFiller formFillerIFrame = (FormFiller) component;
        MethodBinding action = formFillerIFrame.getAction();
        action.invoke(ctx, null);
    }
}
