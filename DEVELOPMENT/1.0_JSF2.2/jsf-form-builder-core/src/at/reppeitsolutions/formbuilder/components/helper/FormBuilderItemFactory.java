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

import at.reppeitsolutions.formbuilder.components.FormBuilderInternal;
import at.reppeitsolutions.formbuilder.components.FormFillerInternal;
import at.reppeitsolutions.formbuilder.components.annotations.SkipDialog;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemProperties;
import at.reppeitsolutions.formbuilder.model.FormBuilderItemData;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderHeading;
import at.reppeitsolutions.formbuilder.components.html.dialogs.HtmlDialog;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderCheckbox;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderConstraint;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderDate;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderDownload;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderFormatArea;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderSpan;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderHorizontalRule;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderImage;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderItem;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderInput;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderLabel;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderListbox;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderMetaData;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderNumber;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderPagebreak;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderRadio;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderSelect;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderSpace;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderTextarea;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderTime;
import at.reppeitsolutions.formbuilder.components.html.formbuilder.HtmlFormBuilderUpload;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public abstract class FormBuilderItemFactory {

    public static final String TYPE_HR = "fbhr";
    public static final String TYPE_INPUT = "fbinput";
    public static final String TYPE_SELECT = "fbselect";
    public static final String TYPE_HEADING = "fbheading";
    public static final String TYPE_TEXTAREA = "fbtextarea";
    public static final String TYPE_RADIO = "fbradio";
    public static final String TYPE_CHECKBOX = "fbcheckbox";
    public static final String TYPE_LISTBOX = "fblistbox";
    public static final String TYPE_LABEL = "fblabel";
    public static final String TYPE_SPACE = "fbspace";
    public static final String TYPE_UPLOAD = "fbupload";
    public static final String TYPE_IMAGE = "fbimage";
    public static final String TYPE_DATE = "fbdate";
    public static final String TYPE_TIME = "fbtime";
    public static final String TYPE_FORMATAREA = "fbformatarea";
    public static final String TYPE_PAGEBREAK = "fbpagebreak";
    public static final String TYPE_DOWNLOAD = "fbdownload";
    public static final String TYPE_NUMBER = "fbnumber";
    public static final String TYPE_CONSTRAINT = "fbconstraint";
    public static final String TYPE_METADATA = "fbmetadata";

    public static HtmlFormBuilderItem getUIComponent(FormBuilderItemData data,
            String mode,
            FormFillerInternal formFillerInternal) {
        return getUIComponent(data.getFormBuilderItem(), data, false, mode, null, formFillerInternal);
    }

    public static HtmlFormBuilderItem getUIComponent(FormBuilderItemBase item) {
        return getUIComponent(item, null, false, null, null, null);
    }

    public static HtmlFormBuilderItem getUIComponentWithDialog(FormBuilderItemBase item) {
        return getUIComponent(item, null, true, null, null, null);
    }

    public static HtmlFormBuilderItem getUIComponentWithDialog(FormBuilderItemBase item,
            FormBuilderInternal formBuilderInternal) {
        return getUIComponent(item, null, true, null, formBuilderInternal, null);
    }

    private static HtmlFormBuilderItem getUIComponent(FormBuilderItemBase item,
            FormBuilderItemData data,
            boolean dialog,
            String mode,
            FormBuilderInternal formBuilderInternal,
            FormFillerInternal formFillerInternal) {
        String type = item.getFormbuildertype();
        HtmlFormBuilderSpan res = new HtmlFormBuilderSpan(item);
        HtmlFormBuilderItem comp = null;
        HtmlDialog diag = null;
        switch (type) {
            case TYPE_HR:
                comp = new HtmlFormBuilderHorizontalRule();
                break;
            case TYPE_INPUT:
                comp = new HtmlFormBuilderInput();
                break;
            case TYPE_NUMBER:
                comp = new HtmlFormBuilderNumber();
                break;
            case TYPE_LABEL:
                comp = new HtmlFormBuilderLabel();
                break;
            case TYPE_SELECT:
                comp = new HtmlFormBuilderSelect();
                break;
            case TYPE_CHECKBOX:
                comp = new HtmlFormBuilderCheckbox();
                break;
            case TYPE_LISTBOX:
                comp = new HtmlFormBuilderListbox();
                break;
            case TYPE_RADIO:
                comp = new HtmlFormBuilderRadio();
                break;
            case TYPE_HEADING:
                comp = new HtmlFormBuilderHeading();
                break;
            case TYPE_TEXTAREA:
                comp = new HtmlFormBuilderTextarea();
                break;
            case TYPE_SPACE:
                comp = new HtmlFormBuilderSpace();
                break;
            case TYPE_UPLOAD:
                comp = new HtmlFormBuilderUpload();
                break;
            case TYPE_IMAGE:
                comp = new HtmlFormBuilderImage();
                break;
            case TYPE_DATE:
                comp = new HtmlFormBuilderDate();
                break;
            case TYPE_TIME:
                comp = new HtmlFormBuilderTime();
                break;
            case TYPE_FORMATAREA:
                comp = new HtmlFormBuilderFormatArea();
                break;
            case TYPE_CONSTRAINT:
                if (formBuilderInternal != null) {
                    comp = new HtmlFormBuilderConstraint(formBuilderInternal.getWorkflowStates(),
                            formBuilderInternal.getConstraintClients(),
                            item.getConstraints());
                } else {
                    comp = new HtmlFormBuilderConstraint();
                }
                break;
            case TYPE_PAGEBREAK:
                comp = new HtmlFormBuilderPagebreak();
                break;
            case TYPE_DOWNLOAD:
                comp = new HtmlFormBuilderDownload();
                break;
            case TYPE_METADATA:
                if (formBuilderInternal != null) {
                    comp = new HtmlFormBuilderMetaData(formBuilderInternal.getMetaDataObject());
                } else if(formFillerInternal != null) {
                    comp = new HtmlFormBuilderMetaData(formFillerInternal.getMetaDataObject());
                } else {
                    comp = new HtmlFormBuilderMetaData();
                }
                break;
        }
        if (comp != null) {
            BeanUtils.copyProperties(item.getProperties(), comp.getProperties());
            item.getProperties().setMaximise(Boolean.FALSE);
            //Create properties dialog
            if (!comp.getClass().isAnnotationPresent(SkipDialog.class)) {
                diag = new HtmlDialog(item);
            }
            //Set data of html object
            if (data != null) {
                comp.setValue(data.getValue());
                comp.setNumberValue(data.getNumberValue());
                comp.setDataUuid(data.getUuid());
                comp.setFile(data.getFile());
            } else {
                comp.setItemUuid(item.getId());
            }
            if (mode != null) {
                comp.setMode(mode);
            }
            //render html object
            comp.render();
            //add dialog to html object
            if (dialog && diag != null) {
                comp.getChildren().add(diag);
            }
            res.getChildren().add(comp);
        }
        return res;
    }

    public static void updateFormBuilderItem(FormBuilderItemBase item, FormBuilderItemUpdate update) {
        if (update != null) {
            invokeMethods(FormBuilderItemBase.class.getMethods(), update, item);
            invokeMethods(FormBuilderItemProperties.class.getMethods(), update, item.getProperties());
        }
    }

    private static void invokeMethods(Method[] methods, FormBuilderItemUpdate update, Object object) {
        if (object != null) {
            for (Method method : methods) {
                if (method.getName().toLowerCase().equals("set" + update.getMethod().toLowerCase())) {
                    try {
                        Class cls = method.getParameterTypes()[0];
                        if (cls == String.class) {
                            method.invoke(object, update.getValue());
                        } else if (cls == Integer.class) {
                            method.invoke(object, Integer.valueOf(update.getValue()));
                        } else if (cls == Boolean.class) {
                            method.invoke(object, Boolean.valueOf(update.getValue()));
                        }
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(FormBuilderItemFactory.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(FormBuilderItemFactory.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(FormBuilderItemFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public static void updateFormBuilderItem(FormBuilderItemBase item, List<FormBuilderItemUpdate> updates) {
        if (updates != null) {
            for (FormBuilderItemUpdate update : updates) {
                updateFormBuilderItem(item, update);
            }
        }
    }
}
