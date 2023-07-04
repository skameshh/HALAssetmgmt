package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.Halliburton.mfgsystems.asstmgt.db.DBUtils;
import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;

public class CheckAnotherAssetActivity extends AppCompatActivity {

    private static String TAG = "HALAssetCheckAnother";
    private TextView lblResult = null;
    private static String AssetNum = "";
    private static String SerialNum = "";
    private static boolean bb_result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_another_asset);
        lblResult = (TextView)findViewById(R.id.lblResult);
        String plant  = "";
        String username = "";
        int count = 0;
        try{
            // HalPrefManager.putInPref(this, Global.SEL_PLANT, sel_plant);
              plant  = HalPrefManager.readFromPref(this, Global.SEL_PLANT);;
              username = HalPrefManager.readFromPref(this, Global.LOGD_USER);
            count =  DBUtils.getCountForTheUser(username, plant);
        }catch (Exception ee){

        }

        try {
            Intent intent = getIntent();
            AssetNum = intent.getStringExtra("AssetNum");
            SerialNum = intent.getStringExtra("SerialNum");
            bb_result =  intent.getBooleanExtra("result", false);

            if(bb_result){
                lblResult.setText("Succussfully inserted \n AssetNum:"+AssetNum +"\n SerialNum:"+SerialNum +" \n  This year "+username +" Evald \n   "+count +" Assets");
            }else{
                lblResult.setText("Failed to insert \n AssetNum:"+AssetNum +"\n SerialNum:"+SerialNum +"\n Please redo again."+" \nThis year "+username +" Evald  \n   "+count +" Assets");
            }

        }catch (Exception ee){
            Log.v(TAG,"Error in "+ee.getLocalizedMessage());
        }
    }


    public void doAnotherYes(View view){

        try {
            Intent intent = new Intent(CheckAnotherAssetActivity.this, AssetTagVisibilityActivity.class);
            //intent.putExtra("user_name", txtUserName.getText().toString());
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    public void doAnotherNo(View view){

        try {
            Intent intent = new Intent(CheckAnotherAssetActivity.this, LoginActivity.class);
            //intent.putExtra("user_name", txtUserName.getText().toString());
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }
}

