package com.gmail.medvedinho.TUFProxy;

// Special handler for register 72

public class Reg72Handler extends RegHandler
{
 private String[] meanings = {
	"no received signal",
	"low received signal",
	"poor received signal",
	"pipe empty",
	"hardware failure",
	"receiving circuits gain adjusting",
	"frequency at the frequency output over flow",
	"current at 4-20mA over flow",
	"RAM check-sum error",
	"main clock or timer clock error",
	"parameters check-sum error",
	"ROM check-sum error",
	"temperature circuits error",
	"reserved",
	"internal timer over flow",
	"analog input over range"};
 private int reg; 
 private String name;
 private String value;

 public Reg72Handler(int reg, String name)
 {
  this.reg=reg;
  this.name=name;
 }

 public String handle(TUFMain tm)
 {
  value="";
  boolean first=true;
  for (int x=0;x<16;x++)
  {
   int temp=reg;
   if ((temp>>x)%2==1)
   {
    value=value.concat((first?"":",")+meanings[x]);
    tm.addCode(meanings[x],"on");
    first=false;
   }
   else tm.addCode(meanings[x],"off");
  }
  if (value.equals("")) value="no error flags";
  tm.addCode(name,""+value);
  return value;
 }

}