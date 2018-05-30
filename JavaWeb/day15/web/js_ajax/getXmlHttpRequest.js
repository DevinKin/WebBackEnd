function getXmlHttpRequest() {
    try {
        //支持大多数浏览器
        return new XMLHttpRequest();
    } catch (e) {
        try {
            //IE6.0
            return ActiveXObject("Msxm2.XML.HTTP");
        } catch (e) {
            try {
                // IE5.5以及更早版本的IE
                return ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
                alert("你用的是什么鬼浏览器？")
                throw e;
            }
        }
    }
}