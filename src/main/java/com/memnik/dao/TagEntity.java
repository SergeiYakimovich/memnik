package com.memnik.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author")
    private String author;
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<MemEntity> mems = new ArrayList<>();
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<JokeEntity> jokes = new ArrayList<>();
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<PostcardEntity> postcards = new ArrayList<>();
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<QuoteEntity> quotes = new ArrayList<>();
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<VideoEntity> videos = new ArrayList<>();

    public void addMem(MemEntity mem){
        this.mems.add(mem);
        mem.getTags().add(this);
    }

    public void removeMem(MemEntity mem){
        this.mems.remove(mem);
        mem.getTags().remove(this);
    }

    public void addJoke(JokeEntity joke){
        this.jokes.add(joke);
        joke.getTags().add(this);
    }

    public void removeJoke(JokeEntity joke){
        this.jokes.remove(joke);
        joke.getTags().remove(this);
    }

    public void addPostcard(PostcardEntity postcard){
        this.postcards.add(postcard);
        postcard.getTags().add(this);
    }

    public void removePostcard(PostcardEntity postcard){
        this.postcards.remove(postcard);
        postcard.getTags().remove(this);
    }

    public void addQuote(QuoteEntity quote){
        this.quotes.add(quote);
        quote.getTags().add(this);
    }

    public void removeQuote(QuoteEntity quote){
        this.quotes.remove(quote);
        quote.getTags().remove(this);
    }

    public void addVideo(VideoEntity video){
        this.videos.add(video);
        video.getTags().add(this);
    }

    public void removeVideo(VideoEntity video){
        this.videos.remove(video);
        video.getTags().remove(this);
    }

}
