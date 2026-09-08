package dybr.dev.task3_spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "email")
    private String email;

    @Setter
    @Column(name = "age")
    private int age;

    @Column(name = "created_at")
    private LocalDate created_at;

    public UserEntity(String name, String email, int age) {
        this.name = name;
        this.email = email;
        created_at = LocalDate.now();
        this.age = age;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' + ", email='" + email + '\'' + ", age=" + age;
    }
}