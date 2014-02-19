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

import java.io.Serializable;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class ConstraintId implements Serializable {

    private String formBuilderItemUuid;
    private Long constraingClientId;

    @Override
    public int hashCode() {
        return ((String) (formBuilderItemUuid + constraingClientId)).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ConstraintId) {
            ConstraintId otherId = (ConstraintId) object;
            return otherId.formBuilderItemUuid != null &&
                   this.formBuilderItemUuid != null &&
                   (otherId.formBuilderItemUuid.equals(this.formBuilderItemUuid)) && 
                   (otherId.constraingClientId == this.constraingClientId);
        }
        return false;
    }

    public String getFormBuilderItemUuid() {
        return formBuilderItemUuid;
    }

    public void setFormBuilderItemUuid(String formBuilderItemUuid) {
        this.formBuilderItemUuid = formBuilderItemUuid;
    }

    public Long getConstraingClientId() {
        return constraingClientId;
    }

    public void setConstraingClientId(Long constraingClientId) {
        this.constraingClientId = constraingClientId;
    }
}
