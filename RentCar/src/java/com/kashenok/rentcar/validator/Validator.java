package com.kashenok.rentcar.validator;

import com.kashenok.rentcar.entity.Car;
import java.util.regex.Pattern;

/**
 * The Validator class. Used to check incoming data
 */
public class Validator {

    public static final String NAME_REGEX = "^[а-яА-ЯёЁa-zA-Z]{1,50}$";
    public static final String EMAIL_REGEX = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,6}$";
    public static final String PASSPORT_REGEX = "[A-Z0-9]{9}$";
    public static final String LOG_REGEX = "[A-Za-z0-9@_\\.]{3,15}$";
    public static final String PASS_REGEX = "[A-Za-z0-9]{3,15}$";

    public static final String MANUFACTURER_REGEX = "^[A-Za-zА-Яа-я0-9 ]{3,15}$";
    public static final String VIN_REGEX = "^[A-Za-z0-9]{10,16}$";
    public static final String COLOUR_REGEX = "^[A-Za-zА-Яа-я ]{3,15}$";
    public static final String PRICE_REGEX = "^[0-9]+(\\.[0-9]+)?$";

    /**
     * The method userCheck. Check incoming User data for correctness in
     * accordance to regular expressions.
     *
     * @param login is the User login
     * @param password is the User password
     * @param email is the User email
     * @param firstName is the User first name
     * @param lastName is the User last name
     * @param passportNumber is the User passport number
     * @return boolean value
     *
     */
    public boolean userCheck(String login, String password, String email, String firstName, String lastName, String passportNumber) {
        boolean isCorrect = true;
        if (!Pattern.compile(LOG_REGEX).matcher(login).find()) {
            isCorrect = false;
            return isCorrect;
        }
        if (!Pattern.compile(PASS_REGEX).matcher(password).find()) {
            isCorrect = false;
            return isCorrect;
        }
        if (!Pattern.compile(EMAIL_REGEX).matcher(email).find()) {
            isCorrect = false;
            return isCorrect;
        }
        if (!Pattern.compile(NAME_REGEX).matcher(firstName).find()) {
            isCorrect = false;
            return isCorrect;
        }
        if (!Pattern.compile(NAME_REGEX).matcher(lastName).find()) {
            isCorrect = false;
            return isCorrect;
        }
        if (!Pattern.compile(PASSPORT_REGEX).matcher(passportNumber).find()) {
            isCorrect = false;
            return isCorrect;
        }
        return isCorrect;
    }

    /**
     * The method carCheck. Check incoming Car data for correctness in
     * accordance to regular expressions.
     *
     * @param car is the Car object
     * @return boolean value
     *
     */
    public boolean carCheck(Car car) {
        boolean isCorrect = true;
        if (!Pattern.compile(MANUFACTURER_REGEX).matcher(car.getManufacturer()).find()) {
            isCorrect = false;
            return isCorrect;
        }
        if (!Pattern.compile(MANUFACTURER_REGEX).matcher(car.getModel()).find()) {
            isCorrect = false;
            return isCorrect;
        }
        if (!Pattern.compile(VIN_REGEX).matcher(car.getVIN()).find()) {
            isCorrect = false;
            return isCorrect;
        }
        if (!Pattern.compile(COLOUR_REGEX).matcher(car.getColour()).find()) {
            isCorrect = false;
            return isCorrect;
        }

        return isCorrect;
    }

}
