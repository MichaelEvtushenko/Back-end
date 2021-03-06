package com.netcraker.repositories.impl;

import com.netcraker.exceptions.UpdateException;
import com.netcraker.model.Role;
import com.netcraker.model.mapper.RoleRowMapper;
import com.netcraker.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PropertySource("classpath:sqlQueries.properties")
public class RoleRepositoryImpl implements RoleRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class);

    @Value("${role.findByRoleName}")
    private String sqlSelectRoleName;

    @Value("${role.findByRoleId}")
    private String sqlSelectRoleId;

    @Value("${role.create}")
    private String sqlCreateRole;

    @Value("${role.update}")
    private String sqlUpdateRole;

    @Value("${role.delete}")
    private String sqlDeleteRole;

    @Value("${role.findByUserId}")
    private String sqlSelectByUserId;

    @Value("${role.findAllByUserId}")
    private String sqlSelectAllByUserId;

    @Value("${role.sqlGetAll}")
    private String sqlGetAll;


    public Optional<Role> findByName(String name) {
        Object[] params = { name };
        List<Role> roles = jdbcTemplate.query(sqlSelectRoleName, params, new RoleRowMapper());
        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
    }
    public List<Role> getAllRoleById(int userId) {
        Object[] params = { userId };
        return jdbcTemplate.query(sqlSelectAllByUserId, params, new RoleRowMapper());
    }

    public Role getRoleByUserId(int userId) {
        Object[] params = { userId };
        return jdbcTemplate.queryForObject(sqlSelectByUserId, params, new RoleRowMapper());
    }

    public Optional<Role> getById(int roleId) {
        Object[] params = { roleId };
        List<Role> roles =  jdbcTemplate.query(sqlSelectRoleId, params, new RoleRowMapper());
        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
    }

    @Override
    public List<Role> getAllRoles() {
        return jdbcTemplate.query(sqlGetAll, new RoleRowMapper());
    }

    public Optional<Role> insert(Role entity) {
        Object[] params = { entity.getName(), entity.getDescription() };
        jdbcTemplate.update(sqlCreateRole, params);
        return findByName(entity.getName());
    }

    public Optional<Role> update(Role entity) {
        Object[] params = { entity.getName(), entity.getDescription(), entity.getRoleId()};
        int changedRowsCount = jdbcTemplate.update(sqlUpdateRole, params);

        if (changedRowsCount == 0){
            throw new UpdateException("Role is not found!");
        }
        if (changedRowsCount > 1){
            throw new UpdateException("Multiple update! Only one role can be changed!");
        }
        return getById(entity.getRoleId());
    }

    public boolean delete(int roleId)  {
        Object[] params = {roleId};
        return jdbcTemplate.update(sqlDeleteRole, params)==1;
    }

}
