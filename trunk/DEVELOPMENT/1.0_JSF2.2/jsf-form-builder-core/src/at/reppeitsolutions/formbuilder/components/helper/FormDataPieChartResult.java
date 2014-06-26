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
import at.reppeitsolutions.formbuilder.model.FormData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormDataPieChartResult extends FormDataBaseResult implements FormDataResult, Comparable<FormDataPieChartResult> {

    private Map<String, Integer> values = new HashMap<>();

    public FormDataPieChartResult(FormBuilderItemBase formBuilderItem) {
        super(formBuilderItem);
    }

    /*
     * @return: Map where the key is the name of the pie and the value is
     * the value of the pie.
     */
    public Map<String, Integer> getValues() {
        return values;
    }

    public void addNewValue(String name, Integer value) {
        if (name != null && value != null) {
            values.put(name, value);
        }
    }

    public void incrementValue(String name) {
        if (name != null) {
            Integer currentValue = values.get(name);
            if(currentValue != null) {
                values.put(name, ++currentValue);
            }
        }
    }

    @Override
    public int compareTo(FormDataPieChartResult t) {
        if(values.size() != t.getValues().size()) {
            return -1;
        }
        Iterator<Map.Entry<String, Integer>> valuesIterator = values.entrySet().iterator();
        Iterator<Map.Entry<String, Integer>> tValuesIterator = t.getValues().entrySet().iterator();
        
        while(valuesIterator.hasNext() && tValuesIterator.hasNext()) {
            Map.Entry<String, Integer> pair1 = valuesIterator.next();
            Map.Entry<String, Integer> pair2 = tValuesIterator.next();
            if(!pair1.getKey().equals(pair2.getKey())) {
                return -1;
            }
            if(!pair1.getValue().equals(pair2.getValue())) { 
                return -1;
            }
        }
        return 0;
    } 
}
