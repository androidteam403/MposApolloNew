package com.apollopharmacy.mpospharmacistTest.utils;

import android.graphics.Bitmap;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.FiltersModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;

import java.util.ArrayList;

public   class   Constant {

    private static final Constant ourInstance = new Constant();

    public static Constant getInstance() {
        return ourInstance;
    }


    /* public String demo_app_key = "472025ba-496e-46ab-99d5-7bd62fcf706e";
    public String prod_app_key="472025ba-496e-46ab-99d5-7bd62fcf706e";
    public  String merchant_name="9910943163";
    public  String user_name="9910943163";
    public  String currency_code="INR";
    public  String app_mode="PROD";
    public  String capture_signature="true";
    public  String prepare_device="false";*/


    //Demo

    public String demo_app_key = "bdb9b6b1-80ac-42a1-bc8a-3caf259c6023";
    public String prod_app_key="bdb9b6b1-80ac-42a1-bc8a-3caf259c6023";
    public  String merchant_name="9866666344";
    public  String user_name="9866666344";
    public  String currency_code="INR";
    public  String app_mode="DEMO";
    public  String capture_signature="true";
    public  String prepare_device="false";
    public  boolean isomsorder=false;
    public  boolean onlinecorporate=false;

    public  String ordersource="";

    public  boolean isomsorder_check=false;


  public   String card_transaction_id="0";
    public   String PhonepeQrcode_transactionid="0";
  public static final String GetOMSTransactionHeader = "http://lms.apollopharmacy.org:51/EPOS/";
     // public static final String UPDATEOMSORDER = "http://172.16.2.251:8033/";

     public static final String UPDATEOMSORDER = "http://lms.apollopharmacy.org:8033/";


    //Eprescription varibles......
    public  ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList=new ArrayList<GetBatchInfoRes.BatchListObj>();
    public  int selected_position=0;
    public  int manualSelectedPosition =0;
    public  boolean isSelectedBatch=false;
    public int enteredQuantity=0;
    public   SalesLineEntity selectedItem=new SalesLineEntity();
    public ArrayList<GetBatchInfoRes.BatchListObj> selectedBatches = new ArrayList<>();
    public  int batchServiceCall = 0;
    public String batchNoTemp;
    public String result;
    public boolean navigate = false;

    public  double requestqty=0;

    public  boolean frompickpakconform=false;

    public  String Orders_type="";

    public String global_barcode_str="";

    public  int global_static_qohcount=0;

    public ArrayList<SalesLineEntity> batchInfoProducts = new ArrayList<>();
    public  ArrayList<String> Customertypearraylist=new ArrayList<>();
    public  ArrayList<String> Categorytypearraylist=new ArrayList<>();
    public  ArrayList<String> Ordersorcetypearraylist=new ArrayList<>();
    public  ArrayList<String> Ordertypearraylist=new ArrayList<>();
    public  ArrayList<String> paymenttypearraylist=new ArrayList<>();
    public  ArrayList<String> Batchinvetoryids=new ArrayList<>();
    public FiltersModel filtersModel=new FiltersModel();
   public boolean isdoneBatchSelect=false;
   public double remainamount=0;
   public  boolean vendorcredit=false;
   public  boolean pickupstatus=false;

   public  String BarcodeStr="";

    public ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> global_ordersArrayList = new ArrayList<>();


}
