package com.rungroup.web.service.impl;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.mapper.ClubMapper;
import com.rungroup.web.models.Club;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.ClubRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rungroup.web.mapper.ClubMapper.mapToClub;
import static com.rungroup.web.mapper.ClubMapper.mapToClubDto;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository,
                           UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ClubDto> findAllClubs() {
        //code that actually gets data from our database
        List<Club> clubs = clubRepository.findAll();
        //we're mapping clubs to ClubDto because we need to return DTOs, but we are getting Club from our repository.
        return clubs.stream()
                .map(ClubMapper::mapToClubDto)
                .toList();
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user); //doing this after mapping club to clubDto, so we don't have to send the user from frontend, we can just pick the user info from current SecurityContextHolder.
        return clubRepository.save(club);

    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepository.findById(clubId).get(); //.findById() returns an Optional so we have to use .get()
        return mapToClubDto(club); //we send DTO to client
    }

    @Override
    public void updateClub(ClubDto clubDto) { //we receive DTO from client
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club = mapToClub(clubDto); //and send normal object to database
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void delete(long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        return clubs.stream()
                .map(ClubMapper::mapToClubDto)
                .toList();
    }
}