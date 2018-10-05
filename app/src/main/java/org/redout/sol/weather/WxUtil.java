package org.redout.sol.weather;



public class WxUtil {
    public static final int TEMP_UNIT_KELVIN = 0;
    public static final int TEMP_UNIT_FAHRENHEIT = 1;
    public static final int TEMP_UNIT_CELCIUS = 2;

    public static Integer convTemp(Double temp, int origScale, int newScale) {
        if (origScale == TEMP_UNIT_KELVIN) {
            if (newScale == TEMP_UNIT_FAHRENHEIT) {
                return k2f(temp).intValue();
            } else if (newScale == TEMP_UNIT_CELCIUS) {
                return k2c(temp).intValue();
            } else return 0;
        } else if (origScale == TEMP_UNIT_FAHRENHEIT) {
            if (newScale == TEMP_UNIT_KELVIN) {
                return f2k(temp).intValue();
            } else if (newScale == TEMP_UNIT_CELCIUS) {
                return f2c(temp).intValue();
            } else return 0;
        } else if (origScale == TEMP_UNIT_CELCIUS) {
            if (newScale == TEMP_UNIT_FAHRENHEIT) {
                return c2f(temp).intValue();
            } else if (newScale == TEMP_UNIT_KELVIN) {
                return c2k(temp).intValue();
            } else return 0;
        } else return 0;
    }

    private static Double k2f(Double temp) {
        return (temp - 273.15) * 9/5 +32;
    }
    private static Double k2c(Double temp) {
        return temp - 273.15;
    }
    private static Double f2k(Double temp) {
        return (temp - 32) * 5/9 + 273.15;
    }
    private static Double f2c(Double temp) {
        return (temp - 32) * 5/9;
    }
    private static Double c2k(Double temp) {
        return temp + 273.15;
    }
    private static Double c2f(Double temp) {
        return (temp * 5/9) +32;
    }
}
