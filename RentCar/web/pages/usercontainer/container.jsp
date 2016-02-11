<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:choose>
    <c:when test="${orderEdited!=null}">
        <%@include file="../WEB-INF/jspf/user/orderchangeconfirm.jspf" %>
    </c:when>
    <c:when test="${orderEdit!=null}">
        <%@include file="../WEB-INF/jspf/user/orderchange.jspf" %>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>


<c:if test="${acceptedOrderList!=null}">
    <%@include file="../WEB-INF/jspf/user/showacceptedorders.jspf" %>
</c:if>
<c:if test="${canceledOrderList!=null}">
    <%@include file="../WEB-INF/jspf/user/showcanceledorders.jspf" %>
</c:if>

<c:if test="${order!=null}">
    <%@include file="../WEB-INF/jspf/user/orderconfirm.jspf" %>
</c:if>
<c:if test="${welcomeText!=null}">
    <fmt:message key="text.main.page" />
</c:if>
<c:if test="${orderCreate!=null}"> 
    <%@include file="../WEB-INF/jspf/user/ordercreate.jspf" %>
</c:if>
<c:if test="${ordersList!=null}">
    <%@include file="../WEB-INF/jspf/user/acceptedorders.jspf" %>
    <%@include file="../WEB-INF/jspf/user/canceledorders.jspf" %>
    <ctg:order-table orderList="${ordersList}">&nbsp;</ctg:order-table>
</c:if>
<c:if test="${refillBalance!=null}">
    <%@include file="../WEB-INF/jspf/user/refillbalance.jspf" %>
</c:if>
<c:if test="${dateIncorrect!=null}">
    <fmt:message key="order.date.incorrect" />
</c:if>
<c:if test="${penaltyPayed!=null}">
    <fmt:message key="penalty.payed" />
</c:if>
<c:if test="${orderedDateBusy!=null}">
    <fmt:message key="ordered.date.busy" />${confirmedOrder.dateFrom}&nbsp;-&nbsp;${confirmedOrder.dateTo}

</c:if>
<c:if test="${startDateIncorrect!=null}">
    <fmt:message key="order.startDate.incorrect" />
</c:if>
<c:if test="${orderConfirmed!=null}">
    <fmt:message key="order.confirmed" />
</c:if>

<c:if test="${orderCanceled!=null}">
    <fmt:message key="order.canceled" />
</c:if>

<c:if test="${orderDelete!=null}">
    <%@include file="../WEB-INF/jspf/user/orderdeleteconfirm.jspf" %>
</c:if>

<c:if test="${orderDeleted!=null}">
    <fmt:message key="order.deleted" />

</c:if>
<c:if test="${profileChanged!=null}">
    <fmt:message key="profile.changed" />
</c:if>

<c:if test="${userChange!=null}">
    <%@include file="../WEB-INF/jspf/user/userchange.jspf" %>
</c:if>
