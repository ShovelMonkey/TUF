package com.gmail.medvedinho.TUFProxy;

// Handles the 3 reg date/time BCD construct

public class DateTimeHandler extends RegHandler
{
 protected int my;
 protected int hd;
 protected int sm; 
 protected String name;
 protected String value;

 public DateTimeHandler(int sm, int hd, int my, String name)
 {
  this.my=my;
  this.hd=hd;
  this.sm=sm;
  this.name=name;
 }

 public String code(int a)
 {
  if (a==0) return "0";
  if (a==1) return "1";
  if (a==2) return "2";
  if (a==3) return "3";
  if (a==4) return "4";
  if (a==5) return "5";
  if (a==6) return "6";
  if (a==7) return "7";
  if (a==8) return "8";
  if (a==9) return "9";
  if (a==10) return "A";
  if (a==11) return "B";
  if (a==12) return "C";
  if (a==13) return "D";
  if (a==14) return "E";
  return "F";
 }

 public String handle(TUFMain tm)
 {
  int TQHNM=0xF000;  // 3rd quarter hi nibble mask
  int TQLNM=0x0F00;  
  int FQHNM=0x00F0;
  int FQLNM=0x000F;
  value=code((my&TQHNM)>>12)+code((my&TQLNM)>>8)+"/"+code((my&FQHNM)>>4)+code(my&FQLNM)+"/"+
code((hd&TQHNM)>>12)+code((hd&TQLNM)>>8)+" "+code((hd&FQHNM)>>4)+code(hd&FQLNM)+":"+
code((sm&TQHNM)>>12)+code((sm&TQLNM)>>8)+"\'"+code((sm&FQHNM)>>4)+code(sm&FQLNM)+"\"";
  

  tm.addCode(name,value);
  if(hasAlias) tm.addCode(alias,value);
  return value;
 }

}
