package com.rungroup.web.controller;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;
import com.rungroup.web.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClubController {
    private final ClubService clubService;

    @Autowired
    public ClubController (ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) { //model allows us to put data on the webpage
        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs" , clubs); //attaching clubs list to clubs attribute so our webpage can access it.
        return "clubs-list";
    }

    @GetMapping("/clubs/new") //
    public String createClubForm(Model model) {
        Club club = new Club(); //so we create an actual model to bring to the view
        model.addAttribute("club" , club);
        return "clubs-create";
    }

    @GetMapping("/clubs/{clubId}") //we need this id to get club we want
    public String clubDetail(@PathVariable("clubId") long clubId, Model model) {
        ClubDto clubDto = clubService.findClubById(clubId); //sending club to client, that's why using clubDto
        model.addAttribute("club", clubDto); //to get this club to webpage
        return "clubs-detail";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") long clubId, Model model) {
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        return "clubs-edit";
    }

    @GetMapping("/clubs/{clubId}/delete") //Delete method
    public String deleteClub(@PathVariable("clubId") long clubId) {
        clubService.delete(clubId);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/search")
    public String searchClubs(@RequestParam(value = "query") String query, Model model) {
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs", clubs);
        return "clubs-list"; //we are going to return the same view, but only inject the clubs in the view returned by searchClubs method
    }

    @PostMapping("/clubs/new")
    public String createClub(@Valid @ModelAttribute("club") ClubDto clubDto,
                           BindingResult result,
                           Model model) {
        if(result.hasErrors()) {
            model.addAttribute(model); //This approach explicitly adds the clubDto object with the key "club" to the model when there are validation errors. This allows the view to pre-populate the form fields with the user's previous input, improving user experience.
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        System.out.println(clubDto);
        return("redirect:/clubs");
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Long clubId
            , @Valid @ModelAttribute("club") ClubDto clubDto
            , BindingResult result) { //path variable matches name in path variable to what to fill in clubId and @ModelAttribute associates "club" named attribute associated to model in the above request when @GetMapping was called for the form. When get was called on /edit, get function sends an empty club to the view, it gets filled by user using the form in view and when form calls post on its endpoint that model is used here. @Valid to validate ClubDto
        if(result.hasErrors()) {
            return "clubs-edit";
        }
        clubDto.setId(clubId);
        clubService.updateClub(clubDto);
        return "redirect:/clubs"; //sending user back to all clubs, so he can view his changes
    }
}
