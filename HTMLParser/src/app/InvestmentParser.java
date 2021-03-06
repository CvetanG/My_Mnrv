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
//	static List<String> myCoinsStrings = new ArrayList<>();
	
	static String divGoldClass = "chart__value";
	
	// for coins default value to enter formula in % cells
	static String def = "comp";
	
	public static String duration(long startTime, long endTime) {
		long totalTime = endTime - startTime;
		
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int milisec = (int) (totalTime - ((seconds * 1000) + (minutes * 60 * 1000)));
		
		StringBuilder sb = new StringBuilder(64);
		sb.append("Elapsed time: ");
        sb.append(minutes);
        sb.append(" min, ");
        sb.append(seconds);
        sb.append(" sec. ");
        sb.append(milisec);
        sb.append(" milsec.");
        
		return sb.toString();
	}
	
	public static String currencyFormater(String curr) {
		curr = curr.replace(",", "");
		curr = curr.replace("$", "");
		double dCurr = Double.parseDouble(curr);
		DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		df.setMinimumFractionDigits(2);
	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setCurrencySymbol("");
	    dfs.setMonetaryDecimalSeparator('.');
//	    dfs.setGroupingSeparator(' ');
	    df.setGroupingUsed(false);
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
//	public static String replaceCurr(String curr) {
//		curr = curr.replace(".", ",");
//		return curr;
//	}
	
	public static List<RowEntry> getCoinsFromTavex(List<String> myCoinsStrings) throws IOException {

		List<RowEntry> myCoinRowEntries = new ArrayList<RowEntry>();
		
		String myUrl = "http://www.tavex.bg/zlato";
		
		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Elements paginations = doc.getElementsByClass("pagination__item");
		
		int i = 0;
		for (Element element : paginations) {
			i ++;
		}
		
		for (int j = 1; j < i; j++) {
			Document tavex = Jsoup.connect(myUrl + "/page/" + j + "/#s")
					.timeout(10000).validateTLSCertificates(false)
					.get();
		
			Elements allElements = tavex.getElementsByClass(divProdClass);
			
			// Iterate in arraylist
			for (String myCoinString : myCoinsStrings) {
				// Iterate in coin div
				for (Element element : allElements) {
					if (myCoinString.equals(element.child(0).ownText())) {
						// element.nextElementSibling()!=null
						// ? element.nextElementSibling()
						// .ownText()
						// : "")) {
						String curCoin = element.child(0).ownText();
						System.out.println(curCoin);
						String htmlurElement = element.toString();
						Document document = Jsoup.parse(htmlurElement);
						
						Elements myElements = document.getElementsByClass(divPriceClass);
						
						String result_01 = clearFormatCurr(myElements.get(0).child(1).child(0).ownText());
						System.out.println("Buy: " + result_01);
						String result_02 = clearFormatCurr(myElements.get(0).child(0).ownText());
						System.out.println("Sell: " + result_02);
							
						RowEntry rowEtry = new RowEntry(
								curCoin,
								result_01,
								def,
								result_02,
								def,
								def,
								null,
								false);
							
						myCoinRowEntries.add(rowEtry);
					}
					
				}
			}
		}
		return myCoinRowEntries;
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
	
	public static RowEntry getBGNUSD() throws IOException {
		String myUrl = "https://ebb.ubb.bg/Log.aspx";

		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Element div = doc.getElementById("currency1").getElementsByTag("tr").get(5);
		
//		String result_01 = replaceCurr(div.child(3).ownText());
		String result_01 = div.child(3).ownText();
		System.out.println("USD Buy:: " + result_01);
		
//		String result_02 = replaceCurr(div.child(4).ownText());
		String result_02 = div.child(4).ownText();
		System.out.println("USD Sell: " + result_02);
		
//		String result_03 = replaceCurr(div.child(2).ownText());
		String result_03 = div.child(2).ownText();
		System.out.println("USD Sell: " + result_03);
		
		RowEntry rowEntry = new RowEntry(
				"Щатски долар",
				result_01,
				null,
				result_02,
				null,
				null,
				result_03,
				false);
		
		return rowEntry;

	}
	
	public static RowEntry getXAUUSD() throws IOException {
		String myUrl = "https://www.bloomberg.com/quote/XAUUSD:CUR";

		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Elements div = doc.getElementsByClass("price");
		
		System.out.println(div.get(0).ownText());
		String result = currencyFormater(div.get(0).ownText());
		System.out.println("XAU_USD:: " + result);
		
		RowEntry rowEntry = new RowEntry(
				"XAUUSD:CUR",
				null,
				null,
				null,
				null,
				"open",
				result,
				false);
		
		return rowEntry;
		
	}
	
	public static RowEntry getXAUBGN() throws IOException {
		String myUrl = "http://www.bnb.bg/Statistics/StExternalSector/StExchangeRates/StERForeignCurrencies/index.htm";

		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Element div = (Element) doc.getElementsByClass("table").get(0).childNode(4).childNode(61).childNode(7);
		
		System.out.println(div.ownText());
		String result = currencyFormater(div.ownText());
		System.out.println("XAU_BGN: " + result);
		
		RowEntry rowEntry = new RowEntry(
				"Злато (в трой унции)",
				"XAU",
				null,
				"1.00",
				null,
				null,
				result,
				false);
		
		return rowEntry;
		
	}
	
	public static RowEntry getEthereumPrice() throws IOException {
		String myUrl = "https://coinmarketcap.com/currencies/ethereum/";
		
		Document doc = Jsoup.connect(myUrl)
				.timeout(10000).validateTLSCertificates(false)
				.get();
		
		Element div = (Element) doc.getElementsByClass("col-xs-6 col-sm-8 col-md-4 text-left").get(0).childNode(1);
		
		System.out.println(div.ownText());
		String result = currencyFormater(div.text());
		System.out.println("Ethereum USD: " + result);
		
		RowEntry rowEntry = new RowEntry(
				"Ethereum Price",
				null,
				null,
				null,
				null,
				null,
				result,
				true);
		
		return rowEntry;
		
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start Program");
		long startTime = System.currentTimeMillis();
		
		/*
		myCoinsStrings.add("1 унция златен канадски кленов лист");
//		myCoinsStrings.add("1 унция златна австрийска филхармония");
		myCoinsStrings.add("5 грама златно кюлче PAMP Фортуна");
		myCoinsStrings.add("1 унция златна китайска панда от 2009");
		*/
//		getCoinsFromTavex();
//		getBGNUSD();
		
//		getXAUUSD();
		
//		getXAUBGN();
		
//		getEthereumPrice();
		
		RowEntry rowEntry_02 = new RowEntry(
				"Канадски кленов лист 1 унция",
				"2,120.00",
				null,
				"2,279.00",
				null,
				null,
				"123",
				false);
		
		System.out.println(rowEntry_02.toString());
		
		long endTime   = System.currentTimeMillis();
		duration(startTime, endTime);
		
		
		
	}
}
