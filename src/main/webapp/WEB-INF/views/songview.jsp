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
        <div align="center" >

            <ul class="list-group">
                <li class="list-group-item active">
                    <h3> ${fn:escapeXml(map.song.getName())}</h3>
                </li>
                <li class="list-group-item">
                    Name: ${fn:escapeXml(map.song.getName())}
                </li>
                <li class="list-group-item">
                    Band: ${fn:escapeXml(map.song.getBand())}
                </li>
                <li class="list-group-item">
                    Country: ${fn:escapeXml(map.song.getReleaseCountry())}
                </li>
                <li class="list-group-item">
                    Date: ${fn:escapeXml(map.song.getReleaseDate())}
                </li>
                <li class="list-group-item">
                    Album: ${fn:escapeXml(map.song.getAlbum())}
                </li>



            </ul>

            <a class="btn btn-primary" href="/songCollection/${fn:escapeXml(map.idCollection)}">View your songs</a>
        </div>
    </body>
</html>