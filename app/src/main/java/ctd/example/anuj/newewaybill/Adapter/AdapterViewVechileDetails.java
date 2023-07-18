package ctd.example.anuj.newewaybill.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ctd.example.anuj.newewaybill.R;
import ctd.example.anuj.newewaybill.SetGet.SetGetVehicleDetails;


public class AdapterViewVechileDetails extends RecyclerView.Adapter<AdapterViewVechileDetails.MyViewHolder> {

    Context context;
    ArrayList<SetGetVehicleDetails> setGetVehicleDetailsArrayList;
    String str_Otp = "";

    public void filterList( ArrayList<SetGetVehicleDetails> setGetVehicleDetailsArrayList) {
        Log.d(">>>>TempSize", setGetVehicleDetailsArrayList.size() + "");
        this.setGetVehicleDetailsArrayList = setGetVehicleDetailsArrayList;
        notifyDataSetChanged();
    }

    public AdapterViewVechileDetails()
    {}


    public AdapterViewVechileDetails(Context context, ArrayList<SetGetVehicleDetails> setGetVehicleDetailsArrayList) {
        this.context = context;
        this.setGetVehicleDetailsArrayList = setGetVehicleDetailsArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_vehicle_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        int temppos=position;
        try {
            holder._tv_sno.setText(String.valueOf(temppos+1));
            holder._tv_vehicleno.setText(setGetVehicleDetailsArrayList.get(position).getVehicleNo());
            holder._tv_engineno.setText(setGetVehicleDetailsArrayList.get(position).getEngineNo());
            holder._tv_chasisno.setText(setGetVehicleDetailsArrayList.get(position).getChasisNo());
            holder._tv_adddate.setText(setGetVehicleDetailsArrayList.get(position).getAddDate());

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return this.setGetVehicleDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView _tv_vehicleno, _tv_engineno, _tv_chasisno,_tv_adddate,_tv_sno;

        public MyViewHolder(View view) {
            super(view);
            _tv_vehicleno = view.findViewById(R.id._tv_vehicleno);
            _tv_engineno = view.findViewById(R.id._tv_engineno);
            _tv_chasisno = view.findViewById(R.id._tv_chasisno);
            _tv_adddate = view.findViewById(R.id._tv_adddate);
            _tv_sno = view.findViewById(R.id._tv_sno);

        }
    }
}
