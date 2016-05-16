package com.codegoons.diesli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "content")
    private String content;

    @Column(name = "title")
    private String title;

    @Column(name = "custom_excerpt")
    private String customExcerpt;

    @Lob
    @Column(name = "featured_image")
    private byte[] featuredImage;

    @Column(name = "featured_image_content_type")    
    private String featuredImageContentType;

    @Column(name = "views")
    private Long views;

    @Column(name = "popularity")
    private Long popularity;

    @OneToMany(mappedBy = "card")
    @JsonIgnore
    private Set<CardReply> cardReplies = new HashSet<>();

    @ManyToOne
    private Board board;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomExcerpt() {
        return customExcerpt;
    }

    public void setCustomExcerpt(String customExcerpt) {
        this.customExcerpt = customExcerpt;
    }

    public byte[] getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageContentType() {
        return featuredImageContentType;
    }

    public void setFeaturedImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public Set<CardReply> getCardReplies() {
        return cardReplies;
    }

    public void setCardReplies(Set<CardReply> cardReplies) {
        this.cardReplies = cardReplies;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        if(card.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Card{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", content='" + content + "'" +
            ", title='" + title + "'" +
            ", customExcerpt='" + customExcerpt + "'" +
            ", featuredImage='" + featuredImage + "'" +
            ", featuredImageContentType='" + featuredImageContentType + "'" +
            ", views='" + views + "'" +
            ", popularity='" + popularity + "'" +
            '}';
    }
}
