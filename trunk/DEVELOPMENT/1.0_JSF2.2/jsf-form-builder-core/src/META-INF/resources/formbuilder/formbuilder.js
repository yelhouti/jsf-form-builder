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

var count = 1;

function setHtmlFormBuilderSpanProperty(id, property, value) {
    $("#" + id).attr(property, value);
    updateForm();
}

function openDialog(id) {
    //TODO why this does not work with $("#" + $("#" + id).attr("diagid")).dialog("open");
    $(".dialog").each(function() {
        if ($(this).attr("id") === $("#" + id).attr("diagid")) {
            $(this).dialog("open");
        }
    });
}

function downloadFile(formdatauuid) {
    document.getElementById(formActionString_).value = "download";
    document.getElementById(formContentString_).value = formdatauuid;
    window.jsf.ajax.request(formid_, null, {render: formid_, onevent: function() {
            window.location.reload();
        }});
}

function deleteItem(id) {
    showLoadImage();
    document.getElementById(formActionString_).value = "delete";
    document.getElementById(formContentString_).value = id;
    window.jsf.ajax.request(formid_, null, {render: formid_, onevent: function() {
            initJQuery();
            hideLoadImage();
        }});
}

function hideLoadImage() {
    $("#ajaxReload").hide();
}

function showLoadImage() {
    $("#ajaxReload").show();
}

function initNumbers() {
    $('.number').keydown(function(event) {
        // Allow special chars + arrows 
        if (event.keyCode === 46 || event.keyCode === 8 || event.keyCode === 9
                || event.keyCode === 27 || event.keyCode === 13
                || (event.keyCode === 65 && event.ctrlKey === true)
                || (event.keyCode >= 35 && event.keyCode <= 39)
                || (event.keyCode === 188)) {
            if (event.keyCode === 188 && $(this).val().indexOf(",") !== -1) {
                event.preventDefault();
            } else {
                return;
            }
        } else {
            // If it's not a number stop the keypress
            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105)) {
                event.preventDefault();
            }
        }
    });
}

function initJQuery() {
    $(".dialog").dialog({
        resizable: false,
        draggable: false,
        autoOpen: false,
        modal: true,
        width: 450,
        title: document.getElementById(formid_ + ":prop-dialog-header").value
    });
    $(".commandButton").button();
    initDatepicker();
    initTimepicker();
    initNumbers();
}

function initDatepicker() {
    $(".datepicker").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd.mm.yy"
    });
}

function initTimepicker() {
    $(".timepicker").timepicker({
        timeFormat: 'H:i'
    });
}

function saveProperties(itemid) {
    var json = "{";
    json += "\"itemId\":\"" + itemid + "\",";
    json += "\"updates\":[";
    $(".property-" + itemid).each(function() {
        json += "{";
        json += "\"method\":\"" + $(this).attr("method") + "\",";
        var value = "";
        if ($(this).val() === "on") {
            value = $(this).prop("checked");
        } else {
            value = $(this).val();
        }
        json += "\"value\":\"" + value + "\"";
        json += "},";
    });
    json = json.substr(0, json.length - 1);
    json += "]";
    json += "}";
    $(".dialog").remove();
    editForm(json);
}

function editForm(json) {
    showLoadImage();
    document.getElementById(formActionString_).value = "edit";
    document.getElementById(formContentString_).value = json;
    window.jsf.ajax.request(formid_, null, {render: formid_, onevent: function() {
            initJQuery();
            hideLoadImage();
        }});
}

function updateForm() {
    showLoadImage();
    $(".dialog").remove();
    document.getElementById(formActionString_).value = "update";
    document.getElementById(formContentString_).value = getJson(formContent_);
    updateFormajaxRequest();
}

function updateFormajaxRequest() {
    window.jsf.ajax.request(formid_, null, {
        render: formid_,
        onevent: function() {
            initJQuery();
            hideLoadImage();
        },
        onerror: function(error) {
            alert(error.errorMessage);
            //TODO not saved changes get lost
            window.location.reload();
        }
    });
}

function getJson() {
    var pos = 0;
    var json = "";
    var sep = "<seperator>";
    $("#" + formContent_ + " li span").each(function(index) {
        json += "{";
        json += "\"position\":\"" + pos + "\",";
        pos++;
        var attributes = $(this).listAttributes();
        var properties_prefix = "properties_";
        var properties = "\"properties\":{";
        var properties_added = false;
        for (var i = 0; i < attributes.length; i++) {
            if (attributes[i] !== "position" && attributes[i] !== "onclick" && attributes[i].indexOf(properties_prefix) === -1) {
                try {
                    json += "\"" + attributes[i] + "\":\"" + $(this).attr(attributes[i]) + "\",";
                } catch (ex) {

                }
            }
            if (attributes[i].indexOf(properties_prefix) === 0) {
                try {
                    if ($(this).attr(attributes[i]) !== "null") {
                        properties += ("\"" + attributes[i].substr(properties_prefix.length, attributes[i].length) + "\":\"" + $(this).attr(attributes[i]) + "\",");
                        properties_added = true;
                    }
                } catch (ex) {

                }
            }
        }
        json = json.substr(0, json.length - 1);
        properties = properties.substr(0, properties.length - 1);
        properties += "}";
        if (properties_added) {
            json += "," + properties;
        }
        json += "}" + sep;
    });
    json = json.substr(0, json.length - sep.length);
    return json;
}

var formActionString_;
var formContentString_;
var formid_;
var formContent_;

function initDownloadable(formid, formActionString, formContentString) {
    formid_ = formid;
    formActionString_ = formActionString;
    formContentString_ = formContentString;
    initDatepicker();
    initTimepicker();
    initNumbers();
}

function initDraggable(formid, palette, formContent, formActionString, formContentString, formActiveTabString) {
    showLoadImage();
    formid_ = formid;
    var palettes = palette.split(";");
    formContent_ = formContent;
    formActionString_ = formActionString;
    formContentString_ = formContentString;

    $("#accordion").accordion({
        heightStyle: "content",
        active: parseInt(document.getElementById(formActiveTabString).value),
        activate: function() {
            document.getElementById(formActiveTabString).value = $("#accordion").accordion("option","active");
        }
    });

    for(var i = 0; i < palettes.length; i++) {
        $("#" + palettes[i] + " li").draggable({
            appendTo: "body",
            helper: "clone",
            cursor: "move"
        });
    }

    $("#" + formContent_).droppable({
        activeClass: "ui-state-highlight",
        hoverClass: "ui-state-hover",
        accept: ":not(.ui-sortable-helper)",
        drop: function(event, ui) {
            $("#" + formContent).find(".placeholder").remove();
            count++;
            $("<li class=\"ui-state-default\" id=\"test_" + count + "\"></li>").html(ui.draggable.html()).appendTo(this);
            updateForm();
        }
    }).sortable({
        items: "li:not(.placeholder)",
        cursor: "move",
        sort: function() {
            // gets added unintentionally by droppable interacting with sortable
            // using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
            $(this).removeClass("ui-state-default");
        },
        update: function() {
            updateForm();
        }
    });

    $("#" + formContent_ + " li").resizable({
        containment: "#" + formContent_,
        maxWidth: 600,
        minWidth: 200,
        grid: 300,
        handles: "e",
        stop: function() {
            var newWidth = $(this).attr("style");
            var start = newWidth.substr(newWidth.indexOf("width: ") + "width: ".length, newWidth.length);
            newWidth = start.substr(0, start.indexOf(";"));
            $(this).children().each(function() {
                if ($(this).prop("tagName") === "SPAN") {
                    var json = "{";
                    json += "\"itemId\":\"" + $(this).attr("id") + "\",";
                    json += "\"updates\":[";
                    json += "{";
                    json += "\"method\":\"width\",";
                    json += "\"value\":\"" + newWidth + "\"";
                    json += "}";
                    json += "]";
                    json += "}";
                    editForm(json);
                }
            });
        }
    });
    hideLoadImage();
    initJQuery();
}