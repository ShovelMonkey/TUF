package com.gmail.medvedinho.TUFProxy;

import java.net.*;
import java.io.*;


// Real simple HTML server

public class TUFServer implements Runnable, TUFOutput
{
 protected boolean running=false;
 protected int port=8080;
 String html;
 int bytecount;

 // sets the port number from cl parameter

 public void setParameter(String para)
 {
  if (para!=null)
  {
   try
   {
    port=Integer.parseInt(para);
   }
   catch(Exception e)
   {
    System.out.println("Second parameter for port is not valid. Resorting to default (8080)");
    port=8080;
   }
  }
 }

 // set preprocessed html from HTMLStabber

 public void setHtml(String html)
 {
  this.html=html;
  try
  {
   bytecount=html.getBytes("UTF-8").length;	// not nice way...
  } 
  catch (Exception e)
  {
   // i think this shouldnt really happen but heres a plan B anyhow
   System.out.println("No support for UTF-8 encoding. Make sure to stick to ASCII within your HTML."); 
   bytecount=html.length();
  }
 }


/*
 public static void main(String [] args)
 {
  Thread severi = new Thread(new TUFServer());
  severi.start();
 }
*/  

 // creates new thread to stand as daemon at the port

 public void launch()
 {
  if (!running)
  new Thread(this).start();
 }

 // the part that serves

 public void run()
 {
  ServerSocket sock = null;
  try
  {
   sock = new ServerSocket(port);
  } 
  catch (Exception e)
  {
   System.out.println("Cannot bind the port "+e);
   System.exit(1);
  }

  System.out.println("Server Starting"); 
  running=true;

  Socket consock= null;
  while(running)
  {
   try 
   {
    consock = sock.accept();
   } 
   catch (Exception e)
   {
    System.out.println("Cant accept a request "+e);
	// handle somehow

   }


   PrintWriter pw=null;
   try
   {
    pw = new PrintWriter(consock.getOutputStream(),true);
   }
   catch (Exception e)
   { 
    System.out.println("could not get a stream to an accepted connection "+e);
   }

   if (pw!=null)
   {
    pw.println("HTTP/1.1 200 OK");
    //pw.println("Connection: close");
    pw.println("Server: TUFServer");
    pw.println("Content-Length: "+bytecount);     
    pw.println("Content-Type: text/html");
    pw.println("");

    pw.println(html);
    pw.flush();

    pw.close();
   }

   try
   {
    consock.close();
   }
   catch (Exception e)
   {
    System.out.println("Could not close socket");
   }

  }

  try
  {
   sock.close();
  }
  catch (Exception e)
  {
   System.out.println("Could not close socket");
  }
  System.out.println("Server Exiting"); 
 }

}
