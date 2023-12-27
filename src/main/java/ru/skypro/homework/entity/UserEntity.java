package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users", schema = "public", catalog = "platformForResellingItems")
public class UserEntity {
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
    private String email;
    @OneToOne
    @JoinColumn(name = "image")
    private ImageEntity imageEntity;
    @OneToMany(mappedBy = "usersByAuthorId")
    @JsonIgnore
    private List<AdEntity> ads;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CommentEntity> comments;

}
