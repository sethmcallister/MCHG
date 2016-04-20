package xyz.mcallister.seth.HG;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sethm on 06/04/2016.
 */
public class GameState
{
    //whether or not the game has started.
    public static AtomicBoolean started = new AtomicBoolean(false);
    //whether or not the game is in lobby.
    public static AtomicBoolean lobby = new AtomicBoolean(true);
    //whether or not the game has ended.
    public static AtomicBoolean ended = new AtomicBoolean(false);
    //whether or not a the players in invincible
    public static AtomicBoolean invincible = new AtomicBoolean(false);
}
