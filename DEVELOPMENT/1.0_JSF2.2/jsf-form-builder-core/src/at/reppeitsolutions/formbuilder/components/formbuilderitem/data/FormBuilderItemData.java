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
package at.reppeitsolutions.formbuilder.components.formbuilderitem.data;

import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.html.renderer.multipart.File;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@Entity(name = "formdataitem")
public class FormBuilderItemData implements Serializable, Comparable {
    
    @Id
    private String uuid = UUID.randomUUID().toString();
    private String value;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="form_data_id")
    protected FormData formData;
    @OneToOne
    private FormBuilderItemBase formBuilderItem;
    @Embedded
    private File file;

    public FormBuilderItemData() {
        
    }
    
    @Override
    public int compareTo(Object t) {
        FormBuilderItemData item = (FormBuilderItemData) t;
        if (getFormBuilderItem().getPosition() > item.getFormBuilderItem().getPosition()) {
            return 1;
        } else if (getFormBuilderItem().getPosition() < item.getFormBuilderItem().getPosition()) {
            return -1;
        }
        return 0;
    }

    public FormBuilderItemBase getFormBuilderItem() {
        return formBuilderItem;
    }

    public void setFormBuilderItem(FormBuilderItemBase formBuilderItem) {
        this.formBuilderItem = formBuilderItem;
    }

    public String getUuid() { 
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public FormData getFormData() {
        return formData;
    }

    public void setFormData(FormData formData) {
        this.formData = formData;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
}
