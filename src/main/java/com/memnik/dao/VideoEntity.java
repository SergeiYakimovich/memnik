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
@Table(name = "videos")
public class VideoEntity extends BaseEntity {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "videos_tags",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<TagEntity> tags = new ArrayList<>();

    @Override
    public void addTag(TagEntity tag){
        this.tags.add(tag);
        tag.getVideos().add(this);
    }
    @Override
    public void removeTag(TagEntity tag){
        this.tags.remove(tag);
        tag.getVideos().remove(this);
    }
}
