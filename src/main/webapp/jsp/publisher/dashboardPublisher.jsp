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
				Welcome ${loggedInUser.contactName}
				<div class="buttons">
					<button type="button" id="downloadDashboard">Download dashboard</button>
					<button type="button" id="signOut">Sign out</button>
				</div>
				
				
				<div class="filters">
					<div>Filters</div>
					<div>
						Country:
						<select id="countryFilter">
						</select>
						
						Domain:
						<select id="domainFilter">
						</select>
						
						<button type="button" id="runFilter">Run Filter</button>
					</div>
				</div>
				
			</div>
			
			
			
		</header>

		<iframe id="kibanaDashboard" onload="manager.publisher.resizeIframe($(this))" style="border:none;" width="100%" src="/publisher-manager/private/app/kibana/${user.token}#/dashboard/${loggedInUser.encodedName}-Dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now-24h,interval:'1d',mode:quick,timezone:Asia%2FJerusalem,to:now))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:7,id:${loggedInUser.encodedName}-Click-Thru-Rate-On-Our-Icon,panelIndex:4,row:6,size_x:6,size_y:3,type:visualization),(col:7,id:${loggedInUser.encodedName}-Click-Thru-Rate-On-Items,panelIndex:5,row:9,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInUser.encodedName}-Top-20-Trending-Clicked-Images,panelIndex:6,row:12,size_x:6,size_y:4,type:visualization),(col:7,id:${loggedInUser.encodedName}-Unique-Users,panelIndex:7,row:12,size_x:3,size_y:2,type:visualization),(col:10,id:${loggedInUser.encodedName}-Average-Time-on-Site,panelIndex:9,row:12,size_x:3,size_y:2,type:visualization),(col:7,id:${loggedInUser.encodedName}-Revenue,panelIndex:10,row:14,size_x:3,size_y:2,type:visualization),(col:1,id:${loggedInUser.encodedName}-Events-Breakdown,panelIndex:12,row:1,size_x:12,size_y:5,type:visualization),(col:1,id:${loggedInUser.encodedName}-World-Map,panelIndex:13,row:16,size_x:6,size_y:4,type:visualization),(col:1,id:${loggedInUser.encodedName}-Devices,panelIndex:14,row:9,size_x:6,size_y:3,type:visualization),(col:1,id:${loggedInUser.encodedName}-Event-Totals,panelIndex:15,row:6,size_x:6,size_y:3,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'Dashboard',uiState:(P-2:(spy:(mode:(fill:!f,name:!n)))))&token=${user.token}"></iframe>
				
		
	</body>
</html>