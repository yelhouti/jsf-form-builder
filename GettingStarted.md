# Getting started with jsf-form-builder #

## JSF Bean (`FormBuilderBean.java`) ##

You need at least one JSF Bean to hold the model of the created form. This bean must be at least view scoped. In the getting started application the form model is hold in a session scoped bean. Therefore we have one form model per session. Form and the FormData classes are annotated for JPA. Therefore you could easy persist your forms into a database.

```
@ManagedBean
@SessionScoped
public class FormBuilderBean implements Serializable {
    
    private Form form;
    private FormData formData;
    
    public FormBuilderBean() {
        
    }
    
    public void reset() {
        form = null;
        formData = null;
    }

    public Form getForm() {
        if(form == null) {
            form = new Form();
        }
        return form;
    }
    
    public String fill() {
        formData = null;
        return "fill";
    }
    
    public FormData getFormData() {
        if(formData == null) {
            formData = new FormData(getForm());
        }
        return formData;
    }
}
```

## Formbuilder (edit.xhtml) ##

This is the important part of the edit.xhtml file:

```
<h:form>
    <r:formBuilder form="#{formBuilderBean.form}" />
</h:form>
```

The `<r:formBuilder />` tag must be inside a `<h:form>` tag with enctype  multipart/form-data and the property model must be from class Form. The command buttons allows you to navigate through the getting started application. The reset button deletes the form model and creates a new one.

```
<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:r="http://www.reppe-itsolutions.at"
      xmlns:f="http://xmlns.jcp.org/jsf/core">
    <f:view>
        <h:head>
            <title>jsf-form-builder getting started</title>
        </h:head>
        <h:body>
            <h1>Edit form</h1>
            <h:form style="display: inline;">
                <h:commandButton action="#{formBuilderBean.reset()}" value="Reset" />
            </h:form>
            <h:form style="display: inline;" target="_blank">
                <h:commandButton action="#{formBuilderBean.fill}" value="Fill form" />
                <h:commandButton action="pdf" value="Pdf export" />
            </h:form>
            <h:form>
                <r:formBuilder form="#{formBuilderBean.form}" />
            </h:form>
        </h:body>
    </f:view>
</html>
```

## Formfiller ##

### Fill data (fill.xhtml) ###

This is the important part of the edit.xhtml file:

```
<h:form>
    <r:formFiller formData="#{formBuilderBean.formData}" />
    <h:commandButton action="view" value="Submit" />
</h:form>
```

The `<r:formFiller />` tag must be inside a `<h:form>` tag with enctype  multipart/form-data and the property model must be from class FormData. You must place a submit button inside the form element. After submit the filled data is automatically submitted in the model.

```
<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:r="http://www.reppe-itsolutions.at"
      xmlns:f="http://xmlns.jcp.org/jsf/core">
    <f:view>
        <h:head>
            <title>jsf-form-builder getting started</title>
        </h:head>
        <h:body>
            <h1>Fill form</h1>
            <button id="submitButton">Submit</button>
            <h:form enctype="multipart/form-data" target="_blank">
                <r:formFiller model="#{formBuilderBean.formData}" action="view" submitButtonId="submitButton" />
            </h:form>
        </h:body>
    </f:view>
</html>
```

### View data (view.xhtml) ###

If you render a `<r:formFiller />` tag with a model containing data you could use this tag to display your filled forms.

```
<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:r="http://www.reppe-itsolutions.at"
      xmlns:f="http://xmlns.jcp.org/jsf/core">
    <f:view>
        <h:head>
            <title>jsf-form-builder getting started</title>
        </h:head>
        <h:body>
            <h1>View filled form</h1>
            <h:form>
                <r:formFiller formData="#{formBuilderBean.formData}" />
            </h:form>
        </h:body>
    </f:view>
</html>
```

## PDF export (pdf.xhtml) ##

This example renders a form into a pdf.

```
<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<pdf  xmlns:f="http://xmlns.jcp.org/jsf/core"
      xmlns:r="http://www.reppe-itsolutions.at/pdf">
    <f:view renderKitId="PDF_BASIC">
        <r:formFillerPdf formData="#{formBuilderBean.formData}" />
    </f:view>
</pdf>
```