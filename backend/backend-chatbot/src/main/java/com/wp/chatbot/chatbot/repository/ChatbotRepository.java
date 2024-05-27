package com.wp.chatbot.chatbot.repository;

import com.wp.chatbot.chatbot.entity.Chatbot;
import com.wp.chatbot.chatbot.repository.search.ChatbotRepositorySearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotRepository extends JpaRepository<Chatbot,Long>, ChatbotRepositorySearch {
}
