<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>

<body>
	<%-- <%out.println(request.getAttribute("imgStr"));%> --%>
	<%
	String data = (String)request.getAttribute("imgStr");
	%>
	<%-- <%=data%> --%>
	<img src="data:image; base64, <%=data%>"/>
	<%-- <input id="button1" type="button" value="图片按钮" onclick="toPic('<%=data%>')"/> --%>
	<input id="button2" type="button" value="图片按钮2" onclick="toPic2()" />
</body>

<script type="text/javascript">
	function toPic2(){
		console.log(window.btoa('china is so nb')); // 编码
		
		console.log(window.atob("Y2hpbmEgaXMgc28gbmI=")); // 解码
		
		
	}
	
	/* window.οnlοad=toPic; */
	
 	function toPic(imgStr){
		
 		/* var msgTip = "${imgStr['img']}"; */
 		console.log(imgStr);
 		var bytes = new Uint8Array(imgStr);
        var blob = new Blob([bytes], { type: "image/png" });
        var url = URL.createObjectURL(blob);
        document.getElementById('image').src = url;
        console.log("received some data");
 	}
</script>
</html>