package ru.kuksov.testproject.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;

/**
 * Класс DTO для выходных данных о пользователе
 * @param uuid уникальный номер пользователя
 * @param fio ФИО
 * @param phoneNumber телефонный номер (фильтр для России)
 * @param avatar url-ссылка на аватарку
 * @param roleName название роли
 */
public record UserIdAndDetails(

        @NotBlank(message = "Идентификатор UUID обязателен")
        @UUID(message = "Некорректный формат UUID")
        String uuid,

        @NotBlank(message = "Указание ФИО обязательно")
        @Size(min = 3, max = 256, message = "ФИО должно быть от 3 до 256 символов")
        String fio,

        @Pattern(regexp="^((8|\\+7)[\\- ]?)?(\\(?\\d{3,4}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Неверный формат телефона")
        String phoneNumber,

        @URL(message = "Неверный формат URL")
        String avatar,

        @NotBlank(message = "Название роли обязательно")
        @Size(min = 3, max = 256, message = "Название роли должно быть от 3 до 256 символов")
        String roleName
) {
}
