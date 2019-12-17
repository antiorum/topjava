package ru.javawebinar.topjava;

import org.springframework.util.Assert;

public interface HasEmail extends HasId{
    String getEmail();

    void setEmail(String email);

    default String email() {
        Assert.notNull(getEmail(), "Entity must has id");
        return getEmail();
    }
}
