package com.mealok.admin.bin;

/**
 * Created by arkadutta on 09/11/16.
 */
import com.sendgrid.*;
import java.io.IOException;

public class MailUtility {

    public static void main(String[] args) {

        Email from = new Email("arka.dutta@mealok.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("arka.dutta@mealok.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
