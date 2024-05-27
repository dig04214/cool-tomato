package com.wp.chatbot.search.repository;

import com.wp.chatbot.chatting.dto.MessageDto;
import com.wp.chatbot.search.document.SearchChatbot;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomSearchRepositoryImpl implements CustomSearchRepository{
    private final ElasticsearchOperations elasticsearchOperations;
    @Override
    public SearchHit<SearchChatbot> searchByQuestion(MessageDto message) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        MatchQueryBuilder matchQuery1 = QueryBuilders.matchQuery("is_deleted", false);
        MatchQueryBuilder matchQuery2 = QueryBuilders.matchQuery("question", message.getMessage());
        MatchQueryBuilder matchQuery3 = QueryBuilders.matchQuery("room_id",message.getRoomId());
        boolQuery.must(matchQuery1);
        boolQuery.must(matchQuery2);
        boolQuery.must(matchQuery3);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withSort(SortBuilders.fieldSort("_score")
                        .order(SortOrder.DESC))
                .build();

        SearchHit<SearchChatbot> searchHit = elasticsearchOperations.searchOne(searchQuery,SearchChatbot.class);

        return searchHit;
    }
}
