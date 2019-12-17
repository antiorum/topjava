package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.HasEmail;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFormValidator implements Validator {

//    @Autowired
//    @Qualifier("emailValidator")
//    EmailValidator emailValidator;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserTo.class.equals(aClass) || User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        HasEmail user = (HasEmail)o;

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
        List<String> emails = userService.getAll().stream().filter(e -> !e.getId().equals(user.getId())).map(User::getEmail).collect(Collectors.toList());

        if (emails.contains(user.getEmail())) {
            errors.rejectValue("email", "user.email.alreadyExist");
        }
    }
}
