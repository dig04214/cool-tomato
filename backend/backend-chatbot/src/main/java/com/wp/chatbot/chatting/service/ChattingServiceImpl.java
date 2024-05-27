package com.wp.chatbot.chatting.service;

import com.wp.chatbot.chatbot.repository.ChatbotRepository;
import com.wp.chatbot.chatting.dto.KomoranAnalyze;
import com.wp.chatbot.chatting.dto.MessageDto;
import com.wp.chatbot.search.document.SearchChatbot;
import com.wp.chatbot.search.repository.ElasticSearchRepository;
import kr.co.shineware.util.common.model.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChattingServiceImpl implements ChattingService{

    private final KomoranAnalyze komoran = new KomoranAnalyze();
    private final ElasticSearchRepository elasticSearchRepository;
    private final ChatbotRepository chatbotRepository;

    @Override
    public MessageDto searchByChatbot(MessageDto message) {
        String query = message.getMessage();
        SearchHit<SearchChatbot> searchHit = elasticSearchRepository.searchByQuestion(message);

        String returnMessage = "해당 질문에 대해 등록된 답변이 없습니다.";
        log.info(searchHit != null ? searchHit.getScore()+" "+searchHit.getContent() : "검색 결과 없음");

        if(searchHit !=null && searchHit.getContent()!=null && searchHit.getScore() > 1){
            returnMessage = searchHit.getContent().getAnswer();
        }

        MessageDto answer = MessageDto.builder().roomId(message.getRoomId()).message(returnMessage).writer(1L).build();
        return answer;
    }

    @Override
    public MessageDto searchByDB(MessageDto message){
        //TODO : 질문에 대한 답변 찾아서 반환해줘야함
        String query = message.getMessage();
        String modelType = "full";

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
        log.info(returnMessage);
        MessageDto answer = MessageDto.builder().roomId(message.getRoomId()).message(returnMessage).writer(1L).build();
        return answer;
    }
}
