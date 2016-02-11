package com.kashenok.rentcar.util;

import javax.servlet.http.HttpServletRequest;

/**
 * The class RefSessionCleaner. Declares one method
 */
public class RefSessionCleaner {

    public static final String ORDER = "order";
    public static final String ORDER_EDITED = "orderEdited";
    public static final String ORDER_EDIT = "orderEdit";
    public static final String WELCOME_TEXT = "welcomeText";
    public static final String ORDER_DELETE = "orderDelete";
    public static final String ORDER_DELETED = "orderDeleted";
    public static final String ACCEPTED_ORDERS_LIST = "acceptedOrderList";
    public static final String CANCELED_ORDERS_LIST = "canceledOrderList";
    public static final String USER_CHANGE = "userChange";
    public static final String REF_PAGE = "refPage";
    public static final String ORDER_NEED_CANCEL = "orderCancelReason";
    public static final String ORDERS_LIST = "ordersList";
    public static final String CAR = "car";
    public static final String CREATE_ORDER = "orderCreate";
    public static final String ORDERED_DAYS = "orderedDatesList";
    public static final String BALANCE_DIFFERENCE_CREDIT = "balanceDifferenceCredit";
    public static final String BALANCE_DIFFERENCE_REFUSE = "balanceDifferenceRefuse";
    public static final String DATE_BUSY = "orderedDateBusy";
    public static final String CONFIRMED_ORDER = "confirmedOrder";
    public static final String SET_PENALTY = "setPenaltyToUser";
    public static final String ABOUT_TEXT = "aboutText";

    public static final String USER_CHANGE_ID = "userChangeId";
    public static final String USER_CHANGE_ORDERS_LIST = "userChangeOrdersList";
    public static final String UNCONFIRMED_ORDERS = "unconfirmedOrders";
    public static final String ADMIN_CARS_LIST = "adminCarsList";
    public static final String ADD_NEW_CAR = "addNewCar";
    public static final String ADMIN_PENALTY_LIST = "adminPenList";
    public static final String ADMIN_STATUS_PENALTY_LIST = "adminStatusPenList";
    public static final String ALL_ORDERS_LIST = "allOrders";
    public static final String ADMIN_STAUS_ORDER_LIST = "adminStatusOrderList";

    /**
     * The static method cleanAttributes. used to remove attributes from session
     *
     * @param request is HttpServletRequest
     *
     */
    public static void cleanAttributes(HttpServletRequest request) {
        request.getSession().removeAttribute(ORDER);
        request.getSession().removeAttribute(ORDER_EDITED);
        request.getSession().removeAttribute(ORDER_EDIT);
        request.getSession().removeAttribute(WELCOME_TEXT);
        request.getSession().removeAttribute(ORDER_DELETE);
        request.getSession().removeAttribute(ORDER_DELETED);
        request.getSession().removeAttribute(ACCEPTED_ORDERS_LIST);
        request.getSession().removeAttribute(CANCELED_ORDERS_LIST);
        request.getSession().removeAttribute(USER_CHANGE);
        request.getSession().removeAttribute(USER_CHANGE_ID);
        request.getSession().removeAttribute(USER_CHANGE_ORDERS_LIST);
        request.getSession().removeAttribute(UNCONFIRMED_ORDERS);
        request.getSession().removeAttribute(ORDER_NEED_CANCEL);
        request.getSession().removeAttribute(ORDERS_LIST);
        request.getSession().removeAttribute(CAR);
        request.getSession().removeAttribute(CREATE_ORDER);
        request.getSession().removeAttribute(BALANCE_DIFFERENCE_CREDIT);
        request.getSession().removeAttribute(BALANCE_DIFFERENCE_REFUSE);
        request.getSession().removeAttribute(DATE_BUSY);
        request.getSession().removeAttribute(CONFIRMED_ORDER);
        request.getSession().removeAttribute(SET_PENALTY);
        request.getSession().removeAttribute(ADMIN_CARS_LIST);
        request.getSession().removeAttribute(ADD_NEW_CAR);
        request.getSession().removeAttribute(ADMIN_PENALTY_LIST);
        request.getSession().removeAttribute(ADMIN_STATUS_PENALTY_LIST);
        request.getSession().removeAttribute(ALL_ORDERS_LIST);
        request.getSession().removeAttribute(ADMIN_STAUS_ORDER_LIST);
        request.getSession().removeAttribute(ABOUT_TEXT);
    }

}
