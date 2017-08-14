package com.gmail.medvedinho.TUFProxy;

import java.util.*;

// Main class of TUF proxy. Contains the software entry point


public class TUFMain
{
 private List<RegHandler> valHolder =null;       // container for data
 private Map<String,String[]> interpretor=null;	 // dictionary for interpreted strings
 private String second;				 // cl parameters	
 private String third;
 private boolean mode=true;			 // mode (not very extendable)
 private int current;				 // for history tracking

 public static void main(String [] args) 
 {
  if (args.length<1)
  {
   System.out.println();
   System.out.println("  TUFProxy");
   System.out.println();
   System.out.println("USAGE: Choose output mode with 1st parameter. Current choiges are:");
   System.out.println();
   System.out.println("  s - for TUFServer,  creates minimalistic webserver on port given as second parameter, serving html-file given as");
   System.out.println("      third parameter. Example: java com.gmail.medvedinho.TUFProxy.TUFMain s 8080 com/gmail/medvedinho/TUFProxy/index.html");
   System.out.println();
   System.out.println("  p - for HTMLPatcher, which updates html in location given as 2nd parameter with preprocessed version of html file given");
   System.out.println("      as 3rd parameter. Example: sudo java com.gmail.medvedinho.TUFProxy.TUFMain p /var/www/index.html com/gmail/medvedinho/TUFProxy/index.html"); 
   System.out.println("      notice that in this case you might need elevated rights to replace the file in question as in above example");
   System.out.println();
   System.out.println("WARNING: 2nd and 3rd parameters default to the values given in above examples. So pay atention to your usage of mode p");
   System.out.println("to avoid potential accidental data loss. This software comes without any warranty.");
   System.out.println();
   System.exit(0);
  }
  TUFMain software = new TUFMain();
  if(args.length>0&&args[0].equals("p")) software.flipmode();
  if(args.length>1) software.setSecond(args[1]);
  if(args.length>2) software.setThird(args[2]);
  software.runner();
 }

 // flips the mode

 public void flipmode()
 {
  if (mode==false) mode = true;
  else mode=false;
 }

 public void setSecond(String nd)
 {
  second=nd;
 }

 public void setThird(String rd)
 {
  third=rd;
 }

 // mainloop for the software

 public void runner()
 {
  current=0;
  interpretor=new HashMap<String,String[]>();
  valHolder=new LinkedList<RegHandler>();
  TUFReader tuffi = new TUFReader();			// creates feed reader for input
  TUFOutput severi=null;				// and output module according to mode
  if(mode) severi=new TUFServer();			// either webserver TUFServer
  else severi=new HTMLPatcher();			// or HTMLPatcher for use with external server
  severi.setParameter(second);

  while (true)						// mainloop starts
  {
   int regs []= tuffi.fetch();				//  gets regs to int table
   if (regs[0]!=-1)
   {
    current++;						// updates current
    if (current>11)current=0;
    addCode("Feed header time",tuffi.getLastHeader());

	// creates handler for each reg or valueand assigns units and possible aliases


    valHolder.add(new Real4Handler(regs[1],regs[2],"Flow Rate").assignUnit("m\u00B3/h"));
    valHolder.add(new Real4Handler(regs[3],regs[4],"Energy Flow Rate").assignUnit("GJ/h"));
    valHolder.add(new Real4Handler(regs[5],regs[6],"Velocity").assignUnit("m/s"));
    valHolder.add(new Real4Handler(regs[7],regs[8],"Fluid sound speed").assignUnit("m/s"));
    valHolder.add(new LongHandler(regs[9],regs[10],"Positive accumulator"));
    valHolder.add(new Real4Handler(regs[11],regs[12],"Positive fraction"));
    valHolder.add(new RealLongHandler(regs[9],regs[10],regs[11],regs[12],"Positive"));
    valHolder.add(new LongHandler(regs[13],regs[14],"Negative accumulator"));
    valHolder.add(new Real4Handler(regs[15],regs[16],"Negative fraction"));
    valHolder.add(new RealLongHandler(regs[13],regs[14],regs[15],regs[16],"Negative"));
    valHolder.add(new LongHandler(regs[17],regs[18],"Positive energy accumulator"));
    valHolder.add(new Real4Handler(regs[19],regs[20],"Positive energy fraction"));
    valHolder.add(new RealLongHandler(regs[17],regs[18],regs[19],regs[20],"Positive energy"));
    valHolder.add(new LongHandler(regs[21],regs[22],"Negative energy accumulator"));
    valHolder.add(new Real4Handler(regs[23],regs[24],"Negative energy fraction"));
    valHolder.add(new RealLongHandler(regs[21],regs[22],regs[23],regs[24],"Negative energy"));
    valHolder.add(new LongHandler(regs[25],regs[26],"Net accumulator"));
    valHolder.add(new Real4Handler(regs[27],regs[28],"Net fraction"));
    valHolder.add(new RealLongHandler(regs[25],regs[26],regs[27],regs[28],"Net"));
    valHolder.add(new LongHandler(regs[29],regs[30],"Net energy accumulator"));
    valHolder.add(new Real4Handler(regs[31],regs[32],"Net energy fraction"));
    valHolder.add(new RealLongHandler(regs[29],regs[30],regs[31],regs[32],"Net energy"));
    valHolder.add(new Real4Handler(regs[33],regs[34],"Temperature #1/inlet").assignUnit("C\u00b0").assignAlias("Inlet temp"));
    valHolder.add(new Real4Handler(regs[35],regs[36],"Temperature #2/outlet").assignUnit("C\u00b0").assignAlias("Outlet temp"));
    valHolder.add(new Real4Handler(regs[37],regs[38],"Analog input AI3"));
    valHolder.add(new Real4Handler(regs[39],regs[40],"Analog input AI4"));
    valHolder.add(new Real4Handler(regs[41],regs[42],"Analog input AI5"));

    //following three value names had error in manual

    valHolder.add(new Real4Handler(regs[43],regs[44],"Current input at AI3").assignUnit("mA").assignAlias("Current AI3"));
    valHolder.add(new Real4Handler(regs[45],regs[46],"Current input at AI4").assignUnit("mA").assignAlias("Current AI4"));
    valHolder.add(new Real4Handler(regs[47],regs[48],"Current input at AI5").assignUnit("mA").assignAlias("Current AI5"));

    valHolder.add(new WBCDHandler(regs[49],regs[50],"System password"));

    valHolder.add(new HWBCDHandler(regs[51],"Password for hardware").assignAlias("HWPW"));

    valHolder.add(new DateTimeHandler(regs[53],regs[54],regs[55],"Calendar (date and time)").assignAlias("Datetime"));
    valHolder.add(new Reg56Handler(regs[56],"Day+Hour for Auto-Save").assignAlias("Auto-Save"));



    valHolder.add(new IntHandler(regs[59],"Key to input"));
    valHolder.add(new IntHandler(regs[60],"Go to Window #"));
    valHolder.add(new IntHandler(regs[61],"LCD Back-lit lights for number of seconds").assignUnit("s").assignAlias("LCD Backlight"));
    valHolder.add(new IntHandler(regs[62],"Times for the beeper"));
   
    // next register number is just a guess it had value 62 (already used) in manual

    valHolder.add(new IntHandler(regs[63],"Pulses left for OCT"));



    valHolder.add(new Reg72Handler(regs[72],"Error Code"));

    valHolder.add(new Real4Handler(regs[77],regs[78],"PT100 resistance of inlet").assignUnit("\u2126").assignAlias("Inlet resistance"));
    valHolder.add(new Real4Handler(regs[79],regs[80],"PT100 resistance of outlet").assignUnit("\u2126").assignAlias("Outlet resistance"));
    valHolder.add(new Real4Handler(regs[81],regs[82],"Total travel time").assignUnit("\u00b5s"));
    valHolder.add(new Real4Handler(regs[83],regs[84],"Delta travel time").assignUnit("ns"));
    valHolder.add(new Real4Handler(regs[85],regs[86],"Upstream travel time").assignUnit("\u00B5s"));
    valHolder.add(new Real4Handler(regs[87],regs[88],"Downstream travel time").assignUnit("\u00b5s"));
    valHolder.add(new Real4Handler(regs[89],regs[90],"Output current").assignUnit("mA"));



    valHolder.add(new Reg92Handler(regs[92],"Working step","Signal Quality"));



    valHolder.add(new IntHandler(regs[93],"Upstream strength"));
    valHolder.add(new IntHandler(regs[94],"Downstream strength"));



    valHolder.add(new Reg96Handler(regs[96],"Language used in user interface").assignAlias("Language"));

    valHolder.add(new Real4Handler(regs[97],regs[98],"The rate of the measured travel time by the calculated travel time.").assignAlias("Travel time rate"));
    valHolder.add(new Real4Handler(regs[99],regs[100],"Reynolds number"));



    for (RegHandler v:valHolder) v.handle(this);    // handles the whole collection of registers to human readable
    // printAll4Test();
    HTMLStabber foo=new HTMLStabber();
    foo.setMain(this);


    String html=null; 
    if (third==null) html=foo.processFile("com/gmail/medvedinho/TUFProxy/index.html");
    else html=foo.processFile(third);
    severi.setHtml(html);

    severi.launch();					// launches the webserver or overwrites the html
   }
   //else System.out.println("FETCHED MATHING REGS");
   System.out.println("WAITING");
   try
   {
    Thread.sleep(150000);				// waits give or take 2 and a half mins
   }							// and tries to fetch feed values again
   catch (Exception e)
   {
    System.out.println("Could not sleep, exiting "+e);
    System.exit(1);
   }
  }
 }

 // test method

 public void printAll4Test()
 {
  for(String s:interpretor.keySet()) System.out.println(s+":"+interpretor.get(s)[0]);
 }

 // interpretes reg name to reg value for html stabber (preprocessor)

 public String peekCode(String key,int t)
 {
  if (!interpretor.containsKey(key)) return "N/A";
  int i=current-t;
  if (i<0) i=12+i;
  if (i<0) return ("N/A");
  String retval= interpretor.get(key)[i];
  if (retval==null) return ("N/A");
  else return retval;
 }

 // stores or updates a value to the interpretor dictionary

 public void addCode(String id, String content)
 {
  if (interpretor.containsKey(id))
  {
   String [] temp = interpretor.get(id);
   temp[current]=content;
  }
  else 
  {
   String[] temp=new String[12];
   temp[current]=content;
   interpretor.put(id,temp);
	
  }
 }

 


} 
