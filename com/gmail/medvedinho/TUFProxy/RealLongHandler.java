package com.gmail.medvedinho.TUFProxy;

public class RealLongHandler extends RegHandler
{

  private int hihwofint;
  private int lohwofint;
  private int hihwofdec;
  private int lohwofdec; 
  private String name;
  private int intvalue;
  private float decvalue;

 public RealLongHandler(int lohwofint, int hihwofint, int lohwofdec, int hihwofdec, String name)
 {
   this.lohwofint=lohwofint;
   this.hihwofint=hihwofint;
   this.lohwofdec=lohwofdec;
   this.hihwofdec=hihwofdec;
   this.name=name;
 }

 public String handle(TUFMain tm)
 {
  try
  {
   intvalue=(hihwofint<<16)+lohwofint;
   decvalue=Float.intBitsToFloat((hihwofdec<<16)+lohwofdec);
  }catch (Exception e)
  {
	System.out.println("could not parse "+ name);
	tm.addCode(name,"N/A");
	return "N/A";
  }
  String temp=new  Float(decvalue).toString();
  int decpoint=temp.indexOf('.');					// potential bug: can desimal point be a different character from Float.toString()?
  tm.addCode(name,""+intvalue+temp.substring(decpoint)+(unit!=""?" "+unit:""));
  return ""+intvalue+unit;
 }

}
