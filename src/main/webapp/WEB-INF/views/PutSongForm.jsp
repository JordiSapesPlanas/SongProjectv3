<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
  <title>
    update song
  </title>
    <%@include file="header.jsp" %>

</head>
<body>
<%@include file="bar.jsp" %>

<br>
<br>
<div align="center">
<h3>Modify Song</h3>
</div>
<form:form method="PUT" action="/songCollection/${map.idCollection}/songs/${map.song.getId()}" modelAttribute="song">
  <table align="center">
      <input type="hidden" name="Id" value="${map.song.getId()}">
      <tr>
            <td>Name:</td>
      </tr>
      <tr>
            <td><input type="text" name="name" value="${map.song.getName()}"/></td>
      </tr>
      <tr>
        <td>Band:</td>
      </tr>
      <tr>
            <td><input type="text" name="band" value="${map.song.getBand()}"/></td>
      </tr>
      <tr>
          <td>Release Country</td>
      </tr>

      <tr>
          <td><input type="text" name="releaseCountry" value="${map.song.getReleaseCountry()}"/></td>
      </tr>
      <tr>
          <td>Release Date</td>
      </tr>

      <tr>

          <td><input type="text" name="releaseDate" value="${map.song.getReleaseDate()}"/></td>
      </tr>
      <tr>
          <td>Album</td>
      </tr>

      <tr>
          <td><input type="text" name="album" value="${map.song.getAlbum()}"/></td>
      </tr>

  </table>
    <div style="text-align: center" >
        <td> <input type="submit" class="btn btn-primary" value="add"/></td>
        <button id="delete" type="button" class="btn btn-danger" style="margin: 2px" value="${map.song.getId()}">Delete</button>
    </div>

</form:form>


    <script>
        $("#delete").click(function(e){

            $.ajax({
                url: '/songCollection/${map.idCollection}/songs/${map.song.getId()}',
                type: 'DELETE',
                success: function(result) {
                    window.location.href ='/songCollection/${map.idCollection}'
                }
            });
        });
        $("#edit").click(function(e){
            var song= {

                name : $("#name").val(),
                band : $("#band").val() ,
                releaseCoutry : $("#releaseCountry").val() ,
                releaseDate : $("#releaseDate").val(),
                album : $("#album").val()
            };
            var data = jQuery.param(song)

            $.ajax({
                url: '/songCollection/${map.idCollection}/songs/${map.song.getId()}?'+data,
                type: 'PUT',
                contentType: 'application/x-www-form-urlencoded',
                success: function(result) {
                    console.log(result)
                    window.location.href ='/songCollection/${map.idCollection}'
                }
            });
        });

    </script>

</body>
</html>
