package com.kemasJmartAK.jmart_android.model;

import java.util.Date;

/**
 * model as a Invoice similar to backend
 * @author Kemas Rafly Omar Thoriq
 */
public class Invoice extends Serializable {
    public static enum Status {
        WAITING_CONFIRMATION, CANCELLED, ON_PROGRESS, ON_DELIVERY, COMPLAINT, FINISHED, FAILED;
    }

    public static enum Rating{
        NONE,BAD,NEUTRAL,GOOD;
    }

    public Date date;
    public int buyerId;
    public int productId;
    public int complaintId;
    public Rating rating;
}
