/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warham;


/**
 * Used for reporting the success of a specific action and 
 * @author Arthas
 */
public class ReportMessage {
    
    boolean sucessful;
    String message;
    int value;

    public ReportMessage(boolean successful, String message) {
        this.sucessful = successful;
        this.message = message;
    }
    
    public ReportMessage(boolean successful, String message,int value) {
        this.sucessful = successful;
        this.message = message;
        this.value=value;
    }
    
    public static ReportMessage ReportError(String message){
        return new ReportMessage(false,message);
    }
    
    public static ReportMessage ReportSuccess(){
        return new ReportMessage(true,"sucess");
    }

    public boolean isSucessful() {
        return sucessful;
    }

    public void setSucessful(boolean sucessful) {
        this.sucessful = sucessful;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSuccessful() {
        return sucessful;
    }

    public void setSuccessful(boolean successful) {
        this.sucessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
