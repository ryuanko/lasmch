//water mark
var setBackground = function () {
    var mark = document.getElementById("mark").value;
    var watermark = document.getElementsByClassName("watermark");
    var waterW = (mark.length * 8.5);
    var svgstring = '<svg id="diagtext" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="100%" height="100%"><style type="text/css">text { fill: #ededed; font-weight: 100; }</style><defs><pattern id="mark" patternUnits="userSpaceOnUse" width="' + waterW + '" height="18"><text y="18" font-size="14" id="name">' + mark + '</text></pattern><pattern xlink:href="#mark"></pattern><pattern id="combo" xlink:href="#mark"><use xlink:href="#name" /><use xlink:href="#occupation" /></pattern></defs><rect width="100%" height="100%" fill="url(#combo)" /></svg>';
    for (var i = 0; i < watermark.length; i++) {
        watermark[i].style.backgroundImage = "url('data:image/svg+xml;base64," + window.btoa(svgstring) + "')";
    }
}
setBackground();