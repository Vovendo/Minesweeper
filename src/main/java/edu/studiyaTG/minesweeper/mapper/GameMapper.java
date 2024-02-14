package edu.studiyaTG.minesweeper.mapper;

import edu.studiyaTG.minesweeper.dto.GameDto;
import edu.studiyaTG.minesweeper.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDto mapToDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setGame_id(game.getId());
        gameDto.setHeight(game.getHeight());
        gameDto.setWidth(game.getWidth());
        gameDto.setField_state(game.getFieldState());
        gameDto.setMines_count(game.getMinesCount());
        gameDto.setCompleted(game.isCompleted());
        return gameDto;
    }

    public Game mapToEntity(GameDto gameDto) {
        Game game = new Game();
        game.setHeight(gameDto.getHeight());
        game.setWidth(gameDto.getWidth());
        game.setMinesCount(gameDto.getMines_count());

        return game;
    }
}
