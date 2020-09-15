package scrape;

import org.jsoup.Jsoup; //TO IMPORT, GET JSOUP JAVA FILE FROM DOWNLOADS,
//Right click on your project -> Properties -> Java Build Path ->
//Click on Add External Jar in Libraries -> apply == Refresh project
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;  

public class ebayStats { //works in theory, need to format
	
public static void main(String[] args) throws Exception {
	//3D dimensional array for storage (Origin Site) (item) (name, price, link, image, location/(Delivery))   --- items should be set to page limit 
	 String Items [] [] [] = new String [3] [200] [5];       //ebay[0][][]   gumtree[1][][]   facebookMarket[2][][]    Amazon?  
	 double PriceDouble [] [] = new double [4] [200];  //store prices copy
	 //NEED EXTRA ARRAY FOR SORRTING /s///
	
	//READING USER INPUT (SEARCHTYPE + URL)
		Scanner sc= new Scanner(System.in);  
	    System.out.print("Enter a string: ");  
	    String usrInput= sc.nextLine();   //reads string   
	    usrInput.replaceAll(" ", "+"); //incude for all other characters
   
	    
	    
	    
	    //EBAY// [0]
	    String buyingformat1 = "&rt=nc&LH_BIN=1"; //BUYNOW exclusive
	    String buyingformat2 = "&rt=nc&LH_Auction=1"; //AUCTION
	    
	    String page = "";
	    String page2 = "&_pgn=2";
	    
	    String sort = "12"; //default best match 
	    String sort1 = "15"; //lowest
	    String sort2 = "10"; // newest
	    
	    //IPG number of pages
	    //LH_pref IS AUSTRALIA ONLY
	    String url = "https://www.ebay.com.au/sch/i.html?_from=R40&_nkw=" + usrInput + "&_sacat=0&LH_TitleDesc=0&_sop=" + sort + "&_ipg=200" + buyingformat1 + page + "&LH_PrefLoc=1";
	    Document document = Jsoup.connect(url).get();
    
    //set items SHOULD BE CONSISTANT 
	   //locates class with link, only works with specificed URL
    Elements Elinks = document.select(".s-item__link");
    Elements Enames = document.select(".s-item__title");
    Elements Eprices = document.select(".s-item__price");
    Elements Eimages = document.select(".s-item__image-img");
    Elements Edelivery = document.select(".s-item__logisticsCost"); //just second half of class name (seperated) 
    
   
   
   //set items in array 
   int i = 0;
   int a = 0;
   int b = 0; 
   int c = 0;
   int d = 0;
   
   for (Element price : Eprices) {
	   
	   Items[0][i][1] = price.html();
	   
	   //formatting price (,)
	   if (Items[0][i][1].contains(",")) {
		   Items[0][i][1] = Items[0][i][1].replaceAll(",","");
	   }
	   
	   //Mutliple prices
	   if (price.html().contains("<span class=\"DEFAULT\">")) {
		   Items[0][i][1] =price.html().substring(0, price.html().indexOf("<")) + " and up to " + price.html().substring(price.html().indexOf("n>") + 2, price.html().length()); 
		   PriceDouble[0][i] = Double.parseDouble(Items[0][i][1].substring(4,price.html().indexOf("<"))); 
	   }
	   //Exclude AU + $. Get double value
	   else {
		   PriceDouble[0][i] = Double.parseDouble(Items[0][i][1].substring(4));
	   }
	   
       i++;
   }
   
   for (Element link : Elinks) {
	   Items[0][a][2] = link.attr("abs:href");
       a++;
   }
   
   for (Element name : Enames) {
	   Items[0][b][0] = name.html() + " - Ebay Listing";  
	   if (name.html().contains("<span class=\"LIGHT_HIGHLIGHT\">New listing</span>") ) { //delete new listing label
		   Items[0][b][0] = name.html().substring(48, name.html().length())  + " - Ebay Listing";
	   }
	   b++;
   }
   
   for (Element image : Eimages) {  
      Items[0][c][3] = image.attr("abs:src"); 
       c++;
   }
   
   for (Element delivery : Edelivery) {
	   Items[0][d][4] = delivery.html(); //maybe issue with local pickup (could add seperate URL) 
       d++;
   }
   
   double average = 0.0;
   double low = PriceDouble[0][0];
   double high = low;
   int lowidx = 0;
   int highidx = 0;
   
   int count = 0;

   //Print all 
   for (int k = 0; k < 200; k++) {   
	   if (Items[0][k][0] != null) {   
		   System.out.println("EBAY");
		   System.out.println(Items[0][k][0]);
		   System.out.println(Items[0][k][1]);
		   System.out.println(Items[0][k][2]);
		   System.out.println(Items[0][k][3]);
		   System.out.println(Items[0][k][4]);
		   System.out.println(PriceDouble[0][k]);
		   System.out.println("\n");
		   average = average + PriceDouble[0][k];
		   count++;
		   
		   if (PriceDouble[0][k] < low) {
			   low = PriceDouble[0][k];
			   lowidx = k;
		   }
		   
		   if (PriceDouble[0][k] > high) {
			   high = PriceDouble[0][k];
			   highidx = k;
		   }
		   
	   }
   }
   average = average / count;
   average = (int)(average * 100) /100;

   
   
   System.out.println("The average price for this item is $" + average);
   System.out.println("The lowest listing was $" + low + "\n" + Items[0][lowidx][2]);
   System.out.println("\n");
   System.out.println("The highest listing was $" + high + "\n" + Items[0][highidx][2] );
   
}
}
