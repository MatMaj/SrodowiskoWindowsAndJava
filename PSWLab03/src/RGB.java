public class RGB {
    private double R_value;
    private double G_value;
    private double B_value;

    public RGB() {
    }

    public RGB(double r_value, double g_value, double b_value) {
        R_value = r_value;
        G_value = g_value;
        B_value = b_value;
    }

    public double getR_value() {
        return R_value;
    }

    public void setR_value(double r_value) {
        R_value = r_value;
    }

    public double getG_value() {
        return G_value;
    }

    public void setG_value(double g_value) {
        G_value = g_value;
    }

    public double getB_value() {
        return B_value;
    }

    public void setB_value(double b_value) {
        B_value = b_value;
    }

    @Override
    public String toString(){
        return R_value + "," + G_value + "," + B_value;
    }

}
