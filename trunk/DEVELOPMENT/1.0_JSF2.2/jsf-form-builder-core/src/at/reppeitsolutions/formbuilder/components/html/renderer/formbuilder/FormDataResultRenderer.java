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
package at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder;

import at.reppeitsolutions.formbuilder.components.FormDataResultFloatComponent;
import at.reppeitsolutions.formbuilder.components.FormDataResultComponent;
import at.reppeitsolutions.formbuilder.components.FormDataResultPieChartComponent;
import at.reppeitsolutions.formbuilder.components.FormDataResultStringComponent;
import at.reppeitsolutions.formbuilder.components.helper.FormDataFloatResult;
import at.reppeitsolutions.formbuilder.components.helper.FormDataResult;
import at.reppeitsolutions.formbuilder.components.helper.FormDataResultFactory;
import at.reppeitsolutions.formbuilder.components.helper.FormDataPieChartResult;
import at.reppeitsolutions.formbuilder.components.helper.FormDataStringResult;
import java.io.IOException;
import java.util.Collection;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(componentFamily = FormDataResultRenderer.FAMILY, rendererType = FormDataResultRenderer.RENDERTYPE)
public class FormDataResultRenderer extends Renderer {

    public static final String RENDERTYPE = "FormDataResultRenderer";
    public static final String FAMILY = "at.rits.formbuilder";

    @Override
    public void encodeBegin(FacesContext ctx, UIComponent component) throws IOException {
        ResponseWriter writer = ctx.getResponseWriter();
        FormDataResultComponent formDatasResultComponent = (FormDataResultComponent)component;
        HtmlPanelGrid panelGrid = new HtmlPanelGrid();
        panelGrid.setColumns(2);
        Collection<FormDataResult> formDataResults = FormDataResultFactory.getFormDataResults(formDatasResultComponent.getFormDatas());
        for(FormDataResult formDataResult : formDataResults) {
            String description = formDataResult.getFormBuilderItem().getProperties().getLabel();
            HtmlOutputText descriptionHtmlOutputText = new HtmlOutputText();
            descriptionHtmlOutputText.setValue(description);
            panelGrid.getChildren().add(descriptionHtmlOutputText);
            if(formDataResult instanceof FormDataPieChartResult) {
                FormDataResultPieChartComponent pieChartComponent = new FormDataResultPieChartComponent();
                pieChartComponent.setPieChart((FormDataPieChartResult)formDataResult);
                panelGrid.getChildren().add(pieChartComponent);
            } else if(formDataResult instanceof FormDataStringResult) {
                FormDataResultStringComponent stringFormDatasResultComponent = new FormDataResultStringComponent();
                stringFormDatasResultComponent.setStringFormDataResult((FormDataStringResult)formDataResult);
                panelGrid.getChildren().add(stringFormDatasResultComponent);
            } else if(formDataResult instanceof FormDataFloatResult) {
                FormDataResultFloatComponent floatFormDatasResultComponent = new FormDataResultFloatComponent();
                floatFormDatasResultComponent.setFloatFormDataResult((FormDataFloatResult)formDataResult);
                panelGrid.getChildren().add(floatFormDatasResultComponent);
            }
        }
        component.getChildren().add(panelGrid);
    }
    
}