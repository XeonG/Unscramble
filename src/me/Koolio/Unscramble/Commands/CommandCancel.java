package me.Koolio.Unscramble.Commands;


import me.Koolio.Unscramble.UnscrambleGameSession;
import me.Koolio.Unscramble.UnscrambleMain;
import me.Koolio.Unscramble.UnscrambleTimerThread;
import me.Koolio.Unscramble.Vars;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandCancel
{
  public static void cancel(Player p)
  {
    if (UnscrambleTimerThread.running)
    {
      Vars.thread.stop();
    }

    if (Vars.session != null)
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3An Admin has canceled the game!");
      if (Vars.config.getBoolean("display-answer-on-failed-games"))
      {
        Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The answer was... §c" + Vars.session.word);
      }
      Vars.session = null;
    }
    else
    {
      p.sendMessage("§a[Unscramble] §4There is no game in progress.");
    }
  }

  public static void cancel(CommandSender p)
  {
    if (UnscrambleTimerThread.running)
    {
      Vars.thread.stop();
    }

    if (Vars.session != null)
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3An Admin has canceled the game!");
      if (Vars.config.getBoolean("display-answer-on-failed-games"))
      {
        Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The answer was... §c" + Vars.session.word);
      }
      Vars.session = null;
    }
    else
    {
      p.sendMessage("[Unscramble] There is no game in progress.");
    }
  }
}