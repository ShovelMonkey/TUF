package com.gmail.medvedinho.TUFProxy;

import java.io.*;
import java.net.*;

// reads the feed and preparses TUF regs.


public class TUFReader
{

 protected String lastHeader="";
 protected static int tweak=0;


 public String getLastHeader()
 {
   return lastHeader;
 }


 public static void main(String [] args) 
 {
  if (args.length<1)
  {
   System.out.println();
   System.out.println("  TUFProxy");
   System.out.println();
   System.out.println("USAGE: Choose output mode with 1st parameter. Current choiges are:");
   System.out.println();
   System.out.println("  s - for TUFServer,  creates minimalistic webserver on port given as second parameter, serving html-file given as");
   System.out.println("      third parameter. Example: java com.gmail.medvedinho.TUFProxy.TUFMain s 8080 com/gmail/medvedinho/TUFProxy/index.html");
   System.out.println();
   System.out.println("  p - for HTMLPatcher, which updates html in location given as 2nd parameter with preprocessed version of html file given");
   System.out.println("      as 3rd parameter. Example: sudo java com.gmail.medvedinho.TUFProxy.TUFMain p /var/www/index.html com/gmail/medvedinho/TUFProxy/index.html"); 
   System.out.println("      notice that in this case you might need elevated rights to replace the file in question as in above example");
   System.out.println();
   System.out.println("WARNING: 2nd and 3rd parameters default to the values given in above examples. So pay atention to your usage of mode p");
   System.out.println("to avoid potential accidental data loss. This software comes without any warranty.");
   System.out.println();
   System.exit(0);
  }
  TUFMain software = new TUFMain();
  if(args.length>0&&args[0].equals("p")) software.flipmode();
  if(args.length>1) software.setSecond(args[1]);
  if(args.length>2) software.setThird(args[2]);
  tweak=19;
  software.runner();
 }

 public int[] fetch()
 {
  int []vals=new int[120];
  InputStream is = null;
  BufferedReader buffe=null;	
  try
  {
   URL feedurl = new URL("http://tuftuf.gambitlabs.fi/feed.txt");
   is = feedurl.openStream();
   buffe= new BufferedReader(new InputStreamReader(is));  
  }
  catch (Exception e)
  {
   System.out.println("Could not access gambits feed"+e);
   System.exit(1);
  }

  String line;
  boolean first=true;
  int counter=0;
  try
  {
   while ((line=buffe.readLine())!=null)
   {
    if (!first)
    {
     if (tweak<counter)
     {
      int splitter = line.indexOf(':');
      try
      {
       vals[ Integer.parseInt(line.substring(0,splitter))-tweak]
 	= Integer.parseInt(line.substring(splitter+1));
      }
      catch(Exception e)
      {
       System.out.println("Feed seems malformed "+e);
       System.exit(1);
      }
     }
    }
    else
    {
     if(line.equals(lastHeader))
     {
      vals[0]=-1;
      return vals;
     }
     else
     {
      vals[0]=0;
      lastHeader=line;
     }
    }
   first=false;
   counter++;
   }
  }
  catch (Exception e)
  {
   System.out.println("Feed error");
   System.exit(1);
  }
 return vals;
 }
}
