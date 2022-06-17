package me.Koolio.Unscramble.Commands;

import me.Koolio.Unscramble.UnscrambleMain;
import me.Koolio.Unscramble.Vars;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandSetScrambleSeparately
{
  public static void setScrambleSeparately(Player p, String trueFalse)
  {
    boolean value;
    if (trueFalse.equalsIgnoreCase("true"))
    {
      value = true;
    }
    else
    {
      //boolean value; //koolio edit
      if (trueFalse.equalsIgnoreCase("false"))
      {
        value = false;
      }
      else
      {
        p.sendMessage("§a[Unscramble] §4Please enter either §ctrue §4or §cfalse.");
        return;
      }
    }
    //boolean value; //koolio edit
    Vars.config.set("scramble-words-separately", Boolean.valueOf(value));
    Vars.plugin.saveYamls();
    p.sendMessage("§a[Unscramble] §2Scramble-Words-Separately set to §e" + value);
  }

  public static void setScrambleSeparately(CommandSender p, String trueFalse)
  {
    boolean value;
    if (trueFalse.equalsIgnoreCase("true"))
    {
      value = true;
    }
    else
    {
      //boolean value; //koolio edit
      if (trueFalse.equalsIgnoreCase("false"))
      {
        value = false;
      }
      else
      {
        p.sendMessage("[Unscramble] Please enter either true or false.");
        return;
      }
    }
    //boolean value; //koolio edit
    Vars.config.set("scramble-words-separately", Boolean.valueOf(value));
    Vars.plugin.saveYamls();
    p.sendMessage("[Unscramble] Scramble-Words-Separately set to " + value);
  }
}