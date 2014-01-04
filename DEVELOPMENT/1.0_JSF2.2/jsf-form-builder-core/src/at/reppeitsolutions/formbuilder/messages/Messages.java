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
package at.reppeitsolutions.formbuilder.messages;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class Messages {

    private static final String BUNDLE_NAME = "messages.messages";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);
    private static final ResourceBundle RESOURCE_BUNDLE_DE = ResourceBundle
            .getBundle(BUNDLE_NAME, new Locale("de"));
    private static final ResourceBundle RESOURCE_BUNDLE_EN = ResourceBundle
            .getBundle(BUNDLE_NAME, new Locale("en"));

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getStringJSF(String key) {
        HttpServletRequest request = null;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        } catch (Exception ex) {
            //JSF context not available
        }
        if (request != null) {
            return getLocaleSpecificString(key, request.getLocale());
        } else {
            return getString(key);
        }
    }

    public static String getLocaleSpecificString(String key, Locale local) {
        try {
            if (local.equals(new Locale("de")) || local.toString().startsWith("de")) {
                return RESOURCE_BUNDLE_DE.getString(key);
            } else if (local.equals(new Locale("en")) || local.toString().startsWith("en")) {
                return RESOURCE_BUNDLE_EN.getString(key);
            } else {
                return RESOURCE_BUNDLE.getString(key);
            }
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}