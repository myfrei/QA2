package com.myfrei.qa.platform.webapp.controllers;

import com.myfrei.qa.platform.models.dto.CommentDto;
import com.myfrei.qa.platform.models.entity.Comment;
import com.myfrei.qa.platform.models.entity.question.CommentQuestion;
import com.myfrei.qa.platform.models.entity.question.answer.CommentAnswer;
import com.myfrei.qa.platform.models.util.action.OnCreate;
import com.myfrei.qa.platform.models.util.action.OnUpdate;
import com.myfrei.qa.platform.service.abstracts.dto.CommentAnswerServiceDto;
import com.myfrei.qa.platform.service.abstracts.dto.CommentQuestionServiceDto;
import com.myfrei.qa.platform.service.abstracts.model.AnswerService;
import com.myfrei.qa.platform.service.abstracts.model.QuestionService;
import com.myfrei.qa.platform.service.abstracts.model.UserService;
import com.myfrei.qa.platform.service.abstracts.model.comment.CommentAnswerService;
import com.myfrei.qa.platform.service.abstracts.model.comment.CommentQuestionService;
import com.myfrei.qa.platform.webapp.converter.CommentAnswerConverter;
import com.myfrei.qa.platform.webapp.converter.CommentConverter;
import com.myfrei.qa.platform.webapp.converter.CommentQuestionConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestControllerAdvice
@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
@Api(value = "CommentApi", description = "Операции с комментариями (создание, изменение, получение списка)")
public class CommentResourceController {

    private final CommentQuestionServiceDto commentQuestionServiceDto;
    private final CommentAnswerServiceDto commentAnswerServiceDto;
    private final CommentQuestionService commentQuestionService;
    private final CommentAnswerService commentAnswerService;
    private final CommentConverter commentConverter;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final CommentAnswerConverter answerConverter;
    private final CommentQuestionConverter questionConverter;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommentResourceController(CommentQuestionServiceDto commentQuestionServiceDto,
                                     CommentAnswerServiceDto commentAnswerServiceDto,
                                     CommentQuestionService commentQuestionService,
                                     CommentAnswerService commentAnswerService,
                                     CommentConverter commentConverter,
                                     AnswerService answerService,
                                     QuestionService questionService,
                                     CommentAnswerConverter answerConverter,
                                     CommentQuestionConverter questionConverter,
                                     UserService userService) {
        this.commentQuestionServiceDto = commentQuestionServiceDto;
        this.commentAnswerServiceDto = commentAnswerServiceDto;
        this.commentQuestionService = commentQuestionService;
        this.commentAnswerService = commentAnswerService;
        this.commentConverter = commentConverter;
        this.answerService = answerService;
        this.questionService = questionService;
        this.answerConverter = answerConverter;
        this.questionConverter = questionConverter;
        this.userService = userService;
    }

    @ApiOperation(value = "Получение списка комментариев для вопроса")
    @GetMapping("/question/{questionId}/comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список комментариев для вопроса получен"),
            @ApiResponse(code = 404, message = "Вопрос не найден")
    })
    public ResponseEntity<?> getCommentsToQuestion(@PathVariable @NonNull Long questionId) {
        if (questionService.existsById(questionId)) {
            List<CommentDto> list = commentQuestionServiceDto.getCommentsToQuestion(questionId);
            logger.info(String.format("Комментарии к вопросу с ID: %d найдены", questionId));
            return ResponseEntity.ok().body(list);
        }
        logger.error(String.format("question with ID: %d not found", questionId));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("question with ID: %d not found", questionId));
    }

    @ApiOperation(value = "Получение списка комментариев для ответа")
    @GetMapping("/answer/{answerId}/comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список комментариев для ответа получен"),
            @ApiResponse(code = 404, message = "Ответ не найден")
    })
    public ResponseEntity<?> getCommentsToAnswer(@PathVariable @NonNull Long answerId) {
        if (answerService.existsById(answerId)) {
            List<CommentDto> list = commentAnswerServiceDto.getCommentsToAnswer(answerId);
            logger.info(String.format("Комментарии к ответу с ID: %d найдены", answerId));
            return ResponseEntity.ok().body(list);
        }
        logger.error(String.format("answer with ID: %d not found", answerId));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("answer with ID: %d not found", answerId));
    }

    @Validated(OnCreate.class)
    @ApiOperation(value = "Добавления комментария к вопросу(параметр ID обязателен для вопроса и автора комментария)")
    @PostMapping("/question/{questionId}/comment")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Комментариев к вопросу добавлен"),
            @ApiResponse(code = 400, message = "Попутка оставить второй комментарий под вопросом"),
            @ApiResponse(code = 404, message = "Автор коментария или Вопрос не найдены")
    })
    public ResponseEntity<?> saveCommentQuestion(@RequestBody @Valid @NonNull CommentDto commentDto,
                                                 @PathVariable @NonNull Long questionId) {
        if (!userService.existsById(commentDto.getUserId())) {
            logger.error(String.format("user with ID: %d not found", commentDto.getUserId()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("user with ID: %d not found", commentDto.getUserId()));
        }
        if (!questionService.existsById(questionId)) {
            logger.error(String.format("question with ID: %d not found", questionId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("question with ID: %d not found", questionId));
        }
        if (commentQuestionServiceDto.hasUserToCommentQuestion(questionId, commentDto.getUserId())) {
            logger.error(String.format("Попутка оставить второй комментарий под вопросом с ID: %d", questionId));
            return ResponseEntity.badRequest().body("You can leave only one comment in question");
        }
        CommentQuestion commentQuestion = questionConverter.toCommentQuestion(commentDto, questionId);
        commentQuestionService.persist(commentQuestion);
        logger.info(String.format("Комментарии к вопросу с ID: %d добавлен", questionId));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @Validated(OnCreate.class)
    @ApiOperation(value = "Добавления комментария к ответу(параметр ID обязателен для ответа и автора комментария)")
    @PostMapping("/answer/{answerId}/comment")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Комментарий к ответу добавлен"),
            @ApiResponse(code = 400, message = "Попытка оставить второй комментарий под ответом"),
            @ApiResponse(code = 404, message = "Автор коментария или Ответ не найдены")
    })
    public ResponseEntity<?> saveCommentAnswer(@RequestBody @Valid @NonNull CommentDto commentDto,
                                               @PathVariable @NonNull Long answerId) {
        if (!userService.existsById(commentDto.getUserId())) {
            logger.error(String.format("user with ID: %d not found", commentDto.getUserId()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("user with ID: %d not found", commentDto.getUserId()));
        }
        if (!answerService.existsById(answerId)) {
            logger.error(String.format("answer with ID: %d not found", answerId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("answer with ID: %d not found", answerId));
        }
        if (commentAnswerServiceDto.hasUserToCommentAnswer(answerId, commentDto.getUserId())) {
            logger.error(String.format("Попытка оставить второй комментарий под ответом с ID: %d", answerId));
            return ResponseEntity.badRequest().body("Only one comment can be left under the answer");
        }
        CommentAnswer commentAnswer = answerConverter.toCommentAnswer(commentDto, answerId);
        commentAnswerService.persist(commentAnswer);
        logger.info(String.format("Комментарии к ответу с ID: %d добавлен", answerId));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @Validated(OnUpdate.class)
    @ApiOperation(value = "Изменение текста комментария под вопросом")
    @PutMapping("/question/comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Текст комментария обновленн под вопросом"),
            @ApiResponse(code = 404, message = "Комментария не найден")
    })
    public ResponseEntity<?> updateCommentQuestion(@RequestBody @Valid @NonNull CommentDto commentDto) {
        if (commentQuestionService.existsById(commentDto.getId())) {
            Comment comment = commentQuestionServiceDto.getByKey(commentDto.getId());
            comment.setText(commentDto.getText());
            commentQuestionServiceDto.update(comment);
            logger.info(String.format("Комментарии с ID: %d обновленн", commentDto.getId()));
            return ResponseEntity.ok().body(commentConverter.toCommentDto(comment));
        }
        logger.error(String.format("comment with ID: %d not found", commentDto.getId()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("comment with ID: %d not found", commentDto.getId()));

    }

    @Validated(OnUpdate.class)
    @ApiOperation(value = "Изменение текста комментария под ответом")
    @PutMapping("/answer/comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Текст комментария обновлен под ответом"),
            @ApiResponse(code = 404, message = "Комментария не найден")
    })
    public ResponseEntity<?> updateCommentAnswer(@RequestBody @Valid @NonNull CommentDto commentDto) {
        if (commentAnswerService.existsById(commentDto.getId())) {
            Comment comment = commentAnswerServiceDto.getByKey(commentDto.getId());
            comment.setText(commentDto.getText());
            commentAnswerServiceDto.update(comment);
            logger.info(String.format("Комментарии с ID: %d обновленн", commentDto.getId()));
            return ResponseEntity.ok().body(commentConverter.toCommentDto(comment));
        }
        logger.error(String.format("comment with ID: %d not found", commentDto.getId()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("comment with ID: %d not found", commentDto.getId()));
    }
}