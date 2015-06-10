<%--
  Created by IntelliJ IDEA.
  User: debian-jordi
  Date: 4/05/15
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html ng-app="Song">
<head>
    <title>Song Collection</title>

    <%@include file="header.jsp" %>

</head>
<body>
<%@include file="bar.jsp" %>


        <c:if test="${not empty SongCollection}">
            <h1>Music list</h1>
            <section ng-controller="SongController as song">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>
                            Name
                    </th>

                    <th>
                            User mail
                    </th>

                    <th>

                    </th>

                </tr>
                </thead>


                <tbody>
                <c:forEach var="song" items="${SongCollection}">

                <tr>
                    <td > ${fn:escapeXml(song.getName())}
                    </td>
                    <td>
                            ${fn:escapeXml(song.getEmail())}
                    </td>
                    <td ng-class="{ active: song.isClicked(${fn:escapeXml(song.getId())})}">
                        <a <%--href="/songCollection/${fn:escapeXml(song.getId())}"--%> ng-click="song.selectClick(${fn:escapeXml(song.getId())})"> More </a>
                    </td>


                    <tr>

                    <td colspan="3" class="song" ng-show="song.isClicked(${fn:escapeXml(song.getId())})">
                       <jsp:include page="/songCollection/${fn:escapeXml(song.getId())}"></jsp:include>
                    </td>
                    </tr>
                </tr>
                </c:forEach>

                </tbody>

            </table>
            </section>
        </c:if>
    <br>
    <div style="text-align: center" >
        <button type="button" class="btn btn-primary" onclick="location.href='/songCollection/form'"> Create your List</button>
    </div>



</body>
</html>
