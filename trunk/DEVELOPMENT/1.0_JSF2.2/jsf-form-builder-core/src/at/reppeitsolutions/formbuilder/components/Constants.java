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
package at.reppeitsolutions.formbuilder.components;

import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class Constants {
    
    public static final String NAMESPACE = "http://www.reppe-itsolutions.at";
    public static final String NAMESPACE_PDF = "http://www.reppe-itsolutions.at/pdf";
    public static char sep = UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance());
    public static final String STRINGSEPERATOR = "<seperator>";
    
    public static final int MAXLABELLENGTH = 20;
    public static final int LABELLENGTHPADDING = 2;
    public static int MINLABELLENGTH = 10;
    
    public static final String TABLE_PREFIX = "formbuilder_";
    
    public static String getResourcesBaseUrl() {
        return getBaseUrl() + "javax.faces.resources/";
    }
    
    public static String getBaseUrl() {
        return ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getContextPath() + "/";
    }
    
}
