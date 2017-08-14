package com.gmail.medvedinho.TUFProxy;

// Special handler for BCD reg 56 autosave 

public class Reg56Handler extends RegHandler
{

 private int reg; 
 private String name;
 private String value;

 public Reg56Handler(int reg, String name)
 {
  this.reg=reg;
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
  int dayA=(reg&TQHNM)>>12;
  int dayB=(reg&TQLNM)>>8;
  String day=null;
  if (dayA==0&&dayB==0) day="everyday";
  else if (dayB==1) day=code(dayA)+code(dayB)+"st";
  else if (dayB==2) day=code(dayA)+code(dayB)+"nd";
  else if (dayB==3) day=code(dayA)+code(dayB)+"rd";
  else day=code(dayA)+code(dayB)+"th";
  value=code((reg&FQHNM)>>4)+code(reg&FQLNM)+":00 on "+day;

  

  tm.addCode(name,value);
  if(hasAlias) tm.addCode(alias,value);
  return value;
 }

}
