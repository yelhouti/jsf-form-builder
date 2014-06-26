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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormDataStringResult extends FormDataBaseResult  implements FormDataResult, Comparable<FormDataStringResult> {
    
    private List<String> stringFormDataResults = new ArrayList<>();

    public FormDataStringResult(FormBuilderItemBase formBuilderItem) {
        super(formBuilderItem);
    }
    
    public void addStringFormDataResult(String value) {
        if(value != null && !"".equals(value)) {
            stringFormDataResults.add(value);
        }
    }

    public List<String> getStringFormDataResults() {
        return stringFormDataResults;
    }

    @Override
    public int compareTo(FormDataStringResult t) {
        if(stringFormDataResults.size() != t.getStringFormDataResults().size()) {
            return -1;
        }
        for(int count = 0; count < stringFormDataResults.size(); ++count) {
            if(!stringFormDataResults.get(count).equals(t.getStringFormDataResults().get(count))) {
                return -1;
            }
        }
        return 0;
    }
    
}
