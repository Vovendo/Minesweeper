package edu.studiyaTG.minesweeper.repo;

import edu.studiyaTG.minesweeper.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(UUID id);
}
