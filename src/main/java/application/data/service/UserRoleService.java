package application.data.service;

import application.data.entity.UserRole;
import application.data.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public void addNewUserRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    public UserRole findUserRolebyRoleIdAndUserId(int roleId, int userId) {
        return userRoleRepository.findUserRolebyRoleIdAndUserId(roleId, userId);
    }

    public List<UserRole> findUserRolebyRoleId(int roleId) {
        return userRoleRepository.findUserRolebyRoleId(roleId);
    }
}
