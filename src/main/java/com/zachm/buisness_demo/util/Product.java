package com.zachm.buisness_demo.util;

public class Product {

    private String vendor;
    private String product;
    private int quantity;
    private int sales;
    private int backstock;
    private int order;
    private int days;

    public Product(String vendor, String product, int quantity, int sales, int backstock, int days) {
        this.vendor = vendor;
        this.product = product;
        this.quantity = quantity;
        this.sales = sales;
        this.days = days;

        //IDEALLY, you would add previous orders to backstock automatically, this is just a demo
        //You could even get that orders ETA and subtract it to days to be a bit more accurate
        //It depends on how you can set it up, at my old job it was set up in a way where it was too random
        this.backstock = backstock;

        //This is based on weekly sales
        //We can divide this by 7 to bring it to its daily sales
        //This is useful because lets say an order is an odd amount of days, we can multiply it to get our order
        double math = ((double)sales/(double)quantity)/7 * days;
        this.order = (int) Math.ceil(math - backstock);
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getBackstock() {
        return backstock;
    }

    public void setBackstock(int backstock) {
        this.backstock = backstock;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

}
