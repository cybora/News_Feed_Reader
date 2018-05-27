package com.newsfeeder.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.newsfeeder.model.News;
import com.newsfeeder.model.News.NewsId;
import com.newsfeeder.services.NewsService;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableAutoConfiguration
@EntityScan(basePackageClasses=News.class)
@ContextConfiguration(classes = {NewsService.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class NewsRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private NewsRepository newsRepository;
    
    @Test
    public void whenFindByIdNewsSourceId_thenReturnNews() {
    	NewsId newsId = new NewsId(1, "3AD5G2AG21");
    	
    	String title = "'Toeristische' viswinkel Leidsestraat moet dicht";
    	String description = "Viswinkel The Seafood Shop in de Leidsestraat moet per 5 juni stoppen met het verkopen van vis.";
    	Date publicationDate = new Date();
    	String comments = "";
    	String link = "https://www.ad.nl/amsterdam/toeristische-viswinkel-leidsestraat-moet-dicht~a14be354/";
    	String imagepath = "https://images1.persgroep.net/rcs/Na85rCru68DsCpVG3blmPgJMq10/diocontent/124136941/_fitwidth/400/"
    			+ "?appId=21791a8992982cd8da851550a453bd7f&amp;quality=0.7";
    	
    	String author = "Door: Rick Plantinga";
    	
    	Date updatedDate = null;
    	
    	News news = new News(newsId, title, description, publicationDate, comments,
    			link, imagepath, author, updatedDate);
    	
    	entityManager.persist(news);
        entityManager.flush();
        
     // when
        List<News> found = newsRepository.findByIdNewsSourceId(1);
        
        String foundDescription = found.get(0).getDescription();
     
        // then
        assertEquals(description, foundDescription);
    	
    }
    
    @Test
    public void whenFindTop1ByIdNewsSourceIdOrderByPublicationDateDesc_thenReturnNews() {
    	
    	NewsId newsId = new NewsId(1, "3AD5G2AG21");
    	
    	String title = "'Toeristische' viswinkel Leidsestraat moet dicht";
    	String description = "Viswinkel The Seafood Shop in de Leidsestraat moet per 5 juni stoppen met het verkopen van vis.";
    	Date publicationDate = new Date(1000000);
    	String comments = "";
    	String link = "https://www.ad.nl/amsterdam/toeristische-viswinkel-leidsestraat-moet-dicht~a14be354/";
    	String imagepath = "https://images1.persgroep.net/rcs/Na85rCru68DsCpVG3blmPgJMq10/diocontent/124136941/_fitwidth/400/"
    			+ "?appId=21791a8992982cd8da851550a453bd7f&amp;quality=0.7";
    	
    	String author = "Door: Rick Plantinga";
    	
    	Date updatedDate = null;
    	
    	News news = new News(newsId, title, description, publicationDate, comments,
    			link, imagepath, author, updatedDate);
    	
    	entityManager.persist(news);
        entityManager.flush();
        
        NewsId newsId2 = new NewsId(1, "67742323A73");
    	
    	String title2 = "Drie Wijzen uit Oost nemen Pacific Parc over: 'Pacific blijft Pacific'";
    	String description2 = "Pacific Parc op het Westergasterrein is verkocht aan de Drie Wijzen uit Oost. Geen grote veranderingen, belooft de nieuwe eigenaar: de naam en het personeel blijven hetzelfde. Vooralsnog de programmering ook. \"Pacific blijft Pacific.\"";
    	Date publicationDate2 = new Date(2000000);
    	String comments2 = "";
    	String link2 = "https://www.ad.nl/amsterdam/drie-wijzen-uit-oost-nemen-pacific-parc-over-pacific-blijft-pacific~a37bf499/";
    	String imagepath2 = "https://images4.persgroep.net/rcs/v0CamC7PhcWsxE"
    			+ "_jne3wRyLm28s/diocontent/124100378/_fitwidth/400/?appId=21791a8992982cd8da851550a453bd7f&amp;quality=0.7";
    	
    	String author2 = "Door: Tom Kieft";
    	
    	Date updatedDate2 = null;
    	
    	News news2 = new News(newsId2, title2, description2, publicationDate2, comments2,
    			link2, imagepath2, author2, updatedDate2);
    	
    	entityManager.persist(news2);
        entityManager.flush();
        
     // when
        News found = newsRepository.findTop1ByIdNewsSourceIdOrderByPublicationDateDesc(1);
        
        String foundTitle = found.getTitle();
     
        // then
        assertEquals(title2, foundTitle); // news2's publication date is more recent
    	
    }
}
