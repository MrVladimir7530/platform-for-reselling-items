package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "ads", schema = "public", catalog = "platformForResellingItems")
public class AdsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int pk;
    private String image;
    private Integer price;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UsersEntity usersByAuthorId;


}
