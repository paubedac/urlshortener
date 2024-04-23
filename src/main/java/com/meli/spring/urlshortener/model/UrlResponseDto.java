package com.meli.spring.urlshortener.model;

public class UrlResponseDto {
  private String originalUrl;
  private String shortLink;
  private Boolean isEnabled;

  public UrlResponseDto(String originalUrl, String shortLink, Boolean isEnabled) {
    this.originalUrl = originalUrl;
    this.shortLink = shortLink;
    this.isEnabled = isEnabled;
  }

  public UrlResponseDto() {
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

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  @Override
  public String toString() {
    return "UrlResponseDto{" +
        "originalUrl='" + originalUrl + '\'' +
        ", shortLink='" + shortLink + '\'' +
        ", isEnabled=" + isEnabled +
        '}';
  }
}