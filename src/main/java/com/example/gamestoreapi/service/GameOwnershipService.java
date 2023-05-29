package com.example.gamestoreapi.service;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Game;
import com.example.gamestoreapi.model.GameOwnership;
import com.example.gamestoreapi.repository.GameOwnershipRepo;
import com.example.gamestoreapi.repository.GameRepo;
import com.example.gamestoreapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

/**
 * Service Class for the logic behind buying and playing games
 */
@Service
@AllArgsConstructor
public class GameOwnershipService {
    private final GameOwnershipRepo gameOwnershipRepo;
    private final GameRepo gameRepo;
    private final UserRepo userRepo;

    /**
     * Method that simulates buying a game
     * @return True or False
     */
    public DTO<Boolean> buy(Integer userId, Integer gameId){
        if(userRepo.findById(userId).isEmpty())
            return new DTO<>(404, "User not found.", Boolean.FALSE);
        Optional<GameOwnership> game = gameOwnershipRepo.findByGameIdAndUserId(gameId, userId);
        if(game.isPresent())
            return new DTO<>(400, "Game already owned.", Boolean.FALSE);

        Optional<Game> game1 = gameRepo.findById(gameId);
        if(game1.isEmpty())
            return new DTO<>(404, "Game not found.", Boolean.FALSE);
        game1.get().setSales(game1.get().getSales()+1);
        gameRepo.save(game1.get());

        gameOwnershipRepo.save(new GameOwnership(0, gameId, userId, 0, null));
        return new DTO<>(200, "Game bought.", Boolean.TRUE);
    }

    /**
     * This method simulates a certain time played in an application, and updates the DB accordingly.
     * @param hours the amount of time to be simulated
     * @return True or False
     */
    public DTO<Boolean> play(Integer userId, Integer gameId, Float hours){
        Optional<GameOwnership> gameOwnership = gameOwnershipRepo.findByGameIdAndUserId(gameId, userId);
        if(gameOwnership.isEmpty())
            return new DTO<>(400, "Game not owned.", Boolean.FALSE);
        GameOwnership game = gameOwnership.get();
        game.setHoursPlayed(game.getHoursPlayed() + hours);
        game.setLastPlayed(new Date(System.currentTimeMillis()));
        gameOwnershipRepo.save(game);
        return new DTO<>(200, "Game played.", Boolean.TRUE);
    }
}
