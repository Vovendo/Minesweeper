package edu.studiyaTG.minesweeper.service;

import edu.studiyaTG.minesweeper.model.Game;
import edu.studiyaTG.minesweeper.repo.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public Game createGame(Game game) {
        validateGameParams(game);

        List<List<String>> fields = new ArrayList<>();
        for (int i = 0; i < game.getHeight(); i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < game.getWidth(); j++) {
                list.add(" ");
            }
            fields.add(list);
        }
        game.setFieldState(fields);
        game.setGenerateIsCompleted(false);

        return gameRepository.save(game);
    }

    @Override
    public Game makeTurn(String gameId, int row, int col) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElseThrow();

        if (!game.isGenerateIsCompleted()) {
            game.setMinesPosition(doGenerateMinesPosition(game.getHeight(), game.getWidth(), game.getMinesCount(), row * game.getWidth() + col + 1));
            game.setGenerateIsCompleted(true);
        }
        Set<Integer> settledCells = new HashSet<>();
        openCell(game, row, col, settledCells, true);

        if (!game.isGenerateIsCompleted()) {
            openAllCells(game);
            gameRepository.delete(game);
            return game;
        }

        if (game.getCountOfOpenedCells() == game.getHeight() * game.getWidth() - game.getMinesCount()) {
            openAllMinesPositions(game);
            game.setCompleted(true);
            gameRepository.delete(game);
            return game;
        }

        List<List<String>> list = new ArrayList<>();
        for (List<String> stringList : game.getFieldState()) {
            List<String> strs = new ArrayList<>(stringList);
            list.add(strs);
        }
        game.setFieldState(list);

        return gameRepository.save(game);
    }

    private void openAllMinesPositions(Game game) {
        for (Integer mine : game.getMinesPosition()) {
            game.getFieldState().get((mine - 1)/ game.getWidth()).set((mine - 1) % game.getWidth(), "M");
        }
    }

    private void openAllCells(Game game) {
        for (int i = 0; i < game.getFieldState().size(); i++) {
            for (int j = 0; j < game.getFieldState().get(i).size(); j++) {
                game.getFieldState().get(i).set(j, "0");
                if (game.getMinesPosition().contains(i * game.getWidth() + j + 1)) {
                    game.getFieldState().get(i).set(j, "X");
                }
                if (game.getFieldState().get(i).get(j).equals(" ")) {
                    for (int k = -1; k < 2; k++) {
                        for (int m = -1; m < 2; m++) {
                            if (game.getMinesPosition().contains((i * game.getWidth() + j + 1) + (k * game.getWidth()) + m)
                                    && i + k > -1 && i + k < game.getHeight()
                                    && j + m > -1 && j + m < game.getWidth()) {
                                int count = Integer.parseInt(game.getFieldState().get(i).get(j)) + 1;
                                game.getFieldState().get(i).set(j, String.valueOf(count));
                            }
                        }
                    }
                }
            }
        }
    }

    private void validateGameParams(Game game) {
        int width = game.getWidth();
        int height = game.getHeight();
        int minesCount = game.getMinesCount();
        if (height < 2 || height > 30) {
            throw new IllegalArgumentException("ширина поля должна быть не менее 2 и не более 30");
        } else if (width < 2 || width > 30) {
            throw new IllegalArgumentException("высота поля должна быть не менее 2 и не более 30");
        } else if (minesCount < 1 || minesCount > width * height - 1) {
            int maxMinsCount = width * height - 1;
            throw new IllegalArgumentException("количество мин должно быть не менее 1 и не более " + maxMinsCount);
        }
    }

    private Set<Integer> doGenerateMinesPosition(int height, int width, int minesCount, int initCell) {
        Set<Integer> minesPositions = new HashSet<>();
        for (int i = 0; i < minesCount; i++) {
            Random random = new Random();
            int position = random.nextInt(height * width) + 1;
            if (minesPositions.contains(position) || position == initCell) {
                i--;
            } else {
                minesPositions.add(position);
            }
        }

        return minesPositions;
    }

    private void openCell(Game game, int row, int col, Set<Integer> settledCells, boolean firstPass) {
        settledCells.add(row * game.getWidth() + col + 1);
        if (game.getFieldState().get(row).get(col).equals(" ") && !game.getMinesPosition().contains(row * game.getWidth() + col + 1)) {
            game.getFieldState().get(row).set(col, "0");
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (game.getMinesPosition().contains((row * game.getWidth() + col + 1) + (i * game.getWidth()) + j)
                            && row + i > -1 && row + i < game.getHeight()
                            && col + j > -1 && col + j < game.getWidth()) {
                        int count = Integer.parseInt(game.getFieldState().get(row).get(col)) + 1;
                        game.getFieldState().get(row).set(col, String.valueOf(count));
                    }
                }
            }
            if (game.getFieldState().get(row).get(col).equals("0")) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (row + i > -1 && row + i < game.getHeight()
                                && col + j > -1 && col + j < game.getWidth()
                                && !(settledCells.contains((row * game.getWidth() + col + 1) + (i * game.getWidth()) + j))) {
                            openCell(game, row + i, col + j, settledCells, false);
                        }
                    }
                }
            }
            game.setCountOfOpenedCells(game.getCountOfOpenedCells() + 1);
        } else if (game.getMinesPosition().contains(row * game.getWidth() + col + 1)) {
            game.setGenerateIsCompleted(false);
        } else if (firstPass) {
            throw new IllegalArgumentException("эта ячейка уже открыта");
        }
    }
}
