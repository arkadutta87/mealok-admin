package com.mealok.admin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mealok.admin.common.InputEnum;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by arkadutta on 24/10/16.
 */
public class Util {

    public static boolean isPwdEligible(String pwd){
        String EMAIL_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }


    public static String encrypt(String key){
        String algorithm = "SHA";

        byte[] plainText = key.getBytes();

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        md.reset();
        md.update(plainText);
        byte[] encodedPassword = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                sb.append("0");
            }

            sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }

        return sb.toString();
    }

    public static String generateSession(){
        synchronized (Util.class){
            return UUID.randomUUID().toString();
        }
    }

    public static String convertToJSON(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(obj);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

        return str;
    }

    public static Object convertToObject(Class aClass,String json){
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try{
            obj = mapper.readValue(json, aClass);
        }catch(IOException e){
            e.printStackTrace();
        }

        return obj;

    }

    public static void  sendSMS(String number, String smsMessage){

        try {
            // Construct data
            String user = "username=" + "arka.dutta@binaryworkers.com";
            String hash = "&hash=" + "8f3f9d872d9f57720938250efdc6edc4a0aad2f1";
            String message = "&message=" + smsMessage;
            String sender = "&sender=" + "HULKFC";
            String numbers = "&numbers=" + number;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
            String data = user + hash + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            System.out.println(stringBuffer.toString());
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            //"Error "+e;
        }
    }

    public static double calculatePrice(double price,double percentage){

        //double price = 9480.99;
        //double discount_percentage = 25.25;

        double final_price = price - (price * percentage/100);
        final_price = Math.round( final_price * 100.0 ) / 100.0;

        System.out.println("The final price is --- "+final_price);
        return  final_price;
    }


    public static String titliCaseString(String str){
        String temp = str.trim();
        //temp = temp.trim();
        String str1 = "";

        String[] arr = temp.split(" ");
        for(String aStr : arr){
            if(aStr.trim().equals(""))
                continue;
            str1 = str1 + " " + StringUtils.capitalize(aStr.trim().toLowerCase());
        }

        return str1.trim();
    }

    public static int printDateDiff(Date dt1 ,Date dt2){
        //String str1 = "2016-09-18 23:59:59";
        //String str2  = "2016-09-20 23:59:59";

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        long diff = dt1.getTime() - dt2.getTime();
        System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

    }

    public static boolean verifyIfFieldQualifies(String value, InputEnum fieldName) {

        if (fieldName == null)
            return false;
        else {
            switch (fieldName) {
                case USERNAME:
                    String EMAIL_PATTERN =
                            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
                    Matcher matcher = pattern.matcher(value);
                    return matcher.matches();

                case EMAIL:
                    if (value.trim().isEmpty())
                        return true;
                    else {
                        EMAIL_PATTERN =
                                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                        pattern = Pattern.compile(EMAIL_PATTERN);
                        matcher = pattern.matcher(value);
                        return matcher.matches();
                    }

                case FIRST_NAME:
                    return value.matches("[a-zA-Z]+");

                case LAST_NAME:
                    return value.matches("[a-zA-Z]+");

                case MOBILE:
                    if (value.trim().isEmpty())
                        return true;
                    else
                        return value.trim().matches("[0-9]+");

                case PASSWORD:
                    //^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
                    EMAIL_PATTERN =
                            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
                    pattern = Pattern.compile(EMAIL_PATTERN);
                    matcher = pattern.matcher(value);
                    return matcher.matches();

                default:
                    return false;

            }
        }
    }

    public static Date getExpirationDateOfSession(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt); // Now use today date.
        c.add(Calendar.YEAR, 2);//Add 100 year

        return c.getTime();
    }

}
