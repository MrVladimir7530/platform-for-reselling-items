package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "comments", schema = "public", catalog = "platformForResellingItems")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int pk;

    @Column(name = "created_at")
    private Date createdAt;

    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "ad_id", referencedColumnName = "pk")
    private AdEntity ad;
}
