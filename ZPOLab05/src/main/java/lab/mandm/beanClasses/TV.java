package lab.mandm.beanClasses;


import java.io.Serializable;
import java.time.LocalDate;

public class TV implements Serializable {
    private String name;
    private TvType tvType = TvType.UNDEFINED;
    private LocalDate localDate;
    private int numberOfDevice;
    private boolean isDeviceWorking = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TvType getTvType() {
        return tvType;
    }

    public void setTvType(TvType tvType) {
        this.tvType = tvType;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int getNumberOfDevice() {
        return numberOfDevice;
    }

    public void setNumberOfDevice(int numberOfDevice) {
        this.numberOfDevice = numberOfDevice;
    }

    public boolean isDeviceWorking() {
        return isDeviceWorking;
    }

    public void setDeviceWorking(boolean deviceWorking) {
        isDeviceWorking = deviceWorking;
    }

    private enum TvType {
        UNDEFINED ("UNDEFINED"), LCD ("LCD"), PLASMA ("Plasma"), CRT ("CRT");

        private final String name;

        TvType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public TV() {}

}
