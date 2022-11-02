package ru.practicum.shareit.user.dto;

import lombok.*;
import ru.practicum.shareit.user.Create;
import ru.practicum.shareit.user.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserDto {
    @NotNull(groups = Update.class)
    private long id;
    @NotNull(groups = Create.class)
    @NotBlank(groups = {Create.class, Update.class})
    private String name;
    @NotNull(groups = Create.class)
    @NotBlank(groups = {Create.class, Update.class})
    private String email;
}
