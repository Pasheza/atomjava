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
 * Данный класс должен содержать значение размера карты
 */
public class Map {
    private int size_x;
    private int size_y;
    private static final Logger log = LogManager.getLogger(Player.class);
    public Map(){
        int size_x;
        int size_y;
        if (log.isInfoEnabled()) {
            log.info("Map in {" + this + "} is created");
        }
    }
}
