package com.kashenok.rentcar.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 *THe class RentTermHolder. Holds rent period of current order.
 */
public class RentTermHolder implements Serializable {

    private static final long serialVersionUID = -3846544353446017315L;

    private Date dateFrom;
    private Date dateTo;

    public RentTermHolder() {
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

    @Override
    public String toString() {
        return "Dates: {" + "DateFrom=" + dateFrom + ", DateTo=" + dateTo + '}';
    }

}
