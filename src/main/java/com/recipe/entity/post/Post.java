package com.recipe.entity.post;

import com.recipe.constant.PostCategory;
import com.recipe.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    private LocalDateTime uploadDate;
    private LocalDateTime updateDate;
    private int viewCount;
    private boolean isDeleted;
    private LocalDateTime deletedTime;

}
