package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.ALL_SORTED, query = "select m from Meal m where m.user.id=:userId order by m.dateTime desc"),
        @NamedQuery(name = Meal.DELETE, query = "delete from Meal m where m.user.id=:userId and m.id=:id"),
        @NamedQuery(name = Meal.GET, query = "select m from Meal m where m.user.id=:userId and m.id=:id"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "select m from Meal m where m.user.id=:userId and m.dateTime>=:before and m.dateTime<=:after order by m.dateTime desc"),
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String DELETE = "Meal.delete";
    public static final String GET = "Meal.get";
    public static final String GET_BETWEEN = "Meal.between";

    @Column(name = "date_time", columnDefinition = "TIMESTAMP", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(nullable = false)
    @Size(min = 1, max = 255)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @Max(10000)
    @Min(1)
    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
