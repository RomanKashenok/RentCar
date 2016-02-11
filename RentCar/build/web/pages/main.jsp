<%@ include file="general/generalparam.jsp" %>

<html>

    <head>
        <title><fmt:message key="title.login" /></title>
    </head>

    <body>
        <style type="text/css">
            <%@include file="../css/style.css" %>
        </style>
        <%@include file="../WEB-INF/jspf/language.jspf" %>

        <div class="container">
            <c:choose>
                <c:when test="${role =='USER'}">
                    <%@include file="../WEB-INF/jspf/user/userheader.jspf" %>
                    <%@include file="../WEB-INF/jspf/user/usernavigation.jspf" %>                            
                </c:when>
                <c:when test="${role == 'ADMIN'}">
                    <%@include file="../WEB-INF/jspf/user/userheader.jspf" %>
                    <%@include file="../WEB-INF/jspf/admin/adminnavigation.jspf" %>
                </c:when>
                <c:when test="${role == 'DISABLED'}">
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
            <table class="conttable" cellspacing="0">
                <tr>
                    <!-- Column with general information about entered user, or uers list - for admin -->
                    <td class="column">
                        <c:choose>
                            <c:when test="${role =='USER'}">

                                <c:choose>
                                    <c:when test="${penalty!=null}">
                                        <fmt:message key="text.penalty.description" />

                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${carsList!=null}">
                                            <%@include file="../WEB-INF/jspf/user/carslist.jspf" %>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>

                            </c:when>
                            <c:when test="${role == 'ADMIN'}">
                                <%@include file="../WEB-INF/jspf/admin/userslist.jspf" %>
                            </c:when>
                            <c:when test="${role == 'DISABLED'}">

                            </c:when>
                            <c:otherwise>

                            </c:otherwise>
                        </c:choose>
                    </td>
                    <!-- Column with generated information (user orders, list of unconfirmed orders - for admin, and ctr) -->
                    <td  class="content">
                        <c:choose>
                            <c:when test="${role =='USER'}">


                                <c:choose>
                                    <c:when test="${penalty!=null}">
                                        <%@include file="../WEB-INF/jspf/user/penalty.jspf" %>
                                        <c:if test="${refillBalance!=null}">
                                            <%@include file="../WEB-INF/jspf/user/refillbalance.jspf" %>
                                        </c:if>
                                        <c:if test="${penaltyConfirmFailed!=null}">
                                            <fmt:message key="penalty.pay.failed" />
                                        </c:if>
                                    </c:when>


                                    <c:otherwise>
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
                                        <c:if test="${aboutText!=null}">
                                            <fmt:message key="text.about" />
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
                                    </c:otherwise>
                                </c:choose>
                            </c:when>


                            <c:when test="${role == 'ADMIN'}">
                                <c:if test="${unconfirmedOrders!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/unconfirmedOrders.jspf" %>
                                </c:if>
                                <c:if test="${orderCancelReason!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/annulconfirm.jspf" %>
                                </c:if>
                                <c:if test="${setPenaltyToUser!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/setpenalty.jspf" %>
                                </c:if>
                                <c:if test="${penaltyAdded!=null}">
                                    <fmt:message key="penalty.added" />
                                </c:if>
                                <c:if test="${userChangeOrdersList!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/userorderslist.jspf" %>
                                </c:if>
                                <c:if test="${orderCancellingSuccessfully!=null}">
                                    <fmt:message key="order.canceled" />
                                </c:if>
                                <c:if test="${ordersAbsent!=null}">
                                    <fmt:message key="orders.absent" />
                                </c:if>
                                <c:if test="${carUpdated!=null}">
                                    <fmt:message key="car.updated" />
                                </c:if>
                                <c:if test="${carSaved!=null}">
                                    <fmt:message key="car.saved" />
                                </c:if>
                                <c:if test="${penaltyUpdated!=null}">
                                    <fmt:message key="penalty.updated" />
                                </c:if>
                                <c:if test="${allOrdersEmpty!=null}">
                                    <fmt:message key="order.list.empty" />
                                </c:if>
                                <c:if test="${imageUploaded!=null}">
                                    <fmt:message key="image.upload.successfully" />
                                </c:if>
                                <c:if test="${imageUploadFailed!=null}">
                                    <fmt:message key="image.upload.failed" />
                                </c:if>
                                <c:if test="${allOrders!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/allorders.jspf" %>
                                </c:if>
                                <c:if test="${addNewCar!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/addnewcar.jspf" %>
                                </c:if>
                                <c:if test="${adminCarsList!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/admincarslist.jspf" %>
                                </c:if>
                                <c:if test="${adminPenList!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/penalty.jspf" %>
                                </c:if>
                                <c:if test="${adminStatusPenList!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/penaltylist.jspf" %>
                                </c:if>
                                <c:if test="${penaltyChange!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/penaltychange.jspf" %>
                                </c:if>
                                <c:if test="${carChange!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/carchange.jspf" %>
                                </c:if>
                                <c:if test="${adminStatusOrderList!=null}">
                                    <%@include file="../WEB-INF/jspf/admin/orders.jspf" %>
                                </c:if>
                            </c:when>



                            <c:when test="${role == 'DISABLED'}">
                            </c:when>
                            <c:otherwise>
                                <%@include file="../WEB-INF/jspf/sessionend.jspf" %>
                            </c:otherwise>
                        </c:choose>

                    </td>
                    <!-- General profile information for role USER, and finished orders list for ADMIN  -->
                    <td  class="column">
                        <c:choose>
                            <c:when test="${role =='USER'}">
                                <%@include file="../WEB-INF/jspf/user/userprofile.jspf" %>
                            </c:when>
                            <c:when test="${role == 'ADMIN'}">
                                <fmt:message key="admin.today.end.order" />
                                <%@include file="../WEB-INF/jspf/admin/finishedorders.jspf" %>
                            </c:when>
                            <c:when test="${role == 'DISABLED'}">

                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
            <%@include file="../WEB-INF/jspf/footer.jspf" %>
        </div>
    </body>
</html>