package com.axonkafka.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuceneIndexConfig {

    @Bean
    public LuceneIndexServiceBean luceneIndexServiceBean(EntityManagerFactory EntityManagerFactory){
        LuceneIndexServiceBean luceneIndexServiceBean = new LuceneIndexServiceBean(EntityManagerFactory);
        luceneIndexServiceBean.triggerIndexing();
        return luceneIndexServiceBean;
    }
    
     class LuceneIndexServiceBean {

        private FullTextEntityManager fullTextEntityManager;

        public LuceneIndexServiceBean(EntityManagerFactory entityManagerFactory){
            fullTextEntityManager = Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
        }

        public void triggerIndexing() {
            try {
                fullTextEntityManager.createIndexer().startAndWait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}