package edu.studiyaTG.minesweeper.controller;

import edu.studiyaTG.minesweeper.dto.GameDto;
import edu.studiyaTG.minesweeper.mapper.GameMapper;
import edu.studiyaTG.minesweeper.model.Game;
import edu.studiyaTG.minesweeper.service.GameService;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final GameMapper gameMapper;

    @PostMapping("/new")
    public GameDto newGame(@RequestBody GameDto gameDto) {
        return gameMapper.mapToDto(gameService.createGame(gameMapper.mapToEntity(gameDto)));
    }

    @PostMapping("/turn")
    public GameDto makeTurn(@RequestBody TurnRequest request) {
        return gameMapper.mapToDto(gameService.makeTurn(request.getGame_id(), request.getRow(), request.getCol()));
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class TurnRequest {
        private String game_id;
        private int row;
        private int col;
    }
}
