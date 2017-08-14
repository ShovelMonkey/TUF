package com.gmail.medvedinho.TUFProxy;

// handles two reg (32bit) long integer which corresponds
// regular java int

public class LongHandler extends RegHandler
{

 protected int hihw;
 protected int lohw; 
 protected String name;
 protected int value;

 public LongHandler(int lohw, int hihw, String name)
 {
   this.lohw=lohw;
   this.hihw=hihw;
   this.name=name;
 }

 public String handle(TUFMain tm)
 {
  try
  {
   value=(hihw<<16)+lohw;
  }catch (Exception e)
  {
   System.out.println("could not parse "+ name);
   tm.addCode(name,"N/A");
   if(hasAlias) tm.addCode(alias,"N/A");
   return "N/A";
  }
  String rform=""+value+(unit!=""?" "+unit:"");
  tm.addCode(name,rform);
  if(hasAlias) tm.addCode(alias,rform);
  return rform;
 }

}
