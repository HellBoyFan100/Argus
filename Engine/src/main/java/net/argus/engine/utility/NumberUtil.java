package net.argus.engine.utility;

public class NumberUtil {

    private NumberUtil() {}

    //Float Bound.
    public static float bound(float value){
        return bound(value, 1, -1);
    }

    public static float bound(float value, float max, float min){
        return Math.min(Math.max(value, min), max);
    }


    //Int Bound.
    public static double bound(double value){
        return bound(value, 1, -1);
    }

    public static double bound(double value, double max, double min){
        return Math.min(Math.max(value, max), min);
    }

}
