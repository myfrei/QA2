package com.myfrei.qa.platform.service.abstracts.model;

import com.myfrei.qa.platform.models.entity.user.Role;

import java.util.Optional;

public interface RoleService extends ReadWriteService<Role, Long> {
    Optional<Role> getByRoleName(String roleName);
}
