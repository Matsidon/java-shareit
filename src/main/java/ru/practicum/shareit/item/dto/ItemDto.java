package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.user.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ItemDto {
    private long id;
    @NotNull(groups = Create.class)
    @NotBlank(groups = Create.class)
    private String name;
    @NotNull(groups = Create.class)
    private String description;
    @NotNull(groups = Create.class)
    private Boolean available;

    public ItemDto(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }
}
