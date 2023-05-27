package com.example.gamestoreapi.service;

import com.example.gamestoreapi.repository.PermissionRepo;
import com.example.gamestoreapi.repository.ReviewRepo;
import com.example.gamestoreapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;

}
