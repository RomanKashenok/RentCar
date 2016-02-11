package com.kashenok.rentcar.entity;

import java.sql.Date;

/**
 *The class Order.
 */
public class Order extends Entity {

    private static final long serialVersionUID = 7567252289204949991L;

    private long orderId;
    private Car car;
    private User user;
    private Date dateFrom;
    private Date dateTo;
    private OrderStatus orderStatus;
    private double coast;
    private String cancelMessage;

    public Order() {
    }

    /**
     *
     * @param car is Car object
     * @param user is User object
     * @param dateFrom is date start order
     * @param dateTo is date end order
     * @param orderStatus is OrderStatus class object
     * @param coast is average rent price
     * @param cancelMessage is message, that sets if order canceled
     */
    public Order(Car car, User user, Date dateFrom, Date dateTo, OrderStatus orderStatus, double coast, String cancelMessage) {

        this.car = car;
        this.user = user;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.orderStatus = orderStatus;
        this.coast = coast;
        this.cancelMessage = cancelMessage;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getCoast() {
        return coast;
    }

    public void setCoast(double coast) {
        this.coast = coast;
    }

    public String getCancelMessage() {
        return cancelMessage;
    }

    public void setCancelMessage(String cancelMessage) {
        this.cancelMessage = cancelMessage;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", car=" + car + ", user=" + user + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", orderStatus=" + orderStatus + ", coast=" + coast + ", cancelMessage=" + cancelMessage + '}';
    }

    @Override
    public int hashCode() {
        final int a = 17;
        int result = super.hashCode();
        result = a * result + car.hashCode();
        result = a * result + dateFrom.hashCode();
        result = a * result + dateTo.hashCode();
        result = a * result + user.hashCode();
        result = a * result + orderStatus.hashCode();
        result = a * result + cancelMessage.hashCode();
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
        Order other = (Order) obj;
        if (coast != other.coast) {
            return false;
        }
        if (cancelMessage == null) {
            if (other.cancelMessage != null) {
                return false;
            }
        } else if (!cancelMessage.equals(other.cancelMessage)) {
            return false;
        }
        if (car == null) {
            if (other.car != null) {
                return false;
            }
        } else if (!car.equals(other.car)) {
            return false;
        }
        if (dateFrom == null) {
            if (other.dateFrom != null) {
                return false;
            }
        } else if (!dateFrom.equals(other.dateFrom)) {
            return false;
        }
        if (dateTo == null) {
            if (other.dateTo != null) {
                return false;
            }
        } else if (!dateTo.equals(other.dateTo)) {
            return false;
        }
        if (orderStatus != other.orderStatus) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }

        return true;
    }
}
