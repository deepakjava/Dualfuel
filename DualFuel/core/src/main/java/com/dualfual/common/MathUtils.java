package com.dualfual.common;
import org.apache.commons.math3.util.Precision;

public class MathUtils {

    public static Double round(Double a, int scale){

        if(a == null)
            return a;

        return Precision.round(a, scale);
    }

}
