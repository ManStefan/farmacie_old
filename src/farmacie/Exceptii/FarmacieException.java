package farmacie.Exceptii;

public class FarmacieException extends Exception{
	private String message;
    public FarmacieException() { }
   
    public FarmacieException(String message){
        this.message = message;
    }
}
