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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class ITextOpenTag {
    
    Object tag;
    Object oldTag;
    Document document;
    
    public ITextOpenTag(Document document) {
        this.document= document;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.oldTag = this.tag;
        this.tag = tag;
    }
    
    public void revertTag() {
        this.tag = this.oldTag;
    }
    
    public void revertToDocument() {
        this.tag = document;
    }

    public void add(Element e) throws DocumentException {
        if(tag != null) {
            if(tag instanceof Document) {
                ((Document)tag).add(e);
            }
            else if(tag instanceof PdfPCell) {
                ((PdfPCell)tag).addElement(e);
            } else if(tag instanceof PdfPTable && e instanceof PdfPCell) {
                ((PdfPTable)tag).addCell((PdfPCell) e);
            } else {
                //Do nothing
            }
        } else {
            throw new DocumentException("ITextOpenTag is null.");
        }
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    
}
