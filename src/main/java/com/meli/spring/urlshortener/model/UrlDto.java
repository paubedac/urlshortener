package com.meli.spring.urlshortener.model;

public class UrlDto {
  private String url;
  private String shortLink;
  private Boolean isEnabled;

  public UrlDto(String url) {
    this.url = url;
  }

  public UrlDto() {
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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
    return "UrlDto{" +
        "url='" + url + '\'' +
        "shortLink='" + shortLink + '\'' +
        "isEnabled='" + isEnabled + '\'' +
        '}';
  }
}
