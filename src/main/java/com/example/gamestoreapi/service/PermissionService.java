package com.example.gamestoreapi.service;

import com.example.gamestoreapi.model.Permission;
import com.example.gamestoreapi.repository.PermissionRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.gamestoreapi.GlobalTags;

@Service
public class PermissionService {
    private final PermissionRepo permissionRepo;

    public PermissionService(PermissionRepo permissionRepo){
        this.permissionRepo = permissionRepo;

        Optional<Permission> optional = permissionRepo.findByName(GlobalTags.ADMIN);
        if(optional.isEmpty())
            permissionRepo.save(new Permission(0, GlobalTags.ADMIN));
        optional = permissionRepo.findByName(GlobalTags.DEV);
        if(optional.isEmpty())
            permissionRepo.save(new Permission(0, GlobalTags.DEV));
        optional = permissionRepo.findByName(GlobalTags.USER);
        if(optional.isEmpty())
            permissionRepo.save(new Permission(0, GlobalTags.USER));
    }
}
