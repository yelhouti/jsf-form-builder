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
package at.reppeitsolutions.formbuilder.components.pdf;

import at.reppeitsolutions.formbuilder.components.Constants;
import at.reppeitsolutions.formbuilder.components.FormFiller;
import static at.reppeitsolutions.formbuilder.components.FormFiller.MODE_FILL;
import at.reppeitsolutions.formbuilder.components.FormFillerComponentBase;
import javax.faces.component.FacesComponent;
import at.reppeitsolutions.formbuilder.components.pdf.renderer.formbuilder.FormFillerPdfRenderer;
import javax.annotation.PostConstruct;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
@FacesComponent(createTag = true, namespace = Constants.NAMESPACE_PDF, tagName = "formFillerPdf")
public class FormFillerPdf extends FormFillerComponentBase {

    public FormFillerPdf() {
        setRendererType(FormFillerPdfRenderer.RENDERTYPE);
    }
    
    @PostConstruct
    public void init() {
        setMode(FormFiller.MODE_FILL);
    }

    @Override
    public String getFamily() {
        return FormFillerPdfRenderer.FAMILY;
    }
}
