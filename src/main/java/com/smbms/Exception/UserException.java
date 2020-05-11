package com.smbms.Exception;

public class UserException extends Exception{
    private String message;
    public UserException(String message){
        super(message);
        this.message = message;
    }
    public UserException(){
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
