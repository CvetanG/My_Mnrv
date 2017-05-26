package app;

public class My_null {
	
	public static void main(String[] args) {
		Long my_class = null;
//		Long my_class = -5L;
//		Long my_class = 5L;
		
		if (my_class != null && my_class > 0) {
			System.out.println("My Long is: " + my_class );
		} else {
			System.out.println("My Long is NULL or smaller then 0");
		}
	}
}
