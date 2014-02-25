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
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseField;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.GrayColor;
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
public class ITextRadio implements PdfPCellEvent {

    protected String[] values = {};
    protected String value;
    protected boolean locked;
    
    public ITextRadio(String[] values, String value, boolean locked) {
        this.values = values;
        this.value = value;
        this.locked = locked;        
    }

    @Override
    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        PdfFormField radiogroup = PdfFormField.createRadioButton(writer, true);
        radiogroup.setFieldName(UUID.randomUUID().toString());
        if(locked) {
            radiogroup.setFieldFlags(BaseField.READ_ONLY);
        }
        RadioCheckField radio;
        for (int i = 0; i < values.length; i++) {
            try {
                Rectangle rect = getBoxRectangle(rectangle, i);
                radio = new RadioCheckField(writer, rect, null, UUID.randomUUID().toString());
                radio.setBorderColor(GrayColor.GRAYBLACK);
                radio.setBackgroundColor(GrayColor.GRAYWHITE);
                radio.setCheckType(RadioCheckField.TYPE_CIRCLE);
                if(value != null && values[i].equals(value)) {
                    radio.setChecked(true);
                }
                PdfFormField field = radio.getRadioField();
                radiogroup.addKid(field);
                addBoxDescription(rectangle, i, values, canvases);
            } catch (IOException ex) {
                Logger.getLogger(ITextRadio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ITextRadio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        writer.addAnnotation(radiogroup);
    }

    public static Rectangle getBoxRectangle(Rectangle rectangle, int i) {
        Rectangle rect = new Rectangle(160, rectangle.getTop(i * 20), 180, rectangle.getTop(20 + i * 20));
        return rect;
    }

    public static void addBoxDescription(Rectangle rectangle, int i, String[] values, PdfContentByte[] canvases) {
        Font f = new Font();
        f.setSize(ITextInputText.FONTSIZE);
        ColumnText.showTextAligned(canvases[0], Element.ALIGN_LEFT,
                new Phrase(values[i], f), 190, rectangle.getTop(i * 20) - 15, 0);
    }
}
