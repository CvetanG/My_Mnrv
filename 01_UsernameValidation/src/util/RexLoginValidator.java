package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RexLoginValidator {
	
	private static Pattern pattern;
	private static Matcher matcher;

	  private static final String USERNAME_PATTERN = "^[A-Za-z0-9_-]{5,31}$";
//	  private static final String USERNAME_PATTERN = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{8,29}$";
	  
	  public RexLoginValidator(){
		  pattern = Pattern.compile(USERNAME_PATTERN);
	  }
	  
	  /**
	   * Validate login with regular expression
	   * @param login login for validation
	   * @return true valid login, false invalid login
	   */
	  public static boolean validate(final String login){
		  
		  matcher = pattern.matcher(login);
		  return matcher.matches();
	    	    
	  }
	  
}
