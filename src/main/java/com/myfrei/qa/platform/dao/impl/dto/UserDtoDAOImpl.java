package com.myfrei.qa.platform.dao.impl.dto;

import com.myfrei.qa.platform.dao.abstracts.dto.UserDtoDAO;
import com.myfrei.qa.platform.dao.impl.model.ReadWriteDAOImpl;
import com.myfrei.qa.platform.dao.util.SingleResultUtil;
import com.myfrei.qa.platform.models.dto.UserBadgesDto;
import com.myfrei.qa.platform.models.dto.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDtoDAOImpl extends ReadWriteDAOImpl<UserDto, Long> implements UserDtoDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<UserDto> getUserDtoList() {

        List<UserDto> getAllUsers = new ArrayList<>();

        try {
            getAllUsers = entityManager.createQuery("SELECT " +
                    "u.id, " +
                    "u.fullName, " +
                    "u.email, " +
                    "u.role.name, " +
                    "u.persistDateTime, " +
                    "u.reputationCount, " +
                    "u.about, " +
                    "u.city, " +
                    "u.imageUser, " +
                    "u.lastUpdateDateTime, " +
                    "u.linkGitHub, " +
                    "u.linkSite, " +
                    "u.linkVk " +
                    "FROM User u")
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            return UserDto.builder()
                                    .id(((Number) objects[0]).longValue())
                                    .fullName((String) objects[1])
                                    .email((String) objects[2])
                                    .role((String) objects[3])
                                    .persistDateTime((LocalDateTime) objects[4])
                                    .reputationCount((Integer) objects[5])
                                    .about((String) objects[6])
                                    .city((String) objects[7])
                                    .imageUser((byte[]) objects[8])
                                    .lastUpdateDateTime((LocalDateTime) objects[9])
                                    .linkGitHub((String) objects[10])
                                    .linkSite((String) objects[11])
                                    .linkVk((String) objects[12])
                                    .build();
                        }

                        @Override
                        public List transformList(List list) {
                            return list;
                        }
                    })
                    .getResultList();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return getAllUsers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<UserDto> getUserDtoById(Long id) {

        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT " +
                "u.id, " +
                "u.fullName, " +
                "u.email, " +
                "u.role.name, " +
                "u.persistDateTime, " +
                "u.reputationCount, " +
                "u.about, " +
                "u.city, " +
                "u.imageUser, " +
                "u.lastUpdateDateTime, " +
                "u.linkGitHub, " +
                "u.linkSite, " +
                "u.linkVk " +
                "FROM User u WHERE u.id = " + id)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .fullName((String) objects[1])
                                .email((String) objects[2])
                                .role((String) objects[3])
                                .persistDateTime((LocalDateTime) objects[4])
                                .reputationCount((Integer) objects[5])
                                .about((String) objects[6])
                                .city((String) objects[7])
                                .imageUser((byte[]) objects[8])
                                .lastUpdateDateTime((LocalDateTime) objects[9])
                                .linkGitHub((String) objects[10])
                                .linkSite((String) objects[11])
                                .linkVk((String) objects[12])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }));
    }

    @Override
    public Long getCountNewUsersByReputation(long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        return entityManager.createQuery("SELECT COUNT(u.id) FROM User AS u " +
                "WHERE u.persistDateTime > :data", Long.class)
                .setParameter("data", data)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDto> getListNewUsersByReputation(int page, int count, long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        List<UserDto> listUsers = entityManager.createQuery("SELECT " +
                "u.id, " +
                "u.persistDateTime, " +
                "u.fullName, " +
                "u.about, " +
                "u.city, " +
                "u.imageUser, " +
                "(SELECT SUM(r.count) FROM Reputation r WHERE u.id = r.user.id AND r.persistDate > :data GROUP BY u.id) AS reputationCount " +
                "FROM User AS u WHERE u.persistDateTime > :data ORDER BY reputationCount DESC")
                .setParameter("data", data)
                .setFirstResult(count * (page - 1))
                .setMaxResults(count)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .persistDateTime((LocalDateTime) objects[1])
                                .fullName((String) objects[2])
                                .about((String) objects[3])
                                .city((String) objects[4])
                                .imageUser((byte[]) objects[5])
                                .reputationCount(getInteger(objects[6]))
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

        return listUsers.isEmpty() ? Collections.emptyList() : listUsers;
    }

    @Override
    public Long getCountUsersByCreationDate(long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        return entityManager.createQuery("SELECT COUNT(u.id) FROM User AS u " +
                "WHERE u.persistDateTime > :data", Long.class)
                .setParameter("data", data)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDto> getListUsersByCreationDate(int page, int count, long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        List<UserDto> listUsers = entityManager.createQuery("SELECT " +
                "u.id, " +
                "u.persistDateTime, " +
                "u.fullName, " +
                "u.about, " +
                "u.city, " +
                "u.imageUser, " +
                "(SELECT SUM(r.count) FROM Reputation r WHERE u.id = r.user.id AND r.persistDate > :data GROUP BY u.id) " +
                "FROM User AS u WHERE u.persistDateTime > :data ORDER BY u.persistDateTime DESC")
                .setParameter("data", data)
                .setFirstResult(count * (page - 1))
                .setMaxResults(count)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .persistDateTime((LocalDateTime) objects[1])
                                .fullName((String) objects[2])
                                .about((String) objects[3])
                                .city((String) objects[4])
                                .imageUser((byte[]) objects[5])
                                .reputationCount(getInteger(objects[6]))
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

        return listUsers.isEmpty() ? Collections.emptyList() : listUsers;
    }

    @Override
    public Long getCountUsersByQuantityEditedText(long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        return entityManager.createQuery("SELECT COUNT(DISTINCT e.user.id) FROM Editor as e " +
                "WHERE e.persistDate > :data", Long.class)
                .setParameter("data", data)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDto> getListUsersByQuantityEditedText(int page, int count, long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        List<UserDto> listUsers = entityManager.createQuery("SELECT " +
                "u.id, " +
                "u.persistDateTime, " +
                "u.fullName, " +
                "u.about, " +
                "u.city, " +
                "u.imageUser, " +
                "SUM(e.count)," +
                "(SELECT SUM(r.count) FROM Reputation r WHERE u.id = r.user.id AND r.persistDate > :data GROUP BY u.id) " +
                "FROM Editor AS e JOIN User AS u ON e.user.id = u.id WHERE e.persistDate > :data " +
                "GROUP BY e.user.id ORDER BY SUM(e.count) DESC")
                .setParameter("data", data)
                .setFirstResult(count * (page - 1))
                .setMaxResults(count)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .persistDateTime((LocalDateTime) objects[1])
                                .fullName((String) objects[2])
                                .about((String) objects[3])
                                .city((String) objects[4])
                                .imageUser((byte[]) objects[5])
                                .countChanges(getInteger(objects[6]))
                                .reputationCount(getInteger(objects[7]))
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

        return listUsers.isEmpty() ? Collections.emptyList() : listUsers;
    }

    @Override
    public Long getCountUsersByName(String name, long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        return entityManager.createQuery("SELECT COUNT(DISTINCT r.user.id) FROM Reputation as r " +
                "WHERE r.persistDate > :data AND r.user.fullName LIKE CONCAT(:searchKeyword, '%')", Long.class)
                .setParameter("data", data)
                .setParameter("searchKeyword", name.toLowerCase())
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDto> getListUsersByNameToSearch(String name, int page, int count, long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        List<UserDto> listUsers = entityManager.createQuery("SELECT " +
                "u.id, " +
                "u.persistDateTime, " +
                "u.fullName, " +
                "u.about, " +
                "u.city, " +
                "u.imageUser, " +
                "SUM(r.count)" +
                "FROM Reputation AS r JOIN User AS u ON r.user.id = u.id WHERE r.persistDate > :data AND r.user.fullName LIKE CONCAT(:searchKeyword, '%') " +
                "GROUP BY r.user.id ORDER BY SUM(r.count) DESC")
                .setParameter("data", data)
                .setParameter("searchKeyword", name.toLowerCase())
                .setFirstResult(count * (page - 1))
                .setMaxResults(count)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .persistDateTime((LocalDateTime) objects[1])
                                .fullName((String) objects[2])
                                .about((String) objects[3])
                                .city((String) objects[4])
                                .imageUser((byte[]) objects[5])
                                .reputationCount(getInteger(objects[6]))
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

        return listUsers.isEmpty() ? Collections.emptyList() : listUsers;
    }

    @Override
    public Long getCountUsersByReputation(long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        return entityManager.createQuery("SELECT COUNT(DISTINCT r.user.id) FROM Reputation as r " +
                "WHERE r.persistDate > :data", Long.class)
                .setParameter("data", data)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDto> getListUsersByReputation(int page, int count, long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        List<UserDto> listUsers = entityManager.createQuery("SELECT " +
                "u.id, " +
                "u.persistDateTime, " +
                "u.fullName, " +
                "u.about, " +
                "u.city, " +
                "u.imageUser, " +
                "SUM(r.count)" +
                "FROM Reputation AS r JOIN User AS u ON r.user.id = u.id WHERE r.persistDate > :data " +
                "GROUP BY r.user.id ORDER BY SUM(r.count) DESC")
                .setParameter("data", data)
                .setFirstResult(count * (page - 1))
                .setMaxResults(count)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .persistDateTime((LocalDateTime) objects[1])
                                .fullName((String) objects[2])
                                .about((String) objects[3])
                                .city((String) objects[4])
                                .imageUser((byte[]) objects[5])
                                .reputationCount(getInteger(objects[6]))
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

        return listUsers.isEmpty() ? Collections.emptyList() : listUsers;
    }

    @Override
    public Long getCountUsersByVoice(long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        return entityManager.createQuery("SELECT COUNT(u.id) FROM User AS u " +
                "WHERE u.persistDateTime > :data", Long.class)
                .setParameter("data", data)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDto> getListUsersByVoice(int page, int count, long weeks) {
        LocalDateTime data = LocalDateTime.now().minusWeeks(weeks);
        List<UserDto> listUsers = entityManager.createQuery("SELECT " +
                "u.id, " +
                "u.persistDateTime, " +
                "u.fullName, " +
                "u.about, " +
                "u.city, " +
                "u.imageUser, " +
                "(SELECT SUM(r.count) FROM Reputation r WHERE u.id = r.user.id), " +
                "((SELECT COALESCE(SUM(vq.vote), 0) AS vq " +
                "FROM VoteQuestion AS vq " +
                "JOIN Question AS q ON vq.voteQuestionPK.question.id = q.id " +
                "JOIN User u2 ON q.user.id = u2.id " +
                "WHERE q.id = vq.voteQuestionPK.question.id " +
                "AND u.id = u2.id AND vq.voteQuestionPK.localDateTime > :data) + " +
                "(SELECT COALESCE(sum(va.vote), 0) AS va " +
                "FROM VoteAnswer AS va " +
                "JOIN Answer a ON va.voteAnswerPK.answer.id = a.id " +
                "JOIN User u3 ON a.user.id = u3.id " +
                "WHERE a.id = va.voteAnswerPK.answer.id " +
                "AND u.id = u3.id AND va.voteAnswerPK.localDateTime > :data)) AS vote " +
                "FROM User AS u ORDER BY vote DESC ")
                .setParameter("data", data)
                .setFirstResult(count * (page - 1))
                .setMaxResults(count)
                .unwrap(Query.class).setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .persistDateTime((LocalDateTime) objects[1])
                                .fullName((String) objects[2])
                                .about((String) objects[3])
                                .city((String) objects[4])
                                .imageUser((byte[]) objects[5])
                                .reputationCount(getInteger(objects[6]))
                                .countVoice(getInteger(objects[7]))
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

        return listUsers.isEmpty() ? Collections.emptyList() : listUsers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDto> getListUsersByModerator() {
        List<UserDto> listUsers = entityManager.createQuery("SELECT " +
                "u.id, " +
                "u.fullName, " +
                "u.city, " +
                "u.imageUser, " +
                "m.persistDate, " +
                "(SELECT SUM(r.count) FROM Reputation r WHERE u.id = r.user.id GROUP BY u.id)" +
                "FROM Moderator AS m JOIN User AS u ON m.user.id = u.id")
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .id(((Number) objects[0]).longValue())
                                .fullName((String) objects[1])
                                .city((String) objects[2])
                                .imageUser((byte[]) objects[3])
                                .dateAssignmentModerator((LocalDateTime) (objects[4]))
                                .reputationCount(getInteger(objects[5]))
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

        return listUsers.isEmpty() ? Collections.emptyList() : listUsers;
    }

    private Integer getInteger(Object o) {
        if (o == null) {
            return 0;
        }
        return ((Number) o).intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserBadgesDto> getUserBadges(Long userId, Integer page) {
        List<UserBadgesDto> userBadgesList = entityManager.createQuery("SELECT " +
                "b.id, " +
                "b.description, " +
                "b.badges " +
                "FROM UserBadges ub JOIN Badges b ON ub.badges.id = b.id " +
                "WHERE ub.user.id = :userId AND ub.ready = true ")
                .setParameter("userId", userId)
                .setFirstResult((page - 1) * 42)
                .setMaxResults(42)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserBadgesDto.builder()
                                .id((Long) objects[0])
                                .badges((String) objects[2])
                                .description((String) objects[1])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();
        return userBadgesList.isEmpty() ? Collections.emptyList() : userBadgesList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getCountOfUserBadges(Long userId) {
        return entityManager.createQuery("SELECT " +
                "COUNT(b.id) " +
                "FROM UserBadges ub JOIN Badges b ON ub.badges.id = b.id " +
                "WHERE ub.user.id = :userId AND ub.ready = true", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getAllViews(Long userId) {
        Long Qlong = entityManager.createQuery("SELECT SUM(q.viewCount) " +
                "FROM Question q WHERE q.user.id = :userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
        Long Along = entityManager.createQuery("SELECT SUM(a.question.viewCount) " +
                "FROM Answer a JOIN Question q ON a.question.id = q.id " +
                "WHERE a.user.id = :userId AND a.user.id <> q.user.id", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
        Long result = Long.sum(Qlong == null ? 0l : Qlong, Along == null ? 0l : Along);
        return result;
    }
}