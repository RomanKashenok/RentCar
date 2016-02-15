package com.kashenok.rentcar.database.dao;

import com.kashenok.rentcar.entity.User;
import com.kashenok.rentcar.entity.UserRole;
import com.kashenok.rentcar.exception.DAOException;
import com.kashenok.rentcar.database.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The Class UserDAO. Perform actions with class User objects and work with
 * database.
 */
public class UserDAO extends AbstractDAO<User> {

    public static final String ADD_USER = "INSERT INTO user (login, password, email, first_name, last_name, passportNumber) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER_BY_ID = "SELECT id_user, login, password, email, first_name, last_name, passportNumber, balance FROM user WHERE id_user = ?";
    public static final String FIND_ALL_USERS = "SELECT user.id_user, user.login, user.password, user.email, user.first_name, user.last_name, user.passportNumber, user.balance, user_role.role FROM user INNER JOIN user_role ON user_role.id_role=user.id_role";
    public static final String FIND_USER_BY_LOG_AND_PASS = "SELECT id_user, login, password, email, first_name, last_name, passportNumber, balance FROM user WHERE login = ? AND password = ?";
    public static final String REMOVE_USER_BY_ID = "DELETE FROM user WHERE id_user = ?";

    public static final String UPDATE_USER = "UPDATE user SET login=?, password=?, email=?, first_name=?, last_name=?, passportNumber=?, id_role=? WHERE id_user = ?";
    public static final String UPDATE_USER_PROFILE = "UPDATE user SET email=?, first_name=?, last_name=? WHERE id_user = ?";
    public static final String FIND_USER_ROLE = "SELECT role FROM user_role WHERE id_role = (SELECT id_role FROM user WHERE login = ? AND password = ?)";
    public static final String FIND_LOGIN = "SELECT login FROM user WHERE login = ?";
    public static final String FIND_EMAIL = "SELECT email FROM user WHERE email = ?";
    public static final String FIND_PASSPORT = "SELECT passportNumber FROM user WHERE passportNumber = ?";
    public static final String FIND_BALANCE = "SELECT balance FROM user WHERE id_user = ?";

    public static final String UPDATE_BALANCE = "UPDATE user SET balance = ? WHERE  id_user = ?";
    public static final String BLOCK_USER = "UPDATE user SET id_role =(SELECT id_role FROM user_role WHERE role = ?) WHERE id_user=?";

    /**
     * The method addEntity. Add new User object to database.
     *
     * @param entity is the User class object
     * @return true if entity successfully added.
     *
     * @throws DAOException
     */
    @Override
    public boolean addEntity(User entity) throws DAOException {
        boolean isAdded = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getFirstName());
            preparedStatement.setString(5, entity.getLastName());
            preparedStatement.setString(6, entity.getPassportNumber());
            preparedStatement.executeUpdate();

            isAdded = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to add new user in addEntity method UserDAO class: ", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isAdded;
        }
    }

    /**
     * The method findEntityById. Look for concrete User by it's id
     *
     * @param id is the User id
     * @return User object if such data exists in database
     *
     * @throws DAOException
     */
    @Override
    public User findEntityById(long id) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        User user = new User();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
                preparedStatement.setLong(1, id);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    user = findUser(resultSet, connection);
                }
            } catch (SQLException e) {
                throw new DAOException("Exception in method UserDAO.checkLogin", e);
            } finally {
                close(preparedStatement);
                pool.returnConnection(connection);
            }
        }
        return user;
    }

    /**
     * The method deleteEntity. Remove concrete User by it's id from database.
     *
     * @param id is the User id
     * @return true if data successfully removed
     *
     * @throws DAOException
     */
    @Override
    public boolean deleteEntity(long id) throws DAOException {
        return false;
    }

    /**
     * The method fingEntities. Look for all entities
     *
     * @return List of all entities which where found
     *
     * @throws DAOException
     */
    @Override
    public List<User> fingEntities() throws DAOException {
        List<User> usersList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet userSet = null;
        UserRole role;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL_USERS);

            userSet = preparedStatement.executeQuery();
            while (userSet.next()) {
                User user = new User();
                user.setUserId(userSet.getInt(1));
                user.setLogin(userSet.getString(2));
                user.setPassword(userSet.getString(3));
                user.setEmail(userSet.getString(4));
                user.setFirstName(userSet.getString(5));
                user.setLastName(userSet.getString(6));
                user.setPassportNumber(userSet.getString(7));
                user.setBalance(userSet.getDouble(8));
                role = UserRole.valueOf((userSet.getString(9)).toUpperCase());
                user.setRole(role);
                usersList.add(user);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find cars by CarStatus in CarDao findCarsByStatus method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return usersList;
    }

    /**
     * The method findUserByLogAndPass. Look for entity with appropriate
     * parameters
     *
     * @param login is the User login
     * @param password is the User password
     * @return concrete User
     *
     * @throws DAOException
     */
    public User findUserByLogAndPass(String login, String password) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement userStatement = null;
        PreparedStatement roleStatement = null;
        User user = new User();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        UserRole role = null;
        if (connection != null) {
            try {
                roleStatement = connection.prepareStatement(FIND_USER_ROLE);
                roleStatement.setString(1, login);
                roleStatement.setString(2, password);
                resultSet = roleStatement.executeQuery();
                while (resultSet.next()) {
                    role = UserRole.valueOf(resultSet.getString(1).toUpperCase());
                }
                close(roleStatement);
                userStatement = connection.prepareStatement(FIND_USER_BY_LOG_AND_PASS);
                userStatement.setString(1, login);
                userStatement.setString(2, password);
                resultSet = userStatement.executeQuery();
                user = createUser(resultSet, role);
            } catch (SQLException e) {
                throw new DAOException("Exception in method UserDAO.findUser", e);
            } finally {
                close(userStatement);
                pool.returnConnection(connection);
            }
        }
        return user;
    }

    /**
     * The method updateBalance. Writes new data to current User field in
     * database
     *
     * @param userId is the User id
     * @param userBalance is the User balance
     * @param orderCoast is the Order coast
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean updateBalance(int userId, double userBalance, double orderCoast) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        boolean isUpdated = false;
        double sumToUpdate = userBalance - orderCoast;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_BALANCE);
            preparedStatement.setDouble(1, sumToUpdate);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method UserDAO.updateBalance", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isUpdated;

        }
    }

    /**
     * The method createUser. Create and set to User object appropriate values
     *
     * @param resultSet is the ResultSet object
     * @param role is the User role
     * @return filled User object
     *
     * @throws DAOException
     */
    private User createUser(ResultSet resultSet, UserRole role) throws SQLException {
        User user = new User();
        while (resultSet.next()) {
            user.setUserId(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setFirstName(resultSet.getString(5));
            user.setLastName(resultSet.getString(6));
            user.setPassportNumber(resultSet.getString(7));
            user.setBalance(resultSet.getDouble(8));
            user.setRole(role);
        }
        return user;
    }

    /**
     * The method checkLogin. Check User login
     *
     * @param login is the String value of login
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean checkLogin(String login) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        boolean isCorrect = false;
        try {
            preparedStatement = connection.prepareStatement(FIND_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                isCorrect = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method UserDAO.checkLogin", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isCorrect;

        }
    }

    /**
     * The method checkEmail. Check User email
     *
     * @param email is the String value of email
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean checkEmail(String email) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        boolean isCorrect = false;
        try {
            preparedStatement = connection.prepareStatement(FIND_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                isCorrect = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method UserDAO.checkEmail", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isCorrect;

        }
    }

    /**
     * The method checkPassport. Check User passport value
     *
     * @param passportNumber is the String value of passportNumber
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean checkPassport(String passportNumber) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        boolean isCorrect = false;
        try {
            preparedStatement = connection.prepareStatement(FIND_PASSPORT);
            preparedStatement.setString(1, passportNumber);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                isCorrect = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method UserDAO.checkPassport", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isCorrect;
        }

    }

    /**
     * The method refillUserBalance. Writes new data to current order field in
     * database
     *
     * @param currentBalance is the current balance value
     * @param refillSum is the value of refilling sum
     * @param userId is the User id
     * @return boolean value if operation successful
     *
     * @throws DAOException
     */
    public boolean refillUserBalance(double currentBalance, double refillSum, int userId) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        boolean isRefilled = false;
        double sumToRefill = refillSum + currentBalance;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_BALANCE);
            preparedStatement.setDouble(1, sumToRefill);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            isRefilled = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method UserDAO.checkPassport", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isRefilled;
        }
    }

    /**
     * The method findUserRefilledBalance. Looks for new value of User balance
     *
     * @param userId is the User id
     * @param currentBalance is the value of User current balance
     * @return double value if operation successful
     *
     * @throws DAOException
     */
    public double findUserRefilledBalance(int userId, double currentBalance) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        double userBalance = currentBalance;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_BALANCE);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userBalance = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method UserDAO.findUserBalance", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return userBalance;
    }

    /**
     * The method findUser. Creates new User class object from incoming data
     *
     * @param resultSet is the resultSet, which consists of User data fields
     * @param connection is the connection object
     * @return filled in User object or empty if resultSet is broken
     *
     * @throws DAOException
     */
    private User findUser(ResultSet resultSet, Connection connection) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt(1));
        user.setLogin(resultSet.getString(2));
        user.setPassword(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        user.setFirstName(resultSet.getString(5));
        user.setLastName(resultSet.getString(6));
        user.setPassportNumber(resultSet.getString(7));
        user.setBalance(resultSet.getDouble(8));

        return user;
    }

    /**
     * The method updateUserProfile. Update concrete User information.
     *
     * @param firstName is the new First name value
     * @param lastName is the new Last name value
     * @param email is the new email value
     * @param userId is the User id
     * @return true if data successfully updated
     *
     * @throws DAOException
     */
    public boolean updateUserProfile(String firstName, String lastName, String email, int userId) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        boolean isUpdated = false;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER_PROFILE);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4, userId);
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method UserDAO.checkPassport", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isUpdated;

        }
    }

    /**
     * The method blockUnblockUser. Update concrete User information.
     *
     * @param action is the value of new UserStatus
     * @param userId is the User id
     * @return true if data successfully updated
     *
     * @throws DAOException
     */
    public boolean blockUnblockUser(int userId, String action) throws DAOException {
        boolean isDone = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(BLOCK_USER);
            preparedStatement.setString(1, action);
            preparedStatement.setInt(2, userId);
            System.out.println("preparedStatement: " + preparedStatement);
            preparedStatement.executeUpdate();
            isDone = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method UserDAO.checkPassport", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isDone;
        }
    }

}
