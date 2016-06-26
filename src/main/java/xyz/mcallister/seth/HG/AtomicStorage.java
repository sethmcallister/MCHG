package xyz.mcallister.seth.HG;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sethm on 06/04/2016.
 */
public class AtomicStorage
{
    private static AtomicInteger alive = new AtomicInteger(0);
    public static ConcurrentMap<String, String> winner = new ConcurrentHashMap<String, String>();

    public static void increment()
    {
        alive.incrementAndGet();
    }

    public static void decrement()
    {
        alive.decrementAndGet();
    }

    public static int value()
    {
        return alive.get();
    }
}
