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
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class ITextOuterTable extends PdfPTable {

    public ITextOuterTable() {
        super(2);
        try {
            setWidths(new int[]{1, 1});
            setWidthPercentage(100);
        } catch (DocumentException ex) {
            Logger.getLogger(ITextOuterTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addCell(ITextOuterTableCell cell) {
        super.addCell(cell);
    }

    private void resetDefaultCell() {
        getDefaultCell().setColspan(2);
        getDefaultCell().setHorizontalAlignment(Element.ALIGN_TOP);
        getDefaultCell().setPadding(0);
    }
}
