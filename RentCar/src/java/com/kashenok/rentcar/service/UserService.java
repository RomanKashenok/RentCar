package com.kashenok.rentcar.service;

import com.kashenok.rentcar.database.dao.UserDAO;
import com.kashenok.rentcar.exception.DAOException;
import com.kashenok.rentcar.encryption.Encryptor;
import com.kashenok.rentcar.entity.User;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.validator.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * The class UserService performs work over OrderDAO.
 */
public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    /**
     * The method checkUser looks for user with appropriate login and password
     *
     * @param login is String login
     * @param password is String password
     * @return the User object
     * @throws ServiceException
     */
    public User checkUser(String login, String password) throws ServiceException {
        User user = new User();
        Encryptor encryptor = new Encryptor();
        try {
            String encryptedPassword = encryptor.encode(password);
            user = userDAO.findUserByLogAndPass(login, encryptedPassword);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform UserService checkUser method ", ex);
        }
        return user;
    }

    /**
     * The method checkLogin looks for distinct login
     *
     * @param login is String login
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean checkLogin(String login) throws ServiceException {
        boolean isCorrect = false;
        try {
            isCorrect = userDAO.checkLogin(login);
        } catch (DAOException e) {
            throw new ServiceException("Impossible to perform UserService checkLogin method ", e);
        }
        return isCorrect;
    }

    /**
     * The method checkEmail looks for distinct email
     *
     * @param email is String email
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean checkEmail(String email) throws ServiceException {
        boolean isCorrect = false;
        try {
            isCorrect = userDAO.checkEmail(email);
        } catch (DAOException e) {
            throw new ServiceException("Impossible to perform UserService checkEmail method ", e);
        }
        return isCorrect;
    }

    /**
     * The method checkPassport looks for distinct passport number
     *
     * @param passportNumber is String passport number
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean checkPassport(String passportNumber) throws ServiceException {
        boolean isCorrect = false;
        try {
            isCorrect = userDAO.checkPassport(passportNumber);
        } catch (DAOException e) {
            throw new ServiceException("Impossible to perform UserService checkPassport method ", e);
        }
        return isCorrect;
    }

    /**
     * The method addUser used to create new User from registration form
     *
     * @param login is User login
     * @param password is User password
     * @param email is User email
     * @param firstName is User first name
     * @param lastName is User last name
     * @param passportNumber is User passport number
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean addUser(String login, String password, String email, String firstName, String lastName, String passportNumber) throws ServiceException {
        Validator validator = new Validator();
        Encryptor encryptor = new Encryptor();
        boolean isCorrect = validator.userCheck(login, password, email, firstName, lastName, passportNumber);
        if (isCorrect) {
            String encryptedPassword = encryptor.encode(password);
            User user = new User(login, encryptedPassword, email, firstName, lastName, passportNumber);
            try {
                isCorrect = userDAO.addEntity(user);
            } catch (DAOException ex) {
                throw new ServiceException("Impossible to perform UserService addUser method ", ex);
            }
        }
        return isCorrect;
    }

    /**
     * The method findUserById searches distinct User
     *
     * @param id is User id
     * @return the User object
     * @throws ServiceException
     */
    public User findUserById(int id) throws ServiceException {
        User user = new User();
        try {
            user = userDAO.findEntityById(id);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform UserService findUserById method ", ex);
        }

        return user;
    }

    /**
     * The method refillBalance used to increase users balance
     *
     * @param currentBalance is User current balance
     * @param refillSum is sum to add or deduct to (from) users balance
     * @param userId is User id
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean refillBalance(double currentBalance, double refillSum, int userId) throws ServiceException {
        boolean isRefilled = false;
        try {
            isRefilled = userDAO.refillUserBalance(currentBalance, refillSum, userId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform UserService refillBalance method ", ex);
        }
        return isRefilled;
    }

    /**
     * The method findUserRefilledBalance used to look for updated users balance
     *
     * @param currentBalance is User current balance (before updating)
     * @param userId is User id
     * @return the boolean value
     * @throws ServiceException
     */
    public double findUserRefilledBalance(int userId, double currentBalance) throws ServiceException {
        double userBalance;
        try {
            userBalance = userDAO.findUserRefilledBalance(userId, currentBalance);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform UserService findUserBalance method ", ex);
        }

        return userBalance;
    }

    /**
     * The method updateBalance used to update users balance (increase or
     * decrease)
     *
     * @param userBalance is User balance
     * @param userId is User id
     * @param coast is User id
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean updateBalance(int userId, double userBalance, double coast) throws ServiceException {
        boolean isUpdated = false;
        try {
            isUpdated = userDAO.updateBalance(userId, userBalance, coast);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform UserService updateBalance method ", ex);
        }
        return isUpdated;
    }

    /**
     * The method updateUser used to update User information
     *
     * @param firstName is User first name
     * @param lastName is User last name
     * @param email is User email
     * @param userId is User id
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean updateUser(String firstName, String lastName, String email, int userId) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDAO.updateUserProfile(firstName, lastName, email, userId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform UserService updateUser method ", ex);
        }
        return isUpdated;
    }

    /**
     * The method findActiveUsers searches Users except Users with role
     * 'disabled' User id and status
     *
     * @return the list of Users
     * @throws ServiceException
     */
    public List<User> findActiveUsers() throws ServiceException {
        List<User> usersList = new ArrayList<>();
        try {
            usersList = userDAO.fingEntities();
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform UserService updateUser method ", ex);
        }
        return usersList;
    }

    /**
     * The method blockUnblockUser used to change User role 'disabled' or 'user'
     *
     * @param userId is User id
     * @param action is String value of role
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean blockUnblockUser(int userId, String action) throws ServiceException {
        boolean isDone = false;
        try {
            isDone = userDAO.blockUnblockUser(userId, action);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform UserService blockUnblockUser method ", ex);
        }

        return isDone;
    }

}
