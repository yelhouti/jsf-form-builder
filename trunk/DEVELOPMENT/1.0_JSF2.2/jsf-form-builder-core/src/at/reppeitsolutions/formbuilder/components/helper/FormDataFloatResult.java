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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormDataFloatResult extends FormDataBaseResult  implements FormDataResult {
    
    private List<Float> floatFormDataResults = new ArrayList<>();

    public FormDataFloatResult(FormBuilderItemBase formBuilderItem) {
        super(formBuilderItem);
    }
    
    public void addFloatFormDataResult(Float value) {
        if(value != null) {
            floatFormDataResults.add(value);
        }
    }
    
    public Float getMaxValue() {
        Float max = null;
        for(Float tmp : floatFormDataResults) {
            if(max == null) {
                max = tmp;
            }
            if(tmp >= max) {
                max = tmp;
            }
        }
        return max;
    }
    
    public Float getMinValue() {
        Float min = null;
        for(Float tmp : floatFormDataResults) {
            if(min == null) {
                min = tmp;
            }
            if(tmp <= min) {
                min = tmp;
            }
        }
        return min;
    }
    
    public Float getAverage() {
        Float sum = new Float(0);
        for(Float tmp : floatFormDataResults) {
            sum += tmp;
        }
        return sum / new Float(floatFormDataResults.size());
    }
    
}
