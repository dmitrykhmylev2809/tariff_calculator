package ru.fastdelivery.domain.common.volume;

import java.math.BigInteger;

public record Volume(BigInteger height, BigInteger width, BigInteger length) {
    private static final BigInteger ROUNDING_VALUE = BigInteger.valueOf(50);
    private static final BigInteger MAX_DIMENSION = BigInteger.valueOf(1500);
    private static final BigInteger MIN_DIMENSION = BigInteger.ZERO;

    public Volume(BigInteger height, BigInteger width, BigInteger length) {
        this.height = roundToNearest50(height);
        this.width = roundToNearest50(width);
        this.length = roundToNearest50(length);

        if (this.height.compareTo(MIN_DIMENSION) < 0 || this.height.compareTo(MAX_DIMENSION) > 0 ||
                this.width.compareTo(MIN_DIMENSION) < 0 || this.width.compareTo(MAX_DIMENSION) > 0 ||
                this.length.compareTo(MIN_DIMENSION) < 0 || this.length.compareTo(MAX_DIMENSION) > 0) {
            throw new IllegalArgumentException("Invalid volume dimensions");
        }
    }

    private static BigInteger roundToNearest50(BigInteger value) {
        return value.add(ROUNDING_VALUE.divide(BigInteger.valueOf(2)))
                .divide(ROUNDING_VALUE)
                .multiply(ROUNDING_VALUE);
    }


}
