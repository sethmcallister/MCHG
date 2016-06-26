package xyz.mcallister.seth.HG.utils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sethm on 26/06/2016.
 */
public class KillData
{
    private static ConcurrentHashMap<UUID, Integer> kills = new ConcurrentHashMap<>();

    public static Integer getKills(UUID uuid)
    {
        if(kills.contains(uuid))
        {
            return kills.get(uuid);
        }
        return 0;
    }

    public static void updateKills(UUID uuid)
    {
        if(kills.contains(uuid))
        {
            kills.put(uuid, kills.get(uuid) + 1);
            return;
        }
        kills.put(uuid, 1);
    }
}
