package ntdgy.cs307project2.exception;

public class InvalidDataException extends Exception{

    public InvalidDataException(String Msg,Throwable throwable){
        super(Msg,throwable);
    }

    public InvalidDataException(String Msg){
        super(Msg);
    }

    public InvalidDataException(){
        super();
    }




}
