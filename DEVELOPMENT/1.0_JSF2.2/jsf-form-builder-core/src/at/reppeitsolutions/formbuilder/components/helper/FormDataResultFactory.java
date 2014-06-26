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

import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemCheckbox;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemDate;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemInput;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemListbox;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemNumber;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemRadio;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemSelect;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemTextarea;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemTime;
import at.reppeitsolutions.formbuilder.model.FormBuilderItemData;
import at.reppeitsolutions.formbuilder.model.FormData;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormDataResultFactory {

    public static Collection<FormDataResult> getFormDataResults(List<FormData> formDatas) {
        boolean firstFormData = true;
        Map<String, FormDataResult> formDatasResults = new HashMap<>();
        for (FormData formData : formDatas) {
            
            for (FormBuilderItemData formBuilderItemData : formData.getData()) {
                FormBuilderItemBase item = formBuilderItemData.getFormBuilderItem();
                if (item instanceof FormBuilderItemInput
                        || item instanceof FormBuilderItemTextarea
                        || item instanceof FormBuilderItemDate
                        || item instanceof FormBuilderItemTime) {
                    if(firstFormData) {
                        FormDataStringResult stringFormDataResult = new FormDataStringResult(item);
                        formDatasResults.put(item.getId(), stringFormDataResult);
                    }
                    ((FormDataStringResult)formDatasResults.get(item.getId())).addStringFormDataResult(formBuilderItemData.getValue());
                } else if (item instanceof FormBuilderItemSelect
                        || item instanceof FormBuilderItemRadio
                        || item instanceof FormBuilderItemListbox
                        || item instanceof FormBuilderItemCheckbox) {
                    FormDataPieChartResult pieChartFormDataResult;
                    if(firstFormData) {
                        pieChartFormDataResult = new FormDataPieChartResult(item);
                        for(String value : item.getProperties().getValues().split(";")) {
                            pieChartFormDataResult.addNewValue(value, 0);
                        }
                        formDatasResults.put(item.getId(), pieChartFormDataResult);
                    }
                    pieChartFormDataResult = ((FormDataPieChartResult)formDatasResults.get(item.getId()));
                    pieChartFormDataResult.incrementValue(formBuilderItemData.getValue());
                } else if (item instanceof FormBuilderItemNumber) {
                    FormDataFloatResult floatFormDataResult;
                    if(firstFormData) {
                        floatFormDataResult = new FormDataFloatResult(item);
                        formDatasResults.put(item.getId(), floatFormDataResult);
                    }
                    ((FormDataFloatResult)formDatasResults.get(item.getId())).addFloatFormDataResult(formBuilderItemData.getNumberValue());
                }
            }
            firstFormData = false;
        }
        return formDatasResults.values();
    }
    
}
