package xyz.mcallister.seth.HG.scoreboard;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sethm on 30/05/2016.
 */
public class Helper
{
    public static class ScoreboardText
    {
        private String left;
        private String right;

        public ScoreboardText(String left, String right)
        {
            this.left = left;
            this.right = right;
        }

        public String getLeft()
        {
            return this.left;
        }

        public void setLeft(String left)
        {
            this.left = left;
        }

        public String getRight()
        {
            return this.right;
        }

        public void setRight(String right)
        {
            this.right = right;
        }
    }

    private List<ScoreboardText> list = new ArrayList();
    private Scoreboard scoreBoard;
    private Objective objective;
    private String tag = "PlaceHolder";
    private int lastSentCount = -1;

    public Helper(Scoreboard scoreBoard)
    {
        this.scoreBoard = scoreBoard;
        this.objective = getOrCreateObjective(this.tag);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Helper(Scoreboard scoreBoard, String title)
    {
        Preconditions.checkState(title.length() <= 32, "title can not be more than 32");

        this.tag = ChatColor.translateAlternateColorCodes('&', title);
        this.scoreBoard = scoreBoard;
        this.objective = getOrCreateObjective(this.tag);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void add(String left, String right)
    {
        Preconditions.checkState(left.length() <= 16, "left can not be more than 16");
        Preconditions.checkState(right.length() <= 16, "right can not be more than 16");
        this.list.add(new ScoreboardText(left, right));
    }

    public void set(int index, String left, String right)
    {
        Preconditions.checkState(left.length() <= 16, "left can not be more than 16");
        Preconditions.checkState(right.length() <= 16, "right can not be more than 16");
        this.list.set(index, new ScoreboardText(left, right));
    }

    public void clear()
    {
        this.list.clear();
    }

    public void remove(int index)
    {
        String name = getNameForIndex(index);
        this.scoreBoard.resetScores(name);
        Team team = getOrCreateTeam(ChatColor.stripColor(this.tag) + index, index);
        team.unregister();
    }

    public void update()
    {
        for (int i = 0; i < this.list.size(); i++)
        {
            Team team = getOrCreateTeam(ChatColor.stripColor(this.tag) + i, i);
            ScoreboardText str = (ScoreboardText)this.list.get(this.list.size() - i - 1);
            team.setPrefix(str.getLeft());
            team.setSuffix(str.getRight());
            this.objective.getScore(getNameForIndex(i)).setScore(i + 1);
        }
        if (this.lastSentCount != -1)
        {
            int sentCount = this.list.size();
            for (int i = 0; i < this.lastSentCount - sentCount; i++) {
                remove(sentCount + i);
            }
        }
        this.lastSentCount = this.list.size();
    }

    public Team getOrCreateTeam(String team, int i)
    {
        Team value = this.scoreBoard.getTeam(team);
        if (value == null)
        {
            value = this.scoreBoard.registerNewTeam(team);
            value.addEntry(getNameForIndex(i));
        }
        return value;
    }

    public Objective getOrCreateObjective(String objective)
    {
        Objective value = this.scoreBoard.getObjective("dummyhubobj");
        if (value == null) {
            value = this.scoreBoard.registerNewObjective("dummyhubobj", "dummy");
        }
        value.setDisplayName(objective);
        return value;
    }

    public String getNameForIndex(int index)
    {
        return ChatColor.values()[index].toString() + ChatColor.RESET;
    }
}
