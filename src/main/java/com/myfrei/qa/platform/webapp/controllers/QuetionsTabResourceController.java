package com.myfrei.qa.platform.webapp.controllers;

import com.myfrei.qa.platform.dao.abstracts.dto.SearchQuestionDAO;
import com.myfrei.qa.platform.models.dto.QuestionDto;
import com.myfrei.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuetionsTabResourceController {
    private final QuestionDtoService questionDtoService;
    private final SearchQuestionDAO searchQuestionDAO;

    @Autowired
    public QuetionsTabResourceController(QuestionDtoService questionDtoService, SearchQuestionDAO searchQuestionDAO) {
        this.questionDtoService = questionDtoService;
        this.searchQuestionDAO = searchQuestionDAO;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getQuestionsSortedByCountValuable() {
        return ResponseEntity.ok(searchQuestionDAO.getQuestionsSortedByVotes());
    }

    @GetMapping("/tagged/{mainTagId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByTagId(@PathVariable Long mainTagId) {
        return ResponseEntity.ok(questionDtoService.getQuestionsByTagId(mainTagId));
    }

}
