package com.gmail.medvedinho.TUFProxy;

import java.io.*;
import java.util.*;

// preprocesses HTML file replacing TUF-elements with human readable 
// register values based on register name in reg property.


public class HTMLStabber
{

 protected TUFMain tm=null;


 public void setMain(TUFMain tm)
 {
	this.tm=tm;
 }


   // deprecated "unit tester" :)
/*
 public static void main (String [] args)
 {
  HTMLStabber foo=new HTMLStabber();
  TUFServer severi=new TUFServer();
  String html =foo.processFile("com/gmail/medvedinho/TUFProxy/index.html");
  severi.setHtml(html);
  severi.launch();
 }
*/

 public String processFile(String name)
 {
  File htmlFile = new File(name);             // handle this more
  FileInputStream fis=null;
  BufferedReader br = null;

  try
  {
   fis = new FileInputStream(htmlFile);
   br = new BufferedReader(new InputStreamReader(fis));

  }
  catch (Exception e)

  {
   System.out.println("Load error "+e);
  }
  String line=null;
  StringBuilder sb=new StringBuilder();

  try
  {
   while((line=br.readLine())!=null)
   {
    String proline= process(line);
    sb.append(proline);
    sb.append(System.lineSeparator());
   }
  }
  catch (Exception e) 
  {
   System.out.println("Read error "+e);
  }
  String result = sb.toString();
  //System.out.println(result);
  return result;
 }

 // recursive method to handle TUF elements on a line of html

 public String process(String input)
 {
  int index=input.indexOf("<TUF");
  if (index==-1) return input;
  String start = input.substring(0,index);
  String rest = input.substring(index);
  int tagend = rest.indexOf('>');
  if (tagend==-1)
  {
   System.out.println("HTML file is not valid");
   System.exit(1);
  }
  String content = rest.substring(4,tagend);
  Map<String,String> map=new HashMap<String,String>();
  parseTag(content,map);
  //System.out.println("Contents:"+content);
  String end=process(rest.substring(tagend+1));
  StringBuilder sb = new StringBuilder(start);
  int age=0;
  if (map.containsKey("t"))
  {
   try
   {
    age=Integer.parseInt(map.get("t"));
   } 
   catch (Exception e)
   {
    System.out.println("faulty t-value in html");
    age=0;
   }

  }
  sb.append(tm.peekCode(map.get("reg"),age));
  sb.append(end);
  return sb.toString();  
 }

 // another recursive method to harvest all property value pairs
 // of single TUF element
 
 public void parseTag(String tag,Map<String,String> map)
 {
  int index=tag.indexOf('=');
  if (index==-1) return;
  String key = tag.substring(0,index).trim();
  String temp=tag.substring(index);
  int first=temp.indexOf('"');
  String temp2=temp.substring(first+1);
  int second=temp2.indexOf('"');
  String content=temp2.substring(0,second);
  map.put(key,content);
  parseTag(temp2.substring(second+1),map);
 }

}
