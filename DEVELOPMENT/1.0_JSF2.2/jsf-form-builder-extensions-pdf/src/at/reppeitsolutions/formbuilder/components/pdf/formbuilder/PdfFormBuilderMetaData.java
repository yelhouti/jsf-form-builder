package at.reppeitsolutions.formbuilder.components.pdf.formbuilder;

import at.reppeitsolutions.formbuilder.components.pdf.itext.ITextInputText;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextInnerTable;
import at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder.ITextParagraph;
import com.lowagie.text.Element;

/**
 *
 * @author ECh
 */
public class PdfFormBuilderMetaData extends PdfFormBuilderItem {

    
    
    @Override
    public Element render() {
        ITextInnerTable innerTable = new ITextInnerTable(getProperties().getOnelinedescription());
        innerTable.getDescription().addElement(new ITextParagraph(getProperties().getMetadatadescription()));
        String val = getProperties().getValues();
        if(getValue() != null) {
            val = getValue();
        } 
        innerTable.getContent().addElement(new ITextParagraph(val));
        innerTable.addCells();
        return innerTable;
    }
    
}
