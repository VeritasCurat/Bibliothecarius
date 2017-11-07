package DrThesaurus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.swing.text.Document;

//import org.apache.http.client.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;

import com.google.gson.Gson;

public class googlen {
	
	String Begriff;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//googlen("licht spendet, hÃ¤ngt decke"); //Deckenleuchte
		//googlen("Maschine rechnet"); //computer
		
		duckduckgo("Computer");
	}
	
	public static void duckduckgo(String suche) throws UnsupportedEncodingException, IOException{
		String duckduckgo = "https://duckduckgo.com/?q="+suche;
		String charset = "UTF-8";
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
		
		org.jsoup.nodes.Document doc =  Jsoup.connect(duckduckgo + URLEncoder.encode(suche, charset)).userAgent(userAgent).get();//.select(".g>.r>a");
		org.jsoup.select.Elements links = doc.select("a[href]"); // a with href

		
		String title = doc.title();
		String a = doc.html();
		
		System.out.println(a);
		
		
		  List<org.jsoup.nodes.Element> liste = new ArrayList<org.jsoup.nodes.Element>();
		    liste.addAll(links.select("*"));
		    System.out.println(liste.size());
		    
		    
		    
			for (org.jsoup.nodes.Element e : liste) {
			   System.out.println(e.text());
			}
//			    String title = e.text();
//			    
//			    String url = e.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
//			    url = URLDecoder.decode(url.substring(url.indexOf("=") + 1, url.indexOf("&")+1), "UTF-8");
//			    if(url.endsWith("&")) url = url.substring(0, url.length()-1);
//			    
//			    if (!url.startsWith("http")) {
//			        continue; // Ads/news/etc.
//			     }
//
//			    if(title.contains("Bilder zu"))continue;
//			    if(title.contains(suche))continue;
//			    
//			    System.out.println("Title: " + title);
//			    System.out.println("URL: "+url);
//			}
	}
	
	public static void googlen(String suche) throws IOException{
		String google = "http://www.google.com/search?q=";
		String search = suche;
		String charset = "UTF-8";
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!

		org.jsoup.select.Elements links =  Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");
	    
	    
	    List<org.jsoup.nodes.Element> liste = new ArrayList<org.jsoup.nodes.Element>();
	    liste.addAll(links.select("*"));
	    System.out.println(liste.size());
	    
		for (org.jsoup.nodes.Element e : liste) {
		    String title = e.text();
		    
		    String url = e.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
		    url = URLDecoder.decode(url.substring(url.indexOf("=") + 1, url.indexOf("&")+1), "UTF-8");
		    if(url.endsWith("&")) url = url.substring(0, url.length()-1);
		    
		    if (!url.startsWith("http")) {
		        continue; // Ads/news/etc.
		     }

		    if(title.contains("Bilder zu"))continue;
		    if(title.contains(suche))continue;
		    
		    System.out.println("Title: " + title);
		    System.out.println("URL: "+url);
		}
	}

}

class GoogleResults {

    private ResponseData responseData;
    public ResponseData getResponseData() { return responseData; }
    public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
    public String toString() { return "ResponseData[" + responseData + "]"; }

    static class ResponseData {
        private List<Result> results;
        public List<Result> getResults() { return results; }
        public void setResults(List<Result> results) { this.results = results; }
        public String toString() { return "Results[" + results + "]"; }
    }

    static class Result {
        private String url;
        private String title;
        public String getUrl() { return url; }
        public String getTitle() { return title; }
        public void setUrl(String url) { this.url = url; }
        public void setTitle(String title) { this.title = title; }
        public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
    }

}
