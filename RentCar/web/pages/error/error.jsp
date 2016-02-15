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
                <div class="input">$
                    <fmt:message key="text.error" />
                </br>
                <div class="input">${pageContext.errorData.statusCode}</div>
            </div>

            <div class="submit">
                <button type="submit"><fmt:message key="nav.back" /></button>
            </div>



        </form>

    </body>
</html>
