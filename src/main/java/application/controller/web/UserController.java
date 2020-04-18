package application.controller.web;

import application.data.entity.User;
import application.data.service.UserService;
import application.model.viewmodel.user.ChangePasswordVM;
import application.model.viewmodel.user.UserVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/change-password")
    public String changePasswordGet(Model model) {

        ChangePasswordVM changePasswordVM = new ChangePasswordVM();

        model.addAttribute("changePassword", changePasswordVM);
        return "/change-password";
    }

    @PostMapping("/change-password")
    public String changePasswordPost(@Valid @ModelAttribute("changePassword") ChangePasswordVM passwordVM) {

        try {
            if (passwordVM.getCurrentPassword() != null && passwordVM.getNewPassword() != null && passwordVM.getConfirmPassword() != null) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userService.findUserByUsername(username);

                if (!passwordEncoder.matches(passwordVM.getCurrentPassword(), user.getPasswordHash())) {
                    return "redirect:/user/change-password?invalidCurrentPassword";
                } else if (!passwordVM.getNewPassword().equals(passwordVM.getConfirmPassword())) {
                    return "redirect:/user/change-password?invalidConfirmPassword";
                } else {
                    user.setPasswordHash(passwordEncoder.encode(passwordVM.getNewPassword()));
                    userService.updateUser(user);
                    return "redirect:/user/change-password?success";
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "redirect:/user/change-password?fail";
    }

    @GetMapping("/detail")
    public String getUserDetail(Model model) {
        UserVM userVM = new UserVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user= userService.findUserByUsername(username);

        userVM.setId(user.getId());
        userVM.setFullName(user.getFullName());
        userVM.setAvatar(user.getAvatar());
        userVM.setEmail(user.getEmail());
        userVM.setGender(user.getGender());
        userVM.setAddressName(user.getAddressName());
        userVM.setPhoneNumber(user.getPhoneNumber());

        model.addAttribute("user", userVM);

        return "/user-detail";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user) {

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User userEntity = userService.findUserByUsername(username);

            userEntity.setAddressName(user.getAddressName());
            userEntity.setAvatar(user.getAvatar());
            userEntity.setEmail(user.getEmail());
            userEntity.setGender(user.getGender());
            userEntity.setFullName(user.getFullName());
            userEntity.setPhoneNumber(user.getPhoneNumber());

            userService.updateUser(userEntity);

            return "redirect:/user/detail?updateSuccess";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "redirect:/user/detail?updateFail";

    }
}
