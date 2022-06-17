package me.Koolio.Unscramble;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UnscrambleClaimPrize
{
  public Player p;
  public Material Prize;
  public int Amount;

  public UnscrambleClaimPrize(Player p, Material prize2, int Amount)
  {
    this.p = p;
    this.Prize = prize2;
    this.Amount = Amount;
  }
}