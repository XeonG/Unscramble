package me.Koolio.Unscramble;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.Server;

public class UnscrambleGameSession
{
  public String word;
  public String scrambledWord;
  public Material prize;
  public int prizeAmount;
  public int time;
  public int timePassed = 0;
  public String category;
  public int hintInterval;
  public String currentHint;
  public int hintsGiven = 0;
  public int currentChat = 0;
  public int currentHintInterval = 0;
  public boolean useTime = false;

  public UnscrambleGameSession(String word, String scrambledWord, Material prize, int prizeAmount, int time, int hintInterval, String category)
  {
    this.word = word;
    this.currentHint = word.replaceAll("[a-zA-Z0-9]", "*");
    this.scrambledWord = scrambledWord;
    this.prize = prize;
    this.prizeAmount = prizeAmount;
    this.time = time;
    this.hintInterval = hintInterval;
    this.category = category;
    if (time != 0)
    {
      this.useTime = true;
    }
  }

  public void giveHint()
  {
    char[] starWord = this.currentHint.toCharArray();
    Random rand = new Random();

    if (this.currentHint.contains("*"))
    {
      int index;
      do {
        index = rand.nextInt(this.word.length());
      }
      while ((starWord[index] != '*') || (starWord[index] == ' '));
      starWord[index] = this.word.charAt(index);
      this.currentHint = String.valueOf(starWord);
    }
    Vars.plugin.getServer().broadcastMessage("§a[Unscramble] §3Hint!... §f" + this.currentHint + "§3, for scrambled word §c"+this.scrambledWord);
  }
}