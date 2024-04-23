package com.meli.spring.urlshortener.service;

import com.google.common.hash.Hashing;
import com.meli.spring.urlshortener.model.Url;
import com.meli.spring.urlshortener.model.UrlDto;
import com.meli.spring.urlshortener.repository.UrlRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {
  private UrlRepository urlRepository;

  public UrlServiceImpl(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  public Url generateShortLink(UrlDto urlDto) {
    if (StringUtils.isNotEmpty(urlDto.getUrl())) {
      LocalDateTime now = LocalDateTime.now();
      String encodedUrl = encodeUrl(urlDto.getUrl());
      Url urlToPersist = new Url();
      urlToPersist.setCreationDate(now);
      urlToPersist.setOriginalUrl(urlDto.getUrl());
      urlToPersist.setShortLink(encodedUrl);
      urlToPersist.setIsEnabled(true);
      urlToPersist.setUpdateDate(now);
      Url urlToRet = persistShortLink(urlToPersist);

      if (urlToRet != null)
        return urlToRet;

      return null;
    }
    return null;
  }

  public Url updateUrl(UrlDto urlDto) {
    if (StringUtils.isNotEmpty(urlDto.getShortLink())) {
      Url urlToPersist = getEncodedUrl(urlDto.getShortLink());
      if (StringUtils.isNotEmpty(urlDto.getUrl())) {
        urlToPersist.setOriginalUrl(urlDto.getUrl());
      }
      urlToPersist.setIsEnabled(urlDto.getIsEnabled());
      urlToPersist.setUpdateDate(LocalDateTime.now());
      Url urlToRet = persistShortLink(urlToPersist);

      if (urlToRet != null)
        return urlToRet;

      return null;
    }
    return null;
  }

  private String encodeUrl(String url) {
    String encodedUrl = "";
    LocalDateTime time = LocalDateTime.now();
    encodedUrl = Hashing.murmur3_32()
        .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
        .toString();
    return encodedUrl;
  }

  public Url persistShortLink(Url url) {
    return urlRepository.save(url);
  }

  public Url getEncodedUrl(String url) {
    return urlRepository.findByShortLink(url);
  }

  public List<Url> getAllUrls() {
    return urlRepository.findAll();
  }
}