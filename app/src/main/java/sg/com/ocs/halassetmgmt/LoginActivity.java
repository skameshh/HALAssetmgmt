package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Halliburton.mfgsystems.asstmgt.db.DBUtils;
import com.Halliburton.mfgsystems.asstmgt.db.UserViewDao;
import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;

public class LoginActivity extends AppCompatActivity {

    private static String TAG = "HALAssetLogin";
    public static EditText txtUserName;
    private TextView lbl_user;
    private Button btnScanEmpid;
    private Button btnCancel;
    private ConstraintLayout lvparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);
        btnScanEmpid = findViewById(R.id.btnScanEmpid);
        txtUserName = findViewById(R.id.txtUserName);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        //this.getSupportedActionBar().hide();

        // lbl_user = findViewById(R.id.lbl_user);
       // lbl_user = (TextView)findViewById(R.id.LabelUser);
       // lbl_user.setText("Welcome: Guest");
        HalPrefManager.putInPref(this,Global.LOGD_USER,"Guest");
        Global.ShowSnackBar(lvparent, this ,"Please enter or scan your Emp Id");
    }

    public void doClick(View v) {
        try {
            Animation animation1 =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
            btnCancel.startAnimation(animation1);
            btnCancel.setVisibility(View.GONE);
        }catch (Exception ee){
            ee.printStackTrace();
        }
    }

    public void startScanner(View view){
        try {
            Intent intent = new Intent(LoginActivity.this, ScanActivityForLogin.class);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }


    public void doLogin(View view){

        CheckLogin login = new CheckLogin();
        login.execute();

    }




    public void doSuccessLogin(){
        //put username in the Memory
        HalPrefManager.putInPref(this,Global.LOGD_USER, txtUserName.getText().toString());
        //String username = HalPrefManager.readFromPref(this, "USER");
        try {
            Intent intent = new Intent(LoginActivity.this, SelectPlantActivity.class);
            intent.putExtra("user_name", txtUserName.getText().toString());
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    private class CheckLogin extends AsyncTask<String, Void, String> {

        String empid = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            empid = txtUserName.getText().toString();
        }


        @Override
        protected String doInBackground(String... strings) {
            UserViewDao dao = DBUtils.doCheckLogin(empid,null);
            if(dao==null){
                Log.v(TAG,"Error in doCHeckLogin dao is null ");
                return "failed";
            }
            return "Success";
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Success")) {
                Global.ShowSnackBar(lvparent, LoginActivity.this ,"Login Success");
                doSuccessLogin();
            }else{
                Global.ShowSnackBar(lvparent, LoginActivity.this ,"Login Failed");
            }
        }
    }
}
