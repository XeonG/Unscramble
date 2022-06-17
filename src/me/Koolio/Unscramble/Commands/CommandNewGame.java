package me.Koolio.Unscramble.Commands;

import java.util.ArrayList;
import me.Koolio.Unscramble.UnscrambleGameSession;
import me.Koolio.Unscramble.UnscrambleHelperMethods;
import me.Koolio.Unscramble.UnscrambleMain;
import me.Koolio.Unscramble.UnscrambleTimerThread;
import me.Koolio.Unscramble.Vars;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandNewGame
{
  public static void newgame(Player p, ArrayList<String> args)
  {
    if (Vars.session != null)
    {
      p.sendMessage("§a[Unscramble] §4There is already a game running.");
      p.sendMessage("§a[Unscramble] §4Use §c/us cancel §4to cancel the current game.");
      return;
    }

    Vars.session = null;

    if (UnscrambleTimerThread.running)
    {
      Vars.thread.stop();
    }

    String scrambledWord = "";
    Material prize = Material.SIGN_POST;
    int amount = 0;
    int timer = 0;
    String category = "";
    boolean useMoney = false;

    int hintInterval = 0;
    String word = "";

    for (String arg : args)
    {
      if ((arg.startsWith("w:")) || (arg.startsWith("W:")))
      {
        arg = arg.replace("w:", "").replace("W:", "");

        if (arg.equalsIgnoreCase("random"))
        {
          arg = UnscrambleHelperMethods.getRandomWord();
        }

        arg = arg.replaceAll("_", " ");
        arg = arg.replaceAll("[^ a-zA-Z0-9]", "");
        word = arg;

        if (Vars.config.getBoolean("scramble-words-separately"))
        {
          String[] words = word.split(" ");
          scrambledWord = "";
          for (String str : words)
          {
            str = UnscrambleHelperMethods.scrambleWord(str);
            scrambledWord = scrambledWord + str + " ";
          }
          scrambledWord = scrambledWord.trim();
        }
        else
        {
          scrambledWord = UnscrambleHelperMethods.scrambleWord(arg);
        }

      }
      else if ((arg.startsWith("p:")) || (arg.startsWith("P:")))
      {
        arg = arg.replace("p:", "").replace("P:", "");

        if (arg.equalsIgnoreCase("$"))
        {
          if ((!Vars.perms.has(p, "unscramble.newgame.prize.$")) && (!Vars.perms.has(p, "unscramble.newgame.prize.*")))
          {
            p.sendMessage("§a[Unscramble] §4You do not have permission for that prize");
            return;
          }
          useMoney = true;
        }
        else
        {
          useMoney = false;
          if (!UnscrambleHelperMethods.doesItHaveLetters(arg))
          {
            try
            {
              prize = Material.getMaterial(Integer.valueOf(arg).intValue());
            }
            catch (Throwable t)
            {
              p.sendMessage("§a[Unscramble] §4The material given was not valid");
              return;
            }

            if ((!Vars.perms.has(p, "unscramble.newgame.prize." + arg)) && (!Vars.perms.has(p, "unscramble.newgame.prize.*")))
            {
              p.sendMessage("§a[Unscramble] §4You do not have permission for that prize");
            }

          }
          else
          {
            p.sendMessage("§a[Unscramble] §4The material given was not valid");
          }

        }

      }
      else if ((arg.startsWith("a:")) || (arg.startsWith("A:")))
      {
        arg = arg.replace("a:", "").replace("A:", "");
        try
        {
          amount = Integer.valueOf(arg).intValue();
        }
        catch (Throwable t)
        {
          p.sendMessage("§a[Unscramble] §4The amount given was not valid");
          return;
        }

        if ((!Vars.perms.has(p, "unscramble.newgame.amount")) && (!Vars.perms.has(p, "unscramble.newgame.*")))
        {
          p.sendMessage("§a[Unscramble] §4You do not have permission for amount");
        }

      }
      else if ((arg.startsWith("t:")) || (arg.startsWith("T:")))
      {
        arg = arg.replace("t:", "").replace("T:", "");
        try
        {
          timer = Integer.valueOf(arg).intValue();
        }
        catch (Throwable t)
        {
          p.sendMessage("§a[Unscramble] §4The time given was not valid");
          return;
        }

        if ((!Vars.perms.has(p, "unscramble.newgame.time")) && (!Vars.perms.has(p, "unscramble.newgame.*")))
        {
          p.sendMessage("§a[Unscramble] §4You do not have permission for timer");
        }

      }
      else if ((arg.startsWith("h:")) || (arg.startsWith("H:")))
      {
        arg = arg.replace("h:", "").replace("H:", "");
        try
        {
          hintInterval = Integer.valueOf(arg).intValue();
        }
        catch (Throwable t)
        {
          p.sendMessage("§a[Unscramble] §4The hint interval given was not valid");
          return;
        }

        if ((!Vars.perms.has(p, "unscramble.newgame.hintinterval")) && (!Vars.perms.has(p, "unscramble.newgame.*")))
        {
          p.sendMessage("§a[Unscramble] §4You do not have permission for hint interval");
        }

      }
      else if ((arg.startsWith("c:")) || (arg.startsWith("C:")))
      {
        arg = arg.replace("c:", "").replace("C:", "");

        arg = arg.replaceAll("_", " ");

        category = arg;

        if ((!Vars.perms.has(p, "unscramble.newgame.category")) && (!Vars.perms.has(p, "unscramble.newgame.*")))
        {
          p.sendMessage("§a[Unscramble] §4You do not have permission for category");
        }

      }
      else
      {
        p.sendMessage("§a[Unscramble] §4The following argument is not valid:");
        p.sendMessage("§a[Unscramble] §c" + arg);
        return;
      }
    }

    if (word == "")
    {
      p.sendMessage("§a[Unscramble] §4You did not include a w: aregument:");
      return;
    }

    if ((prize != null) || (useMoney))
    {
      if (amount == 0)
      {
        amount = 1;
      }
    }

    Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3New Game! Unscramble this:§r§c " + scrambledWord);

    if (useMoney)
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The prize for winning is $" + amount);
    }
    else if ((prize != Material.WALL_SIGN) && (!useMoney))
    {
      if (Vars.config.getBoolean("display-prize"))
      {
        if (amount == 1) {
          Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The prize for winning is " + amount + " " + prize.name());
        }
        else if (prize.name().endsWith("S"))
          Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The prize for winning is " + amount + " " + prize.name());
        else {
          Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The prize for winning is " + amount + " " + prize.name() + "S");
        }
      }
    }

    if (category != "")
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The category for the word/phrase is:§c " + category);
    }

    if (timer != 0)
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3Starting timer at " + timer + " seconds");
    }

    Vars.session = new UnscrambleGameSession(word, scrambledWord, prize, amount, timer, hintInterval, category);
    Vars.thread.start();
  }

  public static void newgame(CommandSender p, ArrayList<String> args)
  {
    if (Vars.session != null)
    {
      p.sendMessage("[Unscramble] There is already a game running.");
      p.sendMessage("[Unscramble] Use /us cancel to cancel the current game.");
      return;
    }

    Vars.session = null;

    if (UnscrambleTimerThread.running)
    {
      Vars.thread.stop();
    }

    String scrambledWord = "";
    Material prize = Material.SIGN_POST;
    int amount = 0;
    int timer = 0;
    String category = "";
    boolean useMoney = false;

    int hintInterval = 0;
    String word = "";

    for (String arg : args)
    {
      if ((arg.startsWith("w:")) || (arg.startsWith("W:")))
      {
        arg = arg.replace("w:", "").replace("W:", "");

        if (arg.equalsIgnoreCase("random"))
        {
          arg = UnscrambleHelperMethods.getRandomWord();
        }

        arg = arg.replaceAll("_", " ");
        arg = arg.replaceAll("[^ a-zA-Z0-9]", "");
        word = arg;

        if (Vars.config.getBoolean("scramble-words-separately"))
        {
          String[] words = word.split(" ");
          scrambledWord = "";
          for (String str : words)
          {
            str = UnscrambleHelperMethods.scrambleWord(str);
            scrambledWord = scrambledWord + str + " ";
          }
          scrambledWord = scrambledWord.trim();
        }
        else
        {
          scrambledWord = UnscrambleHelperMethods.scrambleWord(arg);
        }

      }
      else if ((arg.startsWith("p:")) || (arg.startsWith("P:")))
      {
        arg = arg.replace("p:", "").replace("P:", "");

        if (arg.equalsIgnoreCase("$"))
        {
          if ((!Vars.perms.has(p, "unscramble.newgame.prize.$")) && (!Vars.perms.has(p, "unscramble.newgame.prize.*")))
          {
            p.sendMessage("[Unscramble] You do not have permission for that prize");
            return;
          }
          useMoney = true;
        }
        else
        {
          useMoney = false;
          if (!UnscrambleHelperMethods.doesItHaveLetters(arg))
          {
            try
            {
              prize = Material.getMaterial(Integer.valueOf(arg).intValue());
            }
            catch (Throwable t)
            {
              p.sendMessage("[Unscramble] The material given was not valid");
              return;
            }

            if ((!Vars.perms.has(p, "unscramble.newgame.prize." + arg)) && (!Vars.perms.has(p, "unscramble.newgame.prize.*")))
            {
              p.sendMessage("[Unscramble] You do not have permission for that prize");
            }

          }
          else
          {
            p.sendMessage("[Unscramble] The material given was not valid");
          }

        }

      }
      else if ((arg.startsWith("a:")) || (arg.startsWith("A:")))
      {
        arg = arg.replace("a:", "").replace("A:", "");
        try
        {
          amount = Integer.valueOf(arg).intValue();
        }
        catch (Throwable t)
        {
          p.sendMessage("[Unscramble] The amount given was not valid");
          return;
        }

        if ((!Vars.perms.has(p, "unscramble.newgame.amount")) && (!Vars.perms.has(p, "unscramble.newgame.*")))
        {
          p.sendMessage("§a[Unscramble] §4You do not have permission for amount");
        }

      }
      else if ((arg.startsWith("t:")) || (arg.startsWith("T:")))
      {
        arg = arg.replace("t:", "").replace("T:", "");
        try
        {
          timer = Integer.valueOf(arg).intValue();
        }
        catch (Throwable t)
        {
          p.sendMessage("[Unscramble] The time given was not valid");
          return;
        }

        if ((!Vars.perms.has(p, "unscramble.newgame.time")) && (!Vars.perms.has(p, "unscramble.newgame.*")))
        {
          p.sendMessage("[Unscramble] You do not have permission for timer");
        }

      }
      else if ((arg.startsWith("h:")) || (arg.startsWith("H:")))
      {
        arg = arg.replace("h:", "").replace("H:", "");
        try
        {
          hintInterval = Integer.valueOf(arg).intValue();
        }
        catch (Throwable t)
        {
          p.sendMessage("[Unscramble] The hint interval given was not valid");
          return;
        }

        if ((!Vars.perms.has(p, "unscramble.newgame.hintinterval")) && (!Vars.perms.has(p, "unscramble.newgame.*")))
        {
          p.sendMessage("[Unscramble] You do not have permission for hint interval");
        }

      }
      else if ((arg.startsWith("c:")) || (arg.startsWith("C:")))
      {
        arg = arg.replace("c:", "").replace("C:", "");

        arg = arg.replaceAll("_", " ");

        category = arg;

        if ((!Vars.perms.has(p, "unscramble.newgame.category")) && (!Vars.perms.has(p, "unscramble.newgame.*")))
        {
          p.sendMessage("[Unscramble] You do not have permission for category");
        }

      }
      else
      {
        p.sendMessage("[Unscramble] The following argument is not valid:");
        p.sendMessage("[Unscramble] " + arg);
        return;
      }
    }

    if (word == "")
    {
      p.sendMessage("[Unscramble] You did not include a w: aregument:");
      return;
    }

    if ((prize != null) || (useMoney))
    {
      if (amount == 0)
      {
        amount = 1;
      }
    }

    Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3New Game! Unscramble §othis:§r§c " + scrambledWord);

    if (useMoney)
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The prize for winning is $" + amount);
    }
    else if ((prize != Material.WALL_SIGN) && (!useMoney))
    {
      if (Vars.config.getBoolean("display-prize"))
      {
        if (amount == 1) {
          Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The prize for winning is " + amount + " " + prize.name());
        }
        else if (prize.name().endsWith("S"))
          Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The prize for winning is " + amount + " " + prize.name());
        else {
          Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The prize for winning is " + amount + " " + prize.name() + "S");
        }
      }
    }

    if (category != "")
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The category for the word/phrase is:§c " + category);
    }

    if (timer != 0)
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3Starting timer at " + timer + " seconds");
    }

    Vars.session = new UnscrambleGameSession(word, scrambledWord, prize, amount, timer, hintInterval, category);
    Vars.thread.start();
  }
}