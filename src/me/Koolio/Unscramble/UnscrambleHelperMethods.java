package me.Koolio.Unscramble;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UnscrambleHelperMethods
{
  public static String scrambleWord(String word)
  {
    char[] originalWord = word.toCharArray();
    char[] wordarray = word.toCharArray();
    ArrayList charList = new ArrayList();
    for (char c : wordarray)
    {
      if (c != ' ')
      {
        charList.add(Character.valueOf(c));
      }
    }

    Collections.shuffle(charList);

    for (int index = 0; index < originalWord.length; index++)
    {
      Character cha = Character.valueOf(originalWord[index]);
      if (Character.isSpaceChar(cha.charValue()))
      {
        charList.add(index, Character.valueOf(' '));
      }
    }

    return convertToWord(charList);
  }

  public static String convertToWord(ArrayList<Character> a)
  {
    String word = "";
    for (Iterator localIterator = a.iterator(); localIterator.hasNext(); ) { char letter = ((Character)localIterator.next()).charValue();

      word = word + letter;
    }
    return word;
  }

  public static boolean doesItHaveLetters(String word)
  {
    for (int index = 65; index < 91; index++)
    {
      if (word.contains(String.valueOf((char)index))) {
        return true;
      }

    }

    for (int index = 97; index < 122; index++)
    {
      if (word.contains(String.valueOf((char)index))) {
        return true;
      }

    }

    return false;
  }

  public static String getRandomWord()
  {
    ArrayList wordList;
    try
    {
      wordList = convertListType(Vars.config.getStringList("random-words"));
    }
    catch (Throwable t)
    {
      //ArrayList wordList; //koolio edit
      wordList = new ArrayList();
      wordList.add("stone");
      wordList.add("bedrock");
      wordList.add("crafting bench");
      wordList.add("enchantment table");
      wordList.add("brewing stand");
      wordList.add("light gray wool");
      wordList.add("light blue wool");
      wordList.add("lava bucket");
    }

    Random rand = new Random();
    int ran = rand.nextInt(wordList.size());
    return (String)wordList.get(ran);
  }

  public static ArrayList<String> convertListType(List<String> list)
  {
    ArrayList newlist = new ArrayList();
    for (String word : list)
    {
      newlist.add(word);
    }
    return newlist;
  }

  public static ArrayList<String> seperateArgs(String[] args)
  {
    ArrayList newArgs = new ArrayList();

    for (int index = 1; index < args.length; index++)
    {
      newArgs.add(args[index]);
    }

    return newArgs;
  }

  public static String updateCheck()
    throws Exception
  {
    String pluginUrlString = "http://dev.bukkit.org/server-mods/unscramble/files.rss";
    String lastestVersion = "";
    try
    {
      URL url = new URL(pluginUrlString);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
      doc.getDocumentElement().normalize();
      NodeList nodes = doc.getElementsByTagName("item");
      Node firstNode = nodes.item(0);
      if (firstNode.getNodeType() == 1)
      {
        Element firstElement = (Element)firstNode;
        NodeList firstElementTagName = firstElement.getElementsByTagName("title");
        Element firstNameElement = (Element)firstElementTagName.item(0);
        NodeList firstNodes = firstNameElement.getChildNodes();
        lastestVersion = firstNodes.item(0).getNodeValue();
      }
    }
    catch (Exception localException)
    {
    }
    return lastestVersion;
  }
}