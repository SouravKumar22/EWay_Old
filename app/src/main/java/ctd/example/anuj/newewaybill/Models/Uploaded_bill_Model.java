package ctd.example.anuj.newewaybill.Models;

public class Uploaded_bill_Model {

    String checking_date,vehicle_num,EWB_num,EWB_date_and_time,validity,S_gstin,r_gstin,hsn,item_name,quantity,amount;



    String latitude;
    String longitude;



    String place;


    public Uploaded_bill_Model(String checking_date, String vehicle_num, String EWB_num, String EWB_date_and_time, String validity, String s_gstin, String r_gstin, String hsn, String item_name, String quantity, String amount,String latitude,String longitude,String place) {
        this.checking_date = checking_date;
        this.vehicle_num = vehicle_num;
        this.EWB_num = EWB_num;
        this.EWB_date_and_time = EWB_date_and_time;
        this.validity = validity;
        S_gstin = s_gstin;
        this.r_gstin = r_gstin;
        this.hsn = hsn;
        this.item_name = item_name;
        this.quantity = quantity;
        this.amount = amount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
    }


    public String getChecking_date() {
        return checking_date;
    }

    public String getVehicle_num() {
        return vehicle_num;
    }

    public String getEWB_num() {
        return EWB_num;
    }

    public String getEWB_date_and_time() {
        return EWB_date_and_time;
    }

    public String getValidity() {
        return validity;
    }

    public String getS_gstin() {
        return S_gstin;
    }

    public String getR_gstin() {
        return r_gstin;
    }

    public String getHsn() {
        return hsn;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPlace() {
        return place;
    }

}
