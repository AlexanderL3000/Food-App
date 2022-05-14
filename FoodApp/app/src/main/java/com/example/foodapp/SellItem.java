package com.example.foodapp;

public class SellItem {
    public String title, price, quantity, description, seller, imageUrl;

    public SellItem() {

    }

    public SellItem(String title,String price, String quantity, String description, String seller, String imageUrl) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.seller = seller;
        this.imageUrl = imageUrl;
    }
}
