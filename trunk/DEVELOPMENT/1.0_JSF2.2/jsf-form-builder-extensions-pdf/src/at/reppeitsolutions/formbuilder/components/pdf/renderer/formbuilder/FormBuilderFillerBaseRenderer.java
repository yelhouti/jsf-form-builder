/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.reppeitsolutions.formbuilder.components.pdf.renderer.formbuilder;

import at.reppeitsolutions.formbuilder.components.FormFiller;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemPagebreak;
import at.reppeitsolutions.formbuilder.components.pdf.FormFillerPdf;
import at.reppeitsolutions.formbuilder.components.pdf.helper.FormBuilderFillerPdfContainer;
import at.reppeitsolutions.formbuilder.components.pdf.helper.FormBuilderItemPdfFactory;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextOuterTable;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextOuterTableCell;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextParagraph;
import at.reppeitsolutions.formbuilder.messages.Messages;
import at.reppeitsolutions.formbuilder.model.Form;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.render.Renderer;
import at.reppeitsolutions.formbuilder.model.FormData;
import at.reppeitsolutions.formbuilder.model.FormBuilderItemData;
import java.util.ArrayList;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormBuilderFillerBaseRenderer extends Renderer {

    public void encodePdf(Form form, Document document) {
        encodePdf(null, form, document, null);
    }

    public void encodePdf(FormData formData, Document document, String mode) {
        encodePdf(formData, null, document, mode);
    }

    private void encodePdf(FormData formData, Form form, Document document, String mode) {
        try {
            if (formData != null || form != null) {
                ITextOuterTable outerTable = new ITextOuterTable();
                List<FormBuilderFillerPdfContainer> items = new ArrayList<>();
                if (formData != null && formData.getData() != null && !formData.getData().isEmpty()) {
                    for (FormBuilderItemData formBuilderItemData : formData.getData()) {
                        items.add(new FormBuilderFillerPdfContainer(formBuilderItemData.getFormBuilderItem(), formBuilderItemData));
                    }
                    outerTable = encodeItems(items, outerTable, document, mode);
                } else if (form != null && form.getItems() != null && !form.getItems().isEmpty()) {
                    for (FormBuilderItemBase formBuilderItemBase : form.getItems()) {
                        items.add(new FormBuilderFillerPdfContainer(formBuilderItemBase, null));
                    }
                    outerTable = encodeItems(items, outerTable, document, null);
                } else {
                    document.add(new Paragraph(Messages.getStringJSF("pdf.empty")));
                }
                document.add(outerTable);
            } else {
                document.add(new Paragraph("ERROR: Model is null."));
            }
        } catch (DocumentException ex) {
            Logger.getLogger(FormBuilderPdfRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ITextOuterTable encodeItems(List<FormBuilderFillerPdfContainer> items, 
            ITextOuterTable outerTable, 
            Document document,
            String mode) throws DocumentException {
        ITextOuterTableCell outputCell;
        ITextOuterTableCell dummyCell = null;
        int halfwidthcount = 0;
        for (int i = 0; i < items.size(); ++i) {
            FormBuilderItemBase item = items.get(i).getFormBuilderItem();
            FormBuilderItemData itemData = items.get(i).getFormBuilderItemData();
            if(itemData == null ||
               (mode != null && mode.equals(FormFiller.MODE_VIEW))) {
                item.getProperties().setLocked(Boolean.TRUE);
            }
            if (!item.getSkipRendering()) {
                switch (item.getWidth()) {
                    case FormBuilderItemBase.FULLWIDTH:
                        outputCell = new ITextOuterTableCell(2);
                        halfwidthcount = 0;
                        break;
                    case FormBuilderItemBase.HALFWIDTH:
                        outputCell = new ITextOuterTableCell(1);
                        halfwidthcount++;
                        if (((i + 1 < items.size()
                                && items.get(i + 1).getFormBuilderItem().getWidth().equals(FormBuilderItemBase.FULLWIDTH)) || (i + 1 == items.size()
                                && items.get(i).getFormBuilderItem().getWidth().equals(FormBuilderItemBase.HALFWIDTH)))
                                && halfwidthcount % 2 == 1) {
                            dummyCell = new ITextOuterTableCell(1);
                            dummyCell.addElement(new ITextParagraph(""));
                        }
                        break;
                    default:
                        outputCell = new ITextOuterTableCell(2);
                }
                if(itemData != null) {
                    outputCell.addElement(FormBuilderItemPdfFactory.getUIPdfComponent(itemData));
                } else {
                    outputCell.addElement(FormBuilderItemPdfFactory.getUIPdfComponent(item));
                }
                outerTable.addCell(outputCell);
                if (dummyCell != null) {
                    outerTable.addCell(dummyCell);
                    dummyCell = null;
                }
            } else if (item instanceof FormBuilderItemPagebreak) {
                document.add(outerTable);
                document.newPage();
                outerTable = new ITextOuterTable();
            }
            item.getProperties().resetConstraintVariables();
        }
        return outerTable;
    }
}
