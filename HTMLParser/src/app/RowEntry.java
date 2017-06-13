package app;

public class RowEntry {
	
	String index;
	String buy;
	String myIndF;
	String sell;
	String myIndH;
	String myIndI;
	String diff;
	Boolean underline;
	
	public RowEntry(String index, String buy, String myIndF, String sell, String myIndH, String myIndI, String diff,
			Boolean underline) {
		super();
		this.index = index;
		this.buy = buy;
		this.myIndF = myIndF;
		this.sell = sell;
		this.myIndH = myIndH;
		this.myIndI = myIndI;
		this.diff = diff;
		this.underline = underline;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getBuy() {
		return buy;
	}

	public void setBuy(String buy) {
		this.buy = buy;
	}

	public String getMyIndF() {
		return myIndF;
	}

	public void setMyIndF(String myIndF) {
		this.myIndF = myIndF;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getMyIndH() {
		return myIndH;
	}

	public void setMyIndH(String myIndH) {
		this.myIndH = myIndH;
	}

	public String getMyIndI() {
		return myIndI;
	}

	public void setMyIndI(String myIndI) {
		this.myIndI = myIndI;
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
	
	
	
	
}
