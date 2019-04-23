import Java.Lang.Object;

public class NotEnoughCoinException extends Exception{

	public NotEnoughCoinException(String s){
		super(s);
	}
}

public class InvalidAccountException extends Exception{

	public InvalidAccountException(String s){
		super(s);
	}
}