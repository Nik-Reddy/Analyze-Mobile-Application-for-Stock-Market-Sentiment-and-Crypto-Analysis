package ds.edu.project4android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
 * This class provides capabilities to search for an image on Flickr.com given a search term.  The method "search" is the entry to the class.
 * Network operations cannot be done from the UI thread, therefore this class makes use of inner class BackgroundTask that will do the network
 * operations in a separate worker thread.  However, any UI updates should be done in the UI thread so avoid any synchronization problems.
 * onPostExecution runs in the UI thread, and it calls the ImageView pictureReady method to do the update.
 *
 * Method BackgroundTask.doInBackground( ) does the background work
 * Method BackgroundTask.onPostExecute( ) is called when the background work is
 *    done; it calls *back* to ip to report the results
 */

public class GetSecurity{
    InterestingSecurity ip = null;   // for callback
    private String stockCryptoName;
    boolean isCrypto;
    StringBuilder responseData;
    public void search(String stockCryptoName, boolean isCrypto, Activity activity,  InterestingSecurity ip) {
        this.ip = ip;
        this.stockCryptoName = stockCryptoName;
        this.isCrypto = isCrypto;
        new BackgroundTask(activity).execute();
    }



    // search( )
    // Parameters:
    // String searchTerm: the thing to search for on flickr
    // Activity activity: the UI thread activity
    // InterestingPicture ip: the callback method's class; here, it will be ip.pictureReady( )
//    public void search(String stockEditText, Activity activity, InterestingPicture ip) {
////        System.out.println("------------3-------------");
//        this.ip = ip;
//        this.stockEditText = stockEditText;
//        new BackgroundTask(activity).execute();
//    }

    // class BackgroundTask
    // Implements a background thread for a long running task that should not be
    //    performed on the UI thread. It creates a new Thread object, then calls doInBackground() to
    //    actually do the work. When done, it calls onPostExecute(), which runs
    //    on the UI thread to update some UI widget (***never*** update a UI
    //    widget from some other thread!)
    //
    // Adapted from one of the answers in
    // https://stackoverflow.com/questions/58767733/the-asynctask-api-is-deprecated-in-android-11-what-are-the-alternatives
    // Modified by Barrett
    //
    // Ideally, this class would be abstract and parameterized.
    // The class would be something like:
    //      private abstract class BackgroundTask<InValue, OutValue>
    // with two generic placeholders for the actual input value and output value.
    // It would be instantiated for this program as
    //      private class MyBackgroundTask extends BackgroundTask<String, Bitmap>
    // where the parameters are the String url and the Bitmap image.
    //    (Some other changes would be needed, so I kept it simple.)
    //    The first parameter is what the BackgroundTask looks up on Flickr and the latter
    //    is the image returned to the UI thread.
    // In addition, the methods doInBackground() and onPostExecute( ) could be
    //    abstract methods; would need to finesse the input and ouptut values.
    // The call to activity.runOnUiThread( ) is an Android Activity method that
    //    somehow "knows" to use the UI thread, even if it appears to create a
    //    new Runnable.

    private class BackgroundTask {
        private Activity activity; // The UI thread
        public BackgroundTask(Activity activity) {
            this.activity = activity;
        }
        private void startBackground() {
            new Thread(new Runnable() {
                public void run() {
                   doInBackground();
                    // This is magic: activity should be set to MainActivity.this
                    //    then this method uses the UI thread
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            onPostExecute();
                        }
                    });
                }
            }).start();
        }

        private void execute(){
            // There could be more setup here, which is why
            //    startBackground is not called directly
            startBackground();
        }

        // doInBackground( ) implements whatever you need to do on
        //    the background thread.
        // Implement this method to suit your needs
//        private StringBuilder doInBackground() throws IOException {
//            System.out.println("---------2-----------");
//
//            // Taken from: https://gist.github.com/hitenpratap/8e1f28d60c0bb11a5bca
//
//            String securityName = stockCryptoName.toString();
//            StringBuilder stringBuilder = new StringBuilder("http://10.0.2.2:8080/Project4webserv-1.0-SNAPSHOT/");
//
//            if (isCrypto) {
//                stringBuilder.append("getCryptoResponse");
//                stringBuilder.append("?crypto=");
//            } else {
//                stringBuilder.append("getStocksResponse");
//                stringBuilder.append("?stock=");
//            }
//
//            stringBuilder.append(URLEncoder.encode(securityName, "UTF-8"));
//
//            URL url = new URL(stringBuilder.toString());
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            con.setRequestProperty("Accept-Charset", "UTF-8");
//            System.out.println("\nSending request to URL : " + stringBuilder);
//            System.out.println("Response Code : " + con.getResponseCode());
//            System.out.println("Response Message : " + con.getResponseMessage());
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder responseData = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                responseData.append(inputLine);
//            }
//            in.close();
//
//            JsonObject jsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);
//
//            if (isCrypto) {
//                String symbol = jsonObject.get("symbol").toString();
//                String price = jsonObject.get("priceUsd").toString();
//                String marketCap = jsonObject.get("marketCapUsd").toString();
//                String rank = jsonObject.get("rank").toString();
//                responseData = new StringBuilder(symbol + "," + price + "," + "," + marketCap + "," + rank);
//
//
//            } else {
//                String sentiment = jsonObject.get("sentiment").toString();
//                String priceChange = jsonObject.get("priceChange").toString();
//                String percentagePriceChange = jsonObject.get("percentagePriceChange").toString();
//                String marketCap = jsonObject.get("marketCap").toString();
//                responseData = new StringBuilder(sentiment + "," + priceChange + "," + "," + percentagePriceChange + "," + marketCap);
//            }
//            return responseData;
//        }


        private void doInBackground() {
            System.out.println("---------2-----------");
            StringBuilder stringBuilder = new StringBuilder("http://10.0.2.2:8080/Project4webserv-1.0-SNAPSHOT/");

            if (isCrypto) {
                stringBuilder.append("getCryptoResponse?crypto=").append(stockCryptoName);
            } else {
                stringBuilder.append("getStocksResponse?stock=").append(stockCryptoName);
            }

            responseData = new StringBuilder();

            try {
                System.out.println(stringBuilder);
                URL url = new URL(stringBuilder.toString());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");

                System.out.println("\nSending request to URL : " + stringBuilder);
                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Response Message : " + con.getResponseMessage());
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    responseData.append(inputLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            System.out.println(responseData);

            JsonObject jsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);
            if (isCrypto) {

                JsonElement symbolElement = jsonObject.get("symbol");
                String symbol = symbolElement == null  ? "Detail Not Found" : symbolElement.getAsString();

                JsonElement priceElement = jsonObject.get("price");
                String price = priceElement == null ? "Detail Not found" : priceElement.getAsString();

                JsonElement marketCapElement = jsonObject.get("marketCap");
                String marketCap = marketCapElement == null || "NaN".equals(marketCapElement.getAsString())
                        || "0.0".equals(marketCapElement.getAsString()) ? "Detail Not found" : marketCapElement.getAsString();

                JsonElement rankElement = jsonObject.get("rank");
                String rank = rankElement == null ? "Detail Not found" : rankElement.getAsString();

                responseData = new StringBuilder(symbol + "," + price + ","  + marketCap + "," + rank);

            } else{
                JsonElement sentimentElement = jsonObject.get("sentiment");
                String sentiment = sentimentElement == null ? "Detail Not found" : sentimentElement.getAsString();

                JsonElement dayHighElement = jsonObject.get("day_high");
                String dayHigh = dayHighElement == null ? "Detail Not found" : dayHighElement.getAsString();

                JsonElement dayLowElement = jsonObject.get("day_low");
                String dayLow = dayLowElement == null ? "Detail Not found" : dayLowElement.getAsString();

                JsonElement marketCapElement = jsonObject.get("market_cap");
                String marketCap = marketCapElement == null ? "Detail Not found" : marketCapElement.getAsString();

                responseData = new StringBuilder(sentiment + "," + dayHigh + "," + dayLow + "," + marketCap);
            }
        }


        // onPostExecute( ) will run on the UI thread after the background
        //    thread completes.
        // Implement this method to suit your needs
        public void onPostExecute(){
//            responseData= doInBackground();
            ip.responseReady(responseData, isCrypto);
        }

        private Bitmap getLocalImage(String imagePath) {
            try (FileInputStream fis = new FileInputStream(imagePath);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {

                Bitmap bm = BitmapFactory.decodeStream(bis);
                return bm;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}

