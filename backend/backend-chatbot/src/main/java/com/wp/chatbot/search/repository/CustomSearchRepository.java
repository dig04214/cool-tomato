package com.wp.chatbot.search.repository;

import com.wp.chatbot.chatting.dto.MessageDto;
import com.wp.chatbot.search.document.SearchChatbot;
import org.springframework.data.elasticsearch.core.SearchHit;

public interface CustomSearchRepository {
    SearchHit<SearchChatbot> searchByQuestion(MessageDto message);
}