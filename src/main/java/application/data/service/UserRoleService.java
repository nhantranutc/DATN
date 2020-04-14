package application.data.service;

import application.data.entity.UserRole;
import application.data.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole findUserRolebyRoleIdAndUserId(int roleId, int userId) {
        return userRoleRepository.findUserRolebyRoleIdAndUserId(roleId, userId);
    }
}
