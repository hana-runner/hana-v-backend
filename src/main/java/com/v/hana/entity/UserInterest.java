package com.v.hana.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_interests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Entity e = new Entity()
public class UserInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "interest_id", nullable = false)
    private long interestId;

    @Column(name = "subtitle", nullable = true)
    private String subtitle;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Builder
    public UserInterest(long userId, long interestId, String subtitle, String imageUrl) {
        this.userId = userId;
        this.interestId = interestId;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
    }
}
