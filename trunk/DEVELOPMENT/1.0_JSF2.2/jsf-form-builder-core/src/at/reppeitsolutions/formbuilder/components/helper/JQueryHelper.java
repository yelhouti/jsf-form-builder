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
package at.reppeitsolutions.formbuilder.components.helper;

import at.reppeitsolutions.formbuilder.components.html.HtmlListItem;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderItem;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class JQueryHelper {

    public static void encasulateForJQuery(UIComponent target, HtmlFormBuilderItem component) {
        List<HtmlFormBuilderItem> components = new ArrayList<>();
        components.add(component);
        encasulateForJQuery(target, components);
    }

    public static void encasulateForJQuery(UIComponent target, List<HtmlFormBuilderItem> components) {
        for (HtmlFormBuilderItem comp : components) {
            comp.render();
            comp.setTransient(true);
            HtmlListItem li = new HtmlListItem();
            li.setClassString("ui-state-default box-runde-ecken");
            li.getChildren().add(comp);
            li.setStyle("width: " + comp.getWidth() + ";");
            target.getChildren().add(li);
        }
    }
}
