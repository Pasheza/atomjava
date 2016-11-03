package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static model.GameConstants.MAX_FOOD_CIRCLES_ON_FIELD;
import static model.GameConstants.MAX_TRAPS_ON_FIELD;
/**
 * Created by User on 09.10.2016.
 * Создание экземпляра новой игры
 */
public class Game implements GameSession {
    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);
    private Player player;
    //Создание списков для еды и ловушек, а также карты
    private List<Trap> traps = new ArrayList<>();
    private List<FoodCircle> foodCircles = new ArrayList<>();
    private Map map = new Map();

    public Game() {
        if (log.isInfoEnabled()) {
            log.info("Session{" + this + "} is created");
        }


        for (int i = 1; i <= MAX_FOOD_CIRCLES_ON_FIELD; i++) {
            FoodCircle foodCircle = new FoodCircle();
            foodCircles.add(foodCircle);
            if (log.isInfoEnabled()) {
                log.info(foodCircle + " added to " + this);
            }

        }
        for (int i = 1; i <= MAX_TRAPS_ON_FIELD; i++) {
            Trap trap = new Trap();
            traps.add(trap);
            if (log.isInfoEnabled()) {
                log.info(trap + " added to " + this);
            }
        }

    }

    @Override
    public void join(Player player) {
        this.player = player;
        if (log.isInfoEnabled()) {
            log.info(player + " added to " + this);
        }
    }
}
