package com.gmail.medvedinho.TUFProxy;

//Topmost class for all register handlers which other
//classes in the hierarchy inherit. Class is abstract so no
//objects are created from it directly. Point of these classes
//is to modify reg data to readable form trough "caseles programming"
//and act as container for data for potential future re-use


public abstract class RegHandler
{
 protected String unit="";
 protected boolean hasAlias=false;
 protected String alias;
 public abstract String handle(TUFMain tm);

 // 

 public RegHandler assignUnit(String unit)
 {
  this.unit=unit;
  return this;
 }

 // registers alias for the reg name

 public RegHandler assignAlias(String alias)
 {
  this.alias=alias;
  hasAlias=true;
  return this;
 }
}
