package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Halliburton.mfgsystems.asstmgt.db.AssetDao;
import com.Halliburton.mfgsystems.asstmgt.db.AssetEvalDao;
import com.Halliburton.mfgsystems.asstmgt.db.DBUtils;
import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;

public class AssetNotFoundCommentsActivity extends AppCompatActivity {

    private static String TAG = "HALAssetNotFoundComments";
    private ConstraintLayout lvparent;
    private TextView lbl_title;
    private String serial_num = "";
    private EditText txtNotFoundCommentsSerialNumber;
    private EditText txtNotFoundComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_not_found_comments);
        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);
        lbl_title = (TextView)findViewById(R.id.lbl_title);
        lbl_title.setText("Asset Not Found Comments ");

        txtNotFoundComments = findViewById(R.id.txtNotFoundComments);
        txtNotFoundCommentsSerialNumber = findViewById(R.id.txtNotFoundCommentsSerialNumber);

        try {
            Intent intent = getIntent();
            serial_num   = intent.getStringExtra("serial_num");
            Log.v(TAG, "Got slno = "+serial_num);
            txtNotFoundCommentsSerialNumber.setText(serial_num);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void doSaveAndClose(View view){

        Log.v(TAG, "Entered Text: "+txtNotFoundComments.getText().toString());

        String txtNF = txtNotFoundComments.getText().toString();
        if(txtNF.equals("") || txtNF.length()<1){
            txtNotFoundComments.setBackgroundColor(Color.RED);
        }else{
            txtNotFoundComments.setBackgroundColor(Color.WHITE);
        }

        boolean res = false;

        try {
            String username = HalPrefManager.readFromPref(this, Global.LOGD_USER);
            String plant = HalPrefManager.readFromPref(this, Global.SEL_PLANT);
            String cost_cntrl_code =HalPrefManager.readFromPref(this, Global.SEL_COST_CNTR_CODE );

            AssetEvalDao dao = new AssetEvalDao();

            dao.setAssetDescVisible("");
            dao.setAssetEvalBy(username);
            dao.setAssetEvalDate(Global.getCurrentDateTime());
            dao.setAssetNbr("");
            dao.setPlant(plant);
            dao.setCostCntr(cost_cntrl_code);
            dao.setSerialNbr(serial_num);
            dao.setAssetStatus(1);
            dao.setAssetNotFoundComments(txtNF);
            dao.setAssetSerialNbrVisible("");
            dao.setAssetDescVisible("");
            dao.setAssetCostCntrVisible("");
            dao.setAssetInUseComments("");
            dao.setAssetWIthNoIssuesComments("");

            res = DBUtils.insertAssetEval(dao);
            if (res) {
                Toast.makeText(this, "Insert comments Success ", Toast.LENGTH_LONG).show();
                Global.ShowSnackBar(lvparent, this, "Insert comments Success ");
            } else {
                Toast.makeText(this, "Insert comments Failed ", Toast.LENGTH_LONG).show();
                Global.ShowSnackBar(lvparent, this, "Insert comments Failed ");
            }

        }catch (Exception ee){
            Log.v(TAG,"Error in "+ee.getLocalizedMessage());
            Global.ShowSnackBar(lvparent, this, " Error : "+ee.getLocalizedMessage());
        }


        //send it to new asset page AssetTagVisibilityActivity ! CheckAnotherAssetActivity
        try {
            Intent intent = new Intent(AssetNotFoundCommentsActivity.this, CheckAnotherAssetActivity.class);
            intent.putExtra("result", res);
            intent.putExtra("AssetNum", "");
            intent.putExtra("SerialNum", serial_num);
            startActivity(intent);
          /*  if(res){
                intent.putExtra("ret_message", "Insert comments Success " );
            }else{
                intent.putExtra("ret_message", "Insert comments Failed " );
            }*/


        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }
}
