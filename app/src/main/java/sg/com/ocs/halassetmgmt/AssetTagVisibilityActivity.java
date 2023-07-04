package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Halliburton.mfgsystems.global.Global;

public class AssetTagVisibilityActivity extends AppCompatActivity {

    private static String TAG = "HALAssetAssetTagVisib";
    private ConstraintLayout lvparent;
    private TextView lbl_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);
        setContentView(R.layout.activity_asset_tag_visibility);
        lbl_title = (TextView)findViewById(R.id.lbl_title);
        lbl_title.setText("Asset Tag Visible");

        String ret_message = null;
        try {
            Intent intent = getIntent();
            ret_message   = intent.getStringExtra("ret_message");
            Log.v(TAG, "ret_message = "+ret_message);
            if(ret_message!=null){
                Toast.makeText(this, ret_message, Toast.LENGTH_LONG).show();
                Global.ShowSnackBar(lvparent, this, ret_message);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void doAssetNotAvailable(View view){
        try {
            Intent intent = new Intent(AssetTagVisibilityActivity.this, ManualInputAssetTagActivity.class);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    public void doAssetAvailable(View view){
        try {
            Intent intent = new Intent(AssetTagVisibilityActivity.this, ScanAssetTagActivity.class);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }
}
