package ds.edu.project4android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InterestingSecurity extends AppCompatActivity {
    InterestingSecurity me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Initialize stock spinner with the stock options
//        ArrayAdapter<String> stockAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[] {
//                "AAPL", "ADBE", "AMZN", "NVDA", "TSLA", "MSFT", "NFLX", "META", "GOOGL", "CRM"
//        });
//        stockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        stockSpinner.setAdapter(stockAdapter);

        /*
         * The click listener will need a reference to this object, so that upon successfully finding a picture from Flickr, it
         * can callback to this object with the resulting picture Bitmap.  The "this" of the OnClick will be the OnClickListener, not
         * this InterestingPicture.
         */
        final InterestingSecurity ma = this;
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton radioButtonStocks = (RadioButton) findViewById(R.id.radioButtonStocks);
        final RadioButton radioButtonCrypto = (RadioButton) findViewById(R.id.radioButtonCrypto);
        final EditText stockCryptoEditText = (EditText) findViewById(R.id.stockCryptoEdit);
        final Button submitButton = (Button) findViewById(R.id.submit);

        radioButtonStocks.setChecked(true);
        stockCryptoEditText.setEnabled(true);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            stockCryptoEditText.setEnabled(true);
            submitButton.setEnabled(true);
        });
        submitButton.setOnClickListener(view -> {
            String stockCryptoName = stockCryptoEditText.getText().toString();
            System.out.println(stockCryptoName);
            GetSecurity getPicture = new GetSecurity();
            getPicture.search(stockCryptoName, radioButtonCrypto.isChecked(), me, ma);
        });
    }

    public void responseReady(StringBuilder responseData, boolean isCrypto) {
        final TextView firstFieldLabel = (TextView) findViewById(R.id.firstFieldLabel);
        final TextView secondFieldLabel = (TextView) findViewById(R.id.secondFieldLabel);
        final TextView thirdFieldLabel = (TextView) findViewById(R.id.thirdFieldLabel);
        final TextView fourthFieldLabel = (TextView) findViewById(R.id.fourthFieldLabel);

        final TextView firstFieldValue = (TextView) findViewById(R.id.firstFieldValue);
        final TextView secondFieldValue = (TextView) findViewById(R.id.secondFieldValue);
        final TextView thirdFieldValue = (TextView) findViewById(R.id.thirdFieldValue);
        final TextView fourthFieldValue = (TextView) findViewById(R.id.fourthFieldValue);
        System.out.println(responseData);
        if (responseData != null) {
            String[] responseValues = responseData.toString().split(",");

            if (isCrypto) {
                firstFieldLabel.setText("Rank:");
                secondFieldLabel.setText("price :");
                thirdFieldLabel.setText("Symbol:");
                fourthFieldLabel.setText("Market Cap:");

                firstFieldValue.setText(responseValues[3]);
                System.out.println(responseValues[3]);
                secondFieldValue.setText(responseValues[1]);
                thirdFieldValue.setText(responseValues[0]);
                fourthFieldValue.setText(responseValues[2]);
            } else {
                firstFieldLabel.setText("Sentiment:");
                secondFieldLabel.setText("day High:");
                thirdFieldLabel.setText("day Low:");
                fourthFieldLabel.setText("Market Cap:");

                firstFieldValue.setText(responseValues[0]);
                secondFieldValue.setText(responseValues[1]);
                thirdFieldValue.setText(responseValues[2]);
                fourthFieldValue.setText(responseValues[3]);
            }
        } else {
            firstFieldValue.setText("Not found");
            secondFieldValue.setText("Not found");
            thirdFieldValue.setText("Not found");
            fourthFieldValue.setText("Not found");
        }
    }
}



//
//
//
//
//
//
//        /*
//         * Find the "submit" button, and add a listener to it
//         */
//        Button submitButton = (Button)findViewById(R.id.submit);
//        EditText stockEditText = (EditText)findViewById(R.id.stockEditText);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//              try{
//                if (checkedId == R.id.radioButtonStocks ) {
//                    stockEditText.setEnabled(true);
//                    String stockName = stockEditText.getText().toString();
//                    GetPicture getPicture=new GetPicture();
//                    getPicture.search(stockName,ma,me);
//                    submitButton.setEnabled(true);
//                }
//               else {
//                    stockEditText.setEnabled(false);
//                    stockEditText.setText("");
//                    submitButton.setEnabled(true);
//                }
//              } catch (Exception e) {
//                  throw new RuntimeException(e);
//              }
//            }
//        });
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View viewParam) {
//
//            }
//        });
//    }
//
//
//    /*
//     * This is called by the GetPicture object when the picture is ready.  This allows for passing back the Bitmap picture for updating the ImageView
//     */
//    public void responseReady(StringBuffer responseData) {
////        ImageView pictureView = (ImageView)findViewById(R.id.);
//        EditText stockEditText = (EditText)findViewById(R.id.stockEditText);
//        TextView resultView = (TextView) findViewById(R.id.resultView);
//        System.out.println();
//        if (responseData != null) {
////            pictureView.setImageBitmap(picture);
//            System.out.println("Result");
//            resultView.setText(responseData);
//        } else {
////            pictureView.setImageResource(R.mipmap.ic_launcher);
//            System.out.println("No Result");
////            pictureView.setVisibility(View.INVISIBLE);
//            resultView.setText("Sorry, I could not find a response of"+stockEditText.getText().toString() );
//        }
//        resultView.setText("");
////        pictureView.invalidate();
//    }
//
//    public void processServletData(JsonObject servletData) {
//        // Extract data from the servletData object
//        JsonArray topCoinsByMarketCap = servletData.getAsJsonArray("topCoinsByMarketCap");
//        JsonArray mostDiscussedStocks = servletData.getAsJsonArray("mostDiscussedStocks");
//        JsonObject sentimentAnalysis = servletData.getAsJsonObject("sentimentAnalysis");
//        JsonArray highestTradingVolume = servletData.getAsJsonArray("highestTradingVolume");
//
//        // Update the UI elements
////        TextView topCoinsByMarketCapTextView = findViewById(R.id.topCoinsByMarketCap);
////        TextView mostDiscussedStocksTextView = findViewById(R.id.mostDiscussedStocks);
////        TextView sentimentAnalysisTextView = findViewById(R.id.sentimentAnalysis);
////        TextView highestTradingVolumeTextView = findViewById(R.id.highestTradingVolume);
////
////        topCoinsByMarketCapTextView.setText(formatTopCoinsByMarketCap(topCoinsByMarketCap));
////        mostDiscussedStocksTextView.setText(formatMostDiscussedStocks(mostDiscussedStocks));
////        sentimentAnalysisTextView.setText(formatSentimentAnalysis(sentimentAnalysis));
////        highestTradingVolumeTextView.setText(formatHighestTradingVolume(highestTradingVolume));
//    }
//
//    private String formatTopCoinsByMarketCap(JsonArray topCoins) {
//        StringBuilder sb = new StringBuilder("Top Coins by Market Cap:\n");
//        for (JsonElement coinElement : topCoins) {
//            JsonObject coin = coinElement.getAsJsonObject();
//            String coinName = coin.get("name").getAsString();
//            double marketCap = coin.get("marketCapUsd").getAsDouble();
//            sb.append(coinName).append(": ").append(marketCap).append("\n");
//        }
//        return sb.toString();
//    }
//
//    private String formatMostDiscussedStocks(JsonArray discussedStocks) {
//        StringBuilder sb = new StringBuilder("Most Discussed Stocks on WallStreetBets:\n");
//        for (JsonElement stockElement : discussedStocks) {
//            JsonObject stock = stockElement.getAsJsonObject();
//            String stockSymbol = stock.get("symbol").getAsString();
//            int noOfComments = stock.get("no_of_comments").getAsInt();
//            sb.append(stockSymbol).append(": ").append(noOfComments).append(" comments\n");
//        }
//        return sb.toString();
//    }
//
//    private String formatSentimentAnalysis(JsonObject sentimentAnalysis) {
//        int positiveCount = sentimentAnalysis.get("positiveCount").getAsInt();
//        int negativeCount = sentimentAnalysis.get("negativeCount").getAsInt();
//        int neutralCount = sentimentAnalysis.get("neutralCount").getAsInt();
//
//        return "Sentiment Analysis:\n"
//                + "Positive: " + positiveCount + "\n"
//                + "Negative: " + negativeCount + "\n"
//                + "Neutral: " + neutralCount + "\n";
//    }
//
//    private String formatHighestTradingVolume(JsonArray highestTradingVolume) {
//        StringBuilder sb = new StringBuilder("Stocks with the Highest Trading Volume:\n");
//        for (JsonElement stockElement : highestTradingVolume) {
//            JsonObject stock = stockElement.getAsJsonObject();
//            String stockSymbol = stock.get("symbol").getAsString();
//            long volume = stock.get("volume").getAsLong();
//            sb.append(stockSymbol).append(": ").append(volume).append("\n");
//        }
//        return sb.toString();
//    }
//}
//
//
