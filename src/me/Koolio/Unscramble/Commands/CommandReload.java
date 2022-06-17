package me.Koolio.Unscramble.Commands;

import me.Koolio.Unscramble.UnscrambleMain;
import me.Koolio.Unscramble.Vars;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReload
{
  public static void reload(Player p)
  {
    Vars.plugin.loadYamls();
    p.sendMessage("§aConfig File Reloaded");
  }

  public static void reload(CommandSender p)
  {
    Vars.plugin.loadYamls();
    p.sendMessage("[Unscramble] Config File Reloaded");
  }
}