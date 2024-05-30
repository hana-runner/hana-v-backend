package com.v.hana.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Entity e = new Entity()
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "base_image_url", nullable = false)
    private String baseImageUrl;

    @Column(name = "color", nullable = false)
    private String color;

    @Builder
    public Interest(String title, String description, String baseImageUrl, String color) {
        this.title = title;
        this.description = description;
        this.baseImageUrl = baseImageUrl;
        this.color = color;
    }
}
