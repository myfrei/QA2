package com.myfrei.qa.platform.service.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.RoleDAO;
import com.myfrei.qa.platform.models.entity.user.Role;
import com.myfrei.qa.platform.service.abstracts.model.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl extends ReadWriteServiceImpl<Role, Long> implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        super(roleDAO);
        this.roleDAO = roleDAO;
    }

    @Override
    public Optional<Role> getByRoleName(String roleName) {
        return roleDAO.getByRoleName(roleName);
    }
}
