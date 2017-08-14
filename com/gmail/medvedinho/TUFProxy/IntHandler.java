package com.gmail.medvedinho.TUFProxy;

// handler class for 16 bit integer values
// equal for java short

public class IntHandler extends RegHandler
{

 protected int reg; 
 protected String name;
 protected short value;

 public IntHandler(int reg, String name)
 {
  this.reg=reg;
  this.name=name;
 }

 public String handle(TUFMain tm)
 {
  value=(short)reg;
  String rform=""+value+(unit!=""?" "+unit:"");
  tm.addCode(name,rform);
  if(hasAlias) tm.addCode(alias,rform);
  return rform;
 }

}