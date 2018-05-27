package com.newsfeeder.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.newsfeeder.model.NewsSource;

public interface NewsSourceRepository extends CrudRepository<NewsSource, Integer> {
	
	List<NewsSource> findByActiveFlag(Boolean flag);

}
