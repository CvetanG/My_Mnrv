package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RunMe {
	
	static String divProdClass = "product__content";
	static String divPriceClass = "product__price";
	static List<String> myCoinsStrings = new ArrayList<>();
	
	static String divGoldClass = "chart__value";

	public static void main(String[] args) throws IOException {
		System.out.println("Start Program Run Me");
		long startTime = System.currentTimeMillis();
		
		myCoinsStrings.add("1 унция златен канадски кленов лист");
//		myCoinsStrings.add("1 унция златна австрийска филхармония");
		myCoinsStrings.add("5 грама златно кюлче PAMP Фортуна");
		myCoinsStrings.add("1 унция златна китайска панда от 2009");
		
		InvestmentParser.getCoinsBGN(divProdClass, divPriceClass, myCoinsStrings);
		InvestmentParser.getBGNGold();
		InvestmentParser.getGoldUSD();
		
		long endTime   = System.currentTimeMillis();
		InvestmentParser.duration(startTime, endTime);
		

	}

}
