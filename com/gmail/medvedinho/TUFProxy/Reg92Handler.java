package com.gmail.medvedinho.TUFProxy;


// Special handler for register 92 with two values

public class Reg92Handler extends RegHandler
{
 private int reg; 
 private String name1;
 private String name2;
 private int value1;
 private int value2;

 public Reg92Handler(int reg, String name1, String name2)
 {
  this.reg=reg;
  this.name1=name1;
  this.name2=name2;
 }

 public String handle(TUFMain tm)
 {
  int temp=reg;
  value1=temp>>8;
  tm.addCode(name1,""+value1);
  int temp2 = reg;
  temp2=temp2<<24;
  value2=temp2>>24;
  tm.addCode(name2,""+value2);

  return "";
 }

}