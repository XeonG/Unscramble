package me.Koolio.Unscramble;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Koolio.Unscramble.Commands.UnscrambleCommandHandler;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class UnscrambleMain extends JavaPlugin
{
  static Logger log = Logger.getLogger("Minecraft");
  File configFile;
  Permission perms;
  Economy eco;
  public static FileConfiguration config;

  public void onEnable()
  {
    if (!setupPermissions().booleanValue())
    {
      log.log(Level.SEVERE, "[Unscramble] No Permission found! Disabling plugin!");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    if (!setupEconomy())
    {
      log.log(Level.SEVERE, "[Unscramble] No Economy found! Disabling plugin!");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    this.configFile = new File(getDataFolder(), "config.yml");
    try
    {
      firstRun();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    config = new YamlConfiguration();
    loadYamls();

    UnscrambleListener listeners = new UnscrambleListener();
    PluginManager pm = getServer().getPluginManager();

    pm.registerEvents(listeners, this);

    UnscrambleCommandHandler commandHandler = new UnscrambleCommandHandler();
    getCommand("Unscramble").setExecutor(commandHandler);
    getCommand("us").setExecutor(commandHandler);

    new Vars(this, config, this.perms, this.eco, log);

    log.log(Level.INFO, "[Unscramble] Version 4.0");
    log.log(Level.INFO, "[Unscramble] Started successfully.");
  }

  public void onDisable()
  {
    if (UnscrambleTimerThread.running)
    {
      Vars.thread.stop();
    }
    log.log(Level.INFO, "[Unscramble] Disabling plugin.");
  }

  private void firstRun()
    throws Exception
  {
    if (!this.configFile.exists())
    {
      this.configFile.getParentFile().mkdirs();
      copy(getResource("config.yml"), this.configFile);
    }
  }

  private void copy(InputStream in, File file)
  {
    try
    {
      OutputStream out = new FileOutputStream(file);
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0)
      {
        //int len; //koolio edit
        out.write(buf, 0, len);
      }
      out.close();
      in.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void loadYamls()
  {
    try
    {
      config.load(this.configFile);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void saveYamls()
  {
    try
    {
      config.save(this.configFile);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  private Boolean setupPermissions()
  {
    RegisteredServiceProvider permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
    if (permissionProvider != null)
    {
      this.perms = ((Permission)permissionProvider.getProvider());
    }
    if (this.perms != null) return Boolean.valueOf(true); return Boolean.valueOf(false);
  }

  private boolean setupEconomy()
  {
    RegisteredServiceProvider economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
    if (economyProvider != null)
    {
      this.eco = ((Economy)economyProvider.getProvider());
    }

    return this.eco != null;
  }
}