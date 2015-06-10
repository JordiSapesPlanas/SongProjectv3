<%--
  Created by IntelliJ IDEA.
  User: debian-jordi
  Date: 13/05/15
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Song Collection</title>
    <%@include file="header.jsp" %>

</head>
<body>
<%@include file="bar.jsp" %>

    <c:if test="${not empty SongCollection}">
        <h1>${fn:escapeXml(SongCollection.getName())} list</h1>



    </c:if>
    <c:if test="${empty SongCollection}">
        <p>Song collection not exists</p>
    </c:if>

    <jsp:include page="/songCollection/${fn:escapeXml(SongCollection.getId())}/songs"></jsp:include>


</body>
</html>
