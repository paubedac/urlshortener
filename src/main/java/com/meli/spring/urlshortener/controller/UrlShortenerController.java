package com.meli.spring.urlshortener.controller;

import com.meli.spring.urlshortener.model.Url;
import com.meli.spring.urlshortener.model.UrlDto;
import com.meli.spring.urlshortener.model.UrlErrorResponseDto;
import com.meli.spring.urlshortener.model.UrlResponseDto;
import com.meli.spring.urlshortener.service.UrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {
  private UrlService urlService;

  public UrlShortenerController(UrlService urlService) {
    this.urlService = urlService;
  }

  @GetMapping("/url")
  public List<Url> getAllUrls() {
    return urlService.getAllUrls();
  }

  @GetMapping("/url/{shortLink}")
  public ResponseEntity<?> getUrl(@PathVariable String shortLink,
      HttpServletResponse response)
      throws IOException {
    if (StringUtils.isEmpty(shortLink)) {
      UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
      urlErrorResponseDto.setError("Invalid Url");
      urlErrorResponseDto.setStatus("400");
      return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.BAD_REQUEST);
    }

    Url urlToRet = urlService.getEncodedUrl(shortLink);
    if (urlToRet != null) {
      UrlResponseDto urlResponseDto = new UrlResponseDto();
      urlResponseDto.setOriginalUrl(urlToRet.getOriginalUrl());
      urlResponseDto.setIsEnabled(urlToRet.getIsEnabled());
      urlResponseDto.setShortLink(urlToRet.getShortLink());
      return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
    }

    UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
    urlErrorResponseDto.setStatus("404");
    urlErrorResponseDto.setError("There was an error processing your request. please try again.");
    return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,
        HttpStatus.NOT_FOUND);
  }

  @PostMapping("/generate")
  public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
    Url urlToRet = urlService.generateShortLink(urlDto);

    if (urlToRet != null) {
      UrlResponseDto urlResponseDto = new UrlResponseDto();
      urlResponseDto.setOriginalUrl(urlToRet.getOriginalUrl());
      urlResponseDto.setIsEnabled(urlToRet.getIsEnabled());
      urlResponseDto.setShortLink(urlToRet.getShortLink());
      return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.CREATED);
    }

    UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
    urlErrorResponseDto.setStatus("404");
    urlErrorResponseDto.setError("There was an error processing your request. please try again.");
    return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.NOT_FOUND);
  }

  @PostMapping("/update")
  public ResponseEntity<?> updateShortLink(@RequestBody UrlDto urlDto) {
    Url urlToRet = urlService.updateUrl(urlDto);

    if (urlToRet != null) {
      UrlResponseDto urlResponseDto = new UrlResponseDto();
      urlResponseDto.setOriginalUrl(urlToRet.getOriginalUrl());
      urlResponseDto.setIsEnabled(urlToRet.getIsEnabled());
      urlResponseDto.setShortLink(urlToRet.getShortLink());
      return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
    }

    UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
    urlErrorResponseDto.setStatus("404");
    urlErrorResponseDto.setError("There was an error processing your request. please try again.");
    return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.NOT_FOUND);
  }

  @GetMapping("/redirect/{shortLink}")
  public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response)
      throws IOException {
    if (StringUtils.isEmpty(shortLink)) {
      UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
      urlErrorResponseDto.setError("Invalid url");
      urlErrorResponseDto.setStatus("400");
      return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.BAD_REQUEST);
    }

    Url urlToRet = urlService.getEncodedUrl(shortLink);
    if (urlToRet == null) {
      UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
      urlErrorResponseDto.setError("Url does not exist");
      urlErrorResponseDto.setStatus("404");
      return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.NOT_FOUND);
    }

    response.sendRedirect(urlToRet.getOriginalUrl());
    return null;
  }
}
