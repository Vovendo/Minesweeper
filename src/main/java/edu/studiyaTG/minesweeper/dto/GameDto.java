package edu.studiyaTG.minesweeper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private UUID game_id;
    private int width;
    private int height;
    private int mines_count;
    private boolean completed;
    private List<List<String>> field_state;
}
