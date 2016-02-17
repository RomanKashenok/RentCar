package com.kashenok.rentcar.database.dao;

import com.kashenok.rentcar.exception.DAOException;
import com.kashenok.rentcar.database.pool.ConnectionPool;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.entity.CarStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The Class CarDAO. Perform actions with class Car objects and work with
 * database.
 */
public class CarDAO extends AbstractDAO<Car> {

    public static final String MANUFACTURER = "manufacturer";
    public static final String MODEL = "model";
    public static final String VIN = "vin";
    public static final String COLOUR = "colour";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String TRANSMISSION = "transmission";
    public static final String ENGINE_CAPACITY = "engineCapacity";
    public static final String FUEL_CONSUMPTING = "fuelConsumpting";
    public static final String FUEL = "fuel";
    public static final String PRICE = "price";
    public static final String STATUS = "status";
    public static final String ADD_CAR = "INSERT INTO car (manufacturer, model, vin, colour, issueDate, transmission, engine_capacity, fuelConsumpting, engineType, price, carStatus) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT id_carStatus FROM car_status WHERE carStatus=?))";
    public static final String FIND_ALL_CARS = "SELECT id_car, manufacturer, model, vin, colour, issueDate, transmission, engine_capacity, fuelConsumpting, engineType, price, carStatus FROM car";
    public static final String FIND_CAR_BY_ID = "SELECT id_car, manufacturer, model, vin, colour, issueDate, transmission, engine_capacity, fuelConsumpting, engineType, price, carStatus FROM car WHERE id_car=?";
//    public static final String FIND_CARS_BY_STATUS = "SELECT id_car, manufacturer, model, vin, colour, issueDate, transmission, engine_capacity, fuelConsumpting, engineType, price, carStatus FROM car WHERE carStatus = (SELECT id_carStatus FROM car_status WHERE carStatus=?)";
    public static final String FIND_CARS_BY_STATUS = "SELECT c.id_car, c.manufacturer, c.model, c.vin, c.colour, c.issueDate, c.transmission, c.engine_capacity, c.fuelConsumpting, c.engineType, c.price, cs.carStatus FROM car AS c INNER JOIN car_status AS cs ON c.carStatus=cs.id_carStatus WHERE cs.carStatus=?";
    public static final String FIND_CAR_STATUS = "SELECT carStatus FROM car_status WHERE id_carStatus = ?";
    public static final String CHANGE_STATUS = "UPDATE car SET carStatus=(SELECT id_carStatus FROM car_status WHERE carStatus=?) WHERE id_car=?";
    public static final String UPDATE_CAR = "UPDATE car SET manufacturer=?, model=?, vin=?, colour=?, issueDate=?, transmission=?, engine_capacity=?, fuelConsumpting=?, engineType=?, price=?, carStatus=(SELECT id_carStatus from car_status WHERE carStatus=?) WHERE id_car=?";
    public static final String FIND_VIN = "SELECT vin FROM car WHERE vin = ?";

    /**
     * The method addEntity. Add new Car object to database.
     *
     * @param entity is the Car class object
     * @return true if entity successfully added.
     *
     * @throws DAOException
     */
    @Override
    public boolean addEntity(Car entity) throws DAOException {
        boolean isSaved = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(ADD_CAR);
            preparedStatement.setString(1, entity.getManufacturer());
            preparedStatement.setString(2, entity.getModel());
            preparedStatement.setString(3, entity.getVIN());
            preparedStatement.setString(4, entity.getColour());
            preparedStatement.setDate(5, entity.getIssueDate());
            preparedStatement.setString(6, entity.getTransmission());
            preparedStatement.setInt(7, entity.getEngineCapacity());
            preparedStatement.setInt(8, entity.getFuelConsumpting());
            preparedStatement.setString(9, entity.getEngineType());
            preparedStatement.setDouble(10, entity.getPrice());
            preparedStatement.setString(11, entity.getCarStatus().toString().toLowerCase());
            preparedStatement.executeUpdate();
            isSaved = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to create new car in addEntity method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isSaved;
        }
    }

    /**
     * The method findEntityById. Look for concrete Car by it's id
     *
     * @param id is the Car id
     * @return Car object if such data exists in database
     *
     * @throws DAOException
     */
    @Override
    public Car findEntityById(long id) throws DAOException {
        Car car = null;
        PreparedStatement preparedStatement = null;
        ResultSet carSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_CAR_BY_ID);
            preparedStatement.setLong(1, id);
            carSet = preparedStatement.executeQuery();
            while (carSet.next()) {
                car = createCar(carSet, connection);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find car by carId in CarDao findEntityById method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }

        return car;
    }

    /**
     * The method deleteEntity. Remove concrete Car by it's id from database.
     *
     * @param id is the Car id
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
    public List<Car> fingEntities() throws DAOException {
        List<Car> carList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_CARS);
            while (resultSet.next()) {
                Car car = createCar(resultSet, connection);
                carList.add(car);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find cars by CarStatus in CarDao findCarsByStatus method", ex);
        } finally {
            close(statement);
            pool.returnConnection(connection);
        }

        return carList;
    }

    /**
     * The method findCarsByStatus. Look for entities with concrete status
     *
     * @param status is the Car status (available, unavailable, brocken)
     * @return List of all entities which where found
     *
     * @throws DAOException
     */
    public List<Car> findCarsByStatus(CarStatus status) throws DAOException {
        List<Car> carList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet carSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_CARS_BY_STATUS);
            preparedStatement.setString(1, status.toString().toLowerCase());
            carSet = preparedStatement.executeQuery();
            while (carSet.next()) {
                Car car = createCar(carSet, connection);
                carList.add(car);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to find cars by CarStatus in CarDao findCarsByStatus method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
        }
        return carList;
    }

    /**
     * The method createCar. Creates new Car class object from incoming data
     *
     * @param carSet is the resultSet, which consists of car data fields
     * @param connection is the connection object
     * @return filled in Car object or empty if carSet is broken
     *
     * @throws DAOException
     */
    private Car createCar(ResultSet carSet, Connection connection) throws SQLException {
        Car car = new Car();
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
        car.setcarStatus(CarStatus.valueOf(carSet.getString(12).toUpperCase()));
        return car;
    }

    /**
     * The method findCarStatus. Looking for CarStatus object
     *
     * @param availability is the integer value of id_carStatus field in
     * database
     * @param connection is the connection object
     * @return String interpretation of carStatus
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
        close(statusStatement);
        return status;
    }

    /**
     * The method changeCarStatusById. Change concrete Car status.
     *
     * @param carId is the Car id
     * @param status is the Car status
     * @return true if data successfully updated
     *
     * @throws DAOException
     */
    public boolean changeCarStatusById(long carId, CarStatus status) throws DAOException {
        boolean isConfirmed = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(CHANGE_STATUS);
            preparedStatement.setString(1, status.toString().toLowerCase());
            preparedStatement.setLong(2, carId);
            preparedStatement.executeUpdate();
            isConfirmed = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to change car status in changeCarStatusById method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isConfirmed;
        }
    }

    /**
     * The method updateCar. Update concrete Car information.
     *
     * @param car is the Car
     * @param carId is the Car id
     * @return true if data successfully updated
     *
     * @throws DAOException
     */
    public boolean updateCar(Car car, long carId) throws DAOException {
        boolean isUpdated = false;
        PreparedStatement preparedStatement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CAR);
            preparedStatement.setString(1, car.getManufacturer());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getVIN());
            preparedStatement.setString(4, car.getColour());
            preparedStatement.setDate(5, car.getIssueDate());
            preparedStatement.setString(6, car.getTransmission());
            preparedStatement.setInt(7, car.getEngineCapacity());
            preparedStatement.setInt(8, car.getFuelConsumpting());
            preparedStatement.setString(9, car.getEngineType());
            preparedStatement.setDouble(10, car.getPrice());
            preparedStatement.setString(11, car.getCarStatus().toString().toLowerCase());
            preparedStatement.setLong(12, carId);
            preparedStatement.executeUpdate();
            isUpdated = true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update car in updateCar method", ex);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isUpdated;
        }
    }

    /**
     * The method checkVin. Checks Car VIN.
     *
     * @param vin is the Car VIN
     * @return true if data successfully checked and correct
     *
     * @throws DAOException
     */
    public boolean checkVin(String vin) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        boolean isCorrect = false;
        try {
            preparedStatement = connection.prepareStatement(FIND_VIN);
            preparedStatement.setString(1, vin);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                isCorrect = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method CArDAO.checkVin", e);
        } finally {
            close(preparedStatement);
            pool.returnConnection(connection);
            return isCorrect;
        }
    }

}
