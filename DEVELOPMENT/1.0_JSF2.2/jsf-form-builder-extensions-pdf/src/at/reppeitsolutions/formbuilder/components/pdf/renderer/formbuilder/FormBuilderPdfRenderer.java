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
package at.reppeitsolutions.formbuilder.components.pdf.renderer.formbuilder;

import com.lowagie.text.Document;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import at.reppeitsolutions.formbuilder.components.pdf.FormBuilderPdf;
import at.reppeitsolutions.formbuilder.components.pdf.renderkit.PdfRenderKit;
import at.reppeitsolutions.formbuilder.components.pdf.renderkit.PdfResponseWriter;
import at.reppeitsolutions.formbuilder.model.Form;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(renderKitId = PdfRenderKit.PDF_BASIC, componentFamily = FormBuilderPdfRenderer.FAMILY, rendererType = FormBuilderPdfRenderer.RENDERTYPE)
public class FormBuilderPdfRenderer extends FormBuilderFillerBaseRenderer {

    public static final String RENDERTYPE = "FormBuilderPdfRenderer";
    public static final String FAMILY = "at.rits.pdf";

    public FormBuilderPdfRenderer() {
        
    }

    @Override
    public void encodeBegin(FacesContext ctx,
            UIComponent component) throws IOException {
        PdfResponseWriter writer = (PdfResponseWriter) FacesContext.getCurrentInstance().getResponseWriter();
        Document document = writer.getDocument();
        FormBuilderPdf formBuilder = (FormBuilderPdf) component;
        Form form = formBuilder.getForm();
        encodePdf(form, document);
    }

}
