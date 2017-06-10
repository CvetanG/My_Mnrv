package app;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
	
	public static String currencyFormater(String curr) {
		curr = curr.replace(",", "");
		curr = curr.replace("$", "");
		double dCurr = Double.parseDouble(curr);
		DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		df.setMinimumFractionDigits(2);
	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setCurrencySymbol("");
	    dfs.setMonetaryDecimalSeparator(',');
	    dfs.setGroupingSeparator(' ');
	    df.setDecimalFormatSymbols(dfs);
	    String formCur = df.format(dCurr);
	    return formCur;
	}
	
	public static String clearFormatCurr(String curr) {
		curr = curr.substring(0, curr.length()-4);
		String result = currencyFormater(curr);
		return result;
	}
	
	
	// Using this in BGNUSD
	public static String replaceCurr(String curr) {
		curr = curr.replace(".", ",");
		return curr;
	}
	
	public static void getCoinsFromTavex() throws IOException {
		
		String myUrl = "http://www.tavex.bg/zlato";
		
		Document temp = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Elements paginations = temp.getElementsByClass("pagination__item");
		
		int i = 0;
		for (Element element : paginations) {
			i ++;
		}
		
		for (int j = 1; j < i; j++) {
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
						String curCoin = element.child(0).ownText();
						System.out.println(curCoin);
						String htmlurElement = element.toString();
						Document document = Jsoup.parse(htmlurElement);
						Elements myElements = document.getElementsByClass(divPriceClass);
						for (Element newElement : myElements) {
							String result_01 = clearFormatCurr(newElement.child(0).ownText());
							System.out.println("Sell: " + result_01);
							String result_02 = clearFormatCurr(newElement.child(1).child(0).ownText());
							System.out.println("Buy: " + result_02);
						}
					}
				}
			}
		}
	}
	
	// Not using this method
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
	
	public static void getBGNUSD() throws IOException {
		String myUrl = "https://ebb.ubb.bg/Log.aspx";

		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Element div = doc.getElementById("currency1").getElementsByTag("tr").get(5);
		
		String result_01 = replaceCurr(div.child(3).ownText());
		System.out.println("USD Buy:: " + result_01);
		
		String result_02 = replaceCurr(div.child(4).ownText());
		System.out.println("USD Sell: " + result_02);

	}
	
	public static void getXAUUSD() throws IOException {
		String myUrl = "https://www.bloomberg.com/quote/XAUUSD:CUR";

		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Elements div = doc.getElementsByClass("price");
		
		System.out.println(div.get(0).ownText());
		String result = currencyFormater(div.get(0).ownText());
		System.out.println("XAU_USD:: " + result);
		
	}
	
	public static void getXAUBGN() throws IOException {
		String myUrl = "http://www.bnb.bg/Statistics/StExternalSector/StExchangeRates/StERForeignCurrencies/index.htm";

		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Element div = (Element) doc.getElementsByClass("table").get(0).childNode(4).childNode(61).childNode(7);
		
		System.out.println(div.ownText());
		String result = currencyFormater(div.ownText());
		System.out.println("XAU_BGN: " + result);
		
	}
	
	public static void getEthereumPrice() throws IOException {
		String myUrl = "https://coinmarketcap.com/currencies/ethereum/";
		
		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Element div = (Element) doc.getElementsByClass("col-xs-6 col-sm-8 col-md-4 text-left").get(0).childNode(1);
		
		System.out.println(div.ownText());
		String result = currencyFormater(div.text());
		System.out.println("Ethereum USD: " + result);
		
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start Program");
		long startTime = System.currentTimeMillis();
		
		myCoinsStrings.add("1 унция златен канадски кленов лист");
//		myCoinsStrings.add("1 унция златна австрийска филхармония");
		myCoinsStrings.add("5 грама златно кюлче PAMP Фортуна");
		myCoinsStrings.add("1 унция златна китайска панда от 2009");
		
//		getCoinsFromTavex();
//		getBGNUSD();
		
//		getXAUUSD();
		
//		getXAUBGN();
		
		getEthereumPrice();
		
		long endTime   = System.currentTimeMillis();
		duration(startTime, endTime);
		
	}
}
