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
package at.reppeitsolutions.formbuilder.components.html;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class HtmlIFrame extends HtmlBaseComponent {

    private String src;
    private Integer border;
    private boolean scrolling = false;
    private String id;

    public HtmlIFrame() {
        super("iframe");
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String append = "";
        if (border != null) {
            append += " frameborder=\"" + border + "\" ";
        }
        append += " scrolling=\"";
        if (scrolling) {
            append += "yes";
        } else {
            append += "no";
        }
        append += "\" ";
        writer.write("<" + tagName + " " + getStyleClassIdString() + " src=\"" + src + "\" " + append + " id=\"" + id + "\">");
        writer.write("Your browser does not support iframes.");
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("</" + tagName + ">");
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getBorder() {
        return border;
    }

    public void setBorder(Integer border) {
        this.border = border;
    }

    public boolean isScrolling() {
        return scrolling;
    }

    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public void setId(String id) {
        this.id = id;
    }
} 
