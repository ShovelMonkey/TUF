package com.gmail.medvedinho.TUFProxy;

// handler for word length (yeah im still living on 32bit era)
// binary coded data

public class WBCDHandler extends RegHandler
{

 protected int hihw;
 protected int lohw; 
 protected String name;
 protected String value;

 public WBCDHandler(int lohw, int hihw, String name)
 {
  this.lohw=lohw;
  this.hihw=hihw;
  this.name=name;
 }


 // get hex char simply

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
  value=code((hihw&TQHNM)>>12)+code((hihw&TQLNM)>>8)+code((hihw&FQHNM)>>4)+code(hihw&FQLNM)+
code((lohw&TQHNM)>>12)+code((lohw&TQLNM)>>8)+code((lohw&FQHNM)>>4)+code(lohw&FQLNM);
  

  tm.addCode(name,value);
  if(hasAlias) tm.addCode(alias,value);
  return value;
 }

}
