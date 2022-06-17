package me.Koolio.Unscramble.Commands;

import java.util.ArrayList;
import me.Koolio.Unscramble.UnscrambleClaimPrize;
import me.Koolio.Unscramble.Vars;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandClaim
{
  public static void claim(Player ply)
  {
    boolean found = false;
    UnscrambleClaimPrize prizeFound = null;

    for (UnscrambleClaimPrize prize : Vars.prizes)
    {
      if (prize.p == ply)
      {
        if (prize.Prize.equals(Material.WALL_SIGN))
        {
          Vars.eco.depositPlayer(ply.getName(), prize.Amount);
        }
        else
        {
          ply.getInventory().addItem(new ItemStack[] { new ItemStack(prize.Prize, prize.Amount) });
        }
        found = true;
        prizeFound = prize;
      }
    }

    if (found)
    {
      ply.sendMessage("§a[Unscramble] §3Your prize(s) has/have been added to you inventory/account.");
      Vars.prizes.remove(prizeFound);
    }
    else
    {
      ply.sendMessage("§a[Unscramble] §4No prizes found under your name.");
    }
  }
}