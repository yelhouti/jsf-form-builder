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
package at.reppeitsolutions.formbuilder.components.html.formbuilder;

import at.reppeitsolutions.formbuilder.components.html.HtmlCustomOutputLabel;
import at.reppeitsolutions.formbuilder.components.html.HtmlTextarea;
import javax.faces.component.html.HtmlOutputText;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class HtmlFormBuilderTextarea extends HtmlFormBuilderItem {

    @Override
    public void renderView() {
        HtmlTextarea textarea = new HtmlTextarea();
        if (getDataUuid() != null) {
            textarea.setName(getDataUuid());
        }
        if (isDisabled()) {
            textarea.setDisabled(true);
        }
        String tmpValue = "";
        if (getValue() != null) {
            tmpValue = getValue();
        } else if (properties.getValues() != null) {
            tmpValue = properties.getValues();
        }
        textarea.setValue(tmpValue);
        textarea.setRows(properties.getRows());
        textarea.setCols(properties.getCols());

        HtmlCustomOutputLabel output = new HtmlCustomOutputLabel(properties);
        
        addLabeledComponent(output, textarea);
    }
}
