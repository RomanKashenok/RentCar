<%@ page  isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.content"/>
<html>
    <head>
        <title><fmt:message key="title.error" /></title>
    </head>
    <head><title>Error Page</title></head>
    <body>
        <style>
            <%@include file="../../css/style.css" %>
            <%@include file="../../css/logformstyle.css" %>
        </style>

        <form id="loginForm" action="controller" method="post">
            <input type="hidden" name="command" value="goback" />
            <div class="field">
                <label>Failed request from:</label>
                <div class="input">${pageContext.errorData.requestURI}</div>
                </br>
                <label>Servlet name or type:</label>
                <div class="input">${pageContext.errorData.servletName}</div>
                <br/>
                <label>Status code:</label>
                <div class="input">${pageContext.errorData.statusCode}</div>
                <br/>
                <label>Exception:</label>
                <div class="input">${pageContext.errorData.throwable}</div>
                <br/>
            </div>

            <div class="submit">
                <button type="submit"><fmt:message key="nav.back" /></button>
            </div>



        </form>

    </body>
</html>