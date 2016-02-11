package com.kashenok.rentcar.service;

import com.kashenok.rentcar.database.dao.PenaltyDAO;
import com.kashenok.rentcar.entity.Penalty;
import com.kashenok.rentcar.exception.DAOException;
import com.kashenok.rentcar.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class PenaltyService performs work over OrderDAO.
 */
public class PenaltyService {

    private PenaltyDAO penaltyDAO;

    public PenaltyService() {
        penaltyDAO = new PenaltyDAO();
    }

    /**
     * The method addNewPenalty used to create new appropriate entry
     *
     * @param penalty is Penalty object
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean addNewPenalty(Penalty penalty) throws ServiceException {
        boolean isAdded = false;
        try {
            isAdded = penaltyDAO.addEntity(penalty);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform PenaltyService addNewPenalty method ", ex);
        }
        return isAdded;
    }

    /**
     * The method findUserPenalty used to find distinct Penalty of User
     *
     * @param userId is User id
     * @param isPayed is boolean status of Penalty (true or false)
     * @return the Penalty object
     * @throws ServiceException
     */
    public Penalty findUserPenalty(long userId, boolean isPayed) throws ServiceException {
        Penalty penalty = new Penalty();
        try {
            penalty = penaltyDAO.findUsersPenalty(userId, isPayed);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform PenaltyService findUserPenalty method ", ex);
        }

        return penalty;
    }

    /**
     * The method changePenaltyStatus used to change isPaid status of distinct
     * Penalty
     *
     * @param penalty is Penalty object
     * @param status is Penalty status (boolean true of false)
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean changePenaltyStatus(Penalty penalty, boolean status) throws ServiceException {
        boolean isChanged = false;
        try {
            isChanged = penaltyDAO.changePenaltyStatus(penalty, status);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform PenaltyService changePenaltyStatus method ", ex);
        }
        return isChanged;
    }

    /**
     * The method findAllPenalty searches all Penalty
     *
     * @return the list of Penalty
     * @throws ServiceException
     */
    public List<Penalty> findAllPenalty() throws ServiceException {
        List<Penalty> penaltyList = new ArrayList<Penalty>();
        try {
            penaltyList = penaltyDAO.fingEntities();
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform PenaltyService findAllPenalty method ", ex);
        }
        return penaltyList;
    }

    /**
     * The method findPenaltyByStatus searches all Penalty with appropriate
     * status
     *
     * @param payed is boolean value of payment condition (true or false)
     * @return the list of Penalty
     * @throws ServiceException
     */
    public List<Penalty> findPenaltyByStatus(boolean payed) throws ServiceException {
        List<Penalty> penaltyList = new ArrayList<>();
        try {
            penaltyList = penaltyDAO.findPenaltyByStatus(payed);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform PenaltyService findPenaltyByStatus method ", ex);
        }
        return penaltyList;
    }

    /**
     * The method findPenaltyById searches Penalty with distinct id
     *
     * @param penaltyId is Penalty id
     * @return the Penalty
     * @throws ServiceException
     */
    public Penalty findPenaltyById(long penaltyId) throws ServiceException {
        Penalty penalty = new Penalty();

        try {
            penalty = penaltyDAO.findEntityById(penaltyId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform PenaltyService findPenaltyById method ", ex);
        }
        return penalty;
    }

    /**
     * The method updatePenaltyById updates distinct Penalty
     *
     * @param penaltyId is Penalty id
     * @param newMessage is new String message 
     * @param newSum is new sum of penalty
     * @return the boolean value
     * @throws ServiceException
     */
    public boolean updatePenaltyById(long penaltyId, String newMessage, double newSum) throws ServiceException {
        boolean isAdded = false;
        try {
            isAdded = penaltyDAO.updatePenaltyById(penaltyId, newMessage, newSum);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform PenaltyService updatePenaltyById method ", ex);
        }
        return isAdded;
    }

}
