package com.example.gamestoreapi.service;

import com.example.gamestoreapi.helper.GlobalTags;
import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.*;
import com.example.gamestoreapi.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.regex.Pattern;
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
    private final GameOwnershipRepo gameOwnershipRepo;
    private final TagRepo tagRepo;
    private final GameTagsRepo gameTagsRepo;
    private final GameImagesRepo gameImagesRepo;

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
        if(!Pattern.compile("^[a-zA-Z0-9._ -]+$").matcher(name).matches() || name.length()<3)
            return new DTO<Boolean>(400, "Invalid name (must contain only alphanumerical characters and must be at least 3 characters long).", false);

        Game game = new Game(0, name, devId, new Date(System.currentTimeMillis()), price, 0, iconURL, description);
        gameRepo.save(game);
        return new DTO<Boolean>(200, "Action successful.", true);
    }

    /**
     * Sets the rating, nrOfReviews, tags and discount attributes of every Game from the given list
     */
    public void setExtraAtt(List<Game> games, Integer userId){
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
            if(discount.getDiscountEnd().getTime()>System.currentTimeMillis()){
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
            Iterable<GameOwnership> gameOwnerships = gameOwnershipRepo.findAllByGameIdAndUserId(game.getId(), userId);
            for(GameOwnership ownership : gameOwnerships){
                game.setLastPlayed(ownership.getLastPlayed());
                game.setOwned(true);
                game.setHoursPlayed(ownership.getHoursPlayed());
            }

            Iterable<GameTags> gameTags = gameTagsRepo.findAllByGameId(game.getId());
            List<Tag> tags = new ArrayList<>();
            for(GameTags gameTag : gameTags){
                tags.add(tagRepo.findById(gameTag.getTagId()).get());
            }
            game.setTags(tags);

            Iterable<GameImages> gameImages = gameImagesRepo.findAllByGameId(game.getId());
            game.setImages(gameImages);
        }
    }

    /**
     * Filters the list of games based on the given paramers
     * @param keywords Keeps games that contain any word from this String in their name
     * @param priceMin Keeps games that have a price higher than this
     * @param priceMax Keeps games that have a price lower than this
     * @param discount Keeps games that have a discount
     * @return The processed list
     */
    public List<Game> filter(List<Game> games,String keywords, float priceMin, float priceMax, boolean discount, int tagId){
        games = games.stream().filter(n -> (n.getPriceEuro() * (100.0-n.getDiscountPercent())/100.0)>=priceMin && (n.getPriceEuro()* (100.0-n.getDiscountPercent())/100.0)<=priceMax).collect(Collectors.toList());

        if(keywords.length()>0)
            games = games.stream().filter(n -> {
                String[] words = keywords.split("[- _0-9]+");
                boolean contains = false;
                for(String word : words){
                    if (word.length() > 0 && n.getName().toLowerCase().contains(word.toLowerCase())) {
                        contains = true;
                        break;
                    }
                }
                return contains;
            }).collect(Collectors.toList());

        if(tagId != 0){
            games = games.stream().filter(n -> {
                for(Tag tag : n.getTags()){
                    if(tag.getId() == tagId)
                        return true;
                }

                return false;
            }).collect(Collectors.toList());
        }

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
     * @param date Sorts based on release date
     * @param price Sorts based on price
     * @param trending Sorts based on which games are trending
     * @return The processed list
     */
    public List<Game> sort(List<Game> games, int popularity, int rating, int reviews, int date, int price, int trending){
        if(price == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> (int)(((g1.getPriceEuro() * (100.0-g1.getDiscountPercent())/100.0) - (g2.getPriceEuro() * (100.0-g2.getDiscountPercent())/100.0))*100));
        else if(price == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> (int)(((g2.getPriceEuro() * (100.0-g2.getDiscountPercent())/100.0) - (g1.getPriceEuro() * (100.0-g1.getDiscountPercent())/100.0))*100));
        else if(date == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g1.getReleaseDate().getTime() - g2.getReleaseDate().getTime())/86400000));
        else if(date == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g2.getReleaseDate().getTime() - g1.getReleaseDate().getTime())/86400000));
        else if(popularity == GlobalTags.ASCENDING)
            Collections.sort(games, Comparator.comparingInt(Game::getSales));
        else if(popularity == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> g2.getSales() - g1.getSales());
        else if(rating == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g1.getRating() - g2.getRating())*100));
        else if(rating == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g2.getRating() - g1.getRating())*100));
        else if(reviews == GlobalTags.ASCENDING)
            Collections.sort(games, Comparator.comparingInt(Game::getNrOfReviews));
        else if(reviews == GlobalTags.DESCENDING)
            Collections.sort(games, (g1, g2) -> g2.getNrOfReviews() - g1.getNrOfReviews());
        else if(trending == GlobalTags.ASCENDING)
            Collections.sort(games, (g1, g2) -> (int)((g2.getRating() + g2.getDiscountPercent() - g1.getRating() - g1.getDiscountPercent())*100));
        return games;
    }

    /**
     * Sorts and filters the list of all games in the DB based on the given paramters
     * @return A DTO containing the status, message and processed list
     */
    public DTO<List<Game>> search(Integer userId, int popularity, int rating, int reviews, int date, int price, int trending, String keywords, float priceMin, float priceMax, boolean discount, Integer tagId){
        Iterable<Game> gameIterable = gameRepo.findAll();
        List<Game> games = new ArrayList<>();
        gameIterable.forEach(games::add);
        setExtraAtt(games, userId);
        games = filter(games, keywords, priceMin, priceMax, discount, tagId);
        games = sort(games, popularity, rating, reviews, date, price, trending);

        return new DTO<>(200, "Games found.", games);
    }

    /**
     * Retrieves the game with the given ID
     * @return A DTO with the status, message and Game object
     */
    public DTO<Game> get(int gameId, int userId){
        Optional<Game> optionalGame = gameRepo.findById(gameId);
        if(optionalGame.isEmpty())
            return new DTO<Game>(404, "Game not found.", null);
        List<Game> games = new ArrayList<>();
        games.add(optionalGame.get());
        setExtraAtt(games, userId);
        return new DTO<Game>(200, "Game found.", games.get(0));
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

    public List<Game> getGamesByUserId(Integer userId){
        Iterable<GameOwnership> ownedGames = gameOwnershipRepo.findAllByUserId(userId);
        List<Game> games = new ArrayList<>();
        for(GameOwnership ownedGame:ownedGames){
            Optional<Game> game = gameRepo.findById(ownedGame.getGameId());
            if(game.isPresent())
                games.add(game.get());
        }

        setExtraAtt(games, userId);

        return games;
    }

    /**
     * Returns a list of games made specifically for each user
     * @param userId User's ID
     * @return A DTO containing the List
     */
    public DTO<List<Game>> featured(Integer userId){
        Iterable<GameOwnership> ownedGames = gameOwnershipRepo.findAllByUserId(userId);
        Iterable<Game> allGamesIter = gameRepo.findAll();
        List<Game> allGames = new ArrayList<>();
        List<Game> games = new ArrayList<>();
        for(Game game : allGamesIter)
            allGames.add(game);

        setExtraAtt(allGames, userId);

        Set<Tag> ownedTags = new HashSet<>();
        for(GameOwnership ownedGame : ownedGames){
            Game myGame = gameRepo.findById(ownedGame.getGameId()).get();
            ownedTags.addAll(myGame.getTags());
        }

        for(Game game : allGames){
            if(!game.isOwned()){
                games.add(game);
                for(Tag tag : game.getTags()){
                    if(ownedTags.contains(tag))
                        game.setTagsInCommon(game.getTagsInCommon()+1);
                }
            }
        }
        Collections.sort(games, (g1, g2) -> (int)((g2.getTagsInCommon() - g1.getTagsInCommon())*100000 + g2.getSales()/100000 - g1.getSales()/100000));
        return new DTO<List<Game>>(200, "Games found", games);
    }
}
