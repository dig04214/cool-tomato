package com.wp.chatbot.chatbot.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wp.chatbot.chatbot.dto.request.ChatbotSearchRequest;
import com.wp.chatbot.chatbot.dto.response.ChatbotResponse;
import com.wp.chatbot.chatbot.entity.Chatbot;
import com.wp.chatbot.chatbot.entity.QChatbot;
import com.wp.chatbot.chatting.dto.MessageDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class ChatbotRepositorySearchImpl extends QuerydslRepositorySupport implements ChatbotRepositorySearch{

    private final JPAQueryFactory queryFactory;

    public ChatbotRepositorySearchImpl(JPAQueryFactory queryFactory){
        super(Chatbot.class);
        this.queryFactory = queryFactory;
    }

    @Override
    @Transactional
    public Page<ChatbotResponse> search(ChatbotSearchRequest request){
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        QChatbot chatbot = QChatbot.chatbot;

        List<ChatbotResponse> result = queryFactory.select(Projections.bean(ChatbotResponse.class,
                        chatbot.chatbotId,
                        chatbot.roomId,
                        chatbot.question,
                        chatbot.answer,
                        chatbot.registerDate
                ))
                .from(chatbot)
                .where(chatbot.sellerId.eq(request.getSellerId()))
                .where(chatbot.isDeleted.eq(false))
                .orderBy(chatbot.roomId.desc(),chatbot.registerDate.desc()).fetch();

        JPQLQuery<Long> countQuery = queryFactory.select(chatbot.count())
                .from(chatbot)
                .where(chatbot.sellerId.eq(request.getSellerId()))
                .where(chatbot.isDeleted.eq(false));

        return PageableExecutionUtils.getPage(result,pageable,countQuery::fetchOne);
    }

    @Override
    @Transactional
    public String findChatbotByQuestion(MessageDto message,List<String> list){
        QChatbot chatbot = QChatbot.chatbot;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String question : list) {
            booleanBuilder.or(chatbot.question.contains(question));
        }

        booleanBuilder.and(chatbot.roomId.eq(message.getRoomId()));

        return  queryFactory.select(
                        chatbot.answer
                )
                .from(chatbot)
                .where(booleanBuilder)
                .where(chatbot.isDeleted.eq(false))
                .fetchFirst();
    }
}
