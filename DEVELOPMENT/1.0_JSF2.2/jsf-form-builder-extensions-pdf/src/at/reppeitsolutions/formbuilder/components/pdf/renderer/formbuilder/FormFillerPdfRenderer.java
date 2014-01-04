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
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormBuilderItemData;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormData;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import at.reppeitsolutions.formbuilder.components.pdf.FormFillerPdf;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.pdf.helper.FormBuilderItemPdfFactory;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextOuterTable;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextOuterTableCell;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextParagraph;
import at.reppeitsolutions.formbuilder.components.pdf.renderkit.PdfRenderKit;
import at.reppeitsolutions.formbuilder.components.pdf.renderkit.PdfResponseWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(renderKitId = PdfRenderKit.PDF_BASIC, componentFamily = FormFillerPdfRenderer.FAMILY, rendererType = FormFillerPdfRenderer.RENDERTYPE)
public class FormFillerPdfRenderer extends Renderer {

    public static final String RENDERTYPE = "FormFillerPdfRenderer";
    public static final String FAMILY = "at.rits.pdf";

    public FormFillerPdfRenderer() {
    }

    @Override
    public void encodeBegin(FacesContext ctx,
            UIComponent component) throws IOException {
        PdfResponseWriter writer = (PdfResponseWriter) FacesContext.getCurrentInstance().getResponseWriter();
        Document document = writer.getOpenTag().getDocument();
        FormFillerPdf formFiller = (FormFillerPdf) component;
        FormData formModel = formFiller.getModel();



        try {
            if (formModel != null) {
                ITextOuterTable outerTable = new ITextOuterTable();
                ITextOuterTableCell outputCell;
                ITextOuterTableCell dummyCell = null;

                if (formModel.getData() != null) {
                    List<FormBuilderItemData> items = formModel.getData();
                    int halfwidthcount = 0;
                    for (int i = 0; i < items.size(); ++i) {
                        FormBuilderItemData data = items.get(i);
                        FormBuilderItemBase item = data.getFormBuilderItem();
                        if (!item.getSkipRendering()) {
                            String type = item.getFormbuildertype();
                            switch(item.getWidth()) {
                                case FormBuilderItemBase.FULLWIDTH:
                                    outputCell = new ITextOuterTableCell(2);
                                    halfwidthcount = 0;
                                    break;
                                case FormBuilderItemBase.HALFWIDTH:
                                    outputCell = new ITextOuterTableCell(1);
                                    halfwidthcount++;
                                    if(((i + 1 < items.size() &&
                                       items.get(i + 1).getFormBuilderItem().getWidth().equals(FormBuilderItemBase.FULLWIDTH)) || (
                                       i + 1 == items.size() &&
                                       items.get(i).getFormBuilderItem().getWidth().equals(FormBuilderItemBase.HALFWIDTH))) &&
                                       halfwidthcount % 2 == 1) {
                                        dummyCell = new ITextOuterTableCell(1);
                                        dummyCell.addElement(new ITextParagraph(""));
                                    }
                                    break;
                                default:
                                    outputCell = new ITextOuterTableCell(2);
                            }                           
                            outputCell.addElement(FormBuilderItemPdfFactory.getUIPdfComponent(item));
                            outerTable.addCell(outputCell);
                            if(dummyCell != null) {
                                outerTable.addCell(dummyCell);
                                dummyCell = null;
                            }
                        }
                    }
                }
                document.add(outerTable);
            } else {
                document.add(new Paragraph("ERROR: Model is null."));
            }
        } catch (DocumentException ex) {
            Logger.getLogger(FormFillerPdfRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    private PdfPCell getCell() {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(0);
        return cell;
    }

}
