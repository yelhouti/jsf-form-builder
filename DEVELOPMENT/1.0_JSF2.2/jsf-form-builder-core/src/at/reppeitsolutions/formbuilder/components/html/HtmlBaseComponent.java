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

import at.reppeitsolutions.formbuilder.components.html.renderer.BaseComponentRenderer;
import at.reppeitsolutions.formbuilder.components.html.renderer.HtmlBaseComponentRenderer;
import at.reppeitsolutions.formbuilder.helper.StringUtils;
import java.io.IOException;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public abstract class HtmlBaseComponent extends UIComponentBase {

    protected char sep = UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance());
    protected String style = "";
    protected String classString = "";
    protected String tagName = "";
    protected String onClick;

    public HtmlBaseComponent(String tagName) {
        this.tagName = tagName;
        setRendererType(BaseComponentRenderer.RENDERTYPE);
    }

    @Override
    public String getFamily() {
        return HtmlBaseComponentRenderer.FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String tmpOnClick = "";
        if (onClick != null) {
            tmpOnClick = " onclick=\"" + onClick + "\" ";
        }
        writer.write("<" + tagName + " " + getStyleClassIdString() + tmpOnClick + ">");
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("</" + tagName + ">");
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getClassString() {
        return classString;
    }

    public void setClassString(String classString) {
        this.classString = classString;
    }

    protected String getStyleClassIdString() {
        String result = "";
        if (!StringUtils.isEmpty(getId())) {
            result += "id=\"" + getId() + "\" ";
        }
        if (!StringUtils.isEmpty(classString)) {
            result += "class=\"" + classString + "\" ";
        }
        if (!StringUtils.isEmpty(style)) {
            result += "style=\"" + style + "\" ";
        }
        if(getPassThroughAttributes() != null && !getPassThroughAttributes().isEmpty()) {
            String passthroughAttributes = "";
            for(String key : getPassThroughAttributes().keySet()) {
                passthroughAttributes += " " + key + "=\"" + getPassThroughAttributes().get(key) + "\"" + " ";
            }
            result += passthroughAttributes;
        }
        return result;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }
}
