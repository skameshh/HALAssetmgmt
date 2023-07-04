package sg.com.ocs.halassetmgmt;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivityForAsset extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private static String TAG = "HALAssetScanActivityForAsset";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scan);
        try {
            mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
            setContentView(mScannerView);                // Set the scanner view as the content view
        }catch (Exception e){
            Log.v(TAG,e.getLocalizedMessage());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            // Do something with the result here
            Log.v(TAG, rawResult.getText()); // Prints scan results
            Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
           // MainActivity.txtCode.setText(rawResult.getText());
            //MainActivity.addStockUI();
            String raw = rawResult.getText();

            String assid = null;
            if(raw!=null){
                assid = raw;
                if(assid.contains("Asset ID:")){
                    assid = raw.substring(0,raw.indexOf("\n"));
                    assid = assid.replaceAll("Asset ID: ", "");
                }

                Log.v(TAG, "assid="+assid);
            }

            ScanAssetTagActivity.txtScannedTagAll.setText(raw);
            ScanAssetTagActivity.txtScannedTag.setText(assid);
            //ScanAssetTagActivity.lblInventoryNum.setText("Inv Num:");
            onBackPressed();

            // If you would like to resume scanning, call this method below:
            //mScannerView.resumeCameraPreview(this);
        }catch (Exception e){
            Log.v(TAG,e.getLocalizedMessage());
            ScanAssetTagActivity.txtScannedTagAll.setText("Error");
            ScanAssetTagActivity.txtScannedTag.setText("Error");
        }
    }
}
