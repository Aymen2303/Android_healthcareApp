package com.example.mylogin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Classes.Appointment;
import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.R;
import com.example.mylogin.nurse_HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class Valid_Appointment_Adapter extends RecyclerView.Adapter<Valid_Appointment_Adapter.Valid_AppointmentViewHolder> {
    private List<kid_class> kidInformationList;
    private List<Appointment> appointmentList;
    private Context context;

    private OnApproveButtonClickListener buttonClickListener;
    private int currentPosition;

    public Valid_Appointment_Adapter(List<Appointment> appointmentList, List<kid_class> kidInformationList, Context context, OnApproveButtonClickListener buttonClickListener) {
        this.appointmentList = appointmentList;
        this.kidInformationList = kidInformationList;
        this.context = context;
        this.buttonClickListener = buttonClickListener;
    }

    public interface OnApproveButtonClickListener {
        void onApproveButtonClick(String kidId);
    }








    /**********  END OF BUTTON LISTENER*********/

    public Valid_Appointment_Adapter(List<Appointment> appointmentList, Context context) {
        this.appointmentList = appointmentList;
        this.context = context;
        this.kidInformationList = new ArrayList<>(); // Initialize the kidInformationList
    }




    @NonNull
    @Override
    public Valid_AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_valid_app_only, parent, false);
        return new Valid_AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Valid_AppointmentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Appointment appointment = appointmentList.get(position);


        String name = "Name: " +appointment.getClientUsername();
        String kidID= "Kid id: " +appointment.getKidID();
        String kidName = "Kid name: " +appointment.getKidName();
        String App_Date = "Appointment Date: " +appointment.getAppointmentDate();
        String app_id = "Appointment ID: " +appointment.get_app_ID();

        holder.txt_app_id.setText(app_id);
         holder.txtUsername.setText(name);
        holder.txtKidID.setText(kidID);
         holder.txtKidName.setText(kidName);
         holder.txtDateAppointment.setText(App_Date);


        holder.btn_vaccinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kidInformationList != null && position < kidInformationList.size()) {
                    kid_class kid = kidInformationList.get(position);

                    if (buttonClickListener != null) {
                        buttonClickListener.onApproveButtonClick(kid.getKidId());
                    }

                    // Remove the appointment from the list
                    appointmentList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, appointmentList.size());

                    // Navigate to nurse_HomeFragment
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, nurse_HomeFragment.newInstance(kid.getKidId(), kid.getKidName()));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class Valid_AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername;
        TextView txtKidID;
        TextView txtKidName;
        TextView txtDateAppointment;
      TextView txt_app_id;

      Button btn_vaccinate;


        public Valid_AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtusername_valid);
            txtKidID = itemView.findViewById(R.id.txtkidID_valid);
            txtKidName = itemView.findViewById(R.id.txtkidname_valid);
            txtDateAppointment = itemView.findViewById(R.id.txtdateappointment_valid);
            txt_app_id = itemView.findViewById(R.id.txtid_valid_appointment);
            btn_vaccinate = itemView.findViewById(R.id.btn_vaccinate);
        }
    }
}
