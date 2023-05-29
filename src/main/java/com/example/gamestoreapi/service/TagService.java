package com.example.gamestoreapi.service;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Tag;
import com.example.gamestoreapi.repository.TagRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * A service class for the operations on the Tag table
 */
@Service
@AllArgsConstructor
public class TagService {
    private final TagRepo tagRepo;

    public DTO<Iterable<Tag>> getAll(){
        return new DTO<Iterable<Tag>> ( 200, "Tags found",tagRepo.findAll());
    }
}
