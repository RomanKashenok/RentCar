<%@ include file="general/generalparam.jsp" %>

    <html>
    <head>
        <title><fmt:message key="title.login" /></title>
    </head>

    <body>
        <style>
            <%@include file="../css/logformstyle.css" %>
        </style>
        <%@include file="../WEB-INF/jspf/language.jspf" %>



        <form id="loginForm" action="controller" method="post">
            <input type="hidden" name="command" value="login" />
            <div class="field">
                <label><fmt:message key="user.login"/></label>
                <div class="input"><input type="text" name="login" value="" /></div>
                </br>
                <a href="#" id="forgot"><fmt:message key="login.forgot.password" /></a>
                <label><fmt:message key="user.password"/></label>
                <div class="input"><input type="password" name="password" value="" /></div>
                <br/>
                <c:if test="${not empty errorLoginPassMessage}">
                    <fmt:message key="login.incorrect"/>
                </c:if>
                <c:if test="${not empty userBlocked}">
                    <fmt:message key="user.blocked"/>
                </c:if>
            </div>

            <div class="submit">
                <button type="submit"><fmt:message key="option.enter" /></button>
                <ctg:copyright />
            </div>
        </form>
        <form id="registerForm" method="POST" action="controller">
            <div class="register">
                <input type="hidden" name="command" value="needregistration" />
                <button type="submit"><fmt:message key="option.registration" /></button>
            </div>
        </form>
    </body>
</html>