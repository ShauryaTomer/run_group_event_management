package com.rungroup.web.mapper;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;

import java.util.stream.Collectors;

import static com.rungroup.web.mapper.EventMapper.mapToEventDto;

public class ClubMapper{
    public static Club mapToClub(ClubDto clubDto) { //clubDto to club mapper
        return Club.builder()
                .id(clubDto.getId())
                .title(clubDto.getTitle())
                .photoUrl(clubDto.getPhotoUrl())
                .createdBy(clubDto.getCreatedBy())
                .content(clubDto.getContent())
                .createdOn(clubDto.getCreatedOn())
                .updatedOn(clubDto.getUpdatedOn())
                .events(clubDto.getEvents().stream()
                        .map(EventMapper::mapToEvent)
                        .toList())
                .build();
    }

    public static ClubDto mapToClubDto(Club club) {
        return ClubDto.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents()
                        .stream()
                        .map(EventMapper::mapToEventDto)
                        .toList())
                .build();
    }
}
