/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.reppeitsolutions.formbuilder.components.pdf.helper;

import at.reppeitsolutions.formbuilder.components.formbuilderitem.FormBuilderItemBase;
import at.reppeitsolutions.formbuilder.model.FormBuilderItemData;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class FormBuilderFillerPdfContainer {
    
    private FormBuilderItemBase formBuilderItem;
    private FormBuilderItemData formBuilderItemData;

    public FormBuilderFillerPdfContainer() {
        
    }

    public FormBuilderFillerPdfContainer(FormBuilderItemBase formBuilderItem, FormBuilderItemData formBuilderItemData) {
        this.formBuilderItem = formBuilderItem;
        this.formBuilderItemData = formBuilderItemData;
    }

    public FormBuilderItemBase getFormBuilderItem() {
        return formBuilderItem;
    }

    public void setFormBuilderItem(FormBuilderItemBase formBuilderItem) {
        this.formBuilderItem = formBuilderItem;
    }

    public FormBuilderItemData getFormBuilderItemData() {
        return formBuilderItemData;
    }

    public void setFormBuilderItemData(FormBuilderItemData formBuilderItemData) {
        this.formBuilderItemData = formBuilderItemData;
    }
    
}
