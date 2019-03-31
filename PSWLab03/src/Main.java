public class Main {

    public static void main(String[] args)
    {
        RGBController rc = new RGBController();

        RGB r1=rc.initColor();
        RGB r2=rc.initColor();
        System.out.println(rc.toColor(r1));
        System.out.println(rc.toColor(r2));
        RGB r3=rc.mixColors(r1,r2);
        System.out.println(rc.toColor(r3));
    }
}
