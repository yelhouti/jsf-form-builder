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
package at.reppeitsolutions.formbuilder.components.pdf.renderkit;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import at.reppeitsolutions.formbuilder.components.pdf.itext.ITextOpenTag;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class PdfResponseWriter extends ResponseWriter {

    String characterEncoding;
    String contentType = "application/pdf";
    Document document;
    FacesContext facesContext;
    ITextOpenTag openTag;

    public PdfResponseWriter(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public PdfResponseWriter(String characterEncoding, String contentType) {
        this(characterEncoding);
        this.contentType = contentType;
    }

    public PdfResponseWriter(String characterEncoding, 
                                String contentType, 
                                Document document, 
                                FacesContext facesContext) {
        this(characterEncoding, contentType);
        this.document = document;
        this.facesContext = facesContext;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getCharacterEncoding() {
        return characterEncoding;
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void startDocument() throws IOException {
        facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + UUID.randomUUID().toString() + ".pdf" + "\"");
        OutputStream browserStream = response.getOutputStream();

        document = new Document();

        try {
            PdfWriter.getInstance(document, browserStream);
        } catch (DocumentException ex) {
            throw new IOException(ex);
        }
        document.open();
        openTag = new ITextOpenTag(document);
        openTag.setTag(document);
    }

    @Override
    public void endDocument() throws IOException {
        document.close();
        facesContext.responseComplete();
    }

    @Override
    public void startElement(String name, UIComponent component) throws IOException {
        
    }

    @Override
    public void endElement(String name) throws IOException {
        
    }

    @Override
    public void writeAttribute(String name, Object value, String property) throws IOException {
    }

    @Override
    public void writeURIAttribute(String name, Object value, String property) throws IOException {
    }

    @Override
    public void writeComment(Object comment) throws IOException {
    }

    @Override
    public void writeText(Object text, String property) throws IOException {
    }

    @Override
    public void writeText(char[] text, int off, int len) throws IOException {
    }

    @Override
    public ResponseWriter cloneWithWriter(Writer writer) {
        /*
         * Create and return a new instance of this ResponseWriter, 
         * using the specified Writer as the output destination.
         */
        return new PdfResponseWriter(characterEncoding, contentType, document, facesContext);
    }

    @Override
    public void write(char[] chars, int i, int i1) throws IOException {
        
    }

    @Override
    public void close() throws IOException {
        
    }

    public ITextOpenTag getOpenTag() {
        return openTag;
    }
    
}
