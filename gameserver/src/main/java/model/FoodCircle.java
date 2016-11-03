package model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by User on 09.10.2016.
 * Данный класс должен содержать значения размер, цвет, координаты еды
 */

public class FoodCircle {
    private int size;
    private int x;
    private int y;
    private static final Logger log = LogManager.getLogger(Player.class);
    public FoodCircle(){
        if (log.isInfoEnabled()) {
            log.info("Food in {" + this + "} is created");
        }
    }
}
