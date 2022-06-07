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
import java.io.IOException;
import java.util.*;  

//Unused imports for commented out code. Import Libraries/HTMLUnit .jar files if imports are used 
/* 
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
*/

public class jsoup_JS_Test { 
    public static void main(String[] args) throws Exception {
        int i = 0;
        
        //Attempt to connect and load a HTML page using javascript. EXCEPTION END Error followed by incomplete html page
        /*
        WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.waitForBackgroundJavaScript(40_000);
        HtmlPage myPage = webClient.getPage("https://skinport.com/market?sticker=NiKo+%28Foil%29+%7C+Katowice+2019");
        System.out.println(myPage.asXml());
        */
        
        //Only prints prices ebay example 
        String url = "https://www.ebay.com.au/sch/i.html?_from=R40&_trksid=p2380057.mc570.l1313&_nkw=car&_sacat=0";
        Document document = Jsoup.connect(url).get();
        Elements listing = document.select(".s-item__price");

        for (Element iListing : listing) {
            System.out.println(iListing.text());
            i++;
        }
        System.out.println(i + " - Total prices found on this page");
    }
}