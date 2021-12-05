package com.kemasJmartAK.jmart_android.model;

public class Product extends Serializable{
    public int accountId;
    public double discount;
    public String name;
    public int weight;
    public boolean conditionUsed;
    public ProductCategory category;
    public double price;
    public byte shipmentPlans;

    public Product(int accountId, String name, int weight, boolean conditionUsed,
                   double price, double discount, ProductCategory category, byte shipmentPlans) {
        this.accountId = accountId;
        this.name = name;
        this.weight = weight;
        this.conditionUsed = conditionUsed;
        this.price = price;
        this.discount = discount;
        this.category = category;
        this.shipmentPlans = shipmentPlans;
    }

    public String toString(){
        return (String)this.name;
    }
}
