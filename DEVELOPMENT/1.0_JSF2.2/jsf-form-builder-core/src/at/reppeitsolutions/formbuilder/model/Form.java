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
package at.reppeitsolutions.formbuilder.model;

import at.reppeitsolutions.formbuilder.components.Constants;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemFormatArea;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity(name = Constants.TABLE_PREFIX + "form")
public class Form implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<FormBuilderItemBase> items;

    public Form() {
        items = new ArrayList<>();
    }
    private static final String WITHOUTAREAKEY = "withoutarea";

    @Override
    public Form clone() {
        //TODO copy form and all items
        throw new UnsupportedOperationException();
    }

    public List<FormBuilderItemBase> getItems() {
        Collections.sort(items);
        Map<String, List<FormBuilderItemBase>> labelLengthGroups = new HashMap<>();
        labelLengthGroups.put(WITHOUTAREAKEY, new ArrayList<FormBuilderItemBase>());
        String activeAreaId = null;
        for (FormBuilderItemBase item : items) {
            if (item instanceof FormBuilderItemFormatArea) {
                if (item.getProperties().getFormatareauuid().equals(activeAreaId)) {
                    activeAreaId = null;
                } else {
                    activeAreaId = item.getProperties().getFormatareauuid();
                }
            } else {
                if (activeAreaId == null) {
                    labelLengthGroups.get(WITHOUTAREAKEY).add(item);
                } else {
                    if (labelLengthGroups.containsKey(activeAreaId)) {
                        labelLengthGroups.get(activeAreaId).add(item);
                    } else {
                        List<FormBuilderItemBase> newItems = new ArrayList<>();
                        newItems.add(item);
                        labelLengthGroups.put(activeAreaId, newItems);
                    }
                }
            }
        }
        for (List<FormBuilderItemBase> labelLengthGroup : labelLengthGroups.values()) {
            setLabelLength(labelLengthGroup);
        }
        return items;
    }

    public void addItem(FormBuilderItemBase item) {
        item.setForm(this);
        items.add(item);
    }

    public void addItems(List<FormBuilderItemBase> items) {
        for (FormBuilderItemBase item : items) {
            addItem(item);
        }
    }

    public void setItems(List<FormBuilderItemBase> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void setLabelLength(List<FormBuilderItemBase> items) {
        int maxLabelLength = -1;
        for (FormBuilderItemBase item : items) {
            if (item.getProperties() != null
                    && item.getProperties().getLabel() != null) {
                int offset = 0;
                if(item.getProperties().getMandatory()) {
                    offset = 2;
                }
                int labelLength = item.getProperties().getLabel().length() + offset;
                if (labelLength > maxLabelLength
                        && (item.getProperties().getOnelinedescription() == null || item.getProperties().getOnelinedescription() == Boolean.FALSE)) {
                    maxLabelLength = labelLength;
                }
            }
        }
        for (FormBuilderItemBase item : items) {
            if (item.getProperties() != null) {
                item.getProperties().setLabelLength(maxLabelLength);
            }
        }
    }
}
