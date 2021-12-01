package com.myfrei.qa.platform.dao.abstracts.model;

import com.myfrei.qa.platform.models.entity.user.Role;

import java.util.Optional;

public interface RoleDAO extends ReadWriteDAO<Role, Long> {
    Optional<Role> getByRoleName(String roleName);
}
