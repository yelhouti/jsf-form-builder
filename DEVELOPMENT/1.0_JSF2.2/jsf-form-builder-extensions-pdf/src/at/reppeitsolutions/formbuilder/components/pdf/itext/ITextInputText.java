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
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfBorderDictionary;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class ITextInputText implements PdfPCellEvent {
    
    public static final int FONTSIZE = 12;

    protected String value = "";

    public ITextInputText(String value) {
        this.value = value;
    }

    @Override
    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        TextField text = new TextField(writer, rectangle, String.format("text_" + UUID.randomUUID().toString()));
        text.setBorderStyle(PdfBorderDictionary.STYLE_INSET);
        text.setText(value);
        text.setFontSize(FONTSIZE);
        text.setAlignment(Element.ALIGN_LEFT);
        try {
            PdfFormField field = text.getTextField();
            writer.addAnnotation(field);
        } catch (IOException ioe) {
            throw new ExceptionConverter(ioe);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }
}
