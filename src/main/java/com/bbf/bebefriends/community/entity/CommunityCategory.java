package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommunityCategory extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    public static CommunityCategory createCategory(String name) {
        CommunityCategory category = new CommunityCategory();

        category.name = name;
        return category;
    }

    public void updateCategory(String newName) {
        this.name = newName;
    }
}
