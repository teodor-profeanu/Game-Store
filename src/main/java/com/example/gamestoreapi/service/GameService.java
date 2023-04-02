package com.example.gamestoreapi.service;

import com.example.gamestoreapi.GlobalTags;
import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Game;
import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.repository.GameRepo;
import com.example.gamestoreapi.repository.PermissionRepo;
import com.example.gamestoreapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Class for managing the Permission table, initializes the table with the 3 types of user ADMIN, DEV and USER.
 */
@Service
@AllArgsConstructor
public class GameService {
    private final GameRepo gameRepo;
    private final UserRepo userRepo;
    private final PermissionRepo permissionRepo;

    public DTO<Boolean> add(String name, Integer devId, float price, String iconURL, String description){
        Optional<User> optionalDev = userRepo.findById(devId);
        int devPermissionId = permissionRepo.findByName(GlobalTags.DEV).get().getId();
        int adminPermissionId = permissionRepo.findByName(GlobalTags.ADMIN).get().getId();
        if(optionalDev.isEmpty() || optionalDev.get().getPermissionId() != devPermissionId && optionalDev.get().getPermissionId() != adminPermissionId)
            return new DTO<Boolean>(403, "Permission denied for action.", false);
        if(name.length()<3)
            return new DTO<Boolean>(400, "Name has to be at least 3 characters long.", false);

        Game game = new Game(0, name, devId, new Date(System.currentTimeMillis()), price, 0, iconURL, description);
        gameRepo.save(game);
        return new DTO<Boolean>(200, "Action successful.", true);
    }

    public DTO<List<Game>> filter(int popularity, int rating, int date, int price, int priceMin, int priceMax, boolean discount, String tagIds){
        Iterable<Game> gameIterable = gameRepo.findAll();
        List<Game> games = new ArrayList<>();
        gameIterable.forEach(games::add);
        games = games.stream().filter(n -> n.getPriceEuro()>=priceMin && n.getPriceEuro()<=priceMax).collect(Collectors.toList());

        if(price == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g1.getPriceEuro() - g2.getPriceEuro())*100));
        else if(price == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g2.getPriceEuro() - g1.getPriceEuro())*100));
        return new DTO<>(200, "Games found.", games);
    }
}
