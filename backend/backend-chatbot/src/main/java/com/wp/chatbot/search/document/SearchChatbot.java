package com.wp.chatbot.search.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setting(settingPath = "/static/elastic/elastic-setting.json")
@Mapping(mappingPath = "/static/elastic/chatbot-mappings.json")
@Document(indexName = "chatbot_idx")
public class SearchChatbot {
    @Id
    @Field(name="id",type= FieldType.Long)
    private Long chatbotId;

    @Field(type = FieldType.Text)
    private String question;

    @Field(type = FieldType.Text)
    private String answer;
}
