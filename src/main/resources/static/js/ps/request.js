function xhr(method, uri, body, handler) {
    var req = (window.XMLHttpRequest) ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP');
    req.onreadystatechange = function ()
    {
        if (req.readyState == 4 && handler)
        {
            eval('var o=' + req.responseText);
            handler(o);
        }
    }
    req.open(method, uri, true);
    req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    req.send(body);
}