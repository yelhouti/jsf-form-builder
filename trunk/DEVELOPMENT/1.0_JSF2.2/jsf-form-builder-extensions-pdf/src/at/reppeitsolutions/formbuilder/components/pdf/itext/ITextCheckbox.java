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
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseField;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfAppearance;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RadioCheckField;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class ITextCheckbox implements PdfPCellEvent {

    protected String[] values = {};
    protected String[] selectedValues;
    protected boolean locked;

    public ITextCheckbox(String[] values, String[] selectedValues, boolean locked) {
        this.values = values;
        this.selectedValues = selectedValues;
        this.locked = locked;
    }

    @Override
    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        PdfAppearance[] onOff = new PdfAppearance[2];
        onOff[0] = canvases[0].createAppearance(20, 20);
        onOff[0].rectangle(1, 1, 18, 18);
        onOff[0].stroke();
        onOff[1] = canvases[0].createAppearance(20, 20);
        onOff[1].setRGBColorFill(255, 128, 128);
        onOff[1].rectangle(1, 1, 18, 18);
        onOff[1].fillStroke();
        onOff[1].moveTo(1, 1);
        onOff[1].lineTo(19, 19);
        onOff[1].moveTo(1, 19);
        onOff[1].lineTo(19, 1);
        onOff[1].stroke();
        RadioCheckField checkbox;
        Font f = new Font();
        f.setSize(ITextInputText.FONTSIZE);
        for (int i = 0; i < values.length; i++) {
            try {
                Rectangle rect = ITextRadio.getBoxRectangle(rectangle, i);
                checkbox = new RadioCheckField(writer, rect, UUID.randomUUID().toString(), "Yes");
                boolean checked = false;
                if (selectedValues != null) {
                    for (int i2 = 0; i2 < selectedValues.length; i2++) {
                        if (values[i].equals(selectedValues[i2])) {
                            checked = true;
                            break;
                        }
                    }
                }
                checkbox.setChecked(checked);
                PdfFormField field = checkbox.getCheckField();
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Yes", onOff[1]);
                if (locked) {
                    field.setFieldFlags(BaseField.READ_ONLY);
                }
                writer.addAnnotation(field);
                ITextRadio.addBoxDescription(rectangle, i, values, canvases);
            } catch (IOException ex) {
                Logger.getLogger(ITextCheckbox.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ITextCheckbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
