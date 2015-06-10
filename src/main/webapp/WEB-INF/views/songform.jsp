<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>

            search Song
    </title>
    <%@include file="header.jsp" %>

</head>
<body>
<%@include file="bar.jsp" %>
<br>
<br>
    <div align="center">
        <h1>Search the song by name!</h1>
    </div>
    <form:form method="GET" action="search/" modelAttribute="song">
        <table align="center">
            <section ng-controller="SongController as song">
            <tr>

                <td><form:label path="name">Name</form:label></td>
                <td><form:input path="name"/> <i><form:errors path="name"></form:errors></i></td>
                <td><input type="submit" class="btn btn-primary" value="GO"  style="margin: 4px"/></td>

            </tr>
            <tr>
                <c:if test="${song.getId()>=0}">
                <td><form:label path="band">Band</form:label></td>
                <td><form:input path="band"/> <i><form:errors path="band"></form:errors></i></td>
                </c:if>
                <c:if test="${song.getId()<0}">

                    <td><form:input path="band" value=""/></td>
                </c:if>
            </tr>
>           </section>
        </table>
        <div align="center">

        </div>
    </form:form>
    <%@include file="searchResult.jsp"%>
</body>
</html>
