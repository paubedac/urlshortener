package com.meli.spring.urlshortener.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.meli.spring.urlshortener.model.Url;

@DataJpaTest
public class UrlRepositoryTests {
  @Autowired
  private UrlRepository urlRepository;
  private Url testUrl;

  @BeforeEach
  public void setUp() {
    // Initialize test data before each test method
    testUrl = new Url();
    testUrl.setOriginalUrl("http://www.dummy-url.com");
    testUrl.setShortLink("05920000");
    testUrl.setIsEnabled(true);
    urlRepository.save(testUrl);
  }

  @AfterEach
  public void tearDown() {
    // Release test data after each test method
    urlRepository.delete(testUrl);
  }

  @Test
  void givenUrl_whenSaved_thenCanBeFoundById() {
    Url savedUrl = urlRepository.findById(testUrl.getId()).orElse(null);
    assertNotNull(savedUrl);
    assertEquals(testUrl.getShortLink(), savedUrl.getShortLink());
    assertEquals(testUrl.getOriginalUrl(), savedUrl.getOriginalUrl());
  }

  @Test
  void givenUrl_whenSaved_thenCanBeFoundByShortLink() {
    Url savedUrl = urlRepository.findByShortLink(testUrl.getShortLink());
    assertNotNull(savedUrl);
    assertEquals(testUrl.getShortLink(), savedUrl.getShortLink());
    assertEquals(testUrl.getOriginalUrl(), savedUrl.getOriginalUrl());
  }

  @Test
  void givenUrl_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
    testUrl.setOriginalUrl("http://www.updated-dummy-url");
    urlRepository.save(testUrl);

    Url updatedUrl = urlRepository.findById(testUrl.getId()).orElse(null);
    assertNotNull(updatedUrl);
    assertEquals("http://www.updated-dummy-url", updatedUrl.getOriginalUrl());
  }
}
