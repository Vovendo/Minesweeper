package edu.studiyaTG.minesweeper.model;

import edu.studiyaTG.minesweeper.converter.ListListStringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private int width;
    private int height;
    private int minesCount;
    private int countOfOpenedCells;
    private boolean completed;
    private boolean generateIsCompleted;
    @Convert(converter = ListListStringConverter.class)
    @Column(columnDefinition = "text")
    private List<List<String>> fieldState;
    @ElementCollection
    @CollectionTable(name = "mines_position", joinColumns = @JoinColumn(name = "game_id"))
    private Set<Integer> minesPosition;
}
