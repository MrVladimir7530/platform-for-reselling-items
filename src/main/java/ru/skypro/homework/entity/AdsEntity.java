package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.skypro.homework.dto.CommentsDto;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @OneToMany(mappedBy = "ad")
    @JsonIgnore
    private List<CommentsEntity> comments;


}
