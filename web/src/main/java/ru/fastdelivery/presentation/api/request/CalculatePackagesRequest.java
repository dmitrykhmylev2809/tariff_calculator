package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.fastdelivery.domain.common.coordinates.DeparturePoint;
import ru.fastdelivery.domain.common.coordinates.DestinationPoint;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
        @Schema(description = "Список упаковок отправления",
                example = "[{\"weight\": 4056.45,\n" +
                        "  \"height\": 265,\n" +
                        "  \"length\": 333,\n" +
                        "  \"width\": 166\n" +
                        "}]")
        @NotNull
        @NotEmpty
        List<CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull
        String currencyCode,

        @Schema(description = "Координаты точки доставки",
                example = "[{\"latitude\": 73.398660,\n" +
                        "  \"longitude\": 55.027532\n" +
                        "}]")
        @NotNull
        DestinationPoint destination,


        @Schema(description = "Координаты точки доставки",
                example = "[{\"latitude\": 73.398660,\n" +
                        "  \"longitude\": 55.027532\n" +
                        "}]")
        @NotNull
        DeparturePoint departure
) {
}
