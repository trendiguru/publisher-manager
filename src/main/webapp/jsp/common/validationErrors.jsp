<%@include file="/jsp/common/include.jsp"%>
<% response.setHeader("rd-form-errors","true"); %>
{
	"fieldErrors":
	[
	<s:iterator value="%{fieldErrors}" id="varFE" status="rowFE">
		{"${varFE.key}" : "${varFE.value}"}<c:if test="${!rowFE.last}">,</c:if>	
	</s:iterator>
	],
	"actionErrors":
	[
	<s:iterator value="%{actionErrors}" id="varAE" status="rowAE">
		"<s:property/>"<c:if test="${!rowAE.last}">,</c:if>	
	</s:iterator>
	]
}