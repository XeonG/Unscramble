package me.Koolio.Unscramble;

import java.util.ArrayList;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;

public class Vars
{
  public static UnscrambleMain plugin;
  public static FileConfiguration config;
  public static Permission perms;
  public static Economy eco;
  public static Logger log;
  public static UnscrambleGameSession session;
  public static double versionId = 4.0D;
  public static String versionString = "Unscramble 4.0";
  public static UnscrambleTimerThread thread;
  public static ArrayList<UnscrambleClaimPrize> prizes;

  public Vars(UnscrambleMain plugin, FileConfiguration config, Permission perms, Economy eco, Logger log)
  {
    Vars.plugin = plugin;
    Vars.config = config;
    Vars.perms = perms;
    Vars.eco = eco;
    thread = new UnscrambleTimerThread();
    Vars.log = log;

    prizes = new ArrayList();
  }
}