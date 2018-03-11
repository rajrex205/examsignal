package com.springapp.util;

import java.math.BigDecimal;

public class PointsCalculator {
    private static BigDecimal HUNDRED = BigDecimal.valueOf(100);

    public static int getPointsForMarks(int totalMarks, int maxMarks){
        if(maxMarks == 0 || totalMarks == 0){
            return 0;
        } else {
            BigDecimal d = BigDecimal.valueOf(totalMarks).divide(BigDecimal.valueOf(maxMarks));
            return d.multiply(HUNDRED).intValue();
        }
    }
}
