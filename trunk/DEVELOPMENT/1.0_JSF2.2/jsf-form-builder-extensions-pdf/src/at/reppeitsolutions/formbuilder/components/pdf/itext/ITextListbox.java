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
package at.reppeitsolutions.formbuilder.components.pdf.itext;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseField;
import com.lowagie.text.pdf.PdfBorderDictionary;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class ITextListbox implements PdfPCellEvent {

    protected String[] values = {};
    protected String[] selectedValues;
    protected boolean locked;

    public ITextListbox(String[] values, String[] selectedValues, boolean locked) {
        this.values = values;
        this.selectedValues = selectedValues;
        this.locked = locked;
    }

    @Override
    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        TextField text = new TextField(writer, rectangle, String.format("choice_" + UUID.randomUUID().toString()));
        text.setBorderStyle(PdfBorderDictionary.STYLE_INSET);
        text.setFontSize(ITextInputText.FONTSIZE);
        text.setOptions(TextField.MULTISELECT);
        text.setChoices(values);
        ArrayList<Integer> choiceSelections = new ArrayList<>();
        if (selectedValues != null) {
            for (int i = 0; i < values.length; i++) {
                for (int i2 = 0; i2 < selectedValues.length; i2++) {
                    if (values[i].equals(selectedValues[i2])) {
                        choiceSelections.add(i);
                        break;
                    }
                }
            }
        }
        text.setChoiceSelections(choiceSelections);
        try {
            PdfFormField listField = text.getListField();
            if(locked) {
                listField.setFieldFlags(BaseField.READ_ONLY);
            }
            writer.addAnnotation(listField);
        } catch (IOException ex) {
            Logger.getLogger(ITextListbox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ITextListbox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
