import java.util.Random;

public class RGBController {
    public RGB initColor(){
        Random rd = new Random();
        return new RGB(rd.nextInt(256),rd.nextInt(256),rd.nextInt(256));
    }
    public String toColor(RGB rgb){
        return "["+rgb.toString()+"]";
    }
    public RGB mixColors(RGB rgb1, RGB rgb2){
        return new RGB((rgb1.getR_value()+rgb2.getR_value())%256,((rgb1.getG_value()+rgb2.getG_value())%256),((rgb1.getB_value()+rgb2.getB_value())%256));
    }
}
