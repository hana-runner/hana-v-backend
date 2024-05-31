package com.v.hana.entity.interest;

import com.v.hana.common.annotation.TypeInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Interest", description = "관심사 엔티티 클래스")
@Entity
@Table(name = "Interests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "base_image_url", nullable = false, length = 255)
    private String baseImageUrl;

    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @Builder
    public Interest(Long id, String title, String description, String baseImageUrl, String color) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.baseImageUrl = baseImageUrl;
        this.color = color;
    }
}
