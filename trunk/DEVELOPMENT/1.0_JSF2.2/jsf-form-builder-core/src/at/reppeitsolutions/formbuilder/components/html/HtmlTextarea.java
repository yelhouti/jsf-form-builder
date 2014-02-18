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
public class HtmlTextarea extends HtmlBaseComponent {

    private String value;
    private Integer rows;
    private Integer cols;
    private String name;
    private boolean disabled = false;

    public HtmlTextarea() {
        super("textarea");
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String tmpName = "";
        if(name != null) {
            tmpName = " name=\"" + name + "\" ";
        }
        setStyle("resize:none;" + getPassThroughAttributes().get("style"));
        getPassThroughAttributes().remove("style");
        String tmpRows = "";
        String tmpCols = "";
        String tmpdisabled = "";
        if(cols != null) {
            tmpCols = " cols=\"" + cols + "\" ";
        }
        if(rows != null) {
            tmpRows = " rows=\"" + rows + "\" ";
        }
        if(disabled) {
            tmpdisabled = " disabled ";
        }
        writer.write("<" + tagName + " " + getStyleClassIdString() + tmpName + tmpRows + tmpCols + tmpdisabled + ">" + value);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("</" + tagName + ">");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
}
