package ru.fastdelivery.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.coordinates.CoordinatesValidationService;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.PriceProvider;
import ru.fastdelivery.domain.common.coordinates.CoordinatesConfigProvider;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(PriceProvider priceProvider) {
        return new TariffCalculateUseCase(priceProvider);
    }

    @Bean
    public CoordinatesValidationService coordinatesValidationService(
            @Qualifier("coordinatesProperties") CoordinatesConfigProvider coordinatesConfigProvider) {
        return new CoordinatesValidationService(coordinatesConfigProvider);
    }

}
