package com.kashenok.rentcar.database.dao;

import com.kashenok.rentcar.entity.Entity;
import com.kashenok.rentcar.exception.DAOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * The Class AbstractDAO - base class for all DAO classes.
 */
public abstract class AbstractDAO<T extends Entity> {

    public abstract boolean addEntity(T entity) throws DAOException;

    public abstract T findEntityById(long id) throws DAOException;

    public abstract boolean deleteEntity(long id) throws DAOException;

    public abstract List<T> fingEntities() throws DAOException;

   
    /**
     * The method close. Closes statement
     *
     * @param statement is the statement
     *
     * @throws DAOException
     */
    public void close(Statement statement) //throws DAOException 
    {

        try {
            if (statement != null) {
                statement.close();
            } 
        } catch (SQLException ex) {
          //логи =)))    throw new DAOException("Impossible to close statement: ", ex);
        }

    }

}
