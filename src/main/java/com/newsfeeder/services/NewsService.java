package com.newsfeeder.services;

import java.util.List;


import com.newsfeeder.model.News;


public interface NewsService {
	
	public void saveNews(String url, Integer id);
	
	public List<News> getNewsByMinutes(Integer id, Integer minutes);
	
	public List<News> getNewsByHours(Integer id, Integer hours);
	
	public List<News> getNewsBySource(Integer id);
	
	public List<News> getNewsByInTitleSearch(String title);
	
	public List<News> getNewsByInDescriptionSearch(String description);
	
	public List<News> getAllNews();
	
	public void getNewsFromSources();
	
	public void deleteOldNews();

}
