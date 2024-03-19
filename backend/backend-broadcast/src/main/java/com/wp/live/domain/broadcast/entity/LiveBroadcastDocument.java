package com.wp.live.domain.broadcast.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "live_broadcast")
@Mapping(mappingPath = "elastic/livebroadcast-mapping.json")
@Setting(settingPath = "elastic/livebroadcast-setting.json")
public class LiveBroadcastDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(name = "broadcast_title", type=FieldType.Text)
    private String broadcastTitle;

    @Field(name = "is_deleted", type=FieldType.Text)
    private Boolean isDeleted;
}
