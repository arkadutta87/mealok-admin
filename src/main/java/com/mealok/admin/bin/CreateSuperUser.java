package com.mealok.admin.bin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Console;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mealok.admin.utils.HibernateAnnotationUtil;
import com.mealok.admin.model.AppUser;
import com.mealok.admin.common.InputEnum;
import com.mealok.admin.utils.Util;

/**
 * Created by arkadutta on 24/10/16.
 */
public class CreateSuperUser {

    private Map<String, String> keyBoardInput;
    private static SessionFactory sessionFactory = null;
    private static Session session = null;
    private static Transaction tx = null;

    public CreateSuperUser() {
        keyBoardInput = new HashMap<String, String>();
    }


    public static void main(String[] args) {
        CreateSuperUser user = new CreateSuperUser();
        user.getInputFromUser();
        try {
            user.createUser();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateAnnotationUtil.shutDown();
        }


        System.out.println("The user have been created . Now try login to the system");


    }


    private void createUser() {

        sessionFactory = HibernateAnnotationUtil.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        System.out.println("Session created");

        //start transaction
        tx = session.beginTransaction();

        AppUser user = new AppUser();
        user.setUsername(keyBoardInput.get(InputEnum.USERNAME.value).trim());
        user.setFirstname(keyBoardInput.get(InputEnum.FIRST_NAME.value).trim());
        user.setLastname(keyBoardInput.get(InputEnum.LAST_NAME.value).trim());
        user.setEmail(keyBoardInput.get(InputEnum.EMAIL.value).trim());

        //password
        String pwd = Util.encrypt(keyBoardInput.get(InputEnum.PASSWORD.value));
        user.setPassword(pwd);


        user.setIsactive(true);
        user.setIsotp(true);
        user.setIssuperuser(true);
        user.setIsstaff(true);





        user.setCreated_on(new Date());
        user.setUpdated_on(new Date(0));

        session.save(user);

        tx.commit();
        HibernateAnnotationUtil.shutDown();
    }

    private boolean verifyIfFieldQualifies(String value, InputEnum fieldName) {

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

    private void getInputFromUser() {

        Scanner keyboard = new Scanner(System.in);
        System.out.println("This utlity is used to create a super-user.\nPlease follow along to create a user who has admin priviledge");
        System.out.println(" -------------------------------------------------------------- ");

        //enter username
        boolean flag = false;

        System.out.println("Enter username(We use mail-id for username) : Type in your valid mail id");
        String inpVal = keyboard.nextLine().trim();

        flag = verifyIfFieldQualifies(inpVal, InputEnum.USERNAME);
        if (flag)
            keyBoardInput.put(InputEnum.USERNAME.value, inpVal);
        else {
            while (!flag) {
                System.out.println("The username : " + inpVal + " provided is not a valid email-id . Please Enter a valid email id - ");
                inpVal = keyboard.nextLine();

                flag = verifyIfFieldQualifies(inpVal, InputEnum.USERNAME);

            }
            keyBoardInput.put(InputEnum.USERNAME.value, inpVal);
        }

        //enter firstname
        System.out.println("Enter First Name : Compulsory Field ");
        inpVal = keyboard.nextLine().trim();

        flag = verifyIfFieldQualifies(inpVal, InputEnum.FIRST_NAME);
        if (flag)
            keyBoardInput.put(InputEnum.FIRST_NAME.value, inpVal);
        else {
            while (!flag) {
                System.out.println("The First Name : " + inpVal + " provided is not a valid name . Please Enter a valid name with only" +
                        " english letters - ");
                inpVal = keyboard.nextLine();

                flag = verifyIfFieldQualifies(inpVal, InputEnum.FIRST_NAME);

            }
            keyBoardInput.put(InputEnum.FIRST_NAME.value, inpVal);
        }

        //enter last name
        System.out.println("Enter Last Name : Compulsory Field");
        inpVal = keyboard.nextLine().trim();

        flag = verifyIfFieldQualifies(inpVal, InputEnum.LAST_NAME);
        if (flag)
            keyBoardInput.put(InputEnum.LAST_NAME.value, inpVal);
        else {
            while (!flag) {
                System.out.println("The Last Name : " + inpVal + " provided is not a valid name . Please Enter a valid name with only" +
                        " english letters - ");
                inpVal = keyboard.nextLine();

                flag = verifyIfFieldQualifies(inpVal, InputEnum.LAST_NAME);

            }
            keyBoardInput.put(InputEnum.LAST_NAME.value, inpVal);
        }

        //enter mobile
        System.out.println("Enter Email Id : Non-Compulsory Field");
        inpVal = keyboard.nextLine().trim();

        flag = verifyIfFieldQualifies(inpVal, InputEnum.EMAIL);
        if (flag)
            keyBoardInput.put(InputEnum.EMAIL.value, inpVal);
        else {
            while (!flag) {
                System.out.println("The Email Id : " + inpVal + " provided is not a valid one . Please either keep empty or " +
                        "enter a valid email id.");
                inpVal = keyboard.nextLine();

                flag = verifyIfFieldQualifies(inpVal, InputEnum.EMAIL);

            }
            keyBoardInput.put(InputEnum.EMAIL.value, inpVal);
        }

        //reading password
        Console console = System.console();
        if (console == null) {
            System.out.println("No console: not in interactive mode! Exiting the program.");
            System.exit(0);
        }

        //System.out.print("Enter your username: ");
        //String username = console.readLine();
        readPassword(console);


        while (!keyBoardInput.get(InputEnum.PASSWORD.value).equals(keyBoardInput.get(InputEnum.RETYPE_PASSWORD.value))) {
            System.out.println("Password didnot match  ---------- ");
            readPassword(console);
        }

        System.out.println("Details enetered - ");
        Set<String> keys = keyBoardInput.keySet();

        for (String aKey : keys) {
            System.out.println(aKey + " : " + keyBoardInput.get(aKey));
        }

        System.out.println("Thanks for Entering the details. Now wait for the user to be created in database");

    }

    private void readPassword(Console console) {
        System.out.print("Enter your password: Please enter a password which is atleast 8 characters long, a digit must occur at least once," +
                "a lower case letter must occur at least once ,an upper case letter must occur at least once, " +
                "a special character must occur at least once, no whitespace allowed in the entire string ");
        char[] password = console.readPassword();

        String inpVal = new String(password);
        boolean flag = false;

        flag = verifyIfFieldQualifies(inpVal, InputEnum.PASSWORD);
        if (flag)
            keyBoardInput.put(InputEnum.PASSWORD.value, inpVal);
        else {
            while (!flag) {
                System.out.println("The password provided is not a valid password . " +
                        "Please enter a password which is atleast 8 characters long, a digit must occur at least once," +
                        "a lower case letter must occur at least once ,an upper case letter must occur at least once, " +
                        "a special character must occur at least once, no whitespace allowed in the entire string ");
                password = console.readPassword();
                inpVal = new String(password);

                flag = verifyIfFieldQualifies(inpVal, InputEnum.PASSWORD);

            }
            keyBoardInput.put(InputEnum.PASSWORD.value, inpVal);
        }

        //reading retype-password option
        System.out.print("Re-Enter your password: ");
        password = console.readPassword();

        inpVal = new String(password);
        keyBoardInput.put(InputEnum.RETYPE_PASSWORD.value, inpVal);


    }
}

