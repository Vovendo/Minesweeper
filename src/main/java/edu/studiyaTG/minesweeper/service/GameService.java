package edu.studiyaTG.minesweeper.service;

import edu.studiyaTG.minesweeper.model.Game;

public interface GameService {
    Game createGame(Game game);
    Game makeTurn(String gameId, int row, int col);
}
