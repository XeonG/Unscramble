package me.Koolio.Unscramble;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class UnscrambleListener
  implements Listener
{
  @EventHandler(priority=EventPriority.LOWEST)
  public void playerChat(AsyncPlayerChatEvent event)
  {
    if (Vars.session == null)
    {
      return;
    }

    if (event.isCancelled())
    {
      return;
    }

    if (Vars.session.currentChat == 10)
    {
      Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3Again, the word was...§c " + Vars.session.scrambledWord);
      Vars.session.currentChat = 0;
    }
    else
    {
      Vars.session.currentChat += 1;
    }

    Player p = event.getPlayer();
    String msg = event.getMessage();

    if (msg.equalsIgnoreCase(Vars.session.word))
    {
      if (Vars.session.prize != Material.SIGN_POST)
      {
        Vars.prizes.add(new UnscrambleClaimPrize(p, Vars.session.prize, Vars.session.prizeAmount));
        new UnscrambleWaitThread(p, "§a[Unscramble] §3Congratulations " + p.getName() + "! Word was (§f"+Vars.session.word+"§3)", "§a[Unscramble] §3Use §c/us claim §3to claim your prize!");
      }
      else
      {
        new UnscrambleWaitThread(p, "§a[Unscramble] §3Congratulations " + p.getName() + "! Word was (§f"+Vars.session.word+"§3)", "");
      }

      Vars.session = null;
      Vars.thread.stop();
    }
  }

  @EventHandler(priority=EventPriority.MONITOR)
  public void playerJoin(PlayerJoinEvent event)
  {
    Player p = event.getPlayer();

    if (!Vars.config.getBoolean("check-for-updates"))
    {
      return;
    }

    if (p.isOp())
    {
      String lastestVersion = "";
      try
      {
        lastestVersion = UnscrambleHelperMethods.updateCheck();
      }
      catch (Throwable localThrowable)
      {
      }

      if (!lastestVersion.equalsIgnoreCase(Vars.versionString))
      {
        p.sendMessage("§5=====================================================");
        p.sendMessage("§4 Warning!§f This isnt the lastest version of Unscramble!");
        p.sendMessage("§c " + lastestVersion + "§f Is out! (This is §c" + Vars.versionString + "§f)");
        p.sendMessage("§5=====================================================");
      }
    }
  }
}