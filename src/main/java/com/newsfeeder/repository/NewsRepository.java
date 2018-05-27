package com.newsfeeder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.newsfeeder.model.News;
import com.newsfeeder.model.News.NewsId;

public interface NewsRepository extends PagingAndSortingRepository<News, NewsId> {

	// get the newest news from the latest load of the specific source
	public News findTop1ByIdNewsSourceIdOrderByPublicationDateDesc(Integer newsSourceId);
	
	// return the news from specific source
	public List<News> findByIdNewsSourceId(Integer newsSourceId);
	
	@Query("select n from News n where title like %:title%")
	public List<News> findByTitleIsLike(@Param("title") String title);
	
	@Query("select n from News n where description like %:description%")
	public List<News> findByDescriptionIsLike(@Param("description") String description);

}
