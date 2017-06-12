package app;

public class RowEntry {
	
	String index;
	String buy;
	String sell;
	String myI;
	String diff;
	Boolean underline;
	
	public RowEntry(String index, String buy, String sell, String myI, String diff, Boolean underline) {
		super();
		this.index = index;
		this.buy = buy;
		this.sell = sell;
		this.myI = myI;
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
	public String getSell() {
		return sell;
	}
	public void setSell(String sell) {
		this.sell = sell;
	}
	public String getMyI() {
		return myI;
	}
	public void setMyI(String myI) {
		this.myI = myI;
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
