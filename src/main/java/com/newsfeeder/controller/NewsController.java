package com.newsfeeder.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsfeeder.model.News;
import com.newsfeeder.services.NewsService;
import com.newsfeeder.util.NewsContants;

@RestController
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	
	/**
	 * @param sourceId
	 * @param minutes
	 * @return
	 */
	@GetMapping(value="/getNewsByMinute", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<News> getNewsByMinute(@RequestParam(name="source", required=false) Integer sourceId, @RequestParam(name="minutes", required=true) Integer minutes) {
		return newsService.getNewsByMinutes(sourceId, minutes);
	}

	
	
	/**
	 * @param sourceId
	 * @param hours
	 * @return
	 */
	@GetMapping(value="/getNewsByHours", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<News> getNewsByHours(@RequestParam(name="source", required=false) Integer sourceId, @RequestParam(name="hours", required=true) Integer hours) {
		return newsService.getNewsByHours(sourceId, hours);
	}
	
	
	/**
	 * @return
	 */
	@GetMapping(value="/getAllNews", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<News> getAllNews() {
		return newsService.getAllNews();
	}
	
	
	/**
	 * @param sourceId
	 * @return
	 */
	@GetMapping(value="/getNewsBySource", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<News> getNewsBySource(@RequestParam(name="source") Integer sourceId) {
		return newsService.getNewsBySource(sourceId);
	}
	
	
	/**
	 * @param title
	 * @return
	 */
	@GetMapping(value="/getNewsByInTitleSearch", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<News> getNewsByInTitleSearch(@RequestParam(name="title") String title) {
		return newsService.getNewsByInTitleSearch(title);
	}
	
	@GetMapping(value="/getNewsByInDescriptionSearch", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<News> getNewsByInDescriptionSearch(@RequestParam(name="description") String description) {
		return newsService.getNewsByInDescriptionSearch(description);
	}
	
	
	
	/**
	 * @param e
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler
	void handleNumberFormatException(java.lang.NumberFormatException e, HttpServletResponse response) throws IOException {
	    response.sendError(HttpStatus.BAD_REQUEST.value(), NewsContants.REQUEST_PARAMETER_FORMAT_ERROR);
	}

	
	

}
