package com.myfrei.qa.platform.dao.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.ReputationDAO;
import com.myfrei.qa.platform.dao.util.SingleResultUtil;
import com.myfrei.qa.platform.models.entity.user.Reputation;
import com.myfrei.qa.platform.models.entity.user.User;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReputationDAOImpl extends ReadWriteDAOImpl<Reputation, Long> implements ReputationDAO {

    @Override
    public void persist(Reputation reputation) {
        super.persist(reputation);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Reputation> findByUserIdAndDate(User user) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT " +
                "r.id, " +
                "r.count, " +
                "r.persistDate " +
                "FROM Reputation r WHERE r.user.id = :user_id AND r.persistDate = :persistDate")
                .setParameter("persistDate", LocalDateTime.now())
                .setParameter("user_id", user.getId())
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return Reputation.builder()
                                .id((Long) objects[0])
                                .user(user)
                                .count((Integer) objects[1])
                                .persistDate((LocalDateTime) objects[2])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }));
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Reputation> getReputationByUserId(Long userId){
        List<Reputation> reputation = entityManager.createQuery("SELECT " +
                "r.id, " +
                "r.count, " +
                "r.persistDate " +
                "FROM Reputation r WHERE r.user.id = :userId")
                .setParameter("userId", userId)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return Reputation.builder()
                                .id((Long) objects[0])
                                .count((Integer) objects[1])
                                .persistDate((LocalDateTime) objects[2])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();
        return reputation;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getSumOfUserReputation(Long userId){
        Long reputation = entityManager.createQuery("SELECT " +
                "SUM(r.count) " +
                "FROM Reputation r WHERE r.user.id = :userId", Long.class)
                .setParameter("userId", userId).getSingleResult();
        if (reputation == null)
            return 0l;

        return reputation;
    }

}


