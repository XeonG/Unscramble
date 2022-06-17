package me.Koolio.Unscramble.Commands;


import me.Koolio.Unscramble.Vars;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWelcome
{
  public static void welcome(Player p)
  {
    p.sendMessage("§5=====================================================");
    p.sendMessage("§a Welcome to §cUnscramble §aPlugin §9(" + Vars.versionString + ")");
    p.sendMessage("§a Designed & Programmed by §9Hotshot2162, fixed by §5Koolio");
    p.sendMessage("§5=====================================================");
  }

  public static void welcomeConsole(CommandSender p)
  {
    p.sendMessage("=====================================================");
    p.sendMessage(" Welcome to Unscramble Plugin (" + Vars.versionString + ")");
    p.sendMessage(" Designed & Programmed by Hotshot2162, fixed by §5Koolio");
    p.sendMessage("=====================================================");
  }
}