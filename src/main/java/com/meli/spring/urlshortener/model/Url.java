package com.meli.spring.urlshortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Url {
  @Id
  @GeneratedValue
  private long id;
  @Lob
  private String originalUrl;
  private String shortLink;
  private LocalDateTime creationDate;
  private LocalDateTime updateDate;
  private Boolean isEnabled;

  public Url(long id, String originalUrl, String shortLink, LocalDateTime creationDate, LocalDateTime updateDate,
      Boolean isEnabled) {
    this.id = id;
    this.originalUrl = originalUrl;
    this.shortLink = shortLink;
    this.creationDate = creationDate;
    this.updateDate = updateDate;
    this.isEnabled = isEnabled;
  }

  public Url() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public String getShortLink() {
    return shortLink;
  }

  public void setShortLink(String shortLink) {
    this.shortLink = shortLink;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  @Override
  public String toString() {
    return "Url{" +
        "id=" + id +
        ", originalUrl='" + originalUrl + '\'' +
        ", shortLink='" + shortLink + '\'' +
        ", creationDate=" + creationDate +
        ", updateDate=" + updateDate +
        ", isEnabled=" + isEnabled +
        '}';
  }
}