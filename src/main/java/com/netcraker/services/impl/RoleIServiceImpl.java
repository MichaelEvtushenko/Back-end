package com.netcraker.services.impl;

import com.netcraker.exceptions.FailedToRegisterException;
import com.netcraker.model.Role;
import com.netcraker.repositories.UserRoleRepository;
import com.netcraker.repositories.impl.RoleRepositoryImpl;
import com.netcraker.services.RoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleIServiceImpl implements RoleService {

    private final @NonNull RoleRepositoryImpl roleRepositoryImpl;
    private final UserRoleRepository userRoleRepository;

    @Override
    public Role createRole(Role role) {
        Optional<Role> roleFromDB = roleRepositoryImpl.findByName(role.getName());
        if (roleFromDB.isPresent()) {
            throw new FailedToRegisterException("Role is already created");
        }
        Optional<Role> createdRoleOpt = roleRepositoryImpl.insert(role);
        if (!createdRoleOpt.isPresent()) {
            throw new FailedToRegisterException("Error in creating role! Creation query failure");
        }
        Role createdRole = createdRoleOpt.get();

        System.out.println("created with id: " + createdRole.getRoleId());
        return role;
    }

    @Override
    public Role findByRoleId(int roleId) {
        return roleRepositoryImpl.getById(roleId).orElse(null);
    }

    @Override
    public Role findByRoleName(String name) {
        return roleRepositoryImpl.findByName(name).orElse(null);
    }

    @Override
    public void update(Role role) {
        roleRepositoryImpl.update(role);
    }

    @Override
    public void delete(int id) {
        userRoleRepository.delete(id);
        roleRepositoryImpl.delete(id);
    }
}
