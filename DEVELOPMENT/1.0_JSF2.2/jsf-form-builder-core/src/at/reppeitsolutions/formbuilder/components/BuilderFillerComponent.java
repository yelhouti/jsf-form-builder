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
package at.reppeitsolutions.formbuilder.components;

import at.reppeitsolutions.formbuilder.components.html.HtmlIFrame;
import at.reppeitsolutions.formbuilder.components.html.HtmlUnorderedList;
import javax.annotation.PostConstruct;
import javax.faces.component.UICommand;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public abstract class BuilderFillerComponent extends UICommand {
    
    protected HtmlUnorderedList formContent;
    protected HtmlIFrame iframe;
    
    @PostConstruct
    public void initBase() {
        setInvokeCallback(false);
        if(iframe != null) {
            iframe.setOnload("document.getElementById('loadImg').style.display='none';");
        }
    }
    
    /*
     * Call only from renderer
     */
    public void addLoadImage() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HtmlOutputText loadImage = new HtmlOutputText();
        loadImage.setEscape(false);
        loadImage.setValue("<div id=\"loadImg\"><img width=\"50px\" height=\"50px\" src=\"" + request.getContextPath() + "/javax.faces.resource/formbuilder/images/ajaxReload.gif.xhtml\" /></div>");
        getChildren().add(loadImage);
    }
    
    public HtmlUnorderedList getFormContent() {
        return formContent;
    }
    
    public HtmlIFrame getIFrame() {
        return iframe;
    }
    
    public boolean getInvokeCallback() {
        return (boolean) getStateHelper().eval("invokeCallback");
    }

    public void setInvokeCallback(boolean invokeCallback) {
        getStateHelper().put("invokeCallback", invokeCallback);
    }
    
}
