package com.sky7th.devtimeline.core.domain.chattingRoom.domain;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ChattingRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String imageUrl;

    private Integer priority;

    private String name;

    @Builder
    public ChattingRoom(Long id, String roomId, String imageUrl, Integer priority, String name) {
        this.id = id;
        this.roomId = roomId;
        this.imageUrl = imageUrl;
        this.priority = priority;
        this.name = name;
    }
}