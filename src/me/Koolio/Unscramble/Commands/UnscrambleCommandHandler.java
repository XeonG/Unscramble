package me.Koolio.Unscramble.Commands;

import me.Koolio.Unscramble.UnscrambleHelperMethods;
import me.Koolio.Unscramble.Vars;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnscrambleCommandHandler
  implements CommandExecutor
{
  private static String noPerms = "§a[Unscramble] §4You do not have permission for that command.";
  private static String invalidCmd = "§a[Unscramble] §4That was not a valid command.";
  private static String consoleInvalidCmd = "[Unscramble] That was not a valid command.";

  public boolean onCommand(CommandSender p, Command cmd, String idk, String[] args)
  {
    if ((p instanceof Player))
    {
      Player ply = (Player)p;

      if (args.length == 0)
      {
        if (Vars.perms.has(ply, "unscramble.welcome"))
          CommandWelcome.welcome(ply);
        else
          ply.sendMessage(noPerms);
      }
      else if (args.length == 1)
      {
        if (args[0].equalsIgnoreCase("help"))
        {
          if (Vars.perms.has(ply, "unscramble.help"))
            CommandHelp.help(ply);
          else
            ply.sendMessage(noPerms);
        }
        else if (args[0].equalsIgnoreCase("reload"))
        {
          if (Vars.perms.has(ply, "unscramble.reload"))
            CommandReload.reload(ply);
          else
            ply.sendMessage(noPerms);
        }
        else if (args[0].equalsIgnoreCase("hint"))
        {
          if (Vars.perms.has(ply, "unscramble.hint"))
            CommandHint.hint(ply);
          else
            ply.sendMessage(noPerms);
        }
        else if (args[0].equalsIgnoreCase("cancel"))
        {
          if (Vars.perms.has(ply, "unscramble.cancel"))
            CommandCancel.cancel(ply);
          else
            ply.sendMessage(noPerms);
        }
        else if (args[0].equalsIgnoreCase("claim"))
        {
          if (Vars.perms.has(ply, "unscramble.claim"))
            CommandClaim.claim(ply);
          else {
            ply.sendMessage(noPerms);
          }
        }
        else
          ply.sendMessage(invalidCmd);
      }
      else if (args.length == 2)
      {
        if (args[0].equalsIgnoreCase("setscrambleseparately"))
        {
          if (Vars.perms.has(ply, "unscramble.setscrambleseparately"))
            CommandSetScrambleSeparately.setScrambleSeparately(ply, args[1]);
          else
            ply.sendMessage(noPerms);
        }
        if (args[0].equalsIgnoreCase("setscrambleseparately"))
        {
          if (Vars.perms.has(ply, "unscramble.setscrambleseparately"))
            CommandSetScrambleSeparately.setScrambleSeparately(ply, args[1]);
          else
            ply.sendMessage(noPerms);
        }
        if (args[0].equalsIgnoreCase("newgame"))
        {
          if (Vars.perms.has(ply, "unscramble.newgame"))
            CommandNewGame.newgame(ply, UnscrambleHelperMethods.seperateArgs(args));
          else
            ply.sendMessage(noPerms);
        }
        else
          ply.sendMessage(invalidCmd);
      }
      else if (args.length >= 3)
      {
        if (args[0].equalsIgnoreCase("newgame"))
        {
          if (Vars.perms.has(ply, "unscramble.newgame"))
            CommandNewGame.newgame(ply, UnscrambleHelperMethods.seperateArgs(args));
          else
            ply.sendMessage(noPerms);
        }
        else
          ply.sendMessage(invalidCmd);
      }
      else {
        ply.sendMessage(invalidCmd);
      }
      return true;
    }

    if (args.length == 0)
    {
      CommandWelcome.welcomeConsole(p);
    }
    else if (args.length == 1)
    {
      if (args[0].equalsIgnoreCase("help"))
      {
        CommandHelp.help(p);
      }
      else if (args[0].equalsIgnoreCase("reload"))
      {
        CommandReload.reload(p);
      }
      else if (args[0].equalsIgnoreCase("hint"))
      {
        CommandHint.hint(p);
      }
      else if (args[0].equalsIgnoreCase("cancel"))
      {
        CommandCancel.cancel(p);
      }
      else
        p.sendMessage(consoleInvalidCmd);
    }
    else if (args.length == 2)
    {
      if (args[0].equalsIgnoreCase("setscrambleseparately"))
      {
        CommandSetScrambleSeparately.setScrambleSeparately(p, args[1]);
      }
      else if (args[0].equalsIgnoreCase("newgame"))
      {
        CommandNewGame.newgame(p, UnscrambleHelperMethods.seperateArgs(args));
      }
      else
        p.sendMessage(consoleInvalidCmd);
    }
    else if (args.length >= 3)
    {
      if (args[0].equalsIgnoreCase("newgame"))
      {
        CommandNewGame.newgame(p, UnscrambleHelperMethods.seperateArgs(args));
      }
      else
        p.sendMessage(consoleInvalidCmd);
    }
    else {
      p.sendMessage(consoleInvalidCmd);
    }
    return true;
  }
}