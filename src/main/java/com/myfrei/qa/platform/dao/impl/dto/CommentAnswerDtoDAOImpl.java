package com.myfrei.qa.platform.dao.impl.dto;

import com.myfrei.qa.platform.dao.abstracts.dto.CommentAnswerDtoDAO;
import com.myfrei.qa.platform.dao.impl.model.ReadWriteDAOImpl;
import com.myfrei.qa.platform.models.dto.CommentDto;
import com.myfrei.qa.platform.models.entity.Comment;
import com.myfrei.qa.platform.models.entity.CommentType;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
public class CommentAnswerDtoDAOImpl extends ReadWriteDAOImpl<Comment, Long> implements CommentAnswerDtoDAO {

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<CommentDto> getCommentsToAnswer(Long answerId) {
        String hql = "SELECT " +
                "c.id, " +
                "c.text, " +
                "c.commentType," +
                "c.persistDateTime," +
                "c.lastUpdateDateTime, " +
                "c.user.id, " +
                "c.user.fullName " +
                "FROM Comment as c JOIN CommentAnswer as ca ON c.id = ca.id WHERE ca.answer.id = :answerId";
        List<CommentDto> list = entityManager.createQuery(hql)
                .unwrap(Query.class)
                .setParameter("answerId", answerId)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return CommentDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .text((String) objects[1])
                                .commentType((CommentType) objects[2])
                                .persistDateTime((LocalDateTime) objects[3])
                                .lastUpdateDateTime((LocalDateTime) objects[4])
                                .userId(((Number) objects[5]).longValue())
                                .fullName((String) objects[6])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }).getResultList();

        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public Boolean hasUserCommentAnswer(Long answerId, Long userId) {
        return (Boolean) entityManager.createQuery("SELECT " +
                "CASE WHEN COUNT (c)>0 THEN TRUE ELSE FALSE END FROM Comment AS c JOIN CommentAnswer AS ca ON c.id = ca.id " +
                "WHERE c.user.id = :userId AND ca.answer.id = :answerId")
                .unwrap(Query.class)
                .setParameter("answerId", answerId)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
