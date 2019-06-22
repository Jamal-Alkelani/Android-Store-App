package com.example.mobile_homework;

public class Product {
    private String Id;
    private String name;
    private String description;
    private String production_date;
    private String expiration_date;
    private String photo[]=new String[2]; //1-->name 2-->photo itself

    public Product(String id, String name, String description, String producation_date, String expiration_date, String[] photo) {
        Id = id;
        this.name = name;
        this.description = description;
        this.production_date = producation_date;
        this.expiration_date = expiration_date;
        this.photo = photo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String[] getPhoto() {
        return photo;
    }

    public void setPhoto(String[] photo) {
        this.photo = photo;
    }
}
