package com.memnik.dao;

import com.memnik.dao.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "mems")
public class MemEntity extends BaseEntity {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mems_tags",
            joinColumns = @JoinColumn(name = "mem_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<TagEntity> tags = new ArrayList<>();

    @Override
    public void addTag(TagEntity tag){
        this.tags.add(tag);
        tag.getMems().add(this);
    }
    @Override
    public void removeTag(TagEntity tag){
        this.tags.remove(tag);
        tag.getMems().remove(this);
    }
}
