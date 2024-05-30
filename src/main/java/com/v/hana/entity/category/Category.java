package com.v.hana.entity.category;

import com.v.hana.common.annotation.TypeInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Category", description = "카테고리 엔티티 클래스")
@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @Builder
    public Category(String title, String color) {
        this.title = title;
        this.color = color;
    }
}
