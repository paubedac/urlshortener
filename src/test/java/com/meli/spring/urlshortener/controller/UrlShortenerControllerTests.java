package com.meli.spring.urlshortener.controller;

import java.time.LocalDateTime;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.spring.urlshortener.model.Url;
import com.meli.spring.urlshortener.model.UrlDto;
import com.meli.spring.urlshortener.service.UrlService;

@WebMvcTest(UrlShortenerController.class)
public class UrlShortenerControllerTests {
	private static final String END_POINT_PATH = "/api";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UrlService service;

	@Test
	public void testAddShouldReturn404NotFound() throws Exception {
		String requestURI = END_POINT_PATH + "/generate";

		Url newUrl = new Url();
		newUrl.setOriginalUrl("");

		String requestBody = objectMapper.writeValueAsString(newUrl);

		mockMvc.perform(post(requestURI).contentType("application/json")
				.content(requestBody))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	public void testAddShouldReturn201Created() throws Exception {
		String requestURI = END_POINT_PATH + "/generate";
		String shortLink = "05920000";
		String originalUrl = "http://dummy-url.com/";

		LocalDateTime now = LocalDateTime.now();
		Url url = new Url();
		url.setId(1);
		url.setCreationDate(now);
		url.setOriginalUrl(originalUrl);
		url.setShortLink(shortLink);
		url.setIsEnabled(true);
		url.setUpdateDate(now);

		Mockito.when(service.generateShortLink(Mockito.any(UrlDto.class))).thenReturn(url);

		mockMvc.perform(post(requestURI)
				.contentType("application/json")
				.content("{\"url\":\"http://dummy-url.com/\"}"))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	public void testGetShouldReturn404NotFound() throws Exception {
		String shortLink = "05920000";
		String requestURI = END_POINT_PATH + "/url/" + shortLink;

		Mockito.when(service.getEncodedUrl(shortLink)).thenReturn(null);

		mockMvc.perform(get(requestURI))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	public void testGetShouldReturn200OK() throws Exception {
		String shortLink = "05920000";
		String originalUrl = "http://wwww.dummy-url.com";
		String requestURI = END_POINT_PATH + "/url/" + shortLink;

		LocalDateTime now = LocalDateTime.now();
		Url url = new Url();
		url.setId(1);
		url.setCreationDate(now);
		url.setOriginalUrl(originalUrl);
		url.setShortLink(shortLink);
		url.setIsEnabled(true);
		url.setUpdateDate(now);

		Mockito.when(service.getEncodedUrl(shortLink)).thenReturn(url);

		mockMvc.perform(get(requestURI))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.shortLink").exists())
				.andDo(print());
	}

	@Test
	public void testListShouldReturn200OK() throws Exception {
		String requestURI = END_POINT_PATH + "/url";

		LocalDateTime now = LocalDateTime.now();
		Url url1 = new Url();
		url1.setId(1);
		url1.setCreationDate(now);
		url1.setOriginalUrl("http://wwww.dummy-url.com");
		url1.setShortLink("05920000");
		url1.setIsEnabled(true);
		url1.setUpdateDate(now);

		Url url2 = new Url();
		url2.setId(1);
		url2.setCreationDate(now);
		url2.setOriginalUrl("http://wwww.dummy-url2.com");
		url2.setShortLink("05920001");
		url2.setIsEnabled(true);
		url2.setUpdateDate(now);

		List<Url> listUrls = List.of(url1, url2);

		Mockito.when(service.getAllUrls()).thenReturn(listUrls);

		mockMvc.perform(get(requestURI))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andDo(print());
	}

	@Test
	public void testRedirectShouldReturn400BadRequest() throws Exception {
		String shortLink = "";
		String requestURI = END_POINT_PATH + "/redirect/" + shortLink;

		mockMvc.perform(get(requestURI))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	public void testRedirectShouldReturn404NotFound() throws Exception {
		String shortLink = "05920000";
		String requestURI = END_POINT_PATH + "/redirect/" + shortLink;

		Mockito.when(service.getEncodedUrl(shortLink)).thenReturn(null);

		mockMvc.perform(get(requestURI))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	public void testRedirectShouldReturn200OK() throws Exception {
		String shortLink = "05920000";
		String originalUrl = "http://wwww.dummy-url.com";
		String requestURI = END_POINT_PATH + "/redirect/" + shortLink;

		LocalDateTime now = LocalDateTime.now();
		Url url = new Url();
		url.setId(1);
		url.setCreationDate(now);
		url.setOriginalUrl(originalUrl);
		url.setShortLink(shortLink);
		url.setIsEnabled(true);
		url.setUpdateDate(now);

		Mockito.when(service.getEncodedUrl(shortLink)).thenReturn(url);

		mockMvc.perform(MockMvcRequestBuilders.get(requestURI))
				.andExpect(status().is3xxRedirection())
				.andDo(print());
	}

	@Test
	public void testUpdateShouldReturn404NotFound() throws Exception {
		String shortLink = "05920000";
		String requestURI = END_POINT_PATH + "/update";
		String originalUrl = "http://wwww.dummy-url.com";

		UrlDto urlDto = new UrlDto();
		urlDto.setUrl(originalUrl);
		urlDto.setShortLink(shortLink);
		urlDto.setIsEnabled(true);

		String requestBody = objectMapper.writeValueAsString(urlDto);

		LocalDateTime now = LocalDateTime.now();
		Url url = new Url();
		url.setId(1);
		url.setCreationDate(now);
		url.setOriginalUrl(originalUrl);
		url.setShortLink(shortLink);
		url.setIsEnabled(true);
		url.setUpdateDate(now);

		Mockito.when(service.updateUrl(urlDto)).thenReturn(null);

		mockMvc.perform(post(requestURI).contentType("application/json")
				.content(requestBody))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	public void testUpdateShouldReturn200OK() throws Exception {
		String shortLink = "05920000";
		String requestURI = END_POINT_PATH + "/update";
		String originalUrl = "http://wwww.dummy-url.com";

		LocalDateTime now = LocalDateTime.now();
		Url url = new Url();
		url.setId(1);
		url.setCreationDate(now);
		url.setOriginalUrl(originalUrl);
		url.setShortLink(shortLink);
		url.setIsEnabled(true);
		url.setUpdateDate(now);

		Mockito.when(service.updateUrl(Mockito.any(UrlDto.class))).thenReturn(url);

		mockMvc.perform(post(requestURI)
				.contentType("application/json")
				.content("{\"shortLink\":\"05920000\",\"url\":\"http://dummy-url.com/\",\"isEnabled\":true}"))
				.andExpect(status().isOk())
				.andDo(print());
	}
}
