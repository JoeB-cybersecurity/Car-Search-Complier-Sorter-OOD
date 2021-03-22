(ABANDONED/OUT OF DATE!!!) This is my Car Search Compiler/Sorter program (Central_Marketplace_Project), built on the Eclipse IDE (Java) environment. It collects data from listings specified by the user (keyword/model of car) from Australian sites such as Carsales, Ebay and Gumtree and consolidates it into all one big list. This program only searches for cars and vehicles.

This program can currently collect the name, image, link (URL) and price (including proper conversion to double type of price to sort listings) from all listings that the user specifies (eg. "holden sv6") by manipulating the URL that the site uses to load results and scraping elements of HTML (such as href, scr, labels ect.) into one big array list. They can also specify how many pages the program will process (number of items to be included in search), location, sort method and whether to include buyitnow or auction listings. 

It uses the jsoup library (web scraper) to fill a 3 dimensional (Items) and 2 dimensional array (PriceDouble) for all the item listings specified by user, and when sorted is reorganised into a 2 dimensional (sorted items) and 1 dimensional array (itemPrice).


You many use and modify this program in any way. I intended to implement this code into a website but decided against it and just share raw code. This program requires a lot of optimisation and maintenance (everything is in main, no functions, no database, URL and HTML dependent (lot of formatting (hard coded substring/indexOf)), unnecessary arrays, gumtree images don't work due to dynamic page loading? (img is set to "hidden" not "shown"), limitations of web scraping (Facebook marketplace has HTML classes that I canno't get my head around)). This project is entirely experimental so dont expect this code to be perfect! This was a solo side project with a lot of prototyping, trial and error. 

NOTE: There are a ton of comments and "meaningful" variable names to help you through this code and how to play around with it. I've also made sure to label and separate each listing so it doesn't get mixed while testing and remove/disregard all null spaces (mabye should've used list/dynamic array here). Also a lot of it has been reused but only slightly modified eg. (if (pagesIdx >= 1)/if (pagesIdx >=2)) in order to get around URLs specifying page numbers. 


!!!IMPORTANT!!! TO INSTALL -
TO IMPORT JSOUP LIBRARY (REQUIRED OR IT WONT WORK), MAKE SURE TO ALSO DOWNLOAD JSOUP JAVA FILE,
	//Right click on project -> Properties -> Java Build Path -> Click on Add External Jar in Libraries -> Select Jsoup -> apply == Refresh project

Then run src - scrape - Central_Marketplace_Project.java

To search for a listing, type in console then press enter twice (usrInput)

Note : there is also a class called (ebayStats.java) which collects listings from ebay and calculates item number and average price of any item (prototype). 
