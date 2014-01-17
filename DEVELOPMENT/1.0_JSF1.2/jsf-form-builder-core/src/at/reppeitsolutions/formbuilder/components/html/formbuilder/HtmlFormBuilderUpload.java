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

import javax.faces.component.html.HtmlInputFile;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class HtmlFormBuilderUpload extends HtmlFormBuilderItem {

    @Override
    public void renderView() {
        HtmlOutputText output = new HtmlOutputText();
        output.setValue(properties.getLabel());

        if (file == null) {
            HtmlInputFile input = new HtmlInputFile();
            if (getDataUuid() != null) {
                input.setId(getDataUuid());
            }
            addLabeledComponent(output, input);
        } else {
            HtmlOutputLink downloadLink = new HtmlOutputLink();
            downloadLink.setValue("javascript: downloadFile('" + getDataUuid().substring(HtmlFormBuilderItem.DATA_UUID_PREFIX.length(), getDataUuid().length()) + "');");
            HtmlOutputText linkText = new HtmlOutputText();
            linkText.setValue(file.getFilename());
            downloadLink.getChildren().add(linkText);
            addLabeledComponent(output, downloadLink);
        }
    }
}
