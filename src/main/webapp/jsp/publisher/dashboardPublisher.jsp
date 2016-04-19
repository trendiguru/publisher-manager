<%@include file="/jsp/common/include.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Admin dashboard</title>
		<meta charset="utf-8">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/fonts.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/meyer_reset.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/main.css">
			
		<script src="/publisher-manager/js/lib/jquery-2.2.2.min.js"></script>
		<script src="/publisher-manager/js/main.js"></script>
		<script>
			manager.token = "${user.token}";
		</script>
		<script src="/publisher-manager/js/publisher.js"></script>
	</head>
	
	<body>
		<header class="header-inner">
			<div id="siteTitle">TrendiGuru - Publisher Analytics</div>
			<div class="welcome">Welcome ${loggedInPublisher.name}</div>
		</header>
	   <%-- 
	   	<iframe id="kibanaDashboard" style="border:none;" height="600" width="100%" src="http://localhost:8080/publisher-manager/private/app/kibana/${user.token}#/dashboard/${loggedInPublisher.encodedName}-dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2FM,mode:quick,to:now%2FM))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:Data-Table-for-Publisher-DigitalSpy,panelIndex:1,row:1,size_x:3,size_y:2,type:visualization),(col:4,id:Histogram-for-Publisher-DigitalSpy,panelIndex:2,row:1,size_x:3,size_y:2,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'DigitalSpy%20Dashboard',uiState:())&token=${user.token}"></iframe>
	
		<iframe id="kibanaDashboard" style="border:none;" height="1000" width="100%" src="http://localhost:8080/publisher-manager/private/app/kibana/${user.token}#/dashboard/${loggedInPublisher.encodedName}-dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fy,mode:quick,to:now%2Fy))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:${loggedInPublisher.encodedName}-events-breakdown,panelIndex:1,row:5,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInPublisher.encodedName}-world-map,panelIndex:2,row:1,size_x:12,size_y:4,type:visualization),(col:7,id:${loggedInPublisher.encodedName}-devices,panelIndex:3,row:5,size_x:6,size_y:3,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'${loggedInPublisher.encodedHTMLName}%20Dashboard',uiState:(P-2:(spy:(mode:(fill:!f,name:!n)))))&token=${user.token}"></iframe>
		--%>
		<iframe id="kibanaDashboard" style="border:none;" height="1000" width="100%" src="http://localhost:8080/publisher-manager/private/app/kibana/${user.token}#/dashboard/${loggedInPublisher.encodedName}-dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fy,mode:quick,to:now%2Fy))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:${loggedInPublisher.encodedName}-events-breakdown,panelIndex:1,row:5,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInPublisher.encodedName}-world-map,panelIndex:2,row:1,size_x:12,size_y:4,type:visualization),(col:7,id:${loggedInPublisher.encodedName}-devices,panelIndex:3,row:5,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInPublisher.encodedName}-click-thru-rate-our-icon,panelIndex:4,row:8,size_x:6,size_y:2,type:visualization),(col:7,id:${loggedInPublisher.encodedName}-click-thru-rate-item,panelIndex:5,row:8,size_x:6,size_y:2,type:visualization),(col:1,id:${loggedInPublisher.encodedName}-trending-images,panelIndex:6,row:10,size_x:6,size_y:2,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'InStyle%20Dashboard',uiState:(P-2:(spy:(mode:(fill:!f,name:!n)))))&token=${user.token}"></iframe>
	</body>
</html>