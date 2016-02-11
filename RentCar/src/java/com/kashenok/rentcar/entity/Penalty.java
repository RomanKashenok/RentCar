
package com.kashenok.rentcar.entity;


/**
 *Class Penalty.
 */
public class Penalty extends Entity{

    private static final long serialVersionUID = -2249640774527231444L;

    private long penaltyId;
    private long userId;
    private long orderId;
    private long carId;
    private double sum;
    private String message;
    private boolean isPayed;

    public Penalty() {
    }

    /**
     *
     * @param userId
     * @param orderId
     * @param carId
     * @param sum
     * @param message
     * @param isPayed
     */
    public Penalty(long userId, long orderId, long carId, double sum, String message, boolean isPayed) {
        this.userId = userId;
        this.orderId = orderId;
        this.carId = carId;
        this.sum = sum;
        this.message = message;
        this.isPayed = isPayed;
    }

    public long getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(long penaltyId) {
        this.penaltyId = penaltyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public boolean isIsPayed() {
        return isPayed;
    }

    public void setIsPayed(boolean isPayed) {
        this.isPayed = isPayed;
    }

    @Override
    public String toString() {
        return "Penalty{" + "penaltyId=" + penaltyId + ", userId=" + userId + ", orderId=" + orderId + ", carId=" + carId + ", sum=" + sum + ", mesage=" + message + ", isPayed=" + isPayed + '}';

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
        Penalty other = (Penalty) obj;
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (userId != other.userId) {
            return false;
        }
        if (orderId != other.orderId) {
            return false;
        }
        if (isPayed != other.isPayed) {
            return false;
        }
        if (sum != other.sum) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int a = 17;
        int result = super.hashCode();
        result = result * a + message.hashCode();

        return result;
    }

}
