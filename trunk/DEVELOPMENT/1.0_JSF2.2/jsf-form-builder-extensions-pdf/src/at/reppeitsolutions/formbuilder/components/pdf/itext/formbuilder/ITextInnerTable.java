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
package at.reppeitsolutions.formbuilder.components.pdf.itext.formbuilder;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class ITextInnerTable extends PdfPTable {

    PdfPCell description;
    PdfPCell content;
    Boolean descriptionInNewLine = false;

    public ITextInnerTable() {
        this(false);
    }

    public ITextInnerTable(Boolean descriptionInNewLine) {
        super(2);
        this.descriptionInNewLine = descriptionInNewLine;
        try {
            if (descriptionInNewLine != null && descriptionInNewLine) {
                setWidths(new int[]{20, 1});
            } else {
                setWidths(new int[]{1, 4});
            }
            setWidthPercentage(100);
            description = getCell();
            content = getCell();
        } catch (DocumentException ex) {
            Logger.getLogger(ITextInnerTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addCells() {
        if (descriptionInNewLine != null && descriptionInNewLine) {
            addCell(description);
            addCell(getCell());
            addCell(content);
            PdfPCell dummyCell = getCell();
            dummyCell.addElement(new ITextParagraph(" "));
            addCell(dummyCell);
        } else {
            addCell(description);
            addCell(content);
        }
    }

    private PdfPCell getCell() {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(0);
        cell.setBorderWidth(0);
        return cell;
    }

    public PdfPCell getDescription() {
        return description;
    }

    public PdfPCell getContent() {
        return content;
    }
}
