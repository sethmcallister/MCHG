package xyz.mcallister.seth.HG.scoreboard;


import xyz.mcallister.seth.HG.Main;

/**
 * Created by sethm on 30/05/2016.
 */
public class Manager
{
    private Main plugin;

    public Manager(Main plugin)
    {
        this.plugin = plugin;
    }

    public void init() {}

    public void tear() {}

    public void reload() {}

    public Main getPlugin()
    {
        return this.plugin;
    }
}
