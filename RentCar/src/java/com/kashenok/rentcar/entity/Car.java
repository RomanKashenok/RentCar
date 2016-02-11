package com.kashenok.rentcar.entity;

import java.sql.Date;


/**
 *The class Car.
 */
public class Car extends Entity {

    private static final long serialVersionUID = 2473723330044133514L;
    
    private long carId;
    private String manufacturer;
    private String model;
    private String VIN;
    private String colour;
    private Date issueDate;
    private String transmission;
    private int engineCapacity;
    private int fuelConsumpting;
    private String engineType;
    private double price;
    private CarStatus carStatus;

    public Car() {
    }

    /**
     *
     * @param manufacturer
     * @param model
     * @param VIN is the unique value of every car
     * @param Colour
     * @param issueDate
     * @param transmission
     * @param engineCapacity
     * @param fuelConsumpting
     * @param engineType
     * @param price is the day rent price
     * @param carStatus is CarStatus class object
     */
    public Car(String manufacturer, String model, String VIN, String Colour, Date issueDate, String transmission, int engineCapacity, int fuelConsumpting, String engineType, Double price, CarStatus carStatus) {

        this.manufacturer = manufacturer;
        this.model = model;
        this.VIN = VIN;
        this.colour = Colour;
        this.issueDate = issueDate;
        this.transmission = transmission;
        this.engineCapacity = engineCapacity;
        this.fuelConsumpting = fuelConsumpting;
        this.engineType = engineType;
        this.price = price;
        this.carStatus = carStatus;
    }


    public long getCarId() {
        return carId;
    }

     public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String Colour) {
        this.colour = Colour;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public int getFuelConsumpting() {
        return fuelConsumpting;
    }

    public void setFuelConsumpting(int fuelConsumpting) {
        this.fuelConsumpting = fuelConsumpting;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setcarStatus(CarStatus carStstus) {
        this.carStatus = carStstus;
    }

    @Override
    public String toString() {
        return "Car{" + "carId=" + carId + ", manufacturer=" + manufacturer + ", model=" + model + ", VIN=" + VIN + ", Colour=" + colour + ", issueDate=" + issueDate + ", transmission=" + transmission + ", engineCapacity=" + engineCapacity + ", fuelConsumpting=" + fuelConsumpting + ", engineType=" + engineType + ", price=" + price + ", isAvailible=" + carStatus + '}';
    }

    @Override
    public int hashCode() {
        final int a = 17;
        int result = super.hashCode();
        result = result * a + manufacturer.hashCode();
        result = result * a + model.hashCode();
        result = result * a + VIN.hashCode();
        result = result * a + colour.hashCode();
        result = result * a + issueDate.hashCode();
        result = result * a + transmission.hashCode();
        result = result * a + engineCapacity;
        result = result * a + fuelConsumpting;
        result = result * a + engineType.hashCode();
        result = result * a + carStatus.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Car other = (Car) obj;
        if (manufacturer == null) {
            if (other.manufacturer != null) {
                return false;
            }
        } else if (!manufacturer.equals(other.manufacturer)) {
            return false;
        }
        if (model == null) {
            if (other.model != null) {
                return false;
            }
        } else if (!model.equals(other.model)) {
            return false;
        }
        if (VIN == null) {
            if (other.VIN != null) {
                return false;
            }
        } else if (!VIN.equals(other.VIN)) {
            return false;
        }
        if (colour == null) {
            if (other.colour != null) {
                return false;
            }
        } else if (!colour.equals(other.colour)) {
            return false;
        }
        if (issueDate == null) {
            if (other.issueDate != null) {
                return false;
            }
        } else if (!issueDate.equals(other.issueDate)) {
            return false;
        }
        if (transmission == null) {
            if (other.transmission != null) {
                return false;
            }
        } else if (!transmission.equals(other.transmission)) {
            return false;
        }
        if (engineCapacity != other.engineCapacity) {
            return false;
        }

        if (fuelConsumpting != other.fuelConsumpting) {
            return false;
        }
        if (engineType == null) {
            if (other.engineType != null) {
                return false;
            }
        } else if (!engineType.equals(other.engineType)) {
            return false;
        }
        if (price != other.price) {
            return false;
        }
        if (carStatus == null) {
            if (other.carStatus != null) {
                return false;
            }
        } else if (!carStatus.equals(other.carStatus)) {
            return false;
        }

        return true;

    }

}
