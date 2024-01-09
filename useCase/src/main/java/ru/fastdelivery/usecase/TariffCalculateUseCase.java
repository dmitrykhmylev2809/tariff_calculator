package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.coordinates.DeparturePoint;
import ru.fastdelivery.domain.common.coordinates.DestinationPoint;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.distance.DistanceCalculator;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import java.math.BigDecimal;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final PriceProvider priceProvider;

    public Price calc(Shipment shipment, DeparturePoint departure, DestinationPoint destination) {

        DistanceCalculator distanceCalculator = new DistanceCalculator(departure, destination);
        double distance = distanceCalculator.calculateDistance() / 1000;
        double distanceCoefficient = ( distance > 450) ? (distance / 450.0) : 1.0;

        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var volumeAllPackagesM3 = shipment.volumeAllPackages();
        var minimalPrice = priceProvider.minimalPrice();

        Price allWeightPrice = priceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPrice);

        Price allVolumePrice = priceProvider
                .costPerM3()
                .multiply(volumeAllPackagesM3);

        allWeightPrice = new Price(allWeightPrice.amount().multiply(new BigDecimal(distanceCoefficient)), allWeightPrice.currency());
        allVolumePrice = new Price(allVolumePrice.amount().multiply(new BigDecimal(distanceCoefficient)), allVolumePrice.currency());

        return (allWeightPrice.amount().compareTo(allVolumePrice.amount()) >= 0) ? allWeightPrice : allVolumePrice;
    }

    public Price minimalPrice() {
        return priceProvider.minimalPrice();
    }
}
