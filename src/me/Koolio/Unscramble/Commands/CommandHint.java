package me.Koolio.Unscramble.Commands;


import me.Koolio.Unscramble.UnscrambleGameSession;
import me.Koolio.Unscramble.Vars;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandHint
{
  public static void hint(Player p)
  {
    if (Vars.session != null)
    {
      int maxHints = Vars.config.getInt("max-hints");
      if (maxHints == -1)
      {
        Vars.session.giveHint();
        Vars.session.hintsGiven += 1;
      }
      else if (Vars.session.hintsGiven >= maxHints)
      {
        p.sendMessage("§a[Unscramble] §4You cannot give anymore hints. Change this setting in the config file.");
      }
      else
      {
        Vars.session.giveHint();
        Vars.session.hintsGiven += 1;
      }

    }
    else
    {
      p.sendMessage("§a[Unscramble] §4There is no game in progress.");
    }
  }

  public static void hint(CommandSender p)
  {
    if (Vars.session != null)
    {
      int maxHints = Vars.config.getInt("max-hints");
      if (maxHints == -1)
      {
        Vars.session.giveHint();
        Vars.session.hintsGiven += 1;
      }
      else if (Vars.session.hintsGiven >= maxHints)
      {
        p.sendMessage("[Unscramble] You cannot give anymore hints. Change this setting in the config file.");
      }
      else
      {
        Vars.session.giveHint();
        Vars.session.hintsGiven += 1;
      }

    }
    else
    {
      p.sendMessage("[Unscramble] There is no game in progress.");
    }
  }
}