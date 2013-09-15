<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nuevo player</title>
</head>
<body>
<script type="text/javascript" src="jquery/jquery-2.0.3.min.js"></script>
<script type='text/javascript' src='flowplayer-new/flowplayer-3.2.12.min.js'></script>
<div id="mainBox">
<div id="playerBox">
<div style='padding-left: 50px;'><a style='display:block;width:520px;height:330px' id='player'> </a></div>
</div>
<%
String file = request.getParameter("file");
if(file != null){
	request.getSession(true).setAttribute("file", file);
}
%>

<script>
console.log(<%=(String)request.getAttribute("planId")%>);
var p=flowplayer('player', 'flowplayer-new/flowplayer-3.2.16.swf', {
	log: { level: 'debug', filter: 'org.flowplayer.captions.*' },
    clip: {
    	url: 'stream/sandonga.mp4',
    	autoPlay:true,
        autoBuffering:true,
        scaling:'fit',
        provider: 'lighttpd'
    },
    plugins: {
        lighttpd: {
            url: "flowplayer-new/flowplayer.pseudostreaming/flowplayer.pseudostreaming-3.2.12.swf"
        }
    }
} );
</script>
<%@ include file="progress.jsp" %>
</div>
</body>
</html>