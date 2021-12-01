package com.myfrei.qa.platform.webapp.controllers;

import com.myfrei.qa.platform.models.dto.UserDto;
import com.myfrei.qa.platform.models.dto.UserStatisticDto;
import com.myfrei.qa.platform.models.entity.user.User;
import com.myfrei.qa.platform.models.util.action.OnCreate;
import com.myfrei.qa.platform.models.util.action.OnUpdate;
import com.myfrei.qa.platform.service.abstracts.dto.UserDtoService;
import com.myfrei.qa.platform.service.abstracts.dto.UserStatisticDtoService;
import com.myfrei.qa.platform.service.abstracts.model.UserService;
import com.myfrei.qa.platform.webapp.converter.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javafx.util.Pair;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
@Api(value = "UserApi", description = "Операции с пользователем (создание, изменение, получение списка, получение пользователя по ID)")
@Validated
public class UserResourceController {

    private final String regexps = "[а-яА-ЯёЁa-zA-Z]+.*$";
    private final String messages = "Параметр name должен начинаться с буквы";

    private final UserService userService;
    private final UserDtoService userDtoService;
    private final UserConverter userConverter;
    private final UserStatisticDtoService userStatisticDtoService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserResourceController(UserService userService,
                                  UserDtoService userDtoService,
                                  UserConverter userConverter,
                                  UserStatisticDtoService userStatisticDtoService) {
        this.userService = userService;
        this.userDtoService = userDtoService;
        this.userConverter = userConverter;
        this.userStatisticDtoService = userStatisticDtoService;
    }

    @ApiOperation(value = "получение списка доступных пользователей")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен")
    })
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userDtoService.getUserDtoList());
    }

    @ApiOperation(value = "Добавление пользователя")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь добавлен")
    })
    @Validated(OnCreate.class)
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        userService.persist(userConverter.toEntity(userDto));
        logger.info(String.format("Пользователь с email: %s добавлен в базу данных", userDto.getEmail()));
        return ResponseEntity.ok().body(userDto);
    }

    @ApiOperation(value = "Изменение пользователя (параметр ID обязателен)")
    @PutMapping(path = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Данные пользователя обновлены"),
            @ApiResponse(code = 400, message = "ID не совпадает с ID редактируемого пользователя"),
            @ApiResponse(code = 404, message = "Пользователь не найден по id")
    })
    @Validated(OnUpdate.class)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        if (Objects.equals(id, userDto.getId())) {
            User user = userConverter.toEntity(userDto);
            if (userService.existsById(userDto.getId())) {
                userService.update(user);
                logger.info(String.format("user с ID: %d updated successfully", userDto.getId()));
                return ResponseEntity.ok().body(userConverter.toDto(user));
            }
            logger.info(String.format("Пользователь с ID: %d не существует", userDto.getId()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with ID: %d does not exist", userDto.getId()));
        }
        logger.info(String.format("Указанный ID: %d не совпадает с ID пользователя %d", id, userDto.getId()));
        return ResponseEntity.badRequest().body(String.format("Specified ID: %d does not match user ID: %d", id, userDto.getId()));
    }

    @ApiOperation(value = "Поиск пользователя по ID")
    @GetMapping(path = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь найден по id"),
            @ApiResponse(code = 404, message = "Пользователь не найден по id")
    })
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        Optional<UserDto> optionalUserDto = userDtoService.getUserDtoById(id);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            return ResponseEntity.ok(userDto);
        }
        logger.info(String.format("Пользователь с указанным ID: %d не найден!", id));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with ID: %d does not exist", id));

    }

    @ApiOperation(value = "получение списка новых пользователей отсортированный по репутации")
    @GetMapping(path = "/new/reputation") // ?count=20&page=1&weeks=2
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен"),
    })
    public ResponseEntity<Pair<List<UserDto>, Long>> getListNewUsersByReputation(@RequestParam @NonNull @Positive Long count,
                                                                                 @RequestParam @NonNull @Positive Long page,
                                                                                 @RequestParam @NonNull @Positive Long weeks) {
        return ResponseEntity.ok().body(userDtoService
                .getListNewUsersByReputation(page.intValue(), count.intValue(), weeks));

    }

    @ApiOperation(value = "получение списка новых пользователей отсортированный по дате создания")
    @GetMapping(path = "/new") // ?count=20&page=1&weeks=2
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен"),
    })
    public ResponseEntity<Pair<List<UserDto>, Long>> getListUsersByCreationDate(@RequestParam @NonNull @Positive Long count,
                                                                                @RequestParam @NonNull @Positive Long page,
                                                                                @RequestParam @NonNull @Positive Long weeks) {
        return ResponseEntity.ok().body(userDtoService
                .getListUsersByCreationDate(page.intValue(), count.intValue(), weeks));
    }

    @ApiOperation(value = "получение списка пользователей отсортированный по репутации")
    @GetMapping(path = "/reputation") // ?count=20&page=1&weeks=12
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен"),
    })
    public ResponseEntity<Pair<List<UserDto>, Long>> getListUsersByReputation(@RequestParam @NonNull @Positive Long count,
                                                                              @RequestParam @NonNull @Positive Long page,
                                                                              @RequestParam @NonNull @Positive Long weeks) {
        return ResponseEntity.ok().body(userDtoService
                .getListUsersByReputation(page.intValue(), count.intValue(), weeks));
    }

    @ApiOperation(value = "получение списка пользователей отсортированный по голосам")
    @GetMapping(path = "/voice") // ?count=20&page=1&weeks=12
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен"),
    })
    public ResponseEntity<Pair<List<UserDto>, Long>> getListUsersByVoice(@RequestParam @NonNull @Positive Long count,
                                                                         @RequestParam @NonNull @Positive Long page,
                                                                         @RequestParam @NonNull @Positive Long weeks) {
        return ResponseEntity.ok().body(userDtoService
                .getListUsersByVoice(page.intValue(), count.intValue(), weeks));
    }

    @ApiOperation(value = "получение списка редакторов")
    @GetMapping(path = "/editor") // ?count=20&page=1&weeks=12
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список редакторов получен"),
    })
    public ResponseEntity<Pair<List<UserDto>, Long>> getListUsersByQuantityEditedText(@RequestParam @NonNull @Positive Long count,
                                                                                      @RequestParam @NonNull @Positive Long page,
                                                                                      @RequestParam @NonNull @Positive Long weeks) {
        return ResponseEntity.ok().body(userDtoService
                .getListUsersByQuantityEditedText(page.intValue(), count.intValue(), weeks));
    }

    @ApiOperation(value = "получение списка модераторов")
    @GetMapping(path = "/moderator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен"),
    })
    public ResponseEntity<Pair<List<UserDto>, Long>> getListUsersByModerator() {
        return ResponseEntity.ok().body(userDtoService.getListUsersByModerator());
    }

    @ApiOperation(value = "получение списка пользователей по имяни")
    @GetMapping(path = "/find") // ?count=20&page=1&weeks=12&name=Андрей
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен"),
    })
    public ResponseEntity<Pair<List<UserDto>, Long>> getListUsersByNameToSearch(@RequestParam @NonNull
                                                                                @Pattern(regexp = regexps,
                                                                                        message = messages) String name,
                                                                                @RequestParam @NonNull @Positive Long count,
                                                                                @RequestParam @NonNull @Positive Long page,
                                                                                @RequestParam @NonNull @Positive Long weeks) {

        return ResponseEntity.ok().body(userDtoService
                .getListUsersByNameToSearch(name, page.intValue(), count.intValue(), weeks));
    }

    @ApiOperation(value = "Предоставление статистики пользователя, по id и name")
    @GetMapping("/{id}/{name}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь найден по id"),
            @ApiResponse(code = 404, message = "Пользователь не найден по id")
    })
    public ResponseEntity<UserStatisticDto> getUserStatistics(@PathVariable @NonNull Long id,
                                                              @RequestParam(defaultValue = "profile") String tab,
                                                              @RequestParam(defaultValue = "votes") String sort,
                                                              @RequestParam(defaultValue = "1") int page) {
        Optional<UserDto> optionalUserDto = userDtoService.getUserDtoById(id);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            UserStatisticDto userStatisticDto = userStatisticDtoService.getUserStatistic(userDto, tab, sort, page);
            return ResponseEntity.ok(userStatisticDto);
        } else {
            logger.info(String.format("Пользователь с указанным ID: %d не найден!", id));
            return ResponseEntity.notFound().build();
        }

    }
}