package app.entities;

public class RowEntry {
	
	String index;
	CurrunciesEnum curruncy;
	String buy;
	String priceDown;
	String sell;
	String priceOver;
	String diffPerc;
	String diff;
	Boolean underline;
	
	public RowEntry(String index, CurrunciesEnum curruncy, String buy,
			String priceDown, String sell, String priceOver,
			String diffPerc, String diff, Boolean underline) {
		this.index = index;
		this.curruncy = curruncy;
		this.buy = buy;
		this.priceDown = priceDown;
		this.sell = sell;
		this.priceOver = priceOver;
		this.diffPerc = diffPerc;
		this.diff = diff;
		this.underline = underline;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
	public CurrunciesEnum getCurruncy() {
		return curruncy;
	}

	public void setCurruncy(CurrunciesEnum curruncy) {
		this.curruncy = curruncy;
	}

	public String getPriceDown() {
		return priceDown;
	}

	public void setPriceDown(String priceDown) {
		this.priceDown = priceDown;
	}

	public String getPriceOver() {
		return priceOver;
	}

	public void setPriceOver(String priceOver) {
		this.priceOver = priceOver;
	}

	public String getDiffPerc() {
		return diffPerc;
	}

	public void setDiffPerc(String diffPerc) {
		this.diffPerc = diffPerc;
	}

	public String getBuy() {
		return buy;
	}

	public void setBuy(String buy) {
		this.buy = buy;
	}

	public String getMyIndF() {
		return priceDown;
	}

	public void setMyIndF(String myIndF) {
		this.priceDown = myIndF;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getMyIndH() {
		return priceOver;
	}

	public void setMyIndH(String myIndH) {
		this.priceOver = myIndH;
	}

	public String getMyIndI() {
		return diffPerc;
	}

	public void setMyIndI(String myIndI) {
		this.diffPerc = myIndI;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public Boolean getUnderline() {
		return underline;
	}

	public void setUnderline(Boolean underline) {
		this.underline = underline;
	}
	
	@Override
	public String toString() { 
		String temp = "";
		if (this.diff != null) {
			temp = ",\nFix: " + this.diff;
		}
		String result = "Index: " + this.index + ",\n" +
				"Buy: " + this.buy + ",\n" +
				"Sell: " + this.sell +
				temp;
				
	    return result;
	} 
	
}
