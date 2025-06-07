package com.rungroup.web.service.impl;

import com.rungroup.web.dto.EventDto;
import com.rungroup.web.mapper.EventMapper;
import com.rungroup.web.models.Club;
import com.rungroup.web.models.Event;
import com.rungroup.web.repository.ClubRepository;
import com.rungroup.web.repository.EventRepository;
import com.rungroup.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.rungroup.web.mapper.EventMapper.mapToEvent;
import static com.rungroup.web.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {
//Bring in both our club and event repository
    private final ClubRepository clubRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(ClubRepository clubRepository, EventRepository eventRepository) {
        this.clubRepository = clubRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) { //getting DTO from user
        //To create an event we'll have to tie it to club entity using id of that club, to establish one to many relationship
        Club club = clubRepository.findById(clubId).get(); //.get because optional
        Event event = mapToEvent(eventDto);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map((EventMapper::mapToEventDto))
                .toList();
    }

    @Override
    public EventDto findById(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        Event event = mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
