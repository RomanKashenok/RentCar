package com.kashenok.rentcar.database.dao;

import com.kashenok.rentcar.database.pool.ConnectionPool;
import com.kashenok.rentcar.entity.CarStatus;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.entity.Penalty;
import com.kashenok.rentcar.exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The Class PenaltyDAO. Perform actions with class Penalty objects and work
 * with database.
 */
public class PenaltyDAO extends AbstractDAO<Penalty> {

    public static final String FIND_ALL_PENALTY = "SELECT id_penalty, id_user, id_order, id_car, sum, message, isPayed FROM penalty";
    public static final String ADD_PENALTY = "INSERT INTO penalty (id_user, id_order, id_car, sum, message) VALUES (?, ?, ?, ?, ?)";
    public static final String CHANGE_PENALTY_STATUS = "UPDATE penalty SET isPayed =? WHERE id_penalty=?";
    public static final String CHANGE_CAR_STATUS = "UPDATE car SET carStatus=(SELECT id_carStatus FROM car_status WHERE carStatus=?) WHERE id_car=?";
    public static final String CONFIRM_OR_FINISHED_ORDER = "UPDATE rentcar2.order SET status=(SELECT id_status FROM order_status WHERE orderStatus=?) WHERE id_order=?";
    public static final String FIND_UNPAID_PENALTY = "SELECT id_penalty, id_user, id_order, id_car, sum, message,  isPayed FROM penalty WHERE isPayed = ? AND id_user=?";
    public static final String FIND_PENALTY_BY_STATUS = "SELECT id_penalty, id_user, id_order, id_car, sum, message, isPayed FROM penalty WHERE isPayed = ?";
    public static final String FIND_PENALTY_BY_ID = "SELECT id_penalty, id_user, id_order, id_car, sum, message, isPayed FROM penalty WHERE id_penalty = ?";
    public static final String UPDATE_PENALTY_BY_ID = "UPDATE penalty SET sum=?, message=? WHERE id_penalty = ?";

    /**
     * The method addEntity. Add new Penalty object to database.
     *
     * @param penalty is the Penalty class object
     * @return true if entity successfully added.
     *
     * @throws DAOException
     */
    @Override
    public boolean addEntity(Penalty penalty) throws DAOException {
        boolean isAdded = false;
        PreparedStatement preparedStatementInsertPenalty = null;
        PreparedStatement preparedStatementUpdateCar = null;
        PreparedStatement preparedStatementUpdateOrder = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            connection.setAutoCommit(false);
            preparedStatementInsertPenalty = connection.prepareStatement(ADD_PENALTY);
            preparedStatementInsertPenalty.setLong(1, penalty.getUserId());
            preparedStatementInsertPenalty.setLong(2, penalty.getOrderId());
            preparedStatementInsertPenalty.setLong(3, penalty.getCarId());
            preparedStatementInsertPenalty.setDouble(4, penalty.getSum());
            preparedStatementInsertPenalty.setString(5, penalty.getMessage());
            preparedStatementInsertPenalty.executeUpdate();

            preparedStatementUpdateCar = connection.prepareStatement(CHANGE_CAR_STATUS);
            preparedStatementUpdateCar.setString(1, CarStatus.BROKEN.toString().toLowerCase());
            preparedStatementUpdateCar.setLong(2, penalty.getCarId());
            preparedStatementUpdateCar.executeUpdate();

            preparedStatementUpdateOrder = connection.prepareStatement(CONFIRM_OR_FINISHED_ORDER);
            preparedStatementUpdateOrder.setString(1, OrderStatus.FINISHED.toString().toLowerCase());
            preparedStatementUpdateOrder.setLong(2, penalty.getOrderId());
            preparedStatementUpdateOrder.executeUpdate();

            connection.commit();
            isAdded = true;

        } catch (SQLException ex) {
            throw new DAOException("Impossible to add Penalty in addEntity method", ex);
        } finally {
            close(preparedStatementInsertPenalty);
            close(preparedStatementUpdateCar);
            close(preparedStatementUpdateOrder);
            pool.returnConnection(connection);
            return isAdded;
        }
    }

    /**
     * The method findEntityById. Look for concrete Penalty by it's id
     *
     * @param id is the Penalty id
     * @return Penalty object if such data exists in database
     *
     * @throws DAOException
     */
    @Override
    public Penalty findEntityById(long id) throws DAOException {
        Penalty penalty = new Penalty();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_PENALTY_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                penalty.setPenaltyId(resultSet.getLong(1));
                penalty.setUserId(resultSet.getLong(2));
                penalty.setOrderId(resultSet.getLong(3));
                penalty.setCarId(resultSet.getLong(4));
                penalty.setSum(resultSet.getDouble(5));
                penalty.setMessage(resultSet.getString(6));
                penalty.setIsPayed(resultSet.getBoolean(7));
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find Penalty in findEntityById method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return penalty;
    }

    /**
     * The method deleteEntity. Remove concrete Penalty by it's id from
     * database.
     *
     * @param id is the Penalty id
     * @return true if data successfully removed
     *
     * @throws DAOException
     */
    @Override
    public boolean deleteEntity(long id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /**
     * The method fingEntities. Look for all entities
     *
     * @return List of all entities which where found
     *
     * @throws DAOException
     */
    @Override
    public List fingEntities() throws DAOException {
        List<Penalty> penaltyList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_PENALTY);
            while (resultSet.next()) {
                Penalty penalty = createPenalty(resultSet, connection);
                penaltyList.add(penalty);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to find Penalty in findUsersPenalty method", ex);
        }

        return penaltyList;
    }

    /**
     * The method findUsersPenalty. Look for active Penalty of concrete User
     *
     * @param userId is User id
     * @param payed is boolean value (payed or not)
     * @return Penalty entity if such exists
     *
     * @throws DAOException
     */
    public Penalty findUsersPenalty(long userId, boolean payed) throws DAOException {
        Penalty penalty = new Penalty();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_UNPAID_PENALTY);
            preparedStatement.setBoolean(1, payed);
            preparedStatement.setLong(2, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                penalty.setPenaltyId(resultSet.getLong(1));
                penalty.setUserId(resultSet.getLong(2));
                penalty.setOrderId(resultSet.getLong(3));
                penalty.setCarId(resultSet.getLong(4));
                penalty.setSum(resultSet.getDouble(5));
                penalty.setMessage(resultSet.getString(6));
                penalty.setIsPayed(resultSet.getBoolean(7));
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find Penalty in findUsersPenalty method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return penalty;
    }

    /**
     * The method changePenaltyStatus. Write new data into Penalty status field
     * in database database
     *
     * @param penalty is the Penalty object
     * @param status is the Penalty status value
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean changePenaltyStatus(Penalty penalty, boolean status) throws DAOException {
        boolean isStatusChanged = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(CHANGE_PENALTY_STATUS);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setLong(2, penalty.getPenaltyId());
            preparedStatement.executeUpdate();
            isStatusChanged = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to change Penalty status in changePenaltyStatus method", ex);
        }
        finally{
            close(preparedStatement);
            pool.returnConnection(connection);
            return isStatusChanged;
        }
    }

    /**
     * The method createPenalty. Set to Penalty object values from ResultSet
     *
     * @param resultSet is the ResultSet object
     * @param connection is the Connection object
     * @return filled Penalty object
     *
     * @throws DAOException
     */
    private Penalty createPenalty(ResultSet resultSet, Connection connection) throws SQLException {
        Penalty penalty = new Penalty();
        penalty.setPenaltyId(resultSet.getLong(1));
        penalty.setUserId(resultSet.getLong(2));
        penalty.setOrderId(resultSet.getLong(3));
        penalty.setCarId(resultSet.getLong(4));
        penalty.setSum(resultSet.getDouble(5));
        penalty.setMessage(resultSet.getString(6));
        penalty.setIsPayed(resultSet.getBoolean(7));

        return penalty;
    }

    /**
     * The method findPenaltyByStatus. Look for all entities
     *
     * @param paid is boolean value
     * @return List of all entities which where found
     *
     * @throws DAOException
     */
    public List<Penalty> findPenaltyByStatus(boolean paid) throws DAOException {
        List<Penalty> penaltyList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_PENALTY_BY_STATUS);
            preparedStatement.setBoolean(1, paid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Penalty penalty = createPenalty(resultSet, connection);
                penaltyList.add(penalty);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find Penalty in findUsersPenalty method", ex);
        }
        return penaltyList;
    }

    /**
     * The method updatePenaltyById. Write new data into Penalty in appropriate
     * fields in database
     *
     * @param penaltyId is the Penalty id
     * @param newMessage is the String value of Penalty message
     * @param newSum is the double value of new Sum field
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean updatePenaltyById(long penaltyId, String newMessage, double newSum) throws DAOException {
        boolean isUpdated = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PENALTY_BY_ID);
            preparedStatement.setDouble(1, newSum);
            preparedStatement.setString(2, newMessage);
            preparedStatement.setLong(3, penaltyId);
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to change Penalty status in updatePenaltyById method", ex);
        }finally{
            close(preparedStatement);
            pool.returnConnection(connection);
            return isUpdated;
        }
    }

}
