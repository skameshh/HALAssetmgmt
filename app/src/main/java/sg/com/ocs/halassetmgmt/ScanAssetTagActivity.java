package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Halliburton.mfgsystems.asstmgt.db.AssetDao;
import com.Halliburton.mfgsystems.asstmgt.db.DBUtils;
import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;

import java.util.ArrayList;

public class ScanAssetTagActivity extends AppCompatActivity {

    private static String TAG = "HALAssetScanAsset";
    private ConstraintLayout lvparent;
    public static EditText txtScannedTag;
    //public static TextView lblInventoryNum;
    public static EditText txtScannedTagAll;
    private TextView lbl_title;
    private Button btnScanAssetQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_asset_tag);
        txtScannedTag = (EditText)findViewById(R.id.txtScannedTag);
        txtScannedTagAll = (EditText)findViewById(R.id.txtScannedTagAll);
       // lblInventoryNum = (TextView)findViewById(R.id.lblInventoryNum);
        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);
        lbl_title = (TextView)findViewById(R.id.lbl_title);
        lbl_title.setText("Scan Asset Tag");

        btnScanAssetQRCode = findViewById(R.id.btnScanAssetQRCode);
    }

    public void startScanner(View view){
        try {
            Intent intent = new Intent(ScanAssetTagActivity.this, ScanActivityForAsset.class);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    public void doAssetInfoPage(View view){

        String srl_numx = txtScannedTagAll.getText().toString();
        //txtScannedTag.setText("slno");

        String srl_num = txtScannedTag.getText().toString() ;
        // HalPrefManager.putInPref(this, Global.SEL_PLANT, sel_plant);
        String plant = HalPrefManager.readFromPref(this,Global.SEL_PLANT);
        ArrayList al =  DBUtils.getAllAssets(plant,null,srl_num, null);
        if(al.size()>0){
            try {
                AssetDao dao = (AssetDao)al.get(0);
                //put username in the Memory
                HalPrefManager.saveObjectToSharedPreference(this,  Global.CURRENT_ASSET, dao);

                Intent intent = new Intent(ScanAssetTagActivity.this, AssetInfoActivity.class);
                //intent.putExtra("asset_dao", dao);
                startActivity(intent);
            }catch(Exception e){
                Log.v(TAG,"Error in "+e.getLocalizedMessage());
            }
        }else{
            try {
                HalPrefManager.saveObjectToSharedPreference(this,  Global.CURRENT_ASSET, null);

                Intent intent = new Intent(ScanAssetTagActivity.this, AssetNotFoundActivity.class);
                intent.putExtra("serial_num", srl_num);
                startActivity(intent);
            }catch(Exception e){
                Log.v(TAG,"Error in "+e.getLocalizedMessage());
            }
        }

    }
}
