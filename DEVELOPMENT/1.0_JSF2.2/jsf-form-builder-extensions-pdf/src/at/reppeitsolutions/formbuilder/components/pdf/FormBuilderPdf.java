/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.reppeitsolutions.formbuilder.components.pdf;

import at.reppeitsolutions.formbuilder.components.Constants;
import at.reppeitsolutions.formbuilder.components.FormBuilderComponentBase;
import at.reppeitsolutions.formbuilder.components.pdf.renderer.formbuilder.FormBuilderPdfRenderer;
import at.reppeitsolutions.formbuilder.components.pdf.renderer.formbuilder.FormFillerPdfRenderer;
import javax.faces.component.FacesComponent;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE_PDF, tagName = "formBuilderPdf")
public class FormBuilderPdf extends FormBuilderComponentBase {
    
    public FormBuilderPdf() {
        setRendererType(FormBuilderPdfRenderer.RENDERTYPE);
    }

    @Override
    public String getFamily() {
        return FormBuilderPdfRenderer.FAMILY;
    }
    
}
