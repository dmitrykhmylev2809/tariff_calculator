package ru.fastdelivery.calc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.config.Config;
import ru.fastdelivery.domain.common.coordinates.CoordinatesValidationService;
import ru.fastdelivery.domain.common.coordinates.DeparturePoint;
import ru.fastdelivery.domain.common.coordinates.DestinationPoint;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(classes = Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalculateControllerTest extends ControllerTest {

    final String baseCalculateApi = "/api/v1/calculate/";
    @MockBean
    TariffCalculateUseCase useCase;
    @MockBean
    CurrencyFactory currencyFactory;
    @MockBean
    CoordinatesValidationService coordinatesValidationService;


    @Test
    @DisplayName("Валидные данные для расчета стоимость -> Ответ 200")
    void whenValidInputData_thenReturn200() {
        var request = new CalculatePackagesRequest(
                List.of(new CargoPackage(BigInteger.TEN, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE)), "RUB",
                new DestinationPoint(BigDecimal.valueOf(50), BigDecimal.valueOf(50)),
                new DeparturePoint(BigDecimal.valueOf(51), BigDecimal.valueOf(51)));
        var rub = new CurrencyFactory(code -> true).create("RUB");
        when(useCase.calc(any(), any(), any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Список упаковок == null -> Ответ 400")
    void whenEmptyListPackages_thenReturn400() {
        var request = new CalculatePackagesRequest(null, "RUB",null, null);

        ResponseEntity<String> response = restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


//    @Test
//    @DisplayName("Валидные данные с некорректными координатами -> Ответ 400")
//    void whenValidInputDataAndInvalidCoordinates_thenReturn400() {
//
//        var invalidLatitude = BigDecimal.valueOf(80.0);
//        var invalidLongitude = BigDecimal.valueOf(180.0);
//
//
//        var request = new CalculatePackagesRequest(
//                List.of(new CargoPackage(BigInteger.TEN, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE)),
//                "RUB",
//                new DestinationPoint(invalidLatitude, invalidLongitude),
//                new DeparturePoint(BigDecimal.ONE, BigDecimal.ONE));
//
//        ResponseEntity<CalculatePackagesResponse> response = restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
}
