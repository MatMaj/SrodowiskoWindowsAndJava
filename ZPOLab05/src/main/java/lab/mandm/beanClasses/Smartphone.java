package lab.mandm.beanClasses;

import java.io.Serializable;
import java.time.LocalDate;



public class Smartphone implements Serializable {
    private String model;
    private Brand brand = Brand.UNDEFINED;
    private LocalDate createDate;
    private double price;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private enum Brand {
        UNDEFINED ("UNDEFINED"), ZTE ("ZTE"), SAMSUNG ("SAMSUNG"), MEIZU ("MEIZU"), ASUS ("ASUS"), XIAOMI ("XIAOMI");

        private final String name;

        Brand(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }


}
