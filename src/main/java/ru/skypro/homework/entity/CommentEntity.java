package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
@Data
@Entity
@Table(name = "comments", schema = "public", catalog = "platformForResellingItems")
public class CommentsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int pk;

    @Column(name = "created_at")
    private Date createdAt;

    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "ad_id", referencedColumnName = "pk")
    private AdsEntity ad;
}
