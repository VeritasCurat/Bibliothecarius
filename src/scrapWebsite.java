import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
 
class Book{
	public String title;
	public String author;
	public String publisher;
	public String covertext;
	public String blurb; //Klappentext
	public double rating;
	
	public Book (String title, String author, String publisher, String blurb, double rating) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.blurb = blurb;
		this.rating = rating;
	}
}

public class scrapWebsite {

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		// TODO Auto-generated method stub

		infosBuecher("Metro 2033");
		//ThemaZuBuecherliste("");
		//autorBuecher("Tolkien");
		//aehnlicheBuecher("Metro 2033");
		//search_title("Metro 2033", 5);
	}
	
	
	
	public static Book infosBuecher(String title) throws UnsupportedEncodingException, IOException{
		Book book;
		//link zu buch finden
			String search_1 = "https://www.goodreads.com/search?page=1&query="+title+"&tab=books&utf8=%E2%9C%93";
			org.jsoup.nodes.Document doc =  Jsoup.connect(search_1).get();
			String linkBuch = doc.getElementsByTag("tr").first().html().substring(doc.getElementsByTag("tr").first().html().indexOf("href")+6, doc.getElementsByTag("tr").first().html().indexOf(">", doc.getElementsByTag("tr").first().html().indexOf("href"))-1);
				//System.out.println(linkBuch);

		//link oeffnen und daten lesen
		String author=""; String publisher=""; String blurb=""; double rating=0;
		doc = Jsoup.connect("https://www.goodreads.com"+linkBuch).userAgent("bot101").get();
			//System.out.println(doc.html());
		title = doc.select("title").text().substring(0,doc.select("title").text().indexOf("by")-1);
		author = doc.select("title").text().substring(doc.select("title").text().indexOf("by")+3);
		blurb = doc.html().substring(doc.html().indexOf("span id=\"freeText")+47, doc.html().indexOf("</span>", doc.html().indexOf("span id=\"freeText"))); //TODO: show less umbruecken
		rating = Double.parseDouble(doc.html().substring(doc.html().indexOf("ratingValue")+13, doc.html().indexOf("ratingValue")+17).toString());
		
		book = new Book(title, author, publisher, blurb, rating);
			System.out.println(book.title);
			System.out.println(book.author);
			System.out.println(book.publisher);
			System.out.println(book.blurb);
			System.out.println(book.rating);		
		return book;
	}
		
	public static ArrayList<String> autorBuecher(String autor) throws UnsupportedEncodingException, IOException{
		ArrayList<String> results = new ArrayList<>();
		//link für autor finden
			String search_1 = "https://www.goodreads.com/search?page=1&query="+autor+"&tab=books&utf8=%E2%9C%93";
			org.jsoup.nodes.Document doc =  Jsoup.connect(search_1 + URLEncoder.encode(search_1, "UTF-8")).get();
				//System.out.println(doc.html());
				//TODO: funzt whrsch nicht immer
			String linkAutor = doc.html().substring(doc.html().indexOf("www.goodreads.com/author/show/")+30, doc.html().indexOf("\"", doc.html().indexOf("/www.goodreads.com/author/show/")));
				//System.out.println(linkAutor);
		
		doc = Jsoup.connect("https://www.goodreads.com/author/list/"+linkAutor).userAgent("bot101").get();
		org.jsoup.select.Elements results_doc = doc.getElementsByTag("tr");
		
		String link="";
		for (Element result_doc : results_doc) {
			if(!result_doc.text().contains("�"))break; //TODO: dirty fix
			
			//TODO: link-teil furchtbar, auswahl über html string. gibt es möglichtkeit das <href>-tag des <title> tag auszuwählen??
			if(result_doc.html().contains("href")) link = "\n link:"+result_doc.html().substring(result_doc.html().indexOf("href")+6, result_doc.html().indexOf(">", result_doc.html().indexOf("href"))-1);
		    results.add(result_doc.text().substring(0, result_doc.text().lastIndexOf("�")+1)+link);
		}
		for(String s: results)System.out.println(s);
		return results;
	}
	
	public static ArrayList<String> aehnlicheBuecher(String title) throws UnsupportedEncodingException, IOException{
		ArrayList<String> results = new ArrayList<>();

		//finde link zu buch ~ rufe dafür 1 ergebnis 
			String search_1 = "https://www.goodreads.com/search?page=1&query="+title+"&tab=books&utf8=%E2%9C%93";
			org.jsoup.nodes.Document doc =  Jsoup.connect(search_1 + URLEncoder.encode(search_1, "UTF-8")).get();
			org.jsoup.select.Elements first = doc.getElementsByTag("tr");
				//System.out.println(first.html());
			String link= "https://www.goodreads.com."+first.html().substring(first.html().indexOf("href")+6, first.html().indexOf(">", first.html().indexOf("href"))-1);
				//System.out.println(link);
			doc = Jsoup.connect(link + URLEncoder.encode(search_1, "UTF-8")).userAgent("bot101").get();
				//TODO: funzt u.u. nicht immer
			String linktext_schlecht = doc.html().substring(doc.html().indexOf("/trivia/work/")+13,doc.html().indexOf("\"", doc.html().indexOf("/trivia/work/"))); //System.out.println("link: "+linktext_ann1);
						
		//similar aufrufen
			String similar = "https://www.goodreads.com/book/similar/"+linktext_schlecht;
			//System.out.println(similar);

			doc =  Jsoup.connect(similar).get();
			//System.out.println(doc.html());
			
		//liste der ähnlichen bücher sammeln
			org.jsoup.select.Elements results_doc = doc.getElementsByTag("tr");
			for (Element result_doc : results_doc) {
				//TODO: link-teil furchtbar, auswahl über html string. gibt es möglichtkeit das <href>-tag des <title> tag auszuwählen??
				results.add(result_doc.text().substring(0, result_doc.text().lastIndexOf("�"))+"\n link:"+result_doc.html().substring(result_doc.html().indexOf("href")+6, result_doc.html().indexOf(">", result_doc.html().indexOf("href"))-1));
			}		
			for(String s: results)System.out.println(s);
			
			return results;
	}
	
	/**
	 * searches for title 
	 * @param search
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public static ArrayList<String> search_title(String search, int maxresultpages) throws UnsupportedEncodingException, IOException{
		String search_2 = "https://www.goodreads.com/search?page=1&query="+search+"&tab=books&utf8=%E2%9C%93";
		String search_1 = "https://www.goodreads.com/search?query="+search;
		
		System.out.println(search_1);
		
		//find number of result pages
			org.jsoup.nodes.Document doc =  Jsoup.connect(search_2).userAgent("bot101").get();
			String title ="";
			for(Element meta : doc.select("meta")) if(meta.attr("name").contains("title"))title = meta.attr("content"); //search for meta attr that contains title
			if(title.length() > 0 && title.contains("showing") && title.contains("of"))title = title.substring(title.indexOf("showing")+10, title.indexOf("of", title.indexOf("showing"))-1);
			int pages_number = Math.min(maxresultpages, Integer.parseInt(title));
					
		//go through result pages
			ArrayList<String> results = new ArrayList<>();
			
			for(int page = 1; page < pages_number; page++){
				String url =  "https://www.goodreads.com/search?page="+page+"&query="+search+"&tab=books&utf8=%E2%9C%93";
				doc =  Jsoup.connect(url).userAgent("usrdasf").get();
	       
				//TODO: selektion des docs präzisieren um 
				org.jsoup.select.Elements results_doc = doc.getElementsByTag("tr");
				for (Element result_doc : results_doc) {
					//TODO: link-teil furchtbar, auswahl über html string. gibt es möglichtkeit das <href>-tag des <title> tag auszuwählen??
					results.add(result_doc.text().substring(0, result_doc.text().lastIndexOf("�"))+"\n link:"+result_doc.html().substring(result_doc.html().indexOf("href")+6, result_doc.html().indexOf(">", result_doc.html().indexOf("href"))-1));
				}
				System.out.println(page+"/"+pages_number);
			}
			
			for(String s: results)System.out.println(s);
		return results;
	}
	
	
	//TODO: charakter gegeben -> welches Buch?
	
	//TODO: Bücher für Themem
	public static ArrayList<String> ThemaZuBuecherliste(String thema) throws UnsupportedEncodingException, IOException{
		ArrayList<String> liste = new ArrayList<>();
		
		ArrayList<String> title = new ArrayList<>();
		ArrayList<String> author = new ArrayList<>();

		
		String search_1 = "https://www.goodreads.com/shelf/show/"+thema;
					
			
		org.jsoup.nodes.Document  doc =  Jsoup.connect(search_1).userAgent("usrdasf").get();
		//System.out.println(doc.html());
		
	       
		//TODO: selektion des docs präzisieren um 
				org.jsoup.select.Elements results_titles = doc.select("a").select(".bookTitle");
				
				for (Element result_title : results_titles) {
					//System.out.println(result_title.text());
					
					//TODO: link-teil furchtbar, auswahl über html string. gibt es möglichtkeit das <href>-tag des <title> tag auszuwählen??
					title.add(result_title.text());
				}
				
				org.jsoup.select.Elements results_authors = doc.select("a").select(".authorName");
				
				for (Element result_author : results_authors) {
					//System.out.println(result_author.text());
					
					//TODO: link-teil furchtbar, auswahl über html string. gibt es möglichtkeit das <href>-tag des <title> tag auszuwählen??
					author.add(result_author.text());
				}
				
				
				//merge //TODO: verbessern??
				for(int i=0; i<title.size(); i++) {
					System.out.println(title.get(i).toString() +" by "+author.get(i).toString());
					
					//liste.add(title.get(i)+" by "+author.get(i));
				}
				
				
				
			
			
			//for(String s: liste)System.out.println(s);
		return liste;
	}
}
