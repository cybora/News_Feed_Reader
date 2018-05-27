package com.newsfeeder.services;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newsfeeder.model.News;
import com.newsfeeder.model.News.NewsId;
import com.newsfeeder.model.NewsSource;
import com.newsfeeder.repository.NewsRepository;
import com.newsfeeder.repository.NewsSourceRepository;
import com.newsfeeder.util.NewsContants;
import com.newsfeeder.util.NewsQueryHelper;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class NewsServicesImpl implements NewsService {

	private static final Logger log = LoggerFactory.getLogger(NewsServicesImpl.class);

	@Autowired
	public NewsRepository newsRepository;

	@Autowired
	public NewsSourceRepository newsSourceRepository;

	@Autowired
	public ImageService imageService;

	@Autowired
	public NewsQueryHelper newsQueryHelper;

	@Scheduled(fixedRate = NewsContants.SCHEDULED_PERIOD)
	public void getNewsFromSources() {

		log.info("Getting news from the sources");

		// Getting the active new sources from News_Source table
		Boolean active = true;
		List<NewsSource> sources = (List<NewsSource>) newsSourceRepository.findByActiveFlag(active);

		// for each active source, get the news data and save into the DB
		sources.stream().forEach(f -> saveNews(f.getURL(), f.getId()));

		log.info("News getting completed");
	}

	public void saveNews(String url, Integer id) {
		try {
			// get the latest news from the previous load
			News lastNews = newsRepository.findTop1ByIdNewsSourceIdOrderByPublicationDateDesc(id);
			Date lastDate = new Date(0); // initial date for no data yet for the news source
			List<News> newsList = new ArrayList<>(); // list of incoming news

			if (lastNews != null) { // if there is data from given source already
				lastDate = lastNews.getPublicationDate(); // get the latest one from the previous load for comparison
			}

			try (XmlReader reader = new XmlReader(new URL(url))) {
				SyndFeed feed = new SyndFeedInput().build(reader);
				for (SyndEntry entry : feed.getEntries()) {
					String GUID = entry.getUri(); // get GUID
					if ("".equals(GUID) || GUID == null) { // only news have GUIDs
						continue; // skip this non-news entry
					}
					String title = entry.getTitle(); // title
					String description = entry.getDescription().getValue().trim(); // description, trimmed as some feeds
																					// // send spaces before or after
					Date publishedDate = entry.getPublishedDate(); // published date of news
					String link = entry.getLink(); // link of the news
					String comments = entry.getComments(); // comments on the news
					Date updatedDate = entry.getUpdatedDate(); // update date of the news
					String author = entry.getAuthor(); // the author

					// for enclosures tag
					List<SyndEnclosure> enclosures = entry.getEnclosures();
					String imagePathOnline = "";
					if (enclosures != null) {
						if (enclosures.size() != 0)
							imagePathOnline = enclosures.get(0).getUrl();
					}

					// for media tag
					List<Element> foreignMarkUps = entry.getForeignMarkup();
					if (foreignMarkUps != null) {
						if (foreignMarkUps.size() != 0) {
							imagePathOnline = foreignMarkUps.get(0).getAttribute("url").getValue();
						}
					}

					NewsId newsId = new NewsId(id, GUID); // set the id 
					
					if (publishedDate.after(lastDate)) { // check if the incoming news is newer than the latest of the previous batch
						String imagePath = "";
						if (!"".equals(imagePathOnline.trim())) {
							imagePath = imageService.saveImage(imagePathOnline); // save the image to local disk and save the path in db
						}

						TimeUnit.MILLISECONDS.sleep(NewsContants.SLEEP_TIME); // pause for a while not to overload the
																				// server to avoid 524 error
						News news = new News(newsId, title, description, publishedDate, comments, link, imagePath,
								author, updatedDate);

						newsList.add(news);
					}

				}

				newsRepository.saveAll(newsList); // save the news
				log.info("News have been saved");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(fixedRate = NewsContants.SCHEDULED_REMOVAL_PERIOD)
	public void deleteOldNews() {
		List<News> newsToBeDeleted = newsQueryHelper.findNewsToBeDeleted();

		newsToBeDeleted.stream().forEach(s -> imageService.deleteImage(s.getImagePath()));

		newsQueryHelper.deleteAllByHours();
	}

	public List<News> getNewsByMinutes(Integer id, Integer minutes) {

		List<News> list;

		list = (List<News>) newsQueryHelper.findAllBySourceNewsAndByMinutes(id, minutes);

		convertImagesForNews(list);

		return list;
	}

	public List<News> getNewsByHours(Integer id, Integer hours) {
		List<News> list;

		list = (List<News>) newsQueryHelper.findAllBySourceNewsAndByHours(id, hours);

		convertImagesForNews(list);

		return list;
	}

	public List<News> convertImagesForNews(List<News> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String imagePath = list.get(i).getImagePath();
				if (!"".equals(imagePath)) {
					File f = new File(imagePath);
					String encodedstring = imageService.encodeFileToBase64Binary(f);
					list.get(i).setImageBase64(encodedstring);
				}
			}
		}
		return list;
	}

	@Override
	public List<News> getNewsBySource(Integer id) {
		List<News> list = newsRepository.findByIdNewsSourceId(id);

		convertImagesForNews(list);

		return list;
	}

	@Override
	public List<News> getNewsByInTitleSearch(String title) {
		List<News> list = newsRepository.findByTitleIsLike(title);

		convertImagesForNews(list);

		return list;
	}

	@Override
	public List<News> getAllNews() {
		List<News> list = (List<News>) newsRepository.findAll();

		convertImagesForNews(list);

		return list;
	}

	@Override
	public List<News> getNewsByInDescriptionSearch(String description) {
		List<News> list = (List<News>) newsRepository.findByDescriptionIsLike(description);

		convertImagesForNews(list);

		return list;
	}
}
