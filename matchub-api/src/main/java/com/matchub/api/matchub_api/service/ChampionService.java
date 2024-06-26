package com.matchub.api.matchub_api.service;

import com.matchub.api.matchub_api.domain.Champion;
import com.matchub.api.matchub_api.dto.ChampionDTODetails;
import com.matchub.api.matchub_api.repository.ChampionRepository;
import com.matchub.api.matchub_api.service.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChampionService {
    private final ChampionRepository championRepository;
    private final ModelMapper modelMapper;

    public Champion findDomainById(Long id) {
        // Get information, check existence, and return the domain object
        return championRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Object Not Found. Id: " + id + ", Type: " + Champion.class.getName()));
    }

    public ChampionDTODetails findById(Long id) {
        // Get information, check existence, and convert to DTO in a functional style
        return championRepository.findById(id)
                .map(champion -> {
                    // Converter
                    ChampionDTODetails championDTODetails = new ChampionDTODetails();
                    modelMapper.map(champion, championDTODetails);
                    return championDTODetails;
                })
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Object Not Found. Id: " + id + ", Type: " + Champion.class.getName()));
    }

    public List<ChampionDTODetails> findAll() {
        // Retrieve all champions
        List<Champion> champions = championRepository.findAll();

        // Convert list of Champion domains to list of ChampionDTODetails
        return champions.stream()
                .map(champion -> {
                    ChampionDTODetails championDTODetails = new ChampionDTODetails();
                    modelMapper.map(champion, championDTODetails);
                    return championDTODetails;
                })
                .collect(Collectors.toList());
    }
}
