/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.reppeitsolutions.formbuilder.components.pdf.renderer.formbuilder;

import at.reppeitsolutions.formbuilder.components.FormFiller;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemConstraint;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemPagebreak;
import at.reppeitsolutions.formbuilder.components.helper.ConstraintVariablesContainer;
import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormFillerInternalRenderer;
import at.reppeitsolutions.formbuilder.components.pdf.FormFillerPdf;
import at.reppeitsolutions.formbuilder.components.pdf.helper.FormBuilderFillerPdfContainer;
import at.reppeitsolutions.formbuilder.components.pdf.helper.FormBuilderItemPdfFactory;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextOuterTable;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextOuterTableCell;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextParagraph;
import at.reppeitsolutions.formbuilder.messages.Messages;
import at.reppeitsolutions.formbuilder.model.ConstraintClient;
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
import at.reppeitsolutions.formbuilder.model.WorkflowState;
import java.util.ArrayList;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormBuilderFillerBaseRenderer extends Renderer {

    public void encodePdf(Form form, Document document) {
        encodePdf(null, form, document, null, null, null);
    }
    
    public void encodePdf(FormData formData, Document document) {
        encodePdf(formData, null, document, null, null, null);
    }

    public void encodePdf(FormData formData, Document document, String mode, WorkflowState workflowState, ConstraintClient constraintClient) {
        encodePdf(formData, null, document, mode, workflowState, constraintClient);
    }

    private void encodePdf(
            FormData formData, 
            Form form, 
            Document document, 
            String mode, 
            WorkflowState workflowState, 
            ConstraintClient constraintClient) {
        try {
            if (formData != null || form != null) {
                ITextOuterTable outerTable = new ITextOuterTable();
                List<FormBuilderFillerPdfContainer> items = new ArrayList<>();
                if (formData != null && formData.getData() != null && !formData.getData().isEmpty()) {
                    for (FormBuilderItemData formBuilderItemData : formData.getData()) {
                        items.add(new FormBuilderFillerPdfContainer(formBuilderItemData.getFormBuilderItem(), formBuilderItemData));
                    }
                    outerTable = encodeItems(items, outerTable, document, mode, workflowState, constraintClient);
                } else if (form != null && form.getItems() != null && !form.getItems().isEmpty()) {
                    for (FormBuilderItemBase formBuilderItemBase : form.getItems()) {
                        items.add(new FormBuilderFillerPdfContainer(formBuilderItemBase, null));
                    }
                    outerTable = encodeItems(items, outerTable, document, null, null, null);
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
            String mode,
            WorkflowState workflowState, 
            ConstraintClient constraintClient) throws DocumentException {
        ITextOuterTableCell outputCell;
        ITextOuterTableCell dummyCell = null;
        FormBuilderItemConstraint activeConstraint = null;
        int halfwidthcount = 0;
        for (int i = 0; i < items.size(); ++i) {
            FormBuilderItemBase item = items.get(i).getFormBuilderItem();
            FormBuilderItemData itemData = items.get(i).getFormBuilderItemData();
            if(itemData != null) {
                ConstraintVariablesContainer constraintVariablesContainer = new ConstraintVariablesContainer();
                activeConstraint = FormFillerInternalRenderer.checkConstraints(
                        itemData, 
                        activeConstraint, 
                        constraintVariablesContainer, 
                        workflowState, 
                        constraintClient);
            }
            if (!item.getSkipRendering() &&
                 item.getProperties().getVisible()) {
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
                    outputCell.addElement(FormBuilderItemPdfFactory.getUIPdfComponent(itemData, mode));
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
