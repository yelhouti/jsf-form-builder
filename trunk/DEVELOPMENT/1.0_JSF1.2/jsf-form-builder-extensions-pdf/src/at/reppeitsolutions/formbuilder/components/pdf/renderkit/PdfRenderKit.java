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

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class PdfRenderKit extends RenderKit {
    public static final String PDF_BASIC = "PDF_BASIC";
    
    private Map<String, Renderer> rendererMap;
    
    public PdfRenderKit() {
        rendererMap = new HashMap<>();
    }

    @Override
    public void addRenderer(String family, String rendererType, Renderer renderer) {
        /*
         * Register the specified Renderer instance, associated with the specified 
         * component family and rendererType, to the set of Renderers registered with 
         * this RenderKit, replacing any previously registered Renderer for this combination of identifiers.
         */
        String key = getFamilyRendererTypeKey(family, rendererType);
        if(rendererMap.containsKey(key)) {
            rendererMap.remove(key);
        }
        rendererMap.put(key, renderer);
    }
    
    private String getFamilyRendererTypeKey(String family, String rendererType) {
        return family + rendererType;
    }

    @Override
    public Renderer getRenderer(String family, String rendererType) {
        String key = getFamilyRendererTypeKey(family, rendererType);
        return rendererMap.get(key);
    }

    @Override
    public ResponseStateManager getResponseStateManager() {
        throw new UnsupportedOperationException("Not supported. Is not needed for PDF rendering.");
    }

    @Override
    public ResponseWriter createResponseWriter(Writer writer, String contentTypeList, String characterEncoding) {
        if(contentTypeList == null) {
            return new PdfResponseWriter(characterEncoding);
        } else {
            return new PdfResponseWriter(characterEncoding, contentTypeList);
        }
    }

    @Override
    public ResponseStream createResponseStream(OutputStream out) {
        throw new UnsupportedOperationException("Not supported. Is not needed for PDF rendering.");
    }
    
}
