package com.example.gamestoreapi.service;

import com.example.gamestoreapi.helper.GlobalTags;
import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Discount;
import com.example.gamestoreapi.model.Game;
import com.example.gamestoreapi.model.Review;
import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for managing the Permission table, initializes the table with the 3 types of user ADMIN, DEV and USER.
 */
@Service
@AllArgsConstructor
public class GameService {
    private final GameRepo gameRepo;
    private final UserRepo userRepo;
    private final PermissionRepo permissionRepo;
    private final DiscountRepo discountRepo;
    private final ReviewRepo reviewRepo;

    /**
     * Adds a new Game to the DB.
     * @return A DTO containing the status, message, and a Boolean for whether the Game was addeed successfully.
     */
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

    /**
     * Sets the rating, nrOfReviews and discount attributes of every Game from the given list
     */
    public void setExtraAtt(List<Game> games){
        HashMap<Integer, Integer> reviewSums = new HashMap<>();
        HashMap<Integer, Integer> reviewNr = new HashMap<>();
        HashMap<Integer, Integer> discounts = new HashMap<>();
        Iterable<Review> reviews = reviewRepo.findAll();
        for(Review review : reviews){
            Integer gameId = review.getGameId();
            if(reviewSums.containsKey(gameId)) {
                reviewSums.replace(gameId, reviewSums.get(gameId) + review.getRating());
                reviewNr.replace(gameId, reviewNr.get(gameId) + 1);
            }
            else{
                reviewSums.put(gameId, review.getRating());
                reviewNr.put(gameId, 1);
            }
        }

        for(Discount discount : discountRepo.findAll()){
            if(discount.getDiscountEnd().getTime()<System.currentTimeMillis()){
                discounts.putIfAbsent(discount.getGameId(), discount.getDiscountPercent());
            }
        }

        for(Game game : games){
            if(reviewSums.containsKey(game.getId())) {
                int nrOfReviews = reviewNr.get(game.getId());
                int ratingSum = reviewSums.get(game.getId());
                game.setNrOfReviews(nrOfReviews);
                game.setRating((float) ratingSum / nrOfReviews);
            }
            if(discounts.containsKey(game.getId())){
                game.setDiscountPercent(discounts.get(game.getId()));
            }
        }
    }

    /**
     * Filters the list of games based on the given paramers
     * @param keywords Keeps games that contain any word from this String in their name
     * @param priceMin Keeps games that have a price higher than this
     * @param priceMax Keeps games that have a price lower than this
     * @param discount Keeps games that have a discount
     * @param tagIds Keeps games that contain any of these tags //TODO
     * @return The processed list
     */
    public List<Game> filter(List<Game> games,String keywords, int priceMin, int priceMax, boolean discount, String tagIds){
        games = games.stream().filter(n -> n.getPriceEuro()>=priceMin && n.getPriceEuro()<=priceMax).collect(Collectors.toList());

        if(keywords.length()>0)
            games = games.stream().filter(n -> {
                String[] words = keywords.split("[- _0-9]+");
                boolean contains = false;
                for(String word : words){
                    if(word.length()>0 && n.getName().toLowerCase().contains(word.toLowerCase()))
                        contains = true;
                }
                return contains;
            }).collect(Collectors.toList());

        if(discount){
            games = games.stream().filter(n -> n.getDiscountPercent()!=0).collect(Collectors.toList());
        }
        return games;
    }

    /**
     * Sorts the given game list based on one the parameters, and whether it's value is ASCENDING or DESCENDING.
     * @param popularity Sorts based on number of sales
     * @param rating Sorts based on the rating
     * @param reviews Sorts based on number of reviews
     * @param date Sorts based on realese date
     * @param price Sorts based on price
     * @return The processed list
     */
    public List<Game> sort(List<Game> games, int popularity, int rating, int reviews, int date, int price){
        if(price == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g1.getPriceEuro() - g2.getPriceEuro())*100));
        else if(price == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g2.getPriceEuro() - g1.getPriceEuro())*100));
        else if(date == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g1.getReleaseDate().getTime() - g2.getReleaseDate().getTime())/86400000));
        else if(date == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g2.getReleaseDate().getTime() - g1.getReleaseDate().getTime())/86400000));
        else if(popularity == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> g1.getSales() - g2.getSales());
        else if(popularity == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> g2.getSales() - g1.getSales());
        else if(rating == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g1.getRating() - g2.getRating())*100));
        else if(rating == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g2.getRating() - g1.getRating())*100));
        else if(reviews == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> g1.getNrOfReviews() - g2.getNrOfReviews());
        else if(reviews == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> g2.getNrOfReviews() - g1.getNrOfReviews());
        return games;
    }

    /**
     * Sorts and filters the list of all games in the DB based on the given paramters
     * @return A DTO containing the status, message and processed list
     */
    public DTO<List<Game>> search(int popularity, int rating, int reviews, int date, int price, String keywords, int priceMin, int priceMax, boolean discount, String tagIds){
        Iterable<Game> gameIterable = gameRepo.findAll();
        List<Game> games = new ArrayList<>();
        gameIterable.forEach(games::add);
        setExtraAtt(games);

        games = filter(games, keywords, priceMin, priceMax, discount, tagIds);
        games = sort(games, popularity, rating, reviews, date, price);

        return new DTO<>(200, "Games found.", games);
    }

    /**
     * Retrieves the game with the given ID
     * @return A DTO with the status, message and Game object
     */
    public DTO<Game> get(int gameId){
        Optional<Game> optionalGame = gameRepo.findById(gameId);
        if(optionalGame.isEmpty())
            return new DTO<Game>(404, "Game not found.", null);
        return new DTO<Game>(200, "Game found.", optionalGame.get());
    }

    /**
     * This method retrieves the game with the given ID, it makes the necessary changes, and then it saves it back in the DB.
     * @return Data transfer object with the status, message and a Boolean value
     */
    public DTO<Boolean> edit(int gameId, String name, float price, String iconURL, String description){
        Optional<Game> optionalGame = gameRepo.findById(gameId);
        if(optionalGame.isEmpty())
            return new DTO<Boolean>(404, "Game not found.", false);
        Game game = optionalGame.get();

        game.setName(name);
        game.setPriceEuro(price);
        game.setIconURL(iconURL);
        game.setDescription(description);

        gameRepo.save(game);
        return new DTO<Boolean>(200, "Edit successful.", true);
    }

    /**
     * This method retrieves the game with the given ID, and then it deletes it from the DB.
     * @return Data transfer object with the status, message and a Boolean value
     */
    public DTO<Boolean> delete(int gameId){
        Optional<Game> optionalGame = gameRepo.findById(gameId);
        if(optionalGame.isEmpty())
            return new DTO<Boolean>(404, "Game not found.", false);
        gameRepo.delete(optionalGame.get());
        return new DTO<Boolean>(200, "Delete successful.", true);
    }
}
