<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<body>
<h2>Hello World!</h2>
	<%out.println(request.getAttribute("imgStr"));%>
	<%-- <input id="button2" type="button" value="图片按钮" onclick="toPic(${imgStr})"/>  --%>
</body>

<script>
 	function toPic(imgStr){
 		var bytes = new Uint8Array(strPic);
        var blob = new Blob([bytes], { type: "image/png" });
        var url = URL.createObjectURL(blob);
        document.getElementById('image').src = url;
        console.log("received some data");
 	}
</script>
</html>
