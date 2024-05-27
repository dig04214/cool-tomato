package com.wp.live.domain.broadcast.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "user")
@Mapping(mappingPath = "elastic/user-mapping.json")
@Setting(settingPath = "elastic/user-setting.json")
public class UserDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(name = "nickname", type=FieldType.Text)
    private String nickname;
}
