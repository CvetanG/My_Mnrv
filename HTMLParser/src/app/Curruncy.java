package app;

public enum Curruncy {
	BGN("BGN"),
	USD("USD");
	
	private final String text;

    private Curruncy(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
