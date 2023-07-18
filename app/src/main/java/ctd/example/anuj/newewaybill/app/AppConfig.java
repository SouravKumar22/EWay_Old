package ctd.example.anuj.newewaybill.app;

/**
 * Created by Nimisha on 27/11/2017.
 */

public class AppConfig {


    //  public static String URL_BILL_DETAIL_TYPE2= "http://172.26.11.47/Eway_bill/EwayBill.asmx/Eway_bill_details_Other_transactions";
    //  public static String URL_BILL_DETAIL_TYPE1= "http://172.26.11.47/Eway_bill/EwayBill.asmx/Eway_bill_details_EXUP_to_EXUP_transactions";


    //Live URL
   /* public static String URL_LOGIN = "https://comtaxwebsrv.azurewebsites.net/ocs.asmx/MobileLogin";
    public static String URL_BILL_DETAIL_TYPE1 = "http://52.172.212.251/SahayogSrv/EwayBill.asmx/Eway_bill_details_EXUP_to_EXUP_transactions";
    public static String URL_VIEW_BILL = "http://52.172.212.251/SahayogSrv/EwayBill.asmx/view_bill";
    public static String URL_BILL_DETAIL_TYPE2 = "http://52.172.212.251/SahayogSrv/EwayBill.asmx/Eway_bill_details_Other_transactions";*/

    //Testing URL
    public static String URL_LOGIN = "http://comtaxwebsrv.ap-south-1.elasticbeanstalk.com/ocs.asmx/MobileLogin";
    public static String URL_BILL_DETAIL_TYPE1 = "http://65.0.48.65/SahayogSrv/ewaybill.asmx/Eway_bill_details_EXUP_to_EXUP_transactions";
    public static String URL_VIEW_BILL = "http://65.0.48.65/SahayogSrv/ewaybill.asmx/view_bill";
    public static String URL_BILL_DETAIL_TYPE2 = "http://65.0.48.65/SahayogSrv/ewaybill.asmx/Eway_bill_details_Other_transactions";
    public static String URL_VIEW_VEHICLE_DETAILS = "http://comtaxwebsrv.ap-south-1.elasticbeanstalk.com/ocs.asmx/GetVehicleDetails";
    public static String URL_VIEW_RED_FLAG_VEHICLE_DETAILS = "http://comtaxwebsrv.ap-south-1.elasticbeanstalk.com/ocs.asmx/GetRedFlagVehicleDetails";


}
