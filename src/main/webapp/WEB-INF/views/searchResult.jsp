<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: davidkaste
  Date: 23/02/15
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<br>
<br>
<br>
<div align="center">
    <a type="button" class="btn btn-default" href="/songCollection/${map.idCollection}"> View Songs</a>
    <a type="button" class="btn btn-primary" href="/songCollection/${map.idCollection}/songs/form"> Try Again</a>

</div>

<c:if test="${not empty map.searchList}">

        <table class="table table-striped">
            <th>
                Band Name
            </th>
            <th>
                Song name
            </th>
            <th>
                Date
            </th>
            <th>
                Country
            </th>
            <th>

            </th>
            <c:forEach var="song" items="${map.searchList}">


            <form method="POST" action="/songCollection/${map.idCollection}/songs">
                        <tr>
                            <td>${song.getBand()}<input type="hidden" name="band" value="${song.getBand()}" />
                            <td>${song.getAlbum()}<input type="hidden" name="album" value="${song.getAlbum()}" />
                            <td>${song.getReleaseDate()}<input type="hidden" name="releaseDate" value="${song.getReleaseDate()}" />
                            <td>${song.getReleaseCountry()}<input type="hidden" name="releaseCountry" value="${song.getReleaseCountry()}" />


                            <input type="hidden" name="name" value="${map.song}"/>
                            <td><input type="submit" class="btn btn-success" value="add" /></td>
                        </tr>

            </form>

        </c:forEach>
        </table>

</c:if>
<div align="center">
<a type="button" class="btn btn-default" href="/songCollection/${map.idCollection}"> View Songs</a>
<a type="button" class="btn btn-primary" href="/songCollection/${map.idCollection}/songs/form"> Try Again</a></body>
</div>
</div>

