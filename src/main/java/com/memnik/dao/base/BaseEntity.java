package com.memnik.dao.base;

import com.memnik.dao.TagEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass // Указывает, что это базовый класс для сущностей
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author")
    private String author;
    @Column(name = "information")
    private String information;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "used")
    private boolean used;
    @Column(name = "language")
    private String language;

    public abstract void addTag(TagEntity tag);
    public abstract void removeTag(TagEntity tag);

}