package com.myfrei.qa.platform.service.impl.dto;

import com.myfrei.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.myfrei.qa.platform.models.dto.QuestionDto;
import com.myfrei.qa.platform.service.abstracts.dto.QuestionDtoService;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionDtoServiceImpl implements QuestionDtoService {

    private final QuestionDtoDao questionDtoDao;

    public QuestionDtoServiceImpl(QuestionDtoDao questionDtoDao) {
        this.questionDtoDao = questionDtoDao;
    }

    @Override
    public List<QuestionDto> getAllQuestionDto() {
        return questionDtoDao.getQuestionDtoList();
    }

    @Override
    public Pair<Long, List<QuestionDto>> getPaginationQuestion(int page, int size) {
        List<QuestionDto> list = questionDtoDao.getQuestionList(page, size);
        list.forEach(f -> f.setTags(questionDtoDao.getTagList(f.getId())));
        return new Pair<>(questionDtoDao.getCount(), list);
    }

    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id) {
        return questionDtoDao.getQuestionDtoById(id);
    }

    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id, Long userId) {
        return questionDtoDao.getQuestionDtoById(id, userId);
    }

    @Override
    public List<QuestionDto> getQuestionDtoListByUserId(Long userId) {
        return questionDtoDao.getQuestionDtoListByUserId(userId);
    }

    @Override
    public Optional<QuestionDto> hasQuestionAnswer(Long questionId) {
        return questionDtoDao.hasQuestionAnswer(questionId);
    }

    @Override
    public Integer getCountValuable(Long questionId) {
        return questionDtoDao.getCountValuable(questionId);
    }

    @Override
    public QuestionDto getCountValuableQuestionWithUserVote(Long questionId, Long userId) {
        return questionDtoDao.getCountValuableQuestionWithUserVote(questionId, userId).orElseThrow(() -> new IllegalArgumentException("Не корректные параметры"));
    }

    @Override
    public boolean isUserCanToVoteByQuestionUp(Long questionId, Long userId) {
        return questionDtoDao.sumVotesUserByVote(questionId, userId) > 0;
    }

    @Override
    public boolean isUserCanToVoteByQuestionDown(Long questionId, Long userId) {
        return questionDtoDao.sumVotesUserByVote(questionId, userId) < 0;
    }


    @Override
    public List<QuestionDto> getQuestionsByTagId(Long mainTagId) {
        return questionDtoDao.getQuestionsByTagId(mainTagId);
    }

    @Override
    public List<QuestionDto> getUnansweredQuestions() {
        return questionDtoDao.getUnansweredQuestions();
    }
}