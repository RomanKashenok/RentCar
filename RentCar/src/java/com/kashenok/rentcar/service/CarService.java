package com.kashenok.rentcar.service;

import com.kashenok.rentcar.database.dao.CarDAO;
import static com.kashenok.rentcar.database.dao.CarDAO.*;
import com.kashenok.rentcar.exception.DAOException;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.entity.CarStatus;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.validator.Validator;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class CarService performs work over CarDAO.
 */
public class CarService {

    private CarDAO carDAO;

    public CarService() {
        this.carDAO = new CarDAO();
    }

    /**
     * The method fingAvailibleCars searches available Cars
     *
     * @param status is CarStatus object
     * @return the list of cars
     * @throws ServiceException
     */
    public List<Car> fingAvailibleCars(CarStatus status) throws ServiceException {
        List<Car> carList = new ArrayList<>();
        try {
            carList = carDAO.findCarsByStatus(status);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform CarService findAvailibleCars method ", ex);
        }
        return carList;
    }

    /**
     * The method fingCarById searches available Car
     *
     * @param carId is Car id
     * @return the Car object
     * @throws ServiceException
     */
    public Car fingCarById(long carId) throws ServiceException {
        Car car = null;
        try {
            car = carDAO.findEntityById(carId);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform CarService findCarById method ", ex);
        }
        return car;
    }

    /**
     * The method makeCarBroken set Car status
     *
     * @param carId is Car id
     * @param status is CarStatus object
     * @return boolean value
     * @throws ServiceException
     */
    public boolean makeCarBroken(long carId, CarStatus status) throws ServiceException {
        boolean isBroked = false;
        try {
            isBroked = carDAO.changeCarStatusById(carId, status);
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform CarService makeCarBroken method ", ex);
        }
        return isBroked;
    }

    /**
     * The method fingAllCars searches all Cars
     *
     * @return List of cars
     * @throws ServiceException
     */
    public List<Car> fingAllCars() throws ServiceException {
        List<Car> carList = new ArrayList<>();
        try {
            carList = carDAO.fingEntities();
        } catch (DAOException ex) {
            throw new ServiceException("Impossible to perform CarService fingAllCars method ", ex);
        }
        return carList;
    }

    /**
     * The method updateCar updates Car values
     *
     * @param param is parameters to replace
     * @param carId is Car id
     * @return boolean value
     * @throws ServiceException
     */
    public boolean updateCar(Map<String, String> param, long carId) throws ServiceException {
        Validator validator = new Validator();
        boolean isUpdated = false;
        Car car = new Car();
        try {
            car = createCar(param);
            boolean isCorrect = validator.carCheck(car);
            if (isCorrect) {
                isUpdated = carDAO.updateCar(car, carId);
            }
        } catch (ParseException | DAOException ex) {
            throw new ServiceException("Impossible to perform CarService createCar method ", ex);
        }
        return isUpdated;
    }

    /**
     * The method createCar creates Car object foe further usage into class
     *
     * @param param is parameters to create
     * @return Car object
     * @throws ServiceException
     * @throws ParseException
     */
    private Car createCar(Map<String, String> param) throws ServiceException, ParseException {
        Map<String, String> parameters = new HashMap<>(param);
        Car car = new Car();
        car.setManufacturer(param.get(MANUFACTURER));
        car.setModel(param.get(MODEL));
        car.setVIN(param.get(VIN));
        car.setColour(param.get(COLOUR));
        car.setTransmission(param.get(TRANSMISSION));
        car.setEngineCapacity(Integer.parseInt(param.get(ENGINE_CAPACITY)));
        car.setFuelConsumpting(Integer.parseInt(param.get(FUEL_CONSUMPTING)));
        car.setEngineType(param.get(FUEL));
        car.setPrice(Double.parseDouble(param.get(PRICE)));
        car.setcarStatus(CarStatus.valueOf(param.get(STATUS).toUpperCase()));
        car.setIssueDate(createDate(param.get(YEAR), param.get(MONTH), param.get(DAY)));

        return car;
    }

    /**
     * The method createDate creates Date object from String
     *
     * @param year is String represents year
     * @param month is String represents month
     * @param day is String represents day
     * @return Date object
     * @throws ParseException
     */
    private Date createDate(String year, String month, String day) throws ParseException {
        if (day == null || day == "") {
            return null;
        }
        String dateString = year + "-" + month + "-" + day;
        Date date = Date.valueOf(dateString);
        return date;
    }

    /**
     * The method addNewCar adds the new Car
     *
     * @param parameters is parameters to create
     * @return true is added
     * @throws ServiceException
     */
    public boolean addNewCar(Map<String, String> parameters) throws ServiceException {
        Validator validator = new Validator();
        boolean isSaved = false;
        try {
            Car car = createCar(parameters);
            boolean isCorrect = validator.carCheck(car);
            if (isCorrect) {
                isSaved = carDAO.addEntity(car);
            }
        } catch (ServiceException | ParseException | DAOException ex) {
            throw new ServiceException("Impossible to perform CarService createCar method ", ex);
        }
        return isSaved;
    }

    /**
     * The method checkVIN checks Car VIN field
     *
     * @param vin is String value of Car VIN
     * @return true is correct
     * @throws ServiceException
     */
    public boolean checkVIN(String vin) throws ServiceException {
        boolean isCorrect = false;
        try {
            isCorrect = carDAO.checkVin(vin);
        } catch (DAOException e) {
            throw new ServiceException("Impossible to perform CarService checkVIN method ", e);
        }
        return isCorrect;
    }

}
