<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/common/include.jsp"%>
{
	<s:iterator value="fieldValuesMap" status="keyStatus">
		"<s:property value="key" />" : [
	  		<s:iterator value="value" status="valueStatus">
	    		"<s:property />"<s:if test="#valueStatus.last == false ">,</s:if>
	  		</s:iterator>
	  	]
	  	<s:if test="#keyStatus.last == false ">,</s:if>
	</s:iterator>
}

