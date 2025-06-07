package com.rungroup.web.controller;

import com.rungroup.web.dto.EventDto;
import com.rungroup.web.models.Event;
import com.rungroup.web.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/{clubId}/new") //need clubId to add it to
    public String getEventForm(@PathVariable("clubId") Long clubId, Model model) {
        EventDto eventDto = new EventDto();
        model.addAttribute("clubId" , clubId);
        model.addAttribute("event" , eventDto);
        return "events-create";
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        List<EventDto> events = eventService.findAllEvents();
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String eventDetail(@PathVariable("eventId") Long eventId, Model model) {
        EventDto eventDto = eventService.findById(eventId);
        model.addAttribute("event" , eventDto);
        return "events-detail";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId")Long eventId, Model model) {
        EventDto eventDto = eventService.findById(eventId);
        model.addAttribute("event" , eventDto);
        return "events-edit";
    }

    @GetMapping("events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }
    @PostMapping("/events/{clubId}/new")
    public String createEvent(@Valid @ModelAttribute("events")EventDto eventDto, //getting eventDto from view
                              BindingResult result,//In SpringMVC when you're using BindingResult or Errors are method arguments, they are expected to be declared immediately after the model attribute, the @RequestBody, or the @RequestPart arguments to which they apply.
                              @PathVariable("clubId") Long clubId,
                              Model model) {
        if(result.hasErrors()) {
            model.addAttribute(model);
            return ("events-create");
        }
        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/" + clubId;
    }

    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@Valid @ModelAttribute("event")EventDto eventDto,
                            BindingResult result,
                            @PathVariable("eventId") Long eventId,
                            Model model) {
        if(result.hasErrors()) {
            model.addAttribute("event" , eventDto);
            System.out.println(result.getAllErrors());
            return "events-edit";
        }
        EventDto eventDto1 = eventService.findById(eventId);
        eventDto.setId(eventId); //why do I need this line?
        eventDto.setClub(eventDto1.getClub()); //to add club that is related to current event, if we don't get club we'll get an error
        eventService.updateEvent(eventDto);
        return "redirect:/events";
    }
}
