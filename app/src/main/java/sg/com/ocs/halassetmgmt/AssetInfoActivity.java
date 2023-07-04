package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Halliburton.mfgsystems.asstmgt.db.AssetDao;
import com.Halliburton.mfgsystems.asstmgt.db.AssetEvalDao;
import com.Halliburton.mfgsystems.asstmgt.db.DBUtils;
import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;

public class AssetInfoActivity extends AppCompatActivity {

    private static String TAG = "HALAssetManualInputAsset";
    private TextView lbl_title;
    private EditText txtAssetNumber;
    private EditText txtSerialNumber;
    private EditText txtCostCentre;
    private TextView txtCostCenterName;
    private EditText txtDesc;
    private AssetDao assetDao;
    private ConstraintLayout lvparent;

    //Image Views
    private ImageView imgSerialNbrTick;
    private ImageView imgSerialNbrEdit;

    private ImageView imgDescTick;
    private ImageView imgDescEdit;

    private ImageView imgAssetNbrTick;
    private ImageView imgAssetNbrEdit;

    private ImageView imgCostCntrTick;
    private ImageView imgCostCntrEdit;

    private ImageView imgAssetInUseTick;
    private ImageView imgAssetInUseComments;

    private ImageView imgAssetWithNoIssuesTick;
    private ImageView imgAssetWithNoIssuesComments;

    private EditText txtComments;
    private EditText txtAssetWithNoIssuesComments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_info);
        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);
        lbl_title = (TextView)findViewById(R.id.lbl_title);
        lbl_title.setText("Asset Info");

        //Adjust the keyboard above
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        txtComments= findViewById(R.id.txtComments);
        txtAssetWithNoIssuesComments = findViewById(R.id.txtAssetWithNoIssuesComments);
        txtCostCenterName = (TextView)findViewById(R.id.txtCostCenterName);
        txtAssetNumber  = (EditText)findViewById(R.id.txtAssetNumber);
        txtSerialNumber  = (EditText)findViewById(R.id.txtSerialNumber);
        txtCostCentre  = (EditText)findViewById(R.id.txtCostCentre);
        txtDesc  = (EditText)findViewById(R.id.txtDesc);

        imgSerialNbrTick = (ImageView)findViewById(R.id.imgSerialNbrTick);
        imgSerialNbrEdit = (ImageView)findViewById(R.id.imgSerialNbrEdit);
        imgDescTick = (ImageView)findViewById(R.id.imgDescTick);
        imgDescEdit = (ImageView)findViewById(R.id.imgDescEdit);
        imgAssetNbrTick= (ImageView)findViewById(R.id.imgAssetNbrTick);
        imgAssetNbrEdit= (ImageView)findViewById(R.id.imgAssetNbrEdit);
        imgCostCntrTick= (ImageView)findViewById(R.id.imgCostCntrTick);
        imgCostCntrEdit= (ImageView)findViewById(R.id.imgCostCntrEdit);
        imgAssetInUseTick= (ImageView)findViewById(R.id.imgAssetInUseTick);
        imgAssetInUseComments = (ImageView)findViewById(R.id.imgAssetInUseComments);
        imgAssetWithNoIssuesTick= (ImageView)findViewById(R.id.imgAssetWithNoIssuesTick);
        imgAssetWithNoIssuesComments= (ImageView)findViewById(R.id.imgAssetWithNoIssuesComments);

        imgSerialNbrEdit.setImageResource(R.drawable.graypenicon80);
        imgDescEdit.setImageResource(R.drawable.graypenicon80);
        imgAssetNbrEdit.setImageResource(R.drawable.graypenicon80);
        imgCostCntrEdit.setImageResource(R.drawable.graypenicon80);

        imgAssetInUseComments.setImageResource(R.drawable.commentsicon80);
        imgAssetWithNoIssuesComments.setImageResource(R.drawable.commentsicon80);

        txtComments.setVisibility(View.GONE);
        txtAssetWithNoIssuesComments.setVisibility(View.GONE);

        try {
            Intent intent = getIntent();


            //assetDao   = (AssetDao)intent.getSerializableExtra("asset_dao");
            assetDao = HalPrefManager.getSavedObjectFromPreference(this, Global.CURRENT_ASSET, AssetDao.class);
            Log.v(TAG, "From Pref. Got DAO = "+assetDao.toString());

            txtAssetNumber.setText(assetDao.getAssetNumber());
            txtSerialNumber.setText(assetDao.getSerialNumber());
            txtCostCentre.setText(assetDao.getCostCenterCode());
            txtDesc.setText(assetDao.getAssetDesc());
            txtCostCenterName.setText(assetDao.getCostCenterName());

            txtAssetNumber.setEnabled(false);
            txtSerialNumber.setEnabled(false);
            txtDesc.setEnabled(false);
            txtCostCentre.setEnabled(false);

            //display one box
            doAssetComments();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private Boolean save(){
        Boolean result = false;
        String asset_In_use_comments = "";
        String asset_with_no_issues = "";
        String desc = "";
        String asset_serial_nbr_visible = "";
        String asset_cost_cntr_visible = "";

        asset_In_use_comments = txtComments.getText().toString().trim();
        asset_with_no_issues = txtAssetWithNoIssuesComments.getText().toString().trim();
        desc = txtDesc.getText().toString().trim();
        asset_serial_nbr_visible = txtSerialNumber.getText().toString().trim();
        asset_cost_cntr_visible = txtCostCentre.getText().toString().trim();

        try {

            String username = HalPrefManager.readFromPref(this, Global.LOGD_USER);
            AssetDao assetDao = HalPrefManager.getSavedObjectFromPreference(this, Global.CURRENT_ASSET, AssetDao.class);

            AssetEvalDao dao = new AssetEvalDao();
            dao.setAssetEvalBy(username);
            dao.setAssetEvalDate(Global.getCurrentDateTime());
            dao.setAssetNbr(assetDao.getAssetNumber());
            dao.setPlant(assetDao.getPlant());
            dao.setCostCntr(assetDao.getCostCenterCode());
            dao.setSerialNbr(assetDao.getSerialNumber());

            if(is_asset_in_use_correct) {
                dao.setAssetStatus(2);
            }else{
                dao.setAssetStatus(3);
            }

            dao.setAssetNotFoundComments("");
            if(is_dsc_edited){
                dao.setAssetDescVisible(desc);
            }else{
                dao.setAssetDescVisible(assetDao.getAssetDesc());
            }

            dao.setAssetSerialNbrVisible(asset_serial_nbr_visible);
            dao.setAssetCostCntrVisible(asset_cost_cntr_visible);
            dao.setAssetInUseComments(asset_In_use_comments);
            dao.setAssetWIthNoIssuesComments(asset_with_no_issues);

            boolean bb = DBUtils.insertAssetEval(dao);
            if (bb) {
                result = true;
                Global.ShowSnackBar(lvparent, this, "Insert comments Success ");
                Toast.makeText(this, "Insert comments Success ", Toast.LENGTH_LONG).show();
            } else {
                result = false;
                Global.ShowSnackBar(lvparent, this, "Insert comments Failed ");
                Toast.makeText(this, "Insert comments Failed ", Toast.LENGTH_LONG).show();
            }

        }catch (Exception ee){
            Log.v(TAG,"Error in "+ee.getLocalizedMessage());
            Global.ShowSnackBar(lvparent, this, " Error : "+ee.getLocalizedMessage());
        }

        return result;
    }

    public void doSave(View view){

       Boolean bb =  save();

        try {
            Intent intent = new Intent(AssetInfoActivity.this, CheckAnotherAssetActivity.class);
            intent.putExtra("result", bb);
            intent.putExtra("AssetNum", txtAssetNumber.getText().toString());
            intent.putExtra("SerialNum", txtSerialNumber.getText().toString());
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    //no use
    public void doNext(View view){

        try {
            Intent intent = new Intent(AssetInfoActivity.this, CheckAnotherAssetActivity.class);
            //intent.putExtra("user_name", txtUserName.getText().toString());
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    public void showComments(View view){
        TransitionManager.beginDelayedTransition(lvparent);
        int vx = txtComments.getVisibility();
        if(vx==View.GONE){
            txtComments.setVisibility(View.VISIBLE);
        }else{
            txtComments.setVisibility(View.GONE);
        }
    }

    private boolean is_asset_in_use_comments = false;
    public void doAddAssetInUseComments(View view){
        doAssetComments();
    }

    private void doAssetComments(){
        TransitionManager.beginDelayedTransition(lvparent);
        if(!is_asset_in_use_comments){
            is_asset_in_use_comments = true;
            //Goto Page
            txtComments.setVisibility(View.VISIBLE);
            txtAssetWithNoIssuesComments.setVisibility(View.GONE);
            imgAssetInUseComments.setImageResource(R.drawable.commentsicongreen80);
        }else{
            is_asset_in_use_comments = false;
            txtComments.setVisibility(View.GONE);
            imgAssetInUseComments.setImageResource(R.drawable.commentsicon80);
        }
    }

    private boolean is_asset_with_noissues_comments = false;
    public void doAddAssetWithNoIssuesComments(View view){
        TransitionManager.beginDelayedTransition(lvparent);
        if(!is_asset_with_noissues_comments){
            is_asset_with_noissues_comments = true;
           //goto Page
            txtAssetWithNoIssuesComments.setVisibility(View.VISIBLE);
            txtComments.setVisibility(View.GONE);
            imgAssetWithNoIssuesComments.setImageResource(R.drawable.commentsicongreen80);
        }else{
            is_asset_with_noissues_comments = false;
            txtAssetWithNoIssuesComments.setVisibility(View.GONE);
            imgAssetWithNoIssuesComments.setImageResource(R.drawable.commentsicon80);
        }
    }


    private boolean is_asset_nbr_edited = false;
    public void doEditAssetNumber(View view){
        if(!is_asset_nbr_edited){
            is_asset_nbr_edited = true;
            txtAssetNumber.setEnabled(true);
            txtAssetNumber.requestFocus();
            imgAssetNbrEdit.setImageResource(R.drawable.geenpenicon80);
        }else{
            is_asset_nbr_edited = false;
            txtAssetNumber.setEnabled(false);
            imgAssetNbrEdit.setImageResource(R.drawable.graypenicon80);
        }
    }

    private boolean is_serial_nbr_edited = false;

    public void doEditSerialtNumber(View view){
        if(!is_serial_nbr_edited){
            is_serial_nbr_edited = true;
            txtSerialNumber.setEnabled(true);
            txtSerialNumber.requestFocus();
            imgSerialNbrEdit.setImageResource(R.drawable.geenpenicon80);
        }else{
            is_serial_nbr_edited = false;
            txtSerialNumber.setEnabled(false);
            imgSerialNbrEdit.setImageResource(R.drawable.graypenicon80);
        }


    }

    private boolean is_dsc_edited = false;

    public void doEditDesc(View view){
        if(!is_dsc_edited){
            is_dsc_edited = true;
            txtDesc.setEnabled(true);
            txtDesc.requestFocus();
            imgDescEdit.setImageResource(R.drawable.geenpenicon80);
        }else{
            is_dsc_edited = false;
            txtDesc.setEnabled(false);
            imgDescEdit.setImageResource(R.drawable.graypenicon80);
        }
    }

    private boolean is_cost_cntr_edited = false;

    public void doEditCC(View view){
        if(!is_cost_cntr_edited){
            is_cost_cntr_edited = true;
            txtCostCentre.setEnabled(true);
            txtCostCentre.requestFocus();
            imgCostCntrEdit.setImageResource(R.drawable.geenpenicon80);
        }else{
            is_cost_cntr_edited = false;
            txtCostCentre.setEnabled(false);
            imgCostCntrEdit.setImageResource(R.drawable.graypenicon80);
        }
    }

    private boolean is_serial_nbr_correct = true;
    //Serial number and description show auto
    public void doSerialNumberTick(View view){
        if(!is_serial_nbr_correct){
            is_serial_nbr_correct = true;
            imgSerialNbrTick.setImageResource(R.drawable.greentick80);
        }else{
            is_serial_nbr_correct = false;
            imgSerialNbrTick.setImageResource(R.drawable.graytick80);
        }
    }

    private boolean is_desc_correct = true;
    //Serial number and description show auto
    public void doDescTick(View view){
        if(!is_desc_correct){
            is_desc_correct = true;
            imgDescTick.setImageResource(R.drawable.greentick80);
        }else{
            is_desc_correct = false;
            imgDescTick.setImageResource(R.drawable.graytick80);
        }
    }

    private boolean is_cost_cntr_correct = true;

    public void doCostCntrTick(View view){
        if(!is_cost_cntr_correct){
            is_cost_cntr_correct = true;
            imgCostCntrTick.setImageResource(R.drawable.greentick80);
        }else{
            is_cost_cntr_correct = false;
            imgCostCntrTick.setImageResource(R.drawable.graytick80);
        }
    }


    private boolean is_asset_in_use_correct = true;

    public void doAssetInUseTick(View view){
        if(!is_asset_in_use_correct){
            is_asset_in_use_correct = true;
            imgAssetInUseTick.setImageResource(R.drawable.greentick80);
        }else{
            is_asset_in_use_correct = false;
            imgAssetInUseTick.setImageResource(R.drawable.graytick80);
        }
    }

    private boolean is_asset_with_noissues_correct = true;

    public void doAssetWithNoissuesTick(View view){
        if(!is_asset_with_noissues_correct){
            is_asset_with_noissues_correct = true;
            imgAssetWithNoIssuesTick.setImageResource(R.drawable.greentick80);
        }else{
            is_asset_with_noissues_correct = false;
            imgAssetWithNoIssuesTick.setImageResource(R.drawable.graytick80);
        }
    }




}
