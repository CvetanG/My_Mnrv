package app.entities;

public enum CurrunciesEnum {
	BGN("BGN"),
	USD("USD");
	
	private final String text;

    private CurrunciesEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
