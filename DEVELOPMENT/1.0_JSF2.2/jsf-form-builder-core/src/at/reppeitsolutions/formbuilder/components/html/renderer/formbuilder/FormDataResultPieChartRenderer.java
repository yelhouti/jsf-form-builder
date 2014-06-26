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

import at.reppeitsolutions.formbuilder.components.FormDataResultPieChartComponent;
import at.reppeitsolutions.formbuilder.components.helper.FormDataPieChartResult;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesRenderer(componentFamily = FormDataResultPieChartRenderer.FAMILY, rendererType = FormDataResultPieChartRenderer.RENDERTYPE)
public class FormDataResultPieChartRenderer extends Renderer {

    public static final String RENDERTYPE = "FormDataResultPieChartRenderer";
    public static final String FAMILY = "at.rits.formbuilder";

    @Override
    public void encodeBegin(FacesContext ctx, UIComponent component) throws IOException {
        ResponseWriter writer = ctx.getResponseWriter();
        FormDataResultPieChartComponent pieChartComponent = (FormDataResultPieChartComponent) component;
        FormDataPieChartResult pieChart = pieChartComponent.getPieChart();
        if (pieChart != null && pieChart.getValues() != null && !pieChart.getValues().isEmpty()) {
            String pieChartId = "pieChart" + UUID.randomUUID().toString();
            String json = "[";
            for (Map.Entry pairs : pieChart.getValues().entrySet()) {
                json += "{label:\"" + pairs.getKey() + "\",data:" + pairs.getValue() + "},";
            }
            json += "]";
            writer.write("\n<!--" + json + "-->\n");
            writer.write("<style>"
                    + "#" + pieChartId + "{"
                    + "width: 250px;"
                    + "height: 300px;"
                    + "margin-left: 10px;}"
                    + "</style>");
            writer.write("<div id=\"" + pieChartId + "\"></div>");
            writer.write("<script type=\"text/javascript\">");
            writer.write("var data = " + json + ";");
            writer.write(""
                    + "function labelFormatter(label, series) {\n"
                    + "  return \"<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>\" + label + \"<br/>\" + Math.round(series.percent) + \"%</div>\";\n"
                    + "	}");
            writer.write("$(function(){"
                    + "$.plot('#" + pieChartId + "', data, {\n"
                    + "    series: {\n"
                    + "        pie: {\n"
                    + "            show: true,\n"
                    + "            radius: 1,\n"
                    + "            label: {\n"
                    + "                show: true,\n"
                    + "                radius: 1,\n"
                    + "                formatter: labelFormatter,\n"
                    + "                background: {\n"
                    + "                    opacity: 0.8\n"
                    + "                }\n"
                    + "            }\n"
                    + "        }\n"
                    + "    },\n"
                    + "    legend: {\n"
                    + "        show: false\n"
                    + "    }"
                    + "});"
                    + "});");
            writer.write("</script>");
        }
    }
}