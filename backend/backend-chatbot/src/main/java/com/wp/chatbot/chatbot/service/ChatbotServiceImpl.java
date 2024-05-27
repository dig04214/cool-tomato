package com.wp.chatbot.chatbot.service;

import com.wp.chatbot.chatbot.dto.request.ChatbotCreateRequest;
import com.wp.chatbot.chatbot.dto.request.ChatbotSearchRequest;
import com.wp.chatbot.chatbot.dto.request.ChatbotUpdateRequest;
import com.wp.chatbot.chatbot.dto.response.ChatbotResponse;
import com.wp.chatbot.chatbot.entity.Chatbot;
import com.wp.chatbot.chatbot.repository.ChatbotRepository;
import com.wp.chatbot.global.common.code.ErrorCode;
import com.wp.chatbot.global.exception.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService{

    private final ChatbotRepository chatbotRepository;

    @Override
    public Map<String, Object> search(ChatbotSearchRequest request) {
        Page<ChatbotResponse> result = chatbotRepository.search(request);

        Map<String,Object> map = new HashMap<>();
        map.put("list",result.getContent());
        map.put("totalSize",result.getTotalElements());
        return map;
    }

    @Override
    public void save(ChatbotCreateRequest request,Long userId) {
        Chatbot chatbot = Chatbot.builder()
                .roomId(request.getRoomId())
                .sellerId(userId)
                .question(request.getQuestion())
                .answer(request.getAnswer()).build();
        try {
            chatbotRepository.save(chatbot);
        }catch (Exception e){
            throw new BusinessExceptionHandler("챗봇 등록 중에 에러가 발생했습니다.", ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void update(ChatbotUpdateRequest request,Long userId) {
        Optional<Chatbot> result = chatbotRepository.findById(request.getChatbotId());

        result.orElseThrow(()->
                new BusinessExceptionHandler("해당 아이디로 조회된 챗봇이 없습니다.",ErrorCode.BAD_REQUEST_ERROR));
        
        try {
            Chatbot chatbot = result.get();
            boolean equals = userId.equals(chatbot.getSellerId());
            //판매자 아이디랑 같은지 확인
            if (!equals) {
                throw new Exception();
            }
            chatbot.change(request);

            chatbotRepository.save(chatbot);
        }catch (Exception e){
            throw new BusinessExceptionHandler("챗봇 질의응답 수정에 실패했습니다", ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void delete(Long chatbotId,Long userId) {
        Optional<Chatbot> result = chatbotRepository.findById(chatbotId);

        result.orElseThrow(()->
                new BusinessExceptionHandler("해당 아이디로 조회된 챗봇이 없습니다.",ErrorCode.BAD_REQUEST_ERROR));

        try {
            Chatbot chatbot = result.get();
            boolean equals = userId.equals(chatbot.getSellerId());
            //판매자 아이디랑 같은지 확인
            if (!equals) {
                throw new Exception();
            }
            chatbot.delete();
            chatbotRepository.save(chatbot);
        }catch (Exception e){
            throw new BusinessExceptionHandler("챗봇 질의응답 삭제 중 에러가 발생했습니다",ErrorCode.DELETE_ERROR);
        }
    }
}
