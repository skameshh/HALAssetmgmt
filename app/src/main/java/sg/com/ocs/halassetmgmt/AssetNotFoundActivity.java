package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AssetNotFoundActivity extends AppCompatActivity {

    private static String TAG = "HALAssetNotFound";
    private ConstraintLayout lvparent;
    private TextView lbl_title;
    private TextView lblAssetNotFound;
    private EditText txtSlNo;
    private String serial_num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_not_found);
        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);
        txtSlNo = findViewById(R.id.txtSlNo);

        lbl_title = (TextView)findViewById(R.id.lbl_title);
        lbl_title.setText("Asset Not Found");


        lblAssetNotFound = (TextView)findViewById(R.id.lblAssetNotFound);

        try {
            Intent intent = getIntent();
            serial_num   = intent.getStringExtra("serial_num");

            Log.v(TAG, "Got slno = "+serial_num);
            txtSlNo.setText(serial_num);

            lblAssetNotFound.setText("Asset ["+serial_num +"] Not Found !");
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void doAddComments(View view){
        try {
            Intent intent = new Intent(AssetNotFoundActivity.this, AssetNotFoundCommentsActivity.class);
            intent.putExtra("serial_num", serial_num);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    public void doCancel(View view){
        try {
            Intent intent = new Intent(AssetNotFoundActivity.this, ManualInputAssetTagActivity.class);
            intent.putExtra("serial_num", serial_num);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

}
