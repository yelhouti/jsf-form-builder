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

import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import at.reppeitsolutions.formbuilder.model.Form;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity
public class FormData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();
    private String username = "Testuser";
    @OneToMany(mappedBy = "formData", cascade = CascadeType.ALL)
    private List<FormBuilderItemData> data;
    @OneToOne
    private Form form;

    protected FormData() {
        data = new ArrayList<>();
    }
    
    public FormData(Form form) {
        this.form = form;
        if(form.getItems() != null) {
            data = new ArrayList<>();
            for(FormBuilderItemBase item : form.getItems()) {
                FormBuilderItemData itemData = new FormBuilderItemData();
                itemData.setFormBuilderItem(item);
                itemData.setFormData(this);
                data.add(itemData);
            }
        }
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
    
    public List<FormBuilderItemData> getData() {
        Collections.sort(data);
        return data;
    }

    public void setData(List<FormBuilderItemData> data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
