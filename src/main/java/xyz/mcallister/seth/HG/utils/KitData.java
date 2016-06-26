package xyz.mcallister.seth.HG.utils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sethm on 26/06/2016.
 */
public class KitData
{
    private static ConcurrentHashMap<UUID, String> kit = new ConcurrentHashMap<>();

    public static String getKit(UUID uuid)
    {
        return kit.get(uuid);
    }

    public static void updateKit(UUID uuid, String string)
    {
        kit.put(uuid, string);
        System.out.println("Updated kit to " + string);
    }
}
