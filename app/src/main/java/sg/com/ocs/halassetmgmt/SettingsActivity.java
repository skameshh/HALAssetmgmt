package sg.com.ocs.halassetmgmt;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private static String TAG = "HALAssetAssetTagVisib";
    private ConstraintLayout lvparent;
    private TextView lbl_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);

        lbl_title = (TextView)findViewById(R.id.lbl_title);
        lbl_title.setText("Settings");
        // Enables Always-on

    }
}