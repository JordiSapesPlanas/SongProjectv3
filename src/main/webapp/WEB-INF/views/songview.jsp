<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<html>
    <head>
        <title>Song</title>
        <%@include file="header.jsp" %>
    </head>
    <body>
    <%@include file="bar.jsp" %>
    <br>
    <br>
        <div align="center">
            <h3> ${fn:escapeXml(map.song.getName())} is in your list now!</h3>
            <p>${fn:escapeXml(map.song.getName())} - ${fn:escapeXml(map.song.getBand())}</p>
            <a class="btn btn-primary" href="/songCollection/${fn:escapeXml(map.idCollection)}">View your songs</a>
        </div>
    </body>
</html>