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

import at.reppeitsolutions.formbuilder.components.annotations.SkipDialog;
import at.reppeitsolutions.formbuilder.components.helper.IMetaDataFetcher;
import at.reppeitsolutions.formbuilder.components.helper.MetaDataDescription;
import at.reppeitsolutions.formbuilder.components.helper.exception.MetaDataFetchException;
import at.reppeitsolutions.formbuilder.components.html.HtmlCustomOutputLabel;
import at.reppeitsolutions.formbuilder.components.html.HtmlDiv;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@SkipDialog
public class HtmlFormBuilderMetaData<ObjectType> extends HtmlFormBuilderItem {

    private ObjectType metaDataObject;
    private IMetaDataFetcher<ObjectType> fetcher;

    public HtmlFormBuilderMetaData() {
    }

    public HtmlFormBuilderMetaData(ObjectType metaDataObject, IMetaDataFetcher<ObjectType> fetcher) {
        this.metaDataObject = metaDataObject;
        this.fetcher = fetcher;
    }

    @Override
    public void renderView() {
        HtmlDiv outputValue = new HtmlDiv();

        if (metaDataObject != null) {
            try {
                MetaDataDescription description = 
                        new MetaDataDescription(properties.getMetadataid(), properties.getMetadatadescription());
                
                for (String signature : fetcher.provideMetaData(metaDataObject, description)) {
                    outputValue.getChildren().add(createLine(signature));
                        }
            } catch (MetaDataFetchException ex) {
                outputValue.getChildren().add(createLine("METADATA NOT FOUND!"));
                    }
                }

        properties.setLabel(properties.getMetadatadescription());
        HtmlCustomOutputLabel output = new HtmlCustomOutputLabel(properties);

        addLabeledComponent(output, outputValue, null);
    }
    
    private UIComponent createLine(String text) {
        HtmlDiv div = new HtmlDiv();
        HtmlOutputText output = new HtmlOutputText();
        output.setEscape(false);
        output.setValue(text);
        div.getChildren().add(output);
        return div;
}
}
