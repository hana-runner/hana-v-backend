package com.v.hana.entity.interest;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@TypeInfo(name = "UserInterest", description = "사용자 관심사 엔티티 클래스")
@Entity
@Table(name = "user_interests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Entity e = new Entity()
public class UserInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "interest_id", referencedColumnName = "id", nullable = false)
    private Interest interest;

    @Column(name = "subtitle", nullable = true)
    private String subtitle;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Builder
    public UserInterest(User user, Interest interest, String subtitle, String imageUrl) {
        this.user = user;
        this.interest = interest;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
    }
}
