package com.meli.spring.urlshortener.service;

import com.meli.spring.urlshortener.model.Url;
import com.meli.spring.urlshortener.model.UrlDto;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
  public Url generateShortLink(UrlDto urlDto);

  public Url updateUrl(UrlDto urlDto);

  public Url persistShortLink(Url url);

  public Url getEncodedUrl(String url);

  public List<Url> getAllUrls();
}