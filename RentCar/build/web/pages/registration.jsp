<%@ include file="general/generalparam.jsp" %>
<%@ page pageEncoding="UTF-8" %>

<html>
    <head>
        <title><fmt:message key="title.registration"/></title>
        <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    </head>
    <body>
        <style>
            <%@include file="../css/logformstyle.css" %>
        </style>
        <%@include file="../WEB-INF/jspf/language.jspf" %>
        <form id="loginForm" action="controller" method="post">
            <input type="hidden" name="command" value="registration" />
            <div class="field">
                <label><fmt:message key="reg.user.login" /></label>
                <div class="input"><input type="text" name="login" value="${login}" pattern="[A-Za-z0-9@_\.]{3,15}$" /></div>
                </br>
                <label><fmt:message key="reg.user.password" /></label>
                <div class="input"><input type="password" name="password" value="" pattern="[A-Za-z0-9]{3,15}$" /></div>
                <br/>
                <label><fmt:message key="reg.user.email" /></label>
                <div class="input"><input type="text" name="email" value="${email}" pattern="^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,6}$" /></div>
                <br/>
                <label><fmt:message key="reg.user.firstName" /></label>
                <div class="input"><input type="text" name="firstName" value="${firstName}" pattern="^[а-яА-ЯёЁa-zA-Z]{1,50}$" /></div>
                <br/>
                <label><fmt:message key="reg.user.lastName" /></label>
                <div class="input"><input type="text" name="lastName" value="${lastName}" pattern="^[а-яА-ЯёЁa-zA-Z]{1,50}$" /></div>
                <br/>
                <label><fmt:message key="reg.user.passportNumber" /></label>
                <div class="input"><input type="text" name="passportNumber" value="${passportNumber}" pattern="[0-9A-Za-z]{9}$"/></div>
                <br/>
            </div>
            <b>
                <c:if test="${not empty loginAlreadyExists}">
                    <fmt:message key="option.loginAlreadyExists"/>
                </c:if>
                <c:if test="${not empty emailAlreadyExists}">
                    <fmt:message key="option.emailAlreadyExists"/>
                </c:if>
                <c:if test="${not empty passportAlreadyExists}">
                    <fmt:message key="option.passportAlreadyExists"/>
                </c:if>
                <c:if test="${not empty errorRegistration}">
                    <fmt:message key="registration.error"/>
                </c:if>
            </b>
            <div class="submit">
                <button type="submit"><fmt:message key="option.registration" /></button>

            </div>



        </form>
        <table align="center">


        </table>

    </body>
</html>
