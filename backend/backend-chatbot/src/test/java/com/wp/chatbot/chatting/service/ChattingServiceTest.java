package com.wp.chatbot.chatting.service;

import com.wp.chatbot.chatbot.repository.ChatbotRepository;
import com.wp.chatbot.chatting.dto.KomoranAnalyze;
import com.wp.chatbot.chatting.dto.MessageDto;
import com.wp.chatbot.search.document.SearchChatbot;
import com.wp.chatbot.search.repository.ElasticSearchRepository;
import kr.co.shineware.util.common.model.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.util.StopWatch;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ChattingServiceTest {
    private final KomoranAnalyze komoran = new KomoranAnalyze();
    @Autowired
    ElasticSearchRepository elasticSearchRepository;
    @Autowired
    ChatbotRepository chatbotRepository;
    private StopWatch stopWatch;

    @Test
    void searchByChatbot() {
        stopWatch = new StopWatch("searchByChatbot");

        String question = "고구마 얼마인가요";
        MessageDto message = MessageDto.builder().roomId(1L).message(question).build();

        String query = message.getMessage();
        stopWatch.start("Long type");

        SearchHit<SearchChatbot> searchHit = elasticSearchRepository.searchByQuestion(message);

        stopWatch.stop();

        String returnMessage = "해당 질문에 대해 등록된 답변이 없습니다.";
        if(searchHit.getContent()!=null && searchHit.getScore() > 0.6){
            returnMessage = searchHit.getContent().getAnswer();
        }

//        System.out.println(returnMessage);
        System.out.println(stopWatch.prettyPrint());
        System.out.println("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
    }

    @Test
    void searchByDB() {
        stopWatch = new StopWatch("searchByDB");

        String question = "고구마 얼마인가요";
        MessageDto message = MessageDto.builder().roomId(1L).message(question).build();

        String query = message.getMessage();
        String modelType = "full";

        stopWatch.start("Long type");

        komoran.setUserInput(query, modelType);
        komoran.analyze();
        List<String> list = new ArrayList<>();

        for (Pair<String,String> result : komoran.getQueryResult()){
            if("NN".equals(result.getSecond().substring(0,2))){ //명사만 걸러냄
                System.out.println(result.getFirst());
                list.add(result.getFirst());
            }
        }
        String returnMessage = chatbotRepository.findChatbotByQuestion(message, list);

        stopWatch.stop();
//        System.out.println(returnMessage);

        System.out.println(stopWatch.prettyPrint());
        System.out.println("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
    }
}