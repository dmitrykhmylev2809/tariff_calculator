package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.fastdelivery.domain.common.coordinates.DeparturePoint;
import ru.fastdelivery.domain.common.coordinates.DestinationPoint;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.distance.DistanceCalculator;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.pack.PackVolume;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TariffCalculateUseCaseTest {

    @Mock
    private DistanceCalculator distanceCalculator;

    @InjectMocks
    private TariffCalculateUseCase tariffCalculateUseCase;

    final PriceProvider priceProvider = mock(PriceProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    @Test
    @DisplayName("Расчет стоимости доставки по весу -> успешно")
    void whenCalculateWeightPrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerM3 = new Price(BigDecimal.valueOf(1), currency);

        TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(priceProvider);

        DeparturePoint departure = new DeparturePoint(BigDecimal.valueOf(50), BigDecimal.valueOf(50));
        DestinationPoint destination = new DestinationPoint(BigDecimal.valueOf(50.1), BigDecimal.valueOf(50.1));

        when(priceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(priceProvider.costPerKg()).thenReturn(pricePerKg);
        when(priceProvider.costPerM3()).thenReturn(pricePerM3);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)))),
                List.of(new PackVolume(BigDecimal.valueOf(100))),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(120), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment, departure, destination);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Расчет стоимости доставки по объему -> успешно")
    void whenCalculateVolumePrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(10), currency);
        var pricePerM3 = new Price(BigDecimal.valueOf(5), currency);

        TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(priceProvider);

        DeparturePoint departure = new DeparturePoint(BigDecimal.valueOf(50), BigDecimal.valueOf(50));
        DestinationPoint destination = new DestinationPoint(BigDecimal.valueOf(50.1), BigDecimal.valueOf(50.1));

        when(priceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(priceProvider.costPerKg()).thenReturn(pricePerKg);
        when(priceProvider.costPerM3()).thenReturn(pricePerM3);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(100)))),
                List.of(new PackVolume(BigDecimal.valueOf(1200))),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(6000), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment, departure, destination);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }


    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {

        TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(priceProvider);

        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(priceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPrice);
    }


    @Test
    @DisplayName("Расчет стоимости доставки при весовом тарифе > объемного и расстоянии > 450 -> успешно")
    void whenCalculateWeightPriceAndDistanceGreaterThan450_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerM3 = new Price(BigDecimal.valueOf(1), currency);

        DeparturePoint departure = new DeparturePoint(BigDecimal.valueOf(50), BigDecimal.valueOf(50));
        DestinationPoint destination = new DestinationPoint(BigDecimal.valueOf(55), BigDecimal.valueOf(55));


        when(priceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(priceProvider.costPerKg()).thenReturn(pricePerKg);
        when(priceProvider.costPerM3()).thenReturn(pricePerM3);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)))),
                List.of(new PackVolume(BigDecimal.valueOf(100))),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(173.52), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment, departure, destination);

        actualPrice = new Price(
                actualPrice.amount().setScale(2, RoundingMode.HALF_UP),
                actualPrice.currency()
        );

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }


    @Test
    @DisplayName("Расчет стоимости доставки при весовом тарифе < объемного и расстоянии > 450 -> успешно")
    void whenCalculateVolumePriceAndDistanceGreaterThan450_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerM3 = new Price(BigDecimal.valueOf(50), currency);

        DeparturePoint departure = new DeparturePoint(BigDecimal.valueOf(50), BigDecimal.valueOf(50));
        DestinationPoint destination = new DestinationPoint(BigDecimal.valueOf(55), BigDecimal.valueOf(55));


        when(priceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(priceProvider.costPerKg()).thenReturn(pricePerKg);
        when(priceProvider.costPerM3()).thenReturn(pricePerM3);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(100)))),
                List.of(new PackVolume(BigDecimal.valueOf(1200))),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(86759.92), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment, departure, destination);

        actualPrice = new Price(
                actualPrice.amount().setScale(2, RoundingMode.HALF_UP),
                actualPrice.currency()
        );

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

}