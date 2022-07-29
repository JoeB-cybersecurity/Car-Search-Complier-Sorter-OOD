package scrape;

/* Must first import Jsoup.jar into referenced libraries 
 * In Ecliplse - Right click project -> Properties -> Java Build Path -> 
 * Click on Add External Jar in Libraries -> add libraries/jsoup.jar -> Apply
 * 
 * In Visual Studio Code - Navigate and expand Java Projects tab on left -> 
 * Expand Referenced Libraries -> + Button -> add libraries/jsoup.jar 
 * 
 * Limitations of this project include not working on pages that load 
 * using javascript (disable js and reload site to check what the script can see)
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.jsoup.Connection;
import java.util.*;  

	public class Central_Marketplace_Project { 
		
	public static void main(String[] args) throws Exception {
		//Global Variables
		int pagesIdx = 1; //CHOOSE NUMBER PAGES LOADED
		int noItems = pagesIdx * 150;  //max stored number of items for a site (relient on pagesIdx)
		
		String state = "nsw";  //CHOOSE STATE (nsw ect.)
		String sortMethod = "best match"; //CHOOSE SORT (best match, lowest, newest)
		
		//Ebay exclusive
		String ebayBuyNow = "&rt=nc&LH_BIN=1"; //BUYNOW exclusive
  	    String ebayAuction = "&rt=nc&LH_Auction=1"; //AUCTION
		
  	    
		//3D dimensional array for storage (Origin Site) (item) (name, price, link, image)   --- items should be set to page limit 
		 String Items [] [] [] = new String [3] [noItems] [4];       //ebay[0][][]   gumtree[1][][]   Carsales[2][][]     
		 double PriceDouble [] [] = new double [3] [noItems];  //store double data type prices
		 
		
		//READING USER INPUT (URL search)
  		Scanner sc= new Scanner(System.in);  
  	    System.out.print("Enter a string: ");  
  	    String usrInput= sc.nextLine();   //CHOOSE SEARCH 
  	    usrInput = usrInput.replaceAll(" ", "+"); //include for all other characters
  	    
  	    
  	    
  	    
  	    
  	    
  	    
  	    
  	    
  	    
  	    
  	    
  	    //EBAY// [0]
  	    String sort = "12"; //default best match 
  	    if (sortMethod.equalsIgnoreCase("lowest")) {
  		  	sort = "15"; //lowest
	    }
  	  
  	    if (sortMethod.equalsIgnoreCase("newest")) {
  	    	sort = "10"; // newest
  	    }
  	    
  	    
  	    //Convert state to post code
  	    String postcode = "";
  	    if (state.equalsIgnoreCase("nsw")) {
  	    	postcode = "23000";
  	    }
  	    if (state.equalsIgnoreCase("act")) {
	    	postcode = "2600";
	    }
  	    if (state.equalsIgnoreCase("vic")) {
	    	postcode = "3500";
	    }
 		if (state.equalsIgnoreCase("qld")) {
	    	postcode = "4500";
	    }
 		if (state.equalsIgnoreCase("sa")) {
	    	postcode = "5500";
	    }
 		if (state.equalsIgnoreCase("wa")) {
	    	postcode = "6300";
	    }
 		if (state.equalsIgnoreCase("tas")) {
  	    	postcode = "7300";
  	    }
 		if (state.equalsIgnoreCase("nt")) {
  	    	postcode = "0850";
  	    }
  	    
 		//IPG number of items on pages
  	    //LH_pref IS AUSTRALIA ONLY
 		//set items in array 
 		int i = 0;
 		int a = 0;
 	   	int b = 0; 
 	   	int c = 0;
 		
  	    
  	    
  	    if (pagesIdx >= 1) {
  	    String url = "https://www.ebay.com.au/sch/i.html?_from=R40&_nkw=" + usrInput + "&_sacat=0&LH_TitleDesc=0&_sop=" + sort + "&_ipg=100" + ebayBuyNow + "&LH_PrefLoc=1" + "&_stpos="+ postcode;
  	    Document document = Jsoup.connect(url).get();
	    System.out.println(url);
	    //set items SHOULD BE CONSISTANT 
  	   //locates class with link, only works with specificed URL
	    Elements Elinks = document.select(".s-item__link");
	    Elements Enames = document.select(".s-item__title");
	    Elements Eprices = document.select(".s-item__price");
	    Elements Eimages = document.select(".s-item__image-img");
	   
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
			   Items[0][b][0] = name.html().substring(48, name.html().length())  + " - EBAY Listing";
		   }
		   b++;
	   }
	   
	   for (Element image : Eimages) {  
          Items[0][c][3] = image.attr("abs:src"); 
	       c++;
	   }
	   
  	    }
	   
  	    
  	  if (pagesIdx >= 2) {
  		  for (int x = 2; x <= pagesIdx; x++) {
  		  
    	    String url = "https://www.ebay.com.au/sch/i.html?_from=R40&_nkw=" + usrInput + "&_sacat=0&LH_TitleDesc=0&_sop=" + sort + "&_ipg=100" + ebayBuyNow + "&_pgn=" + Integer.toString(x) + "&LH_PrefLoc=1" + "&_stpos="+ postcode;
    	    Document document = Jsoup.connect(url).get();
  	    System.out.println(url);
  	    //set items SHOULD BE CONSISTANT 
    	   //locates class with link, only works with specificed URL
  	    Elements Elinks = document.select(".s-item__link");
  	    Elements Enames = document.select(".s-item__title");
  	    Elements Eprices = document.select(".s-item__price");
  	    Elements Eimages = document.select(".s-item__image-img");
  	   
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
  			   Items[0][b][0] = name.html().substring(48, name.html().length())  + " - EBAY Listing";
  		   }
  		   b++;
  	   }
  	   
  	   for (Element image : Eimages) {  
            Items[0][c][3] = image.attr("abs:src"); 
  	       c++;
  	   }
  	   
    	    }
  	  }
	   
	   
	   
	    //GumTree [1]
 	    //
	    String asort = "?sort=rank"; //default best match
 	    if (sortMethod.equalsIgnoreCase("lowest")) {
 	    	asort = "?sort=price_asc"; //lowest
	    }
 	  
 	    if (sortMethod.equalsIgnoreCase("newest")) {
 	    	asort = ""; //Most recent (default)
 	    }
 	    
 	  //set items in array 
 	   int ai = 0;
 	   int aa = 0;
 	   int ab = 0; 
 	  // int ac = 0;

	    
	    
	    
	    
	    
	    if (pagesIdx >= 1) {
 	    String aurl = "https://www.gumtree.com.au/s-cars-vans-utes/" + state + "/" + usrInput + "/k0c18320l3008839" + asort + "&price=1000.00__";;
 	    Document adocument = Jsoup.connect(aurl).get();
	    System.out.println(aurl);
 	    
	    //set items SHOULD BE CONSISTANT 
 	   //locates class with link, only works with specificed URL
	    Elements aElinks = adocument.select(".user-ad-row");
	    Elements aEnames = adocument.select(".user-ad-row__title");
	    Elements aEprices = adocument.select(".user-ad-price__price");
	   // Elements aEimages = adocument.select(".user-ad-row__main-image-wrapper"); //references it, but hidden. 
	  
	   for (Element aprice : aEprices) {
		   Items[1][ai][1] = aprice.html();
		   //formatting price (,)
		   if (Items[1][ai][1].contains(",")) {
			   Items[1][ai][1] = Items[1][ai][1].replaceAll(",","");
		   }
		   
		   PriceDouble[1][ai] = Double.parseDouble(Items[1][ai][1].substring(1));
	       ai++;
	   }
	   
	   for (Element alink : aElinks) {
		   Items[1][aa][2] = alink.attr("abs:href");
	       aa++;
	   }
	   
	   for (Element aname : aEnames) {
		   Items[1][ab][0] = aname.html() + " - GUMTREE Listing";  
		   
		   ab++;
	   }
	   
	   ////TEMPORARY FIX FOR IMAGE LINKS////// set other images as default 
	   
	 //  for (Element aimage : aEimages) {  
	//	  String old = (aimage.html().substring(aimage.html().indexOf("=") + 2, aimage.html().indexOf("src") -2 ));
	//	  String replace = ("user-ad-row__image image image--is-visible");
	//	  aimage.html().replaceAll(old,replace);   //IT CHANGES, CANT IMPORT
		   
		   
     //    Items[1][ac][3] = aimage.attr("abs:src");  //FIX IMAGES
	 //    System.out.println(aimage.html());  
     //    ac++;
	 //  }
	    }
	    
	    if (pagesIdx >=2) {
	    	for (int y = 2; y <=pagesIdx; y++) {
	    
	    	String aurl = "https://www.gumtree.com.au/s-cars-vans-utes/" + state + "/" + usrInput + "/page-" + Integer.toString(y) + "/" + "k0c18320l3008839" + asort + "&price=1000.00__";;
	 	    Document adocument = Jsoup.connect(aurl).get();
		    System.out.println(aurl);
	 	    
		    //set items SHOULD BE CONSISTANT 
	 	   //locates class with link, only works with specificed URL
		    Elements aElinks = adocument.select(".user-ad-row");
		    Elements aEnames = adocument.select(".user-ad-row__title");
		    Elements aEprices = adocument.select(".user-ad-price__price");
		   // Elements aEimages = adocument.select(".user-ad-row__main-image-wrapper"); //references it, but hidden. 
		  
		   for (Element aprice : aEprices) {
			   Items[1][ai][1] = aprice.html();
			   //formatting price (,)
			   if (Items[1][ai][1].contains(",")) {
				   Items[1][ai][1] = Items[1][ai][1].replaceAll(",","");
			   }
			   
			   PriceDouble[1][ai] = Double.parseDouble(Items[1][ai][1].substring(1));
		       ai++;
		   }
		   
		   for (Element alink : aElinks) {
			   Items[1][aa][2] = alink.attr("abs:href");
		       aa++;
		   }
		   
		   for (Element aname : aEnames) {
			   Items[1][ab][0] = aname.html() + " - GUMTREE Listing";  
			   
			   ab++;
		   }
		   
		   ////TEMPORARY FIX FOR IMAGE LINKS////// set other images as default 
		   
		 //  for (Element aimage : aEimages) {  
		//	  String old = (aimage.html().substring(aimage.html().indexOf("=") + 2, aimage.html().indexOf("src") -2 ));
		//	  String replace = ("user-ad-row__image image image--is-visible");
		//	  aimage.html().replaceAll(old,replace);   //IT CHANGES, CANT IMPORT
			   
			   
	     //    Items[1][ac][3] = aimage.attr("abs:src");  //FIX IMAGES
		 //    System.out.println(aimage.html());  
	     //    ac++;
		 //  }
	   
	    	}
	    }
	   
	   
	   
	   
	   
	   //Carsales [2] 
	   //
	   String bsort = "&sort=TopDeal"; //default best match
	    if (sortMethod.equalsIgnoreCase("lowest")) {
	    	bsort = "&sort=~Price"; //lowest
	    }
	  
	    if (sortMethod.equalsIgnoreCase("newest")) {
	    	bsort = "&sort=LastUpdated"; //most recent 
	    }
	   
	    
	    String formatState = "";
	    if (state.equalsIgnoreCase("nsw")) {
	    	formatState = "New+South+Wales";
	    }
	    if (state.equalsIgnoreCase("act")) {
	    	postcode = "ACT";
	    }
  	    if (state.equalsIgnoreCase("vic")) {
	    	postcode = "Victoria";
	    }
 		if (state.equalsIgnoreCase("qld")) {
	    	postcode = "Queensland";
	    }
 		if (state.equalsIgnoreCase("sa")) {
	    	postcode = "South+Australia";
	    }
 		if (state.equalsIgnoreCase("wa")) {
	    	postcode = "Western+Australia";
	    }
 		if (state.equalsIgnoreCase("tas")) {
  	    	postcode = "Tasmania";
  	    }
 		if (state.equalsIgnoreCase("nt")) {
  	    	postcode = "Northern+Territory";
  	    }
	    
	  //set items in array 
		   int bi = 0;
		   int ba = 0;
		   int bc = 0;
		   
		   
		   
		   
		 if (pagesIdx >= 1) {  
	    String burl = "https://www.carsales.com.au/cars/?q=(And.Service.carsales._.CarAll.keyword(" + usrInput + ")._.State." + formatState + ".)" + bsort;
	    //Document bdocument = Jsoup.connect(burl).get();
		
		//Setting header information for our connection request to simulate a normal browser session 
		//Avoids HTML 403 Forbidden errors
		//https://blog.krybot.com/a?ID=00600-e57e6687-2221-4d74-8f43-8033cd819961
		Connection connection = Jsoup.connect(burl);
		connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		connection.header("Accept-Encoding", "gzip, deflate, sdch");
		connection.header("Accept-Language", "zh-CN,zh;q=0.8");
		connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

		Document bdocument = connection.get();

	    System.out.println(burl);
	    
	    //set items SHOULD BE CONSISTANT 
	   //locates class with link, only works with specificed URL
	    Elements bElinks = bdocument.select("a[href^=/cars/details/]");  //ONLY REFERENCE ONCE, does it x5 due to extra buttons
	    Elements bEprices = bdocument.select(".price");
	    Elements bEimages = bdocument.select(".carousel-item.active");

	   for (Element bprice : bEprices) {

		//Updated find price substring value
		   Items[2][bi][1] = bprice.html().substring(bprice.html().indexOf(">") + 1, bprice.html().indexOf("*<")); //find price
		   //formatting price (,/*)
		   if (Items[2][bi][1].contains(",") || Items[2][bi][1].contains("*")) {
			   Items[2][bi][1] = Items[2][bi][1].replaceAll(",","");
			   Items[2][bi][1] = Items[2][bi][1].replaceAll("\\*","");
		   }
		   PriceDouble[2][bi] = Double.parseDouble(Items[2][bi][1].substring(1)); //$ sign
	       bi++;
	   }
	   
	   for (Element blink : bElinks) {
		   
		   if (ba % 5 == 0) {
			   Items[2][ba/5][2] = blink.attr("abs:href"); //Fix for extra links
			   ba++;
		   }
		   else {
			   ba++;
		   }
		   
	       
	   }
	   
	   for (Element bimage : bEimages) {  
        Items[2][bc][3] = bimage.html().substring(bimage.html().indexOf("c=") + 3, bimage.html().indexOf(">") -1);  
	       bc++;
	   }
		 }
	   
	
		 
		 if (pagesIdx >= 2) {
			 for (int z = 12; z <= pagesIdx*12; z = z + 12) {
				 //12 items == Approx 1 page (Increment 12 for 1 page) 
				 
			 String burl = "https://www.carsales.com.au/cars/?q=(And.Service.carsales._.CarAll.keyword(" + usrInput + ")._.State." + formatState + ".)" + bsort + "&offset=" + z;
			    Document bdocument = Jsoup.connect(burl).get();
			    System.out.println(burl);
			    
			    //set items SHOULD BE CONSISTANT 
			   //locates class with link, only works with specificed URL
			    Elements bElinks = bdocument.select("a[href^=/cars/details/]");  //ONLY REFERENCE ONCE, does it x5 due to extra buttons
			    Elements bEprices = bdocument.select(".price");
			    Elements bEimages = bdocument.select(".carousel-item.active");
			    
			   for (Element bprice : bEprices) {
				   Items[2][bi][1] = bprice.html().substring(bprice.html().indexOf(">") + 1, bprice.html().indexOf("*<")); //find price
				   //formatting price (,/*)
				   if (Items[2][bi][1].contains(",") || Items[2][bi][1].contains("*")) {
					   Items[2][bi][1] = Items[2][bi][1].replaceAll(",","");
					   Items[2][bi][1] = Items[2][bi][1].replaceAll("\\*","");
				   }
				   PriceDouble[2][bi] = Double.parseDouble(Items[2][bi][1].substring(1)); //$ sign
			       bi++;
			   }
			   
			   for (Element blink : bElinks) {
				   
				   if (ba % 5 == 0) {
					   Items[2][ba/5][2] = blink.attr("abs:href"); //Fix for extra links
					   ba++;
				   }
				   else {
					   ba++;
				   }
				   
			       
			   }
			   
			   for (Element bimage : bEimages) {  
		        Items[2][bc][3] = bimage.html().substring(bimage.html().indexOf("c=") + 3, bimage.html().indexOf(">") -1);  
			       bc++;
			   }
			 }
		 }

		 //Carsales name conversions
		 for (int bb = 0; bb < (ba/5); bb++) { //SET TO ITEM LIMIT
			  Items[2][bb][0] = Items[2][bb][2].replaceAll("-", " ");
			  Items[2][bb][0] = Items[2][bb][0].substring(41); 
			  Items[2][bb][0] = Items[2][bb][0].substring(0, Items[2][bb][0].indexOf("/")) + " - CARSALES Listing";
			  
		   }
	   
	  
	   
	   
	   int count = 0;
	   //Sort Items
	   
	  
	   //Intended sorted arrays final 
	   double [] itemPrice = new double [noItems*3];
	   String [] [] sortedItems = new String [noItems*3] [4]; //items then attributes 0 = name, 1 = price, 2 = link, 3 = image
	  
	  //Initialisation of both itemPrice[] and sortedItems[][]
	  int h = 0;
		  for (int f = 0; f < 3; f++) {
			  for (int e = 0; e < noItems; e++) {
				  if (Items[f][e][1] != null) {
					  sortedItems[h][0] = Items[f][e][0];
					  sortedItems[h][1] = Items[f][e][1];
					  sortedItems[h][2] = Items[f][e][2];
					  sortedItems[h][3] = Items[f][e][3];
					  count++;
					  h++;
					  
				  }
				  
				  
			  }
		  }
	  
	  int d = 0;
		  for (int f = 0; f < 3; f++) {		 
			  for (int e = 0; e < noItems; e++) {
				  if (PriceDouble[f][e] != 0.0) {
					itemPrice[d] = PriceDouble[f][e];
					d++;
			   }
		  	}
		 }

	  
		  
		  
	  //SORTING (BY LOWEST) Insertion sort
      for (int in = 1; in < itemPrice.length; ++in) { 
    	  if (itemPrice[in] != 0.0) {
          double key = itemPrice[in];
          String key0 = sortedItems[in][0];
          String key1 = sortedItems[in][1];
          String key2 = sortedItems[in][2];
          String key3 = sortedItems[in][3];
          
          int j = in - 1; 

          /* Move elements of arr[0..i-1], that are 
             greater than key, to one position ahead 
             of their current position */
          //MOVING ELEMENTS
          while (j >= 0 && itemPrice[j] > key) { 
              itemPrice[j + 1] = itemPrice[j]; 
              sortedItems[j+1][0] = sortedItems[j][0];
              sortedItems[j+1][1] = sortedItems[j][1];
              sortedItems[j+1][2] = sortedItems[j][2];
              sortedItems[j+1][3] = sortedItems[j][3];
              
              j = j - 1;
          } 
          itemPrice[j + 1] = key; 
          sortedItems[j+1][0] = key0;
          sortedItems[j+1][1] = key1;
          sortedItems[j+1][2] = key2;
          sortedItems[j+1][3] = key3;
          
    	  }
      } 
      
      
      for (int z = 0; z < itemPrice.length; z++) {
		  if (itemPrice[z] != 0.0) {
			  if (sortedItems[z][0] != null) {
			  System.out.println(itemPrice[z]);
			  System.out.println(sortedItems[z][0]);
			  System.out.println(sortedItems[z][1]);
			  System.out.println(sortedItems[z][2]);
			  System.out.println(sortedItems[z][3]);
			  System.out.println("\n");
			  }
		  }
	  }
      
      
      
      
      //SORTING (BEST MATCH/MOST RECENT) - Change sortMethod for URL
      //DO NOT RUN SORTING BY LOWEST!!
  for (int k = 0; k < noItems; k++) {   
    for (int j = 0; j < 3; j++) {         //Alternating between each site for one item
    	if (PriceDouble[j][k] != 0.0) {
		   if (Items[0][k][0] != null) {    
			  //System.out.println(PriceDouble[j][k]);
			  //System.out.println(Items[j][k][0]);
			  //System.out.println(Items[j][k][1]);
			  //System.out.println(Items[j][k][2]);
			  //System.out.println(Items[j][k][3]);
			  //System.out.println("\n");
		   }
    	}
	  }
    }
      
      
	  
	 
	   			
	   System.out.println("Returned searches for " + usrInput + " with a total of " + (count -1) + " found listings");
	   
	   
	
	  
 
	
	}
}	
	

