package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.pack.PackVolume;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ShipmentTest {

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);

        var volume1 = BigDecimal.valueOf(100);

        var packages = List.of(new Pack(weight1), new Pack(weight2));
        var volumePackages = List.of(new PackVolume(volume1), new PackVolume(volume1));
        var shipment = new Shipment(packages, volumePackages, new CurrencyFactory(code -> true).create("RUB"));

        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }

    @Test
    void testVolumeAllPackages() {

        PackVolume volume1 = Mockito.mock(PackVolume.class);
        PackVolume volume2 = Mockito.mock(PackVolume.class);

        when(volume1.cargoVolume()).thenReturn(BigDecimal.valueOf(100));
        when(volume2.cargoVolume()).thenReturn(BigDecimal.valueOf(50));

        Shipment shipment = new Shipment(List.of(), List.of(volume1, volume2), new CurrencyFactory(code -> true).create("RUB"));

        assertEquals(BigDecimal.valueOf(150), shipment.volumeAllPackages());
    }


}