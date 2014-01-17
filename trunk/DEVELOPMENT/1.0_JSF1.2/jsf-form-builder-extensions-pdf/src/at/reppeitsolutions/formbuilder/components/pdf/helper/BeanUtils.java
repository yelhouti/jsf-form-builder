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
package at.reppeitsolutions.formbuilder.components.pdf.helper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author
 * http://stackoverflow.com/questions/835416/how-to-copy-properties-from-one-java-bean-to-another
 */
public class BeanUtils {

    public static void copyProperties(Object fromObj, Object toObj) {
        if (fromObj != null && toObj != null) {
            Class<? extends Object> fromClass = fromObj.getClass();
            Class<? extends Object> toClass = toObj.getClass();

            try {
                BeanInfo fromBean = Introspector.getBeanInfo(fromClass);
                BeanInfo toBean = Introspector.getBeanInfo(toClass);

                PropertyDescriptor[] toPd = toBean.getPropertyDescriptors();
                List<PropertyDescriptor> fromPd = Arrays.asList(fromBean
                        .getPropertyDescriptors());

                for (PropertyDescriptor propertyDescriptor : toPd) {
                    propertyDescriptor.getDisplayName();
                    PropertyDescriptor pd = fromPd.get(fromPd.indexOf(propertyDescriptor));
                    if (pd.getDisplayName().equals(
                            propertyDescriptor.getDisplayName())
                            && !pd.getDisplayName().equals("class")) {
                        if (propertyDescriptor.getWriteMethod() != null) {
                            propertyDescriptor.getWriteMethod().invoke(toObj, pd.getReadMethod().invoke(fromObj, (Object[]) null));
                        }
                    }

                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
