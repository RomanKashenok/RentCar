package com.kashenok.rentcar.service;

import com.kashenok.rentcar.database.dao.OrderDAO;
import com.kashenok.rentcar.exception.DAOException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.entity.RentTermHolder;
import com.kashenok.rentcar.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class OrderService performs work over OrderDAO.
 */
public class OrderService {

    private OrderDAO orderDAO;

    public OrderService() {
        orderDAO = new OrderDAO();
    }

    /**
     * The method createOrder used to create new Order object
     *
     * @param param is parameters to fill the object fields
     * @return the Order object
     * @throws ServiceException
     */
    public Order createOrder(Map param) throws ServiceException {
        Order order = null;
        try {
            order = orderDAO.createOrder(param);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService createOrder method ", ex);
        }
        return order;
    }

    /**
     * The method findOrdersByUserIdAndStatus searches Orders with appropriate
     * User id and status
     *
     * @param status is OrderStatus object
     * @param userId is User id
     * @return the list of Orders
     * @throws ServiceException
     */
    public List<Order> findOrdersByUserIdAndStatus(int userId, OrderStatus status) throws ServiceException {
        List<Order> userCanceledOrdersList = new ArrayList<>();
        try {
            userCanceledOrdersList = orderDAO.findOrdersByUserIdAndStatus(userId, status);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService findAcceptedOrdersByUserId method ", ex);
        }
        return userCanceledOrdersList;
    }

    /**
     * The method findOrderById searches concrete Order with appropriate id and
     * status
     *
     * @param orderId is Order id
     * @return the concrete Order
     * @throws ServiceException
     */
    public Order findOrderById(int orderId) throws ServiceException {
        Order order = new Order();
        try {
            order = orderDAO.findEntityById(orderId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService findOrderById method ", ex);
        }
        return order;
    }

    /**
     * The method confirmOrder used to make changes or write appropriate
     * information in database fields
     *
     * @param order is Order
     * @param userId is User id
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean confirmOrder(Order order, int userId) throws ServiceException {
        boolean isConfirmed;
        try {
            isConfirmed = orderDAO.confirmOrder(order, userId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService confirmOrder method ", ex);
        }
        return isConfirmed;
    }

    /**
     * The method updateOrder updates Order values
     *
     * @param order is Order object with new values
     * @return boolean value
     * @throws ServiceException
     */
    public boolean updateOrder(Order order) throws ServiceException {
        boolean isUpdated = false;
        try {
            isUpdated = orderDAO.updateChangedOrder(order);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService updateOrder method ", ex);
        }
        return isUpdated;
    }

    /**
     * The method deleteOrderById used to remove Order data from database
     *
     * @param orderId is Order id
     * @return boolean value
     * @throws ServiceException
     */
    public boolean deleteOrderById(long orderId) throws ServiceException {
        boolean isDeleted = false;
        try {
            isDeleted = orderDAO.deleteEntity(orderId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService deleteOrderById method ", ex);
        }

        return isDeleted;
    }

    /**
     * The method findAllUserOrders searches all Orders of current user
     *
     * @param userChangeId is User id
     * @return List of Orders
     * @throws ServiceException
     */
    public List<Order> findAllUserOrders(int userChangeId) throws ServiceException {
        List<Order> userOrderList = new ArrayList<>();
        try {
            userOrderList = orderDAO.findOrdersByUserId(userChangeId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService findAllOrdersByUserId method ", ex);
        }
        return userOrderList;
    }

    /**
     * The method changeOrderStatusById used to chande current order status
     * field
     *
     * @param orderId is Order id
     * @param status is OrderStatus object
     * @return boolean value
     * @throws ServiceException
     */
    public boolean changeOrderStatusById(int orderId, OrderStatus status) throws ServiceException {
        boolean isConfirmed = false;
        try {
            isConfirmed = orderDAO.adminChangeOrderStatusById(orderId, status);

        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService confirmUsersOrder method ", ex);
        }
        return isConfirmed;
    }

    /**
     * The method cancelUsersOrder used to change order status to 'canceled' and
     * set message to appropriate field field
     *
     * @param orderId is Order id
     * @param cancelMessage is String with canceling information
     * @return boolean value
     * @throws ServiceException
     */
    public boolean cancelUsersOrder(int orderId, String cancelMessage) throws ServiceException {
        boolean isCanceled = false;
        try {
            isCanceled = orderDAO.adminCancelOrder(orderId, cancelMessage);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService confirmUsersOrder method ", ex);
        }
        return isCanceled;
    }

    /**
     * The method findOrdersByStatus searches all Orders with appropriate status
     *
     * @param orderStatus is Order status
     * @return List of Orders
     * @throws ServiceException
     */
    public List<Order> findOrdersByStatus(OrderStatus orderStatus) throws ServiceException {
        List<Order> ordersListByStatus = new ArrayList<Order>();
        try {
            ordersListByStatus = orderDAO.findOrdersByStatus(orderStatus);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService findOrdersByStatus method ", ex);
        }
        return ordersListByStatus;
    }

    /**
     * The method findCarOrderedDates searches all time periods in which car
     * from current order is busy (already ordered and order have 'confirmed'
     * status)
     *
     * @param carId is Car id
     * @return List of RentTermHolder objects
     * @throws ServiceException
     */
    public List<RentTermHolder> findCarOrderedDates(int carId) throws ServiceException {
        List<RentTermHolder> orderedDatesList = new ArrayList<>();
        try {
            orderedDatesList = orderDAO.findOrderedDatesList(carId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService findCarOrderedDates method ", ex);
        }
        return orderedDatesList;
    }

    /**
     * The method findAllOrder searches all available Orders
     *
     * @return List of Order objects
     * @throws ServiceException
     */
    public List<Order> findAllOrder() throws ServiceException {
        List<Order> allOrdersList = new ArrayList<>();
        try {
            allOrdersList = orderDAO.fingEntities();
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform OrderService findCarOrderedDates method ", ex);
        }
        return allOrdersList;
    }

}
