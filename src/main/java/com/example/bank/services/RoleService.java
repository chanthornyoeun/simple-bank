package com.example.bank.services;

import com.example.bank.dto.RoleDTO;
import com.example.bank.models.Role;
import com.example.bank.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public long count() {
        return this.roleRepository.count();
    }

    public Role getRoleById(long roleId) {
        return roleRepository.findById(roleId).orElseThrow();
    }

    public Role create(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        return roleRepository.save(role);
    }

    public Role updateRoleById(long roleId, RoleDTO roleDTO) {
        Role role = this.getRoleById(roleId);
        role.setRoleName(roleDTO.getRoleName());
        return roleRepository.save(role);
    }

}
