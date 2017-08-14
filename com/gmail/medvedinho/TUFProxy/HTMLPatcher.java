package com.gmail.medvedinho.TUFProxy;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;

// class for writing html file. Idea is overwrite such for
// utilizing external server

public class HTMLPatcher implements TUFOutput
{
 private String html;
 private String dir="/var/www/";
 private String file="index.html";

 public void setHtml(String html)
 {
  this.html=html;
 }

 // splits cl parameter for more suitable form

 public void setParameter(String para)
 {
  if (para!=null)
  {
   int a = para.lastIndexOf('/');
   int b = para.lastIndexOf('\\');
   int index=a>b?a:b;
   if (index<0)
   {
    dir="./";
    file=para;
   }
   else
   {
    dir=para.substring(0,index+1);
    file=para.substring(index+1);
   }
		
  }
 }


 //writes the html

 public void launch()
 {
  File directory=new File(dir);
  if (!directory.exists())
  {
   if(!directory.mkdir())
   {
    System.out.println("Couldnt create directory. Root up!");
    System.exit(1);
   }
  } 
  else if (!directory.isDirectory())
  {
   System.out.println(dir +" is not a directory");
   System.exit(1);
  }

  File fil = new File(directory,file);
  FileOutputStream fos = null;
  try
  {
   fos=new FileOutputStream(fil,false); 
  }
  catch(Exception e)
  {

   System.out.println("Couldnt create fileoutput stream");
   System.exit(1);

  }

  FileChannel chanel = fos.getChannel();
  byte[] bytes=null;
  try
  {
   bytes=html.getBytes("UTF-8");
  }
  catch(Exception e)
  {
   System.out.println("No UTF-8 support... exiting...");
   System.exit(1);
  }
  int size=bytes.length;
  ByteBuffer bumper = ByteBuffer.allocate(size);
  bumper.put(bytes);

  bumper.flip();

  try
  {

   chanel.write(bumper);
   fos.close();

  }
  catch (Exception e)
  {
   System.out.println("writing to a file failed");
   System.exit(1);
  }

 }
}

