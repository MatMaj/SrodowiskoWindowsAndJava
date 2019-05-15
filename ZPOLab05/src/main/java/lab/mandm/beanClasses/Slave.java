package lab.mandm.beanClasses;

import java.io.Serializable;
import java.time.LocalDate;

public class Slave implements Serializable {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private boolean isAdult;
    private double price;
    private Sex sex = Sex.UNDEFINED;

    private enum Sex {
        UNDEFINED ("UNDEFINED"),MAN ("Man"), WOMAN ("Woman");

        private final String name;

        Sex(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

}
