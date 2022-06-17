package me.Koolio.Unscramble;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;

public class UnscrambleTimerThread
  implements Runnable
{
  private Thread thread;
  public static boolean running = false;

  public void run()
  {
    while (running)
    {
      try
      {
        Thread.sleep(1000L);
      }
      catch (Throwable localThrowable)
      {
      }

      if (Vars.session != null)
      {
        Vars.session.timePassed += 1;

        if ((Vars.session.hintInterval != 0) && (Vars.session.timePassed != 0))
        {
          if (Vars.session.timePassed % Vars.session.hintInterval == 0)
          {
            Vars.session.giveHint();
          }
        }

        if (Vars.session.useTime)
        {
          if (Vars.session.timePassed == Vars.session.time)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 Oh! Sorry, you didnt get the word in time!");
            if (Vars.config.getBoolean("display-answer-on-failed-games"))
            {
              Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3The answer was... §c" + Vars.session.word);
            }
            Vars.session.hintsGiven = 0;
            Vars.session = null;
            stop();
          }
          else if (Vars.session.time - Vars.session.timePassed == 180)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 3 Minutes Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 150)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 2 Minutes 30 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 120)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 2 Minutes Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 90)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 1 Minute 30 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 60)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 1 Minute Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 45)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 45 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 30)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 30 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 20)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 20 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 15)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 15 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 10)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 10 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 5)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 5 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 3)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 3 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 2)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 2 Seconds Left");
          }
          else if (Vars.session.time - Vars.session.timePassed == 1)
          {
            Vars.plugin.getServer().broadcastMessage("§a[Unscramble]§3 1 Second Left");
          }
        }
      }
    }
  }

  public void start()
  {
    running = true;
    this.thread = new Thread(this);
    this.thread.start();
  }

  public void stop()
  {
    if (running)
    {
      running = false;
      this.thread.stop();
    }
  }
}