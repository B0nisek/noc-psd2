package utils;

public class Util {
    public static String getLocalBaseUri(){
        return "http://localhost:8080/";
    }

    public static String getCustomerBankPath(String bank, String customer){
            return  bank + "/customer/" + customer;
    }

}
