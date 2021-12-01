package com.myfrei.qa.platform.dao.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.RoleDAO;
import com.myfrei.qa.platform.dao.util.SingleResultUtil;
import com.myfrei.qa.platform.models.entity.user.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleDAOImpl extends ReadWriteDAOImpl<Role, Long> implements RoleDAO {

    @Override
    public Optional<Role> getByRoleName(String roleName) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class)
                .setParameter("roleName", roleName));
    }
}