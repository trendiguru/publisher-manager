<%@include file="/jsp/common/include.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Admin dashboard</title>
		<meta charset="utf-8">
		<link rel="shortcut icon" type="image/x-icon" href="/publisher-manager/favicon.ico" />
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
			<div class="welcome">
				Welcome ${loggedInUser.name}
				<div class="buttons">
					<button type="button" id="downloadDashboard">Download dashboard</button>
					<button type="button" id="signOut">Sign out</button>
				</div>
			</div>
			
		</header>
		<%--
		<iframe id="kibanaDashboard" style="border:none;" height="1000" width="100%" src="/publisher-manager/private/app/kibana/${user.token}#/dashboard/${loggedInPublisher.encodedName}-dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fw,mode:quick,to:now%2Fw))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:${loggedInPublisher.encodedName}-events-breakdown,panelIndex:1,row:5,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInPublisher.encodedName}-world-map,panelIndex:2,row:1,size_x:12,size_y:4,type:visualization),(col:7,id:${loggedInPublisher.encodedName}-devices,panelIndex:3,row:5,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInPublisher.encodedName}-click-thru-rate-our-icon,panelIndex:4,row:8,size_x:6,size_y:2,type:visualization),(col:7,id:${loggedInPublisher.encodedName}-click-thru-rate-item,panelIndex:5,row:8,size_x:6,size_y:2,type:visualization),(col:1,id:${loggedInPublisher.encodedName}-trending-images,panelIndex:6,row:10,size_x:6,size_y:2,type:visualization),(col:7,id:${loggedInPublisher.encodedName}-unique-users,panelIndex:7,row:10,size_x:4,size_y:2,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'Dashboard',uiState:(P-2:(spy:(mode:(fill:!f,name:!n)))))&token=${user.token}"></iframe>
		--%>
		
		<%-- get the share url --%>
		
		
		<iframe id="kibanaDashboard" onload="manager.publisher.resizeIframe($(this))" style="border:none;" width="100%" src="/publisher-manager/private/app/kibana/${user.token}#/dashboard/${loggedInUser.encodedName}-Dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fw,mode:quick,to:now%2Fw))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:${loggedInUser.encodedName}-Events-Breakdown,panelIndex:1,row:5,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInUser.encodedName}-World-Map,panelIndex:2,row:1,size_x:12,size_y:4,type:visualization),(col:7,id:${loggedInUser.encodedName}-Devices,panelIndex:3,row:5,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInUser.encodedName}-Click-Thru-Rate-On-Our-Icon,panelIndex:4,row:8,size_x:6,size_y:2,type:visualization),(col:7,id:${loggedInUser.encodedName}-Click-Thru-Rate-On-Items,panelIndex:5,row:8,size_x:6,size_y:2,type:visualization),(col:1,id:${loggedInUser.encodedName}-Top-20-Trending-Images,panelIndex:6,row:10,size_x:6,size_y:2,type:visualization),(col:1,id:${loggedInUser.encodedName}-Unique-Users,panelIndex:7,row:12,size_x:4,size_y:2,type:visualization),(col:7,id:${loggedInUser.encodedName}-Top-20-Trending-Categories,panelIndex:8,row:10,size_x:6,size_y:2,type:visualization),(col:5,id:${loggedInUser.encodedName}-Average-Time-on-Site,panelIndex:9,row:12,size_x:3,size_y:2,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'Dashboard',uiState:(P-1:(spy:(mode:(fill:!f,name:!n))),P-2:(spy:(mode:(fill:!f,name:!n)))))&token=${user.token}"></iframe>
		
		
	</body>
</html>