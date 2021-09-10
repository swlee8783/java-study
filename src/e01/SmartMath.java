package e01;

public class SmartMath {
    public static double round(double d, int n) {

        return ((double) Math.round(d * Math.pow(10, n))) / Math.pow(10, n);
    }

    public static double ceil(double d, int n) {
        return ((double) Math.ceil(d * Math.pow(10, n))) / Math.pow(10, n);
    }

    public static double floor(double d, int n) {
        return ((double) Math.floor(d * Math.pow(10, n))) / Math.pow(10, n);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
