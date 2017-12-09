package app;

public class MyColumn {
	int colNum;
	char colChar;
	
	public MyColumn(char colChar, int colNum) {
		this.colChar = colChar;
		this.colNum = colNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public char getColChar() {
		return colChar;
	}

	public void setColChar(char colChar) {
		this.colChar = colChar;
	}
	
}
