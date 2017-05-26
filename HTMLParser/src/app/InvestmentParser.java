package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InvestmentParser {
	
	static String divProdClass = "product__content";
	static String divPriceClass = "product__price";
	static List<String> myCoinsStrings = new ArrayList<>();
	
	static String divGoldClass = "chart__value";
	
	private static void duration(long startTime, long endTime) {
		long totalTime = endTime - startTime;
		
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int milisec = (int) (totalTime - ((seconds * 1000) + (minutes * 60 * 1000)));
		
		System.out.print("Elapsed time: ");
		StringBuilder sb = new StringBuilder(64);
        sb.append(minutes);
        sb.append(" min, ");
        sb.append(seconds);
        sb.append(" sec. ");
        sb.append(milisec);
        sb.append(" milsec.");
        
		System.err.println(sb.toString());
		System.out.println();
		System.out.println("End Program");
	}
	
	public static void getFromTavex() throws IOException {
		
		String myUrl = "http://www.tavex.bg/zlato";
		
		Document temp = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Elements paginations = temp.getElementsByClass("pagination__item");
		
		int i = 0;
		for (Element element : paginations) {
			i ++;
		}
		
		for (int j = 1; j < i-1; j++) {
			Document tavex = Jsoup.connect(myUrl + "/page/" + j + "/#s")
					.timeout(10000).validateTLSCertificates(false)
					.get();
		
			Elements allElements = tavex.getElementsByClass(divProdClass);
			
			for (Element element : allElements) {
				for (String myString : myCoinsStrings) {
					if (myString.equals(element.child(0).ownText())) {
						// element.nextElementSibling()!=null
						// ? element.nextElementSibling()
						// .ownText()
						// : "")) {
						System.out.println(element.child(0).ownText());
						String htmlurElement = element.toString();
						Document document = Jsoup.parse(htmlurElement);
						Elements myElements = document.getElementsByClass(divPriceClass);
						for (Element newElement : myElements) {
							System.out.println("Sell: " + newElement.child(0).ownText());
							System.out.println("Buy: " + newElement.child(1).child(0).ownText());
						}
					}
				}
			}
		}
	}
	
	public static void getGoldFromTavex() throws IOException {
		String myUrl = "http://www.tavex.bg/zlato/#charts-modal";
		
		
			Document tavex = Jsoup.connect(myUrl)
					.timeout(10000).validateTLSCertificates(false)
					.get();
			
			
			Elements allElements = tavex.getElementsByClass(divGoldClass);
			
			for (Element element : allElements) {
				System.out.println("Gold: " + element.child(0).ownText());
			}
		
		
	}
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start Program");
		long startTime = System.currentTimeMillis();
		
		myCoinsStrings.add("1 унция златен канадски кленов лист");
//		myCoinsStrings.add("1 унция златна австрийска филхармония");
		myCoinsStrings.add("5 грама златно кюлче PAMP Фортуна");
		
		getFromTavex();
		getGoldFromTavex();
		
		long endTime   = System.currentTimeMillis();
		duration(startTime, endTime);
		
	}
}
