package com.wp.live.domain.broadcast.service;

import com.wp.live.domain.broadcast.dto.controller.request.*;
import com.wp.live.domain.broadcast.dto.controller.response.GetBroadcastInfoResponseDto;
import com.wp.live.domain.broadcast.dto.controller.response.GetBroadcastListResponseDto;

import java.util.Map;

public interface BroadcastService {

    public Long reserveBroadcast(ReservationRequestDto reservation, Long sellerId);
    public Map<String, String> startBroadcast(StartRequestDto start, Long sellerId);
    public void stopBroadcast(StopRequestDto stop, Long sellerId);
    public String participateBroadcast(ParticipationRequestDto participation);
    public GetBroadcastListResponseDto getBroadcastList(int page, int size);
    public void updateBroadcastInfo(UpdateReservationRequestDto info);
    public GetBroadcastInfoResponseDto getBroadcastInfo(Long broadcastId);
    public GetBroadcastListResponseDto getReservationBroadcastListById(Long id, int page, int size);
    public GetBroadcastListResponseDto getStopBroadcastListById(Long id, int page, int size);
    public void deleteBroadcast(Long broadcastId, Long sellerId);

}
