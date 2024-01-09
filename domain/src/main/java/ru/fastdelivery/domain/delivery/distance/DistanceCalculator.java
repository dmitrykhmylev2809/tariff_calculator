package ru.fastdelivery.domain.delivery.distance;

import ru.fastdelivery.domain.common.coordinates.DeparturePoint;
import ru.fastdelivery.domain.common.coordinates.DestinationPoint;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record DistanceCalculator(DeparturePoint departure, DestinationPoint destination) {

    private static final BigDecimal RAD = BigDecimal.valueOf(6372795);
    private static final BigDecimal PI = BigDecimal.valueOf(Math.PI);

    private static final BigDecimal VALUE180 = BigDecimal.valueOf(180);

    public double calculateDistance() {


        BigDecimal lat1 = departure.latitude().multiply(PI).divide(VALUE180, 10, RoundingMode.HALF_UP);
        BigDecimal lat2 = destination.latitude().multiply(PI).divide(VALUE180, 10, RoundingMode.HALF_UP);
        BigDecimal long1 = departure.longitude().multiply(PI).divide(VALUE180, 10, RoundingMode.HALF_UP);
        BigDecimal long2 = destination.longitude().multiply(PI).divide(VALUE180, 10, RoundingMode.HALF_UP);

        BigDecimal cl1 = BigDecimal.valueOf(Math.cos(lat1.doubleValue()));
        BigDecimal cl2 = BigDecimal.valueOf(Math.cos(lat2.doubleValue()));
        BigDecimal sl1 = BigDecimal.valueOf(Math.sin(lat1.doubleValue()));
        BigDecimal sl2 = BigDecimal.valueOf(Math.sin(lat2.doubleValue()));
        BigDecimal delta = long2.subtract(long1);
        BigDecimal cdelta = BigDecimal.valueOf(Math.cos(delta.doubleValue()));
        BigDecimal sdelta = BigDecimal.valueOf(Math.sin(delta.doubleValue()));

        BigDecimal y = BigDecimal.valueOf(Math.sqrt(Math.abs(Math.pow(cl2.multiply(sdelta).doubleValue(), 2) +
                Math.pow(cl1.multiply(sl2).subtract(sl1.multiply(cl2).multiply(cdelta)).doubleValue(), 2))));
        BigDecimal x = sl1.multiply(sl2).add(cl1.multiply(cl2).multiply(cdelta));
        BigDecimal ad = BigDecimal.valueOf(Math.atan2(y.doubleValue(), x.doubleValue()));
        BigDecimal dist = ad.multiply(RAD);

        return dist.doubleValue();
    }

}

