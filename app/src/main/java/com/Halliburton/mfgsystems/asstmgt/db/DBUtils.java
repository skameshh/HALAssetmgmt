package com.Halliburton.mfgsystems.asstmgt.db;

import android.os.StrictMode;
import android.util.Log;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

public class DBUtils {
    private static String TAG = "HALAssetDBUtils";
    private static boolean use_home_phone = false;//if you use home wifi connect to this


    /**
     *  Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
     *             String username = "2088_REPORTS";
     *             String password = "L4c^K0i~K1f#";
     *             Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://10.218.195.48:1433/AP101_MPM;user=" + username + ";password=" + password);
     * @return
     */
    public static Connection getRemoteServerConnectionWithMSSQLAUth(){
        Connection conn = null;
        doInit();
        try {
            String dbURL = "jdbc:jtds:sqlserver://10.116.18.14:1433/AP101_MPM";//10.218.195.48=old ip
            conn = DriverManager.getConnection(dbURL, "2088_REPORTS", "L4c^K0i~K1f#");
            Log.v(TAG,"Connection success \n");
        }catch(Exception e){
            e.printStackTrace();
            Log.v(TAG,"Remote Connection failed, msg="+e.getMessage()+"\n\n");
        }
        return conn;
    }

    public static Connection getLionServerConnectionWithMSSQLAUth(){
        doInit();
        Connection conn = null;
         conn = commonU(conn);
        return conn;
    }

    private static Connection commonU(Connection conn){
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String dbURL = null;
            if(use_home_phone){
                dbURL = "jdbc:jtds:sqlserver://192.168.1.51:1433/AssetMgmt2021";
            }else{
                //dbURL = "jdbc:jtds:sqlserver://10.116.18.14:1433/AssetMgmt";//10.221.199.37
              //  dbURL = "jdbc:jtds:sqlserver://10.221.199.37:1433/SQLEXPRESS;DatabaseName=AssetMgmt";
               // dbURL = "jdbc:jtds:sqlserver://CARDEV001:1433/AssetMgmt"; //- working - June 2021

               // dbURL = "jdbc:jtds:sqlserver://AZUSCDDBW208.corp.halliburton.com:1433/AssetMgmt"; //- Working azure - December 2021
               // dbURL = "jdbc:jtds:sqlserver://10.123.20.110:1433/AssetMgmt";// cardev - No Use

                //MFGDDBS001 - Dev = azuscscdbw220.corp.halliburton.com  [10.123.20.24]
                dbURL = "jdbc:jtds:sqlserver://10.123.20.24:1433/AssetMgmt";

                //String url = "jdbc:jtds:sqlserver://localhost:<portnumber>/TESTDATA;DatabaseName=master;user=sa;password="
                //jdbc:jtds:sqlserver://localhost/master;instance=TESTDATA;
                //dbURL = "jdbc:jtds:sqlserver://DKTP611587/AssetMgmt;instance=SQLEXPRESS";
            }

            if(use_home_phone){
                conn = DriverManager.getConnection(dbURL, "sa", "ABCD7890&*()");
            }else{
                conn = DriverManager.getConnection(dbURL, "ASSET_MGMT_USER", "ABCD7890&*()");
            }

            //conn = DriverManager.getConnection(dbURL, "sa", "ABCD7890&*()");
            Log.v(TAG,"Lion Connection success \n");
        }catch(Exception e){
            e.printStackTrace();
            Log.v(TAG,"SQL Connection Failed, msg="+e.getMessage()+"\n\n");
        }
        return conn;
    }

    public static Connection doMSSQLConnectionWithMSSQLAUth(){
        Connection conn = null;
        try {
            String dbURL = "jdbc:jtds:sqlserver://10.218.195.48:1433/AP101_MPM";
            conn = DriverManager.getConnection(dbURL, "2088_REPORTS", "L4c^K0i~K1f#");
            Log.v(TAG,"Connection success \n");
        }catch(Exception e){
            e.printStackTrace();
            Log.v(TAG,"Connection failed, msg="+e.getMessage()+"\n\n");
        }
        return conn;
    }

    //===================

    static String INSERT_ASSET_EVAL_SQL = "insert into t_asset_eval( ASSET_EVAL_BY, ASSET_EVAL_DATE, ASSET_NBR,  PLANT, "
            + " COST_CNTR, SERIAL_NBR, ASSET_STATUS, ASSET_NOT_FOUND_COMMENTS,ASSET_SERIAL_NBR_VISIBLE, ASSET_DESC_VISIBLE, " +
            " ASSET_COST_CNTR_VISIBLE, ASSET_IN_USE_COMMENTS, ASSET_WITH_NO_ISSUES_COMMENTS" +
            " ) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static boolean insertAssetEval(AssetEvalDao dao){
        Connection conn = null;
        PreparedStatement st = null;
        int intResult = 0;
        boolean bOk = false;

        try {
            conn = getLionServerConnectionWithMSSQLAUth();
            st = conn.prepareStatement(INSERT_ASSET_EVAL_SQL);
            int cc = 1;

            st.setString(cc++, dao.getAssetEvalBy());//1
            st.setString(cc++, dao.getAssetEvalDate());
            st.setString(cc++, dao.getAssetNbr());
            st.setString(cc++, dao.getPlant());
            st.setString(cc++, dao.getCostCntr());
            st.setString(cc++, dao.getSerialNbr());
            st.setInt(cc++, dao.getAssetStatus());
            st.setString(cc++, dao.getAssetNotFoundComments());
            st.setString(cc++, dao.getAssetSerialNbrVisible());
            st.setString(cc++, dao.getAssetDescVisible());
            st.setString(cc++, dao.getAssetCostCntrVisible());
            st.setString(cc++, dao.getAssetInUseComments());
            st.setString(cc++, dao.getAssetWIthNoIssuesComments());

            Log.v(TAG,"insert INSERT_ASSET_EVAL_SQL sql = "+INSERT_ASSET_EVAL_SQL +", bean="+dao.toString() );
            //
            intResult = st.executeUpdate();

            if (intResult > 0) {
                bOk = true;
                // con.commit();
                Log.v(TAG,"insertAssetEval Success ");
            }else{
                Log.v(TAG,"insertAssetEval Failed ");
            }

        } catch (Exception ex) {

            Log.v(TAG,"insertAssetEval : "+ ex);
            ex.printStackTrace();
        } finally {
            doClose(conn, null, null);
        }

        return bOk;
    }




    //==================

    public static UserViewDao doCheckLogin(String empid, String password){
        String det = null;
        UserViewDao dao = null;
        ResultSet rs = null;
        CallableStatement cstmt = null;
        Connection conn = null;
        try {
            doInit();
            //
            conn = getLionServerConnectionWithMSSQLAUth();
            Log.v(TAG, "doCheckLogin() empid= "+empid);
            cstmt = conn.prepareCall("{call CHECK_LOGIN(?,?)}");
            cstmt.setString("EMPID", empid);
            cstmt.setString("PASSWORD", "654321");

            boolean results = cstmt.execute();
            int rowsAffected = 0;

            // Protects against lack of SET NOCOUNT in stored prodedure
            while (results || rowsAffected != -1) {
                if (results) {
                    rs = cstmt.getResultSet();
                    break;
                } else {
                    rowsAffected = cstmt.getUpdateCount();
                }
                results = cstmt.getMoreResults();
            }

            while (rs.next()) {

                String full_name = rs.getString("full_name");
                String hid = rs.getString("hid");
                String department = rs.getString("department");
                String cost_center = rs.getString("cost_center");
                String plant = rs.getString("plant");
                String role_name = rs.getString("role_name");
                String emp_id = rs.getString("emp_id");
                String rpassword = rs.getString("password");
                Integer id = rs.getInt("oid");


                dao = new UserViewDao();
                dao.setId(id);
                dao.setPlant(plant);
                dao.setCostCenter(cost_center);
                dao.setDepartment(department);
                dao.setEmpId(emp_id);
                dao.setFullName(full_name);
                dao.setHid(hid);
                dao.setPassword(rpassword);
                dao.setRoleName(role_name);

            }

            rs.close();
            cstmt.close();
            conn.close();

        } catch (Exception e) {
            Log.v(TAG, "doCheckLogin() Error connection "+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {

            doClose(conn, cstmt, rs);
        }

        return dao;

    }

    private static String SQL_GET_COST_CENTRE_CODE = "select COST_CNTR_CD from view_list_of_cost_centres " +
            " where COST_CNTR_NM=? and PLNT_CD=?";
    public static String getCostCenterCDForCCName(String ccName, String plant){
        String det = null;
        String ccCode = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            doInit();
            //
            conn = getLionServerConnectionWithMSSQLAUth();
            Log.v(TAG, "getCostCenterCDForCCName() Connection OK");
            pstmt = conn.prepareStatement(SQL_GET_COST_CENTRE_CODE);
            pstmt.setString(1, ccName);
            pstmt.setString(2, plant);

            rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {

                ccCode = rs.getString("COST_CNTR_CD");
                Log.v(TAG, "ccCode = " + ccCode );

            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            Log.v(TAG, "getCostCenterCDForCCName() Error connection "+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {

            doClose(conn, pstmt, rs);
        }

        return ccCode;
    }

    private static String SQL_GET_USER_EVAL_COUNT = "select count(*) as count from T_ASSET_EVAL " +
            " where plant=? and ASSET_EVAL_BY=? and  year(ASSET_EVAL_DATE)=?";
    public static int getCountForTheUser(String empNo, String plant){

        int count   = 0;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        try {
            doInit();
            //
            conn = getLionServerConnectionWithMSSQLAUth();
            Log.v(TAG, "getCountForTheUser() Connection OK");
            pstmt = conn.prepareStatement(SQL_GET_USER_EVAL_COUNT);
            pstmt.setString(1, plant);
            pstmt.setString(2, empNo);
            pstmt.setString(3, year+"");

            rs = pstmt.executeQuery();

            while (rs.next()) {

                count = rs.getInt("count");
                Log.v(TAG, "count = " + count );

            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            Log.v(TAG, "getCountForTheUser() Error connection "+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {

            doClose(conn, pstmt, rs);
        }

        return count;
    }


    public static ArrayList getAllCostCenterSP(String plant){
        String det = null;
        ArrayList al = new ArrayList();
        ResultSet rs = null;
        CallableStatement cstmt = null;
        Connection conn = null;
        try {

            //
            conn = getLionServerConnectionWithMSSQLAUth();
            Log.v(TAG, "getAllCostCenterSP() Connection OK");
            cstmt = conn.prepareCall("{call COST_CENTER_LIST(?)}");
            cstmt.setString("PLANT", plant);

            boolean results = cstmt.execute();
            int rowsAffected = 0;

            // Protects against lack of SET NOCOUNT in stored prodedure
            while (results || rowsAffected != -1) {
                if (results) {
                    rs = cstmt.getResultSet();
                    break;
                } else {
                    rowsAffected = cstmt.getUpdateCount();
                }
                results = cstmt.getMoreResults();
            }


            while (rs.next()) {
                String PLNT_CD = rs.getString("PLANT");
                String COST_CNTR_NM = rs.getString("CC_CNTR_NM");
                String COST_CNTR_CD = rs.getString("CC_CNTR");
                //Log.v(TAG, "COST_CNTR_CD = " + COST_CNTR_CD );

                CostCenterDao dao = new CostCenterDao();
                dao.setCostCenterCode(COST_CNTR_CD);
                dao.setCostCenterName(COST_CNTR_NM);
                dao.setPlant(PLNT_CD);
                al.add(dao);
            }

            rs.close();
            cstmt.close();
            conn.close();

        } catch (Exception e) {
            Log.v(TAG, "getAllCostCenterSP() Error connection "+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {

            doClose(conn, cstmt, rs);
        }

        return al;

    }


   // private static String SQL_GET_ALL_COST_CENTRE = "Select * from t_cc_cntr ";
    //get all cost centres
    public static ArrayList getAllCostCenter(String plant){
        String det = null;
        ArrayList al = new ArrayList();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            doInit();
            //
            String SQL_GET_ALL_COST_CENTRE = "Select * from t_cc_cntr where PLANT='"+plant+"'";
            conn = getLionServerConnectionWithMSSQLAUth();
            Log.v(TAG, "getAllCostCenter() Connection OK");
            pstmt = conn.prepareStatement(SQL_GET_ALL_COST_CENTRE);
            //stmt.setString(1, wono);

            rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                String PLNT_CD = rs.getString("PLANT");
                String COST_CNTR_NM = rs.getString("CC_CNTR_NM");
                String COST_CNTR_CD = rs.getString("CC_CNTR");
                Log.v(TAG, "COST_CNTR_CD = " + COST_CNTR_CD );

                CostCenterDao dao = new CostCenterDao();
                dao.setCostCenterCode(COST_CNTR_CD);
                dao.setCostCenterName(COST_CNTR_NM);
                dao.setPlant(PLNT_CD);
                al.add(dao);
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            Log.v(TAG, "Error connection "+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {

            doClose(conn, pstmt, rs);
        }

        return al;

    }


    public static ArrayList getAllAssets(String plant, String costCenter, String serialNumber, String invNumber){

        ArrayList al = new ArrayList();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        String sql = "select ast.id, ast.CO_CD, ast.COST_CNTR_CD, ast.ASSET_NBR, ast.PLANT, ast.ASSET_CLS, ast.SERIAL_NBR, ast.INVNTRY_NBR,  ast.ASSET_NUM_TEXT, ast.ASSET_DSC, ast.WBS_ELEMENT, " +
                "ast.ASSET_YEAR, ast.INSERTED_ON, ast.INSERTED_BY, cc.CC_CNTR_NM  " +
                "from t_ASSET_REGISTER ast, T_CC_CNTR cc  where ast.COST_CNTR_CD=cc.CC_CNTR";
        try {
            doInit();

            if(plant!=null){
                if(sql.contains(" where ")){
                    sql = sql + " and ast.PLANT='"+plant+"'";
                }else{
                    sql = sql + " where ast.PLANT='"+plant+"'";
                }
            }

           /* if(assetNumber!=null){
                if(sql.contains(" where ")){
                    sql = sql + " and ast.ASSET_NBR='"+assetNumber+"'";
                }else{
                    sql = sql + " where ast.ASSET_NBR='"+assetNumber+"'";
                }
            }

            if(serialNumber!=null){
                if(sql.contains(" where ")){
                    sql = sql + " and ast.SERIAL_NBR='"+serialNumber+"'";
                }else{
                    sql = sql + " where ast.SERIAL_NBR='"+serialNumber+"'";
                }
            }*/

            if(serialNumber!=null){
                if(sql.contains(" where ")){
                    sql = sql + " and (ast.SERIAL_NBR='"+serialNumber+"' OR ast.ASSET_NBR='"+serialNumber+"'  )";
                }else{
                    sql = sql + " where ast.SERIAL_NBR='"+serialNumber+"'  OR ast.ASSET_NBR='"+serialNumber+"' ";
                }
            }

            int year = Calendar.getInstance().get(Calendar.YEAR);

            if(sql.contains(" where ")){
                sql = sql + " and  ASSET_YEAR='"+year+"'";
            }else{
                sql = sql + " where  ASSET_YEAR='"+year+"'";
            }

            //
            conn = getLionServerConnectionWithMSSQLAUth();
            Log.v(TAG, "getAllAssets() Connection OK, sql = "+sql);
            pstmt = conn.prepareStatement(sql);
            //stmt.setString(1, wono);

            rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String PLNT_CD = rs.getString("PLANT");
                String CO_CD = rs.getString("CO_CD");
                String ASSET_NBR = rs.getString("ASSET_NBR");
               // String MAIN_ASSET_NBR = rs.getString("MAIN_ASSET_NBR");
               // String ASSET_SUB_NBR = rs.getString("ASSET_SUB_NBR");
                String ASSET_CLS = rs.getString("ASSET_CLS");
                String POSID_WBS_ELEMENT = rs.getString("WBS_ELEMENT");
                String COST_CNTR_CD = rs.getString("COST_CNTR_CD");
                String COST_CNTR_NM = rs.getString("CC_CNTR_NM");
                String ASSET_DSC = rs.getString("ASSET_DSC");
                //String POSTU_SHORT_DESC = rs.getString("POSTU_SHORT_DESC");
                String SERIAL_NBR = rs.getString("SERIAL_NBR");
                String INVNTRY_NBR = rs.getString("INVNTRY_NBR");
               // String ASSET_CAP_DT = rs.getString("ASSET_CAP_DT");
               // String DEACTVTN_DT = rs.getString("DEACTVTN_DT");

                AssetDao dao = new AssetDao();
               // dao.setAssetCapDate(ASSET_CAP_DT);
                dao.setAssetCls(ASSET_CLS);
               // dao.setAssetDesc(ASSET_DSC);
                dao.setAssetNumber(ASSET_NBR);
               // dao.setAssetPostuShortDesc(POSTU_SHORT_DESC);
               // dao.setAssetSubNumber(ASSET_SUB_NBR);
                dao.setCocd(CO_CD);
                dao.setCostCenterCode(COST_CNTR_CD);
                dao.setCostCenterName(COST_CNTR_NM);
               // dao.setDeactivationDate(DEACTVTN_DT);
                dao.setId(id);
                dao.setInventoryNumber(INVNTRY_NBR);
                //dao.setMainAssetNumber(MAIN_ASSET_NBR);
                dao.setSerialNumber(SERIAL_NBR);
                dao.setPosidWbsElement(POSID_WBS_ELEMENT);
                dao.setPlant(PLNT_CD);
                dao.setAssetDesc(ASSET_DSC);

                Log.v(TAG, "PLNT_CD = " + PLNT_CD );
                al.add(dao);
            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            Log.v(TAG, "getAllAssets Error connection "+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            doClose(conn, pstmt, rs);
        }

        return al;
    }


    private static String SQL_GET_ALL_PLANTS = "select * from t_plant";
    //get all cost centres
    public static ArrayList getAllPlants(){
        String det = null;
        ArrayList al = new ArrayList();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {

            //
              conn = getLionServerConnectionWithMSSQLAUth();
            Log.v(TAG, "getAllPlants() Connection OK");
              pstmt = conn.prepareStatement(SQL_GET_ALL_PLANTS);
            //stmt.setString(1, wono);

            rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                String PLNT_CD = rs.getString("PLANT");
                Log.v(TAG, "PLNT_CD = " + PLNT_CD );
                al.add(PLNT_CD);
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            Log.v(TAG, "Error connection");
            e.printStackTrace();
        } finally {

            doClose(conn, pstmt, rs);
        }

        return al;

    }


    private static boolean init_done = false;
    private static void doInit(){
       // if(!init_done) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
         //   init_done = true;
        //}

       // doFetch(wono);
    }
    public static String SELECT_PROD_ORD = "SELECT [PROD_ORDR_NBR] ,[Material_NOZERO],[Old_Material],[BTCH_NBR] "
            + " FROM [AP101_MPM].[MPM_RPTS_VIEWS].[HV_PROD_ORDR_HEADER] B JOIN [CAR01_CPS_DATA].[dbo].[MATERIAL_MASTER_CPS_HEADER] C ON C.[Material] = B.[MTRL_NBR] "
            + " where PROD_ORDR_NBR =?";

    public static String doFetch(String wono) {
        String det = null;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            doInit();
            //
              conn = doMSSQLConnectionWithMSSQLAUth();
            Log.v(TAG, "Connection OK");
              stmt = conn.prepareStatement(SELECT_PROD_ORD);
            stmt.setString(1, wono);


              rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                String iprodOrdNo = rs.getString("PROD_ORDR_NBR");
                String materialNo = rs.getString("Material_NOZERO");
                String batchNo = rs.getString("BTCH_NBR");
                String oldMaterialNo = rs.getString("Old_Material");
                Log.v(TAG, "iprodOrdNo = " + iprodOrdNo + ", mat-on=" + materialNo);

                det = "Order No :" + iprodOrdNo + "\n MaterialNo: " + materialNo + "\n batchNo:" + batchNo + "\n OldMaterialNo:" + oldMaterialNo;
               // txtResult.setText(result);

                count++;
                return  det;
            }

            if (count == 0) {
               // txtResult.setText("No Data found for this wo no = " + wono);
                Log.v(TAG, "No info found for this wono="+wono);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            Log.v(TAG, "Error connection");
            e.printStackTrace();
        } finally {

            doClose(conn, stmt, rs);
        }

        return det;
    }


    private static void doClose(Connection conn, PreparedStatement pstmt, ResultSet rs ){
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        }catch (Exception ee){
            ee.printStackTrace();
        }

        try{
            if(pstmt!=null){
                pstmt.close();
                pstmt = null;
            }
        }catch (Exception ee){
            ee.printStackTrace();
        }

        try{
            if(rs!=null){
                rs.close();
                rs = null;
            }

        }catch (Exception ee){
            ee.printStackTrace();
        }
    }
}
