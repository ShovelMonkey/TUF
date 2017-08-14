package com.gmail.medvedinho.TUFProxy;

// Special handler for reg 96 (language)

public class Reg96Handler extends RegHandler
{

 private int reg; 
 private String name;
 private String value;

 public Reg96Handler(int reg, String name)
 {
  this.reg=reg;
  this.name=name;
 }

 public String handle(TUFMain tm)
 {
  if (reg==0)
   value="English";
  else if (reg==1)
   value ="Chinese";
  else value="other";
  tm.addCode(name,value);
  if(hasAlias) tm.addCode(alias,value);
  return value;
 }

}