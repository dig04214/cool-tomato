package com.wp.chatbot.search.repository;

import com.wp.chatbot.search.document.SearchChatbot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepository extends ElasticsearchRepository<SearchChatbot,Long>, CustomSearchRepository  {
}
