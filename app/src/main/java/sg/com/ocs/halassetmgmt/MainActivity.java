package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Halliburton.mfgsystems.asstmgt.db.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "HALAssetSelectPlant";
    private Button btnFetch;
    private EditText txtWo;
    private EditText txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWo = (EditText)findViewById(R.id.txtWo);
        txtResult = (EditText)findViewById(R.id.txtResult);
        btnFetch = (Button)findViewById(R.id.btnFetch);

        this.txtWo.setText("000106301577");
    }

    public void getInfo(View view){
        try {
            ConnectToDatabase();//doMSSQLConnectionWithMSSQLAUth();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *Provider=SQLOLEDB.1;Password=L4c^K0i~K1f#;Persist Security Info=True;User ID=2088_REPORTS;Initial Catalog=AP101_MPM;
     * Data Source=AP1REPORTING;Use Procedure for Prepare=1;Auto Translate=True;Packet Size=4096;Use Encryption for Data=False;Tag with column collation when possible=False
     * @return
     */
    public static Connection doMSSQLConnectionWithMSSQLAUth(){
        Connection conn = null;
        try {
            String dbURL = "jdbc:sqlserver://AP1REPORTING;databaseName=AP101_MPM";
            conn = DriverManager.getConnection(dbURL, "2088_REPORTS", "L4c^K0i~K1f#");
            Log.v(TAG,"Connection success \n");
        }catch(Exception e){
            e.printStackTrace();
            Log.v(TAG,"Connection failed, msg="+e.getMessage()+"\n\n");
        }
        return conn;
    }

    public  void ConnectToDatabase(){
        txtResult.setText("");
        String wono = txtWo.getText().toString().trim();

        //doFetchWIthoutThread(wono);

        //MyThread mt = new MyThread(wono);
       // mt.start();

        String res = DBUtils.doFetch(wono);
        txtResult.setText(res);
    }


    private class MyThread extends  Thread{
        String wono = null;
        public MyThread(String wono){
            Log.v(TAG, "MTTHread\n\n");
            this.wono = wono;
        }
        public void run(){
            doFetch(wono);
        }
    }
    public   String SELECT_PROD_ORD = "SELECT [PROD_ORDR_NBR] ,[Material_NOZERO],[Old_Material],[BTCH_NBR] "
            + " FROM [AP101_MPM].[MPM_RPTS_VIEWS].[HV_PROD_ORDR_HEADER] B JOIN [CAR01_CPS_DATA].[dbo].[MATERIAL_MASTER_CPS_HEADER] C ON C.[Material] = B.[MTRL_NBR] "
            + " where PROD_ORDR_NBR =?";

    private void doFetchWIthoutThread(String wono){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        doFetch(wono);
    }

    private void doFetch(String wono){
        try {
            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "2088_REPORTS";
            String password = "L4c^K0i~K1f#";
            Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://10.218.195.48:1433/AP101_MPM;user=" + username + ";password=" + password);

            Log.v(TAG,"Connection OK");
            PreparedStatement stmt = DbConn.prepareStatement(SELECT_PROD_ORD);
            stmt.setString(1, wono);


            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                String iprodOrdNo = rs.getString("PROD_ORDR_NBR");
                String materialNo = rs.getString("Material_NOZERO");
                String batchNo = rs.getString("BTCH_NBR");
                String oldMaterialNo = rs.getString("Old_Material");
                Log.v(TAG,"iprodOrdNo = "+iprodOrdNo +", mat-on="+materialNo);

                String result = "Order No :"+iprodOrdNo +"\n MaterialNo: "+materialNo +"\n batchNo:"+batchNo+"\n OldMaterialNo:"+oldMaterialNo;
                txtResult.setText(result);
                count ++;
            }

            if(count==0){
                txtResult.setText("No Data found for this wo no = "+wono);
            }

            rs.close();
            stmt.close();
            DbConn.close();

        } catch (Exception e)
        {
            Log.v(TAG,"Error connection");
            e.printStackTrace();
        }finally{

        }
    }


    //Order
//105041092
//105041093
//105229681
//105299981
//105308066
//105372342
//105580533
//105703646
//106054372
//106102366
//106134567



    public static void close(String methodName, ResultSet rs, Statement stat, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ex) {
            //Logger.info(methodName + " release the db RS [Exception]>>" + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            if (stat != null) {
                stat.close();
            }

        } catch (Exception ex) {
            // Logger.info(methodName + " release the db stmt [Exception]>>" + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (Exception ex) {
            //Logger.info(methodName + " release the db conn [Exception]>>" + ex.getMessage());
            ex.printStackTrace();
        }

    }



}
