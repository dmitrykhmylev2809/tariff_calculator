package ru.fastdelivery.domain.common.volume;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VolumeTest {

    @Test
    void testRoundToNearest50() {
        assertThat(Volume.roundToNearest50(BigInteger.valueOf(47))).isEqualTo(BigInteger.valueOf(50));
        assertThat(Volume.roundToNearest50(BigInteger.valueOf(78))).isEqualTo(BigInteger.valueOf(100));
        assertThat(Volume.roundToNearest50(BigInteger.valueOf(1234))).isEqualTo(BigInteger.valueOf(1250));
    }

    @Test
    void testCreateVolumeWithValidDimensions() {
        BigInteger height = BigInteger.valueOf(100);
        BigInteger width = BigInteger.valueOf(200);
        BigInteger length = BigInteger.valueOf(300);

        Volume volume = new Volume(height, width, length);

        assertThat(volume.height()).isEqualTo(height);
        assertThat(volume.width()).isEqualTo(width);
        assertThat(volume.length()).isEqualTo(length);
    }

    @Test
    void testCreateVolumeWithInvalidDimensions() {
        BigInteger invalidHeight = BigInteger.valueOf(1600);
        BigInteger validWidth = BigInteger.valueOf(200);
        BigInteger validLength = BigInteger.valueOf(300);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Volume(invalidHeight, validWidth, validLength));

        assertThat(exception.getMessage()).isEqualTo("Invalid volume dimensions");
    }

    @Test
    void testCreateVolumeWithNegativeHeight() {
        BigInteger negativeHeight = BigInteger.valueOf(-50);
        BigInteger validWidth = BigInteger.valueOf(200);
        BigInteger validLength = BigInteger.valueOf(300);

        assertThatThrownBy(() -> new Volume(negativeHeight, validWidth, validLength))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void testCreateVolumeWithNegativeWidth() {
        BigInteger validHeight = BigInteger.valueOf(50);
        BigInteger negativeWidth = BigInteger.valueOf(-200);
        BigInteger validLength = BigInteger.valueOf(300);

        assertThatThrownBy(() -> new Volume(validHeight, negativeWidth, validLength))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void testCreateVolumeWithNegativeLength() {
        BigInteger validHeight = BigInteger.valueOf(-50);
        BigInteger validWidth = BigInteger.valueOf(200);
        BigInteger negativeLength = BigInteger.valueOf(300);

        assertThatThrownBy(() -> new Volume(validHeight, validWidth, negativeLength))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
