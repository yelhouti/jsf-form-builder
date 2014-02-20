var allIframes_ = new Array();

function regIframe(iframe) {
    var i = document.getElementById(iframe);
    if (i !== null)
        allIframes_.push(i);
}

$(function() {
    window.setInterval("autor_frames()", 400);
});

function autor_frames() {
    for (var i = 0; i < allIframes_.length; ++i) {
        if (allIframes_[i].contentWindow !== null) {
            if(allIframes_[i].contentWindow.document.body) {
                var framefenster_size = allIframes_[i].contentWindow.document.body.offsetHeight;
                if(document.all && !window.opera) {
                    framefenster_size = allIframes_[i].contentWindow.document.body.scrollHeight;
                }
                allIframes_[i].style.height = (framefenster_size + 50) + 'px';
            }
        }
    }
}