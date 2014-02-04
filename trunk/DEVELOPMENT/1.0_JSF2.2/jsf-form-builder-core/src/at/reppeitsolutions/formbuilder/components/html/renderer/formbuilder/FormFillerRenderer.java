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
import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormBuilderItemData;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormData;
import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemFactory;
import at.reppeitsolutions.formbuilder.components.html.HtmlListItem;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderItem;
import at.reppeitsolutions.formbuilder.components.html.renderer.multipart.File;
import at.reppeitsolutions.formbuilder.components.html.renderer.multipart.MultipartRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(componentFamily = FormFillerRenderer.FAMILY, rendererType = FormFillerRenderer.RENDERTYPE)
public class FormFillerRenderer extends Renderer {

    public static final String RENDERTYPE = "FormFillerRenderer";
    public static final String FAMILY = "at.rits.formfiller";

    @Override
    public void encodeBegin(FacesContext ctx,
            UIComponent component) throws IOException {
        FormFiller formFiller = (FormFiller) component;
        HtmlForm form = FormBuilderRenderer.getHtmlForm(formFiller);
        form.setTransient(true);
        formFiller.getFormContent().setTransient(true);
        formFiller.getFormContent().setId(form.getId() + "formContent" + UUID.randomUUID().toString());

        FormData formModel = formFiller.getModel();

        if (formModel != null) {
            List<HtmlFormBuilderItem> components = new ArrayList<>();

            if (formModel.getData() != null) {
                for (FormBuilderItemData item : formModel.getData()) {                        
                    if(!item.getFormBuilderItem().getSkipRendering()) {
                        components.add(FormBuilderItemFactory.getUIComponent(item));
                    }
                }
            }

            for (HtmlFormBuilderItem comp : components) {
                HtmlListItem li = new HtmlListItem();
                li.setClassString("ui-state-default");
                li.setStyle("width:" + comp.getWidth() + ";");
                li.getChildren().add(comp);
                li.setTransient(true);
                formFiller.getFormContent().getChildren().add(li);
            }
        } else {
            HtmlOutputText output = new HtmlOutputText();
            output.setValue("ERROR: Model of form builder is null.");
            formFiller.getChildren().add(output);
        }
    }

    @Override
    public void encodeEnd(FacesContext ctx,
            UIComponent component) throws IOException {
        ResponseWriter writer = ctx.getResponseWriter();
        FormFiller formFiller = (FormFiller) component;
        HtmlForm form = FormBuilderRenderer.getHtmlForm(formFiller);


        writer.write("<script type=\"text/javascript\">"
                + "initDownloadable(\"" + form.getClientId() + "\","
                + "\"" + FormBuilderRenderer.getFormActionStringId(component) + "\","
                + "\"" + FormBuilderRenderer.getFormContentStringId(component) + "\""
                + ");</script>");
    }

    public static String extractDataUuid(String key) {
        return key.substring(key.indexOf(HtmlFormBuilderItem.DATA_UUID_PREFIX) + HtmlFormBuilderItem.DATA_UUID_PREFIX.length(), key.length());
    }

    @Override
    public void decode(FacesContext ctx, UIComponent component) {
        FormFiller formFiller = (FormFiller) component;
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();

        String formContentString = ctx.getExternalContext().getRequestParameterMap().get(FormBuilderRenderer.getFormContentStringId(component));
        String formActionString = ctx.getExternalContext().getRequestParameterMap().get(FormBuilderRenderer.getFormActionStringId(component));

        boolean formSubmit = true;

        if (formActionString != null) {
            switch (formActionString) {
                case "download":
                    if (formFiller.getModel().getData() != null) {
                        for (FormBuilderItemData data : formFiller.getModel().getData()) {
                            if (data.getUuid().equals(formContentString)) {
                                HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                                prepareResponseFor(response, data.getFile());
                                try {
                                    streamFileTo(response, data.getFile());
                                } catch (IOException ex) {
                                    Logger.getLogger(FormFillerRenderer.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    formSubmit = false;
                    break;
            }
        }

        if (formFiller.getModel().getData() != null && formSubmit) {
            MultipartRequest multiRequest = new MultipartRequest(request);
            for (String key : multiRequest.getFiles().keySet()) {
                if (key.contains(HtmlFormBuilderItem.DATA_UUID_PREFIX)) {
                    String dataUuid = extractDataUuid(key);
                    for (FormBuilderItemData data : formFiller.getModel().getData()) {
                        if (data.getUuid().equals(dataUuid)) {
                            data.setFile(multiRequest.getFiles().get(key));
                        }
                    }
                }
            }
            for (String key : multiRequest.getParameterMaps().keySet()) {
                if (key.contains(HtmlFormBuilderItem.DATA_UUID_PREFIX)) {
                    String dataUuid = extractDataUuid(key);
                    for (FormBuilderItemData data : formFiller.getModel().getData()) {
                        if (data.getUuid().equals(dataUuid)) {
                            List<String> result = multiRequest.getParameterMaps().get(key);
                            if (result.size() == 1) {
                                data.setValue(result.get(0));
                            } else {
                                String tmp = "";
                                for (String str : result) {
                                    tmp += str;
                                    tmp += ";";
                                }
                                data.setValue(tmp.substring(0, tmp.length() - 1));
                            }
                        }
                    }
                }
            }
        }
    }

    public void setTransient(FacesContext ctx, UIComponent comp) throws IOException {
        for (UIComponent compRekursion : comp.getChildren()) {
            comp.setTransient(true);
            setTransient(ctx, compRekursion);
        }
    }

    private void prepareResponseFor(HttpServletResponse response, File file) {
        StringBuilder type = new StringBuilder("attachment; filename=\"" + file.getFilename() + "\"");
        response.setContentLength((int) file.getFilesize());
        response.setContentType(file.getFiletype());
        response.setHeader("Content-Disposition", type.toString());
    }

    private void streamFileTo(HttpServletResponse response, File file) throws IOException {
        OutputStream os = response.getOutputStream();
        os.write(file.getFile());
        os.flush();
    }
}
