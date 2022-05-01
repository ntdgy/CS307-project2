package ntdgy.cs307project2.exception;

public class InvalidDataException extends Exception{

    private String Msg;

    public InvalidDataException(String Msg,Throwable throwable){
        super(Msg,throwable);
    }




}
