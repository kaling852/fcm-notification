package me.kaling;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;

public class Main {

    //TODO API KEY
    private static final String KEY = "Firebase API KEY";
    //TODO THE PERSON YOU WANT TO SEND TO
    private static final String sendToToken = "the person you want to send to";

    public static void main(String[] args) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost( "https://fcm.googleapis.com/fcm/send");

        NotificationRequestModel notificationRequestModel = new NotificationRequestModel();

        NotificationData notificationData = new NotificationData();
        notificationData.setTitle("Whatever Title");//TODO  YOUR TITLE
        notificationData.setSound("default");
        notificationData.setBody("This is Ka sending From his Computer"); //TODO YOUR BODY
        notificationData.setShowID("ABCDEFG-1234-1234-1234-ABCDEFG"); //TODO SHOW_KEY
        notificationData.setClick_action(".MainActivity");

        notificationRequestModel.setData(notificationData);
        notificationRequestModel.setTo(sendToToken);

        Gson gson = new Gson();
        Type type = new TypeToken<NotificationRequestModel>() {}.getType();

        String json = gson.toJson(notificationRequestModel, type);
        System.out.println(json);
        StringEntity input = new StringEntity(json);
        input.setContentType("application/json");

        // server key of your firebase project goes here in header field, get it from firebase console.

        postRequest.addHeader("Authorization", "key="+KEY);
        postRequest.setEntity(input);

        System.out.println("request:" + json);

        HttpResponse response = httpClient.execute(postRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        } else if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("response:" + EntityUtils.toString(response.getEntity()));
        }
    }
}
