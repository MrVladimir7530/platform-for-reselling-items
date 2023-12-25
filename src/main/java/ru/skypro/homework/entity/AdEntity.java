package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "ads", schema = "public", catalog = "platformForResellingItems")
public class AdEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int pk;
    private String image;
    private Integer price;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity usersByAuthorId;
    @OneToMany(mappedBy = "ad")
    @JsonIgnore
    private List<CommentEntity> comments;


}
