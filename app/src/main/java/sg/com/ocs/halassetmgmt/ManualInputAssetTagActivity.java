package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.Halliburton.mfgsystems.asstmgt.db.AssetDao;
import com.Halliburton.mfgsystems.asstmgt.db.DBUtils;
import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;

import java.util.ArrayList;

public class ManualInputAssetTagActivity extends AppCompatActivity {

    private static String TAG = "HALAssetManualInputAsset";
    private ConstraintLayout lvparent;
    private TextView lbl_title;
    private EditText txtAssetSerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input_asset_tag);
        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);

        lbl_title = (TextView)findViewById(R.id.lbl_title);
        lbl_title.setText("Manual Input");

        txtAssetSerialNumber = (EditText)findViewById(R.id.txtAssetSerialNumber);
    }


    /**
     * If asset found, go to Asset Info page
     * If not found go to Not found page and Add Comment
     * @param view
     */
    public void doRetrieveAsset(View view){
        String srl_num = txtAssetSerialNumber.getText().toString();
        String plant = HalPrefManager.readFromPref(this, Global.SEL_PLANT);
        ArrayList al =  DBUtils.getAllAssets(plant,null,srl_num, null);

        if(al.size()>0){
            try {
                AssetDao dao = (AssetDao)al.get(0);
                //put username in the Memory
                HalPrefManager.saveObjectToSharedPreference(this,  Global.CURRENT_ASSET, dao);

                Intent intent = new Intent(ManualInputAssetTagActivity.this, AssetInfoActivity.class);
                //intent.putExtra("asset_dao", dao);
                startActivity(intent);
            }catch(Exception e){
                Log.v(TAG,"Error in "+e.getLocalizedMessage());
            }
        }else{
            try {
                HalPrefManager.saveObjectToSharedPreference(this,  Global.CURRENT_ASSET, null);

                Intent intent = new Intent(ManualInputAssetTagActivity.this, AssetNotFoundActivity.class);
                intent.putExtra("serial_num", srl_num);
                startActivity(intent);
            }catch(Exception e){
                Log.v(TAG,"Error in "+e.getLocalizedMessage());
            }
        }


    }
}
