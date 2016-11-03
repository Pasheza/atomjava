package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by User on 09.10.2016.
 * В данном классе необходимо реализвать размер ловушки, цвет  и координаты
 */
public class Trap {
    private int size;
    private int x;
    private int y;
    private static final Logger log = LogManager.getLogger(Player.class);
    public Trap(){
        if (log.isInfoEnabled()) {
            log.info("Trap in {" + this + "} is created");
        }
    }

}
