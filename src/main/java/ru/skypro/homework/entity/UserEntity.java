package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "users", schema = "public", catalog = "platformForResellingItems")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String password;
    private String phone;
    private String role;
    private String image;
    private String email;
    @OneToMany(mappedBy = "usersByAuthorId")
    @JsonIgnore
    private List<AdsEntity> ads;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CommentsEntity> comments;

}
