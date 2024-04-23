package com.meli.spring.urlshortener.repository;

import com.meli.spring.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
  public Url findByShortLink(String shortLink);
}