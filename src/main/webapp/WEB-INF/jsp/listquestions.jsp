<%@include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
input[type="radio"]{  margin: 0 10px 0 10px;

}

hr {
    border: 1px solid #a6a6a6;
    margin-bottom: 25px;
}
.container {
    padding: 16px;
}
.container1{
float: right;
}
.h2
{ display :inline;
}
</style>

</head>
<body>
<div class="container">
<h1><b>The list of questions</b></h1>
<hr>



   <!--<c:set var="mapElement" value="${hm}"/>-->
   
      
       <c:forEach items="${hm}" var="listElemen" >
       <h2>
              ${listElemen.key}</h2>
              <div class="container1">
              <a class="btn btn-info btn-sm" style="margin-left:40px;height: 35px;width :60px;font-size: 130%" href="${pageContext.request.contextPath}/ap/editquestion?question=${listElemen.key}&cid=${id}">Edit</a>
           
<a class="btn btn-danger btn-sm" style="margin-left:20px;height: 35px;width :70px;font-size: 130%" href="${pageContext.request.contextPath}/ap/deletequestion?question=${listElemen.key}&cid=${id}">Delete</a>
           
           </div>
           <h3>
          <c:forEach items="${listElemen.value}" var="listElement" >
           <input type="radio" name="radioname" value=" ${listElement.key}"> ${listElement.key}
           </c:forEach>
           </h3><br>
           </c:forEach>
     
  

<div style="margin-left:100px;margin-top:100px"><center>
<a class="btn btn-info btn-lg" href="${pageContext.request.contextPath}/ap/addquestions/${id}">Add</a></center>
</div>
</div>




