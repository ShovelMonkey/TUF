package com.gmail.medvedinho.TUFProxy;

// handles 4 octet real which basicly is java float


public class Real4Handler extends RegHandler
{

 protected int hihw;
 protected int lohw; 
 protected String name;
 protected float value;

 public Real4Handler(int lohw, int hihw, String name)
 {
  this.lohw=lohw;
  this.hihw=hihw;
  this.name=name;
 }

 public String handle(TUFMain tm)
 {
  try
  {
   value=Float.intBitsToFloat((hihw<<16)+lohw);
  }catch (Exception e)
  {
   System.out.println("unexpected problem "+ name);
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
