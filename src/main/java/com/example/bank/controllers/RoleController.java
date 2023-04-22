package com.example.bank.controllers;

import com.example.bank.dto.RoleDTO;
import com.example.bank.models.Role;
import com.example.bank.services.RoleService;
import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;

    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    ResponseDTO list() {
        Iterable<Role> roles = this.roleService.getAllRoles();
        long totalRecord = this.roleService.count();
        return ResponseUtil.list(roles, totalRecord, true, "List all roles");
    }

    @GetMapping("{id}")
    ResponseDTO get(@PathVariable long id) {
        try {
            Role role = this.roleService.getRoleById(id);
            return ResponseUtil.object(role, true, "Get role by id: " + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("Error while getting role: " + id + " " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

    @PostMapping
    ResponseDTO save(@RequestBody RoleDTO payload) {
        try {
            Role customer = this.roleService.create(payload);
            return ResponseUtil.object(customer, true, "Role has been created.");
        } catch (Exception ex) {
            LOGGER.error("Error while creating role: " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

    @PutMapping("{id}")
    ResponseDTO update(@PathVariable long id, @RequestBody RoleDTO payload) {
        try {
            Role role = this.roleService.updateRoleById(id, payload);
            return ResponseUtil.object(role, true, "Role has been updated.");
        } catch (Exception ex) {
            LOGGER.error("Error while updating role: " + id + " " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

}
