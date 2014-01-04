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

import at.reppeitsolutions.formbuilder.components.helper.FormBuilderItemFactory;
import com.lowagie.text.Element;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItem;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.data.FormBuilderItemData;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderCheckbox;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderSelect;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderHeading;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderHorizontalRule;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderImage;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderInput;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderItem;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderLabel;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderListbox;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderRadio;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderSpace;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderTextarea;
import at.reppeitsolutions.formbuilder.components.pdf.formbuilder.PdfFormBuilderUpload;
import at.reppeitsolutions.formbuilder.messages.Messages;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public abstract class FormBuilderItemPdfFactory {

    public static Element getUIPdfComponent(FormBuilderItemData data) {
        return getUIPdfComponent(data.getFormBuilderItem(), data);
    }

    public static Element getUIPdfComponent(FormBuilderItem item) {
        return getUIPdfComponent(item, null);
    }

    public static Element getUIPdfComponent(FormBuilderItem item, FormBuilderItemData data) {
        String type = item.getFormbuildertype();
        PdfFormBuilderItem comp;
        boolean skipPropertiesCopy = false;
        switch (type) {
            case FormBuilderItemFactory.TYPE_LABEL:
                comp = new PdfFormBuilderLabel();
                break;
            case FormBuilderItemFactory.TYPE_HEADING:
                comp = new PdfFormBuilderHeading();
                break;
            case FormBuilderItemFactory.TYPE_HR:
                comp = new PdfFormBuilderHorizontalRule();
                break;
            case FormBuilderItemFactory.TYPE_SPACE:
                comp = new PdfFormBuilderSpace();
                break;
            case FormBuilderItemFactory.TYPE_DATE:
            case FormBuilderItemFactory.TYPE_TIME:
            case FormBuilderItemFactory.TYPE_INPUT:
                comp = new PdfFormBuilderInput();
                break;
            case FormBuilderItemFactory.TYPE_TEXTAREA:
                comp = new PdfFormBuilderTextarea();
                break;
            case FormBuilderItemFactory.TYPE_IMAGE:
                comp = new PdfFormBuilderImage();
                break;
            case FormBuilderItemFactory.TYPE_UPLOAD:
                comp = new PdfFormBuilderUpload();
                break;
            case FormBuilderItemFactory.TYPE_SELECT:
                comp = new PdfFormBuilderSelect();
                break;
            case FormBuilderItemFactory.TYPE_LISTBOX:
                comp = new PdfFormBuilderListbox();
                break;
            case FormBuilderItemFactory.TYPE_CHECKBOX:
                comp = new PdfFormBuilderCheckbox();
                break;
            case FormBuilderItemFactory.TYPE_RADIO:
                comp = new PdfFormBuilderRadio();
                break;
            default:
                comp = new PdfFormBuilderLabel();
                comp.getProperties().setValues(Messages.getStringJSF("pdf.error"));
                skipPropertiesCopy = true;
                break;
        }
        if (!skipPropertiesCopy) {
            BeanUtils.copyProperties(item.getProperties(), comp.getProperties());
        }
        //Set data of html object
        if (data != null) {
            comp.setValue(data.getValue());
            comp.setFile(data.getFile());
        } else {
            comp.setItemUuid(item.getId());
        }
        //render html object
        return comp.render();
    }
}
