package com.kashenok.rentcar.database.dao;

import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.entity.User;
import com.kashenok.rentcar.exception.DAOException;
import com.kashenok.rentcar.database.pool.ConnectionPool;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.entity.CarStatus;
import com.kashenok.rentcar.entity.RentTermHolder;
import com.kashenok.rentcar.entity.UserRole;
import com.kashenok.rentcar.util.DateCreatorSQL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * The Class OrderDAO. Perform actions with class Order objects and work with
 * database.
 */
public class OrderDAO extends AbstractDAO<Order> {

    public static final String CAR_ID = "carId";
    public static final String USER_ID = "userId";
    public static final String YEAR_FROM = "yearFrom";
    public static final String MONTH_FROM = "monthFrom";
    public static final String DAY_FROM = "dayFrom";
    public static final String YEAR_TO = "yearTo";
    public static final String MONTH_TO = "monthTo";
    public static final String DAY_TO = "dayTo";
    public static final String ORDER_ID = "orderId";

    public static final String ADD_ORDER = "INSERT INTO rentcar2.order (id_car, id_user, orderDate, returnDate, coast) VALUES(?, ?, ?, ?, ?)";
    public static final String FIND_ALL_ORDERS = "SELECT id_order, id_car, id_user, orderDate, returnDate, status, coast, cancelMessage FROM rentcar2.order";
    public static final String FIND_ORDER_BY_ID = "SELECT id_order, id_car, id_user, orderDate, returnDate, status, coast, cancelMessage FROM rentcar2.order WHERE id_order=?";
    public static final String FIND_ORDERS_BY_STATUS = "SELECT id_order, id_car, id_user, orderDate, returnDate, status, coast, cancelMessage FROM rentcar2.order WHERE status=(SELECT id_status FROM order_status WHERE orderStatus = ?) ORDER BY id_order";
    public static final String FIND_ORDERS_BY_USER_ID = "SELECT id_order, id_car, id_user, orderDate, returnDate, status, coast, cancelMessage FROM rentcar2.order WHERE id_user=?  ORDER BY id_order";

    public static final String FIND_CAR_BY_ID = "SELECT id_car, manufacturer, model, vin, colour, issueDate, transmission, engine_capacity, fuelConsumpting, engineType, price, carStatus FROM car WHERE id_car=?";
    public static final String FIND_CAR_STATUS = "SELECT carStatus FROM car_status WHERE id_carStatus = ?";
    public static final String FIND_USER_BY_ID = "SELECT id_user, login, password, email, first_name, last_name, passportNumber, id_role, balance FROM user WHERE id_user = ?";
    public static final String FIND_USER_ROLE_BY_ID = "SELECT role FROM user_role WHERE id_role=?";
    public static final String FIND_ORDER_STATUS_BY_ID = "SELECT orderStatus FROM order_status WHERE id_status=?";
    public static final String FIND_ORDERS_CAR_CONFIRMED = "SELECT orderDate, returnDate FROM rentcar2.order WHERE status =(SELECT id_status FROM order_status WHERE orderStatus='confirmed') AND id_car=? ORDER BY orderDate";

    public static final String FIND_ORDERS_BY_USER_ID_AND_STATUS = "SELECT id_order, id_car, id_user, orderDate, returnDate, status, coast, cancelMessage FROM rentcar2.order WHERE id_user=? AND  status = (SELECT id_status from order_status WHERE orderStatus = ?)";
    public static final String CHANGE_STATUS_AND_COAST = "UPDATE rentcar2.order SET status=?, coast=? WHERE  id_order=?";
    public static final String REMOVE_ORDER_BY_ID = "DELETE FROM rentcar2.order WHERE id_order = ?";
    public static final String UPDATE_ORDER = "UPDATE rentcar2.order SET id_car=?, id_user=?, orderDate=?, returnDate=?, coast=? WHERE id_order=?";
    public static final String CONFIRM_OR_FINISHED_ORDER = "UPDATE rentcar2.order SET status=(SELECT id_status FROM order_status WHERE orderStatus=?) WHERE id_order=?";
    public static final String CANCEL_ORDER = "UPDATE rentcar2.order SET status=1, cancelMessage=? WHERE id_order=?";
    public static final String CHANGE_ORDER_COAST = "UPDATE rentcar2.order SET coast=? WHERE id_order=?";

    /**
     * The method addEntity. Add new Order object to database.
     *
     * @param entity is the Car class object
     * @return true if entity successfully added.
     *
     * @throws DAOException
     */
    @Override
    public boolean addEntity(Order entity) throws DAOException {
        return false;
    }

    /**
     * The method findEntityById. Look for concrete Order by it's id
     *
     * @param id is the Car id
     * @return Order object if such data exists in database
     *
     * @throws DAOException
     */
    @Override
    public Order findEntityById(long id) throws DAOException {
        Order order = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_ORDER_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = findOrder(resultSet, connection);
            }
        } catch (SQLException ex) {
            throw new DAOException("Exception in OrderDAO findEntityById method: ", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return order;
    }

    /**
     * The method deleteEntity. Remove concrete Order by it's id from database.
     *
     * @param id is the Car id
     * @return true if data successfully removed
     *
     * @throws DAOException
     */
    @Override
    public boolean deleteEntity(long id) throws DAOException {
        boolean isDeleted;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(REMOVE_ORDER_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            isDeleted = true;
        } catch (SQLException ex) {
            throw new DAOException("Exception in OrderDAO deleteEntityToArchive method: ", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }

        return isDeleted;
    }

    /**
     * The method fingEntities. Look for all entities
     *
     * @return List of all entities which where found
     *
     * @throws DAOException
     */
    @Override
    public List<Order> fingEntities() throws DAOException {
        List<Order> allOrdersList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_ORDERS);
            while (resultSet.next()) {
                Order order = findOrder(resultSet, connection);
                allOrdersList.add(order);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find orders in fingEntities method", ex);
        } finally {
            close(statement);
            pool.returnConnection(connection);
        }
        return allOrdersList;
    }

    /**
     * The method findOrdersByUserId. Look for entities of concrete user
     *
     * @param userId is the User id
     * @return List of all entities which where found
     *
     * @throws DAOException
     */
    public List<Order> findOrdersByUserId(int userId) throws DAOException {
        List<Order> userOrdersList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = findOrder(resultSet, connection);
                userOrdersList.add(order);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to find orders by user ID in 'findOrdersByUserId method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return userOrdersList;
    }

    /**
     * The method findOrdersByUserIdAndStatus. Look for entities of concrete
     * user with concrete status
     *
     * @param userId is the User id
     * @param status is the OrderStatus value
     * @return List of all entities which where found
     *
     * @throws DAOException
     */
    public List<Order> findOrdersByUserIdAndStatus(int userId, OrderStatus status) throws DAOException {
        List<Order> userOrdersList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_USER_ID_AND_STATUS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, status.toString().toLowerCase());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = findOrder(resultSet, connection);
                userOrdersList.add(order);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to find orders by user ID in 'findOrdersByUserId method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return userOrdersList;
    }

    /**
     * The private method findOrder. Set to Order object values from ResultSet
     *
     * @param resultSet is the ResultSet with values for filling Order
     * @param connection is the Connection object
     * @return filled Order object
     *
     * @throws DAOException
     */
    private Order findOrder(ResultSet resultSet, Connection connection) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt(1)); // idOrder

        Car car = createCar(resultSet.getInt(2), connection);  // idCar
        order.setCar(car);

        User user = createUser(resultSet.getInt(3), connection); // idUser
        order.setUser(user);

        order.setDateFrom(resultSet.getDate(4));
        order.setDateTo(resultSet.getDate(5));

        OrderStatus orderStatus = createOrderStatus(resultSet.getInt(6), connection);
        order.setOrderStatus(orderStatus);

        order.setCoast(resultSet.getDouble(7));
        order.setCancelMessage(resultSet.getString(8));

        return order;
    }

    /**
     * The method createCar. Set to Car object values from ResultSet
     *
     * @param carId is the Car id
     * @param connection is the Connection object
     * @return filled Car object
     *
     * @throws DAOException
     */
    private Car createCar(int carId, Connection connection) throws SQLException {
        Car car = new Car();
        PreparedStatement carStatement = connection.prepareStatement(FIND_CAR_BY_ID);
        carStatement.setInt(1, carId);
        ResultSet carSet = carStatement.executeQuery();
        while (carSet.next()) {
            car.setCarId(carSet.getInt(1));
            car.setManufacturer(carSet.getString(2));
            car.setModel(carSet.getString(3));
            car.setVIN(carSet.getString(4));
            car.setColour(carSet.getString(5));
            car.setIssueDate(carSet.getDate(6));
            car.setTransmission(carSet.getString(7));
            car.setEngineCapacity(carSet.getInt(8));
            car.setFuelConsumpting(carSet.getInt(9));
            car.setEngineType(carSet.getString(10));
            car.setPrice(carSet.getDouble(11));
            int availability = carSet.getInt(12);
            String carStatus = findCarStatus(availability, connection);
            car.setcarStatus(CarStatus.valueOf(carStatus.toUpperCase()));
        }
        return car;
    }

    /**
     * The method findCarStatus. Looking for CarStatus
     *
     * @param availability is the Car availability integer value
     * @param connection is the Connection object
     * @return String value of CarStatus
     *
     * @throws DAOException
     */
    private String findCarStatus(int availability, Connection connection) throws SQLException {
        PreparedStatement statusStatement = connection.prepareStatement(FIND_CAR_STATUS);
        statusStatement.setInt(1, availability);
        ResultSet statusSet = statusStatement.executeQuery();
        String status = "";
        while (statusSet.next()) {
            status = statusSet.getString(1);
        }
        return status;
    }

    /**
     * The method createUser. Set to User object values from ResultSet
     *
     * @param userId is the User id
     * @param connection is the Connection object
     * @return filled User object
     *
     * @throws DAOException
     */
    private User createUser(int userId, Connection connection) throws SQLException {
        User user = new User();
        PreparedStatement userStatement = connection.prepareStatement(FIND_USER_BY_ID);
        userStatement.setInt(1, userId);
        ResultSet userSet = userStatement.executeQuery();
        while (userSet.next()) {

            user.setUserId(userSet.getInt(1));
            user.setLogin(userSet.getString(2));
            user.setPassword(userSet.getString(3));
            user.setEmail(userSet.getString(4));
            user.setFirstName(userSet.getString(5));
            user.setLastName(userSet.getString(6));
            user.setPassportNumber(userSet.getString(7));
            int roleId = userSet.getInt(8);
            UserRole userRole = findUserRole(roleId, connection);
            user.setRole(userRole);
            user.setBalance(userSet.getDouble(9));

        }
        return user;
    }

    /**
     * The method findUserRole. Looking for User role
     *
     * @param roleId is the id_userRole integer representation in database
     * @param connection is the Connection object
     * @return String value of CarStatus
     *
     * @throws DAOException
     */
    private UserRole findUserRole(int roleId, Connection connection) throws SQLException {
        UserRole userRole = UserRole.USER;
        PreparedStatement roleStatement = connection.prepareStatement(FIND_USER_ROLE_BY_ID);
        roleStatement.setInt(1, roleId);
        ResultSet roleSet = roleStatement.executeQuery();
        while (roleSet.next()) {
            userRole = UserRole.valueOf(roleSet.getString(1).toUpperCase());
        }
        return userRole;
    }

    /**
     * The method createOrderStatus. Looking OrderStatus
     *
     * @param orderStatusId is the id_status integer representation in database
     * @param connection is the Connection object
     * @return String value of CarStatus
     *
     * @throws DAOException
     */
    private OrderStatus createOrderStatus(int orderStatusId, Connection connection) throws SQLException {
        OrderStatus orderStatus = OrderStatus.UNCONFIRMED;
        PreparedStatement orderStatusStatement = connection.prepareStatement(FIND_ORDER_STATUS_BY_ID);
        orderStatusStatement.setInt(1, orderStatusId);
        ResultSet statusSet = orderStatusStatement.executeQuery();
        while (statusSet.next()) {
            orderStatus = OrderStatus.valueOf(statusSet.getString(1).toUpperCase());
        }
        return orderStatus;
    }

    /**
     * The method createOrder. Create and set to Order object appropriate values
     *
     * @param param is the Map object with String Order parameters
     * @return filled Order object
     *
     * @throws DAOException
     */
    public Order createOrder(Map param) throws DAOException {
        Order order = new Order();
        Map<String, String> parameters = new HashMap<>(param);
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        int userId = Integer.parseInt(parameters.get(USER_ID));
        int carId = Integer.parseInt(parameters.get(CAR_ID));
        String yearFrom = parameters.get(YEAR_FROM);
        String yearTo = parameters.get(YEAR_TO);
        String monthFrom = parameters.get(MONTH_FROM);
        String monthTo = parameters.get(MONTH_TO);
        String dayFrom = parameters.get(DAY_FROM);
        String dayTo = parameters.get(DAY_TO);

        try {
            Car car = createCar(carId, connection);
            User user = createUser(userId, connection);
            Date dateFrom = DateCreatorSQL.createDate(yearFrom, monthFrom, dayFrom);
            Date dateTo = DateCreatorSQL.createDate(yearTo, monthTo, dayTo);
            if (dateFrom == null || dateTo == null) {
                return null;
            }

            double dayCoast = car.getPrice();
            int days = daysBetween(dateFrom, dateTo);
            if (days <= 0) {
                return null;
            }
            double rentCoast = rentCoastCalculate(days, dayCoast);
            order.setCar(car);
            order.setUser(user);
            order.setDateFrom(dateFrom);
            order.setDateTo(dateTo);
            order.setCoast(rentCoast);

        } catch (SQLException ex) {
            throw new DAOException("Impossible to create Order in OrderDAO createOrder method", ex);
        } finally {
            pool.returnConnection(connection);
        }
        return order;
    }

    private int daysBetween(Date dateFrom, Date dateTo) {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        cal1.setTime(dateFrom);
        cal2.setTime(dateTo);
        return (int) ((dateTo.getTime() - dateFrom.getTime()) / (1000 * 60 * 60 * 24));
    }

    private double rentCoastCalculate(int days, double dayCoast) {
        return days * dayCoast;
    }

    /**
     * The method confirmOrder. Changes Order status to 'confirmed'
     *
     * @param order is the Order object
     * @param userId is User id
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean confirmOrder(Order order, int userId) throws DAOException {
        boolean isAdded = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(ADD_ORDER);
            preparedStatement.setLong(1, order.getCar().getCarId());
            preparedStatement.setInt(2, userId);
            preparedStatement.setDate(3, order.getDateFrom());
            preparedStatement.setDate(4, order.getDateTo());
            preparedStatement.setDouble(5, order.getCoast());
            preparedStatement.executeUpdate();
            isAdded = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to confirm order in confirmOrder method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return isAdded;
    }

    /**
     * The method updateChangedOrder. Writes new data to current order field in
     * database
     *
     * @param order is the Order object
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean updateChangedOrder(Order order) throws DAOException {
        boolean isUpdated = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        //UPDATE rentcar2.order SET id_car=?, id_user=?, orderDate=?, returnDate=?, coast=? WHERE id_order=?
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ORDER);
            preparedStatement.setLong(1, order.getCar().getCarId());
            preparedStatement.setInt(2, order.getUser().getUserId());
            preparedStatement.setDate(3, order.getDateFrom());
            preparedStatement.setDate(4, order.getDateTo());
            preparedStatement.setDouble(5, order.getCoast());
            preparedStatement.setLong(6, order.getOrderId());
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to confirm order in confirmOrder method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return isUpdated;
    }

    /**
     * The method adminChangeOrderStatusById. Write new data into Order status
     * field in database database
     *
     * @param orderId is the Order id value
     * @param status is the OrderStatus value
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean adminChangeOrderStatusById(long orderId, OrderStatus status) throws DAOException {
        boolean isConfirmed = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(CONFIRM_OR_FINISHED_ORDER);
            preparedStatement.setString(1, status.toString().toLowerCase());
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
            isConfirmed = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to confirm order in confirmOrder method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return isConfirmed;
    }

    /**
     * The method adminCancelOrder. Changes Order status to 'canceled' and write
     * cancelMessage to appropriate field
     *
     * @param orderId is the Order id value
     * @param cancelMessage is String with cancel note
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean adminCancelOrder(long orderId, String cancelMessage) throws DAOException {
        boolean isCanceled = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(CANCEL_ORDER);
            preparedStatement.setString(1, cancelMessage);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
            isCanceled = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to confirm order in confirmOrder method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return isCanceled;
    }

    /**
     * The method findOrdersByStatus. Look for all orders with appropriate
     * status
     *
     * @param orderStatus OrderStatus value
     * @return List of all Orders which where found
     *
     * @throws DAOException
     */
    public List<Order> findOrdersByStatus(OrderStatus orderStatus) throws DAOException {
        List<Order> userOrdersList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_STATUS);
            preparedStatement.setString(1, orderStatus.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = findOrder(resultSet, connection);
                userOrdersList.add(order);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find orders by orderStatus in findOrdersByStatus method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return userOrdersList;
    }

    /**
     * The method findOrderedDatesList. Look for all RentTermHolder objects 
     *
     * @param carId Car id
     * @return List of all RentTermHolder objects which where found
     *
     * @throws DAOException
     */
    public List<RentTermHolder> findOrderedDatesList(int carId) throws DAOException {
        List<RentTermHolder> orderedDatesList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_ORDERS_CAR_CONFIRMED);
            preparedStatement.setInt(1, carId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RentTermHolder rentTermHolder = new RentTermHolder();
                rentTermHolder.setDateFrom(resultSet.getDate(1));
                rentTermHolder.setDateTo(resultSet.getDate(2));
                orderedDatesList.add(rentTermHolder);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find orders by orderStatus in getOrderedDatesList method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return orderedDatesList;
    }

}
