package com.example.mylogin.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Api_request.Delete_Api;
import com.example.mylogin.Api_request.Update_Api;
import com.example.mylogin.Classes.Appointment;
import com.example.mylogin.Classes.SharedPrefManager;
import com.example.mylogin.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.RequestAppViewHolder> {
    private List<Appointment> appointmentRequests;


private Context context;
    public AppointmentAdapter(List<Appointment> appointmentRequests) {
        this.appointmentRequests = appointmentRequests;
    }

    @NonNull
    @Override
    public RequestAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_request, parent, false);
        return new RequestAppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAppViewHolder holder, @SuppressLint("RecyclerView") int position) {
               Appointment request = appointmentRequests.get(position);

        String name = "Name: " + request.getClientUsername();
        String kidid = "Kid id: " +request.getKidID();
        String kidname = "Kid name: " +request.getKidName();
        String App_Date = "Appointment Date: " +request.getAppointmentDate();
        String app_id = "Appointment ID: " +request.get_app_ID();

        holder.txtapp_id.setText(app_id);
        holder.txtUsername.setText(name);
        holder.txtKidID.setText(kidid);
        holder.txtKidName.setText(kidname);
        holder.txtDateAppointment.setText(App_Date);

        /******  ClickListener Of Approve AND Decline Buttons ********/

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appointmentID = appointmentRequests.get(position).get_app_ID();
                String nurseID = String.valueOf(SharedPrefManager.getInstance(context).getID());

                Update_Api updateApi = new Update_Api(v.getContext().getApplicationContext());
                updateApi.updateAppointmentStatus(appointmentID,nurseID, new Update_Api.AppointmentUpdateCallback() {
                    @Override
                    public void onAppointmentStatusUpdated() {
                        // Handle the successful update
                        // Remove the item from the RecyclerView
                        appointmentRequests.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, appointmentRequests.size());
                    }

                    @Override
                    public void onAppointmentStatusUpdateError(String errorMessage) {
                        // Handle the update error
                    }
                });
            }
        });


        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appointmentID = appointmentRequests.get(position).get_app_ID();

                // Create an instance of Delete_Api
                Delete_Api deleteApi = new Delete_Api(holder.itemView.getContext());

                // Call the deleteAppointment method
                deleteApi.deleteAppointment(appointmentID, new Delete_Api.AppointmentDeleteCallback() {
                    @Override
                    public void onAppointmentDeleted() {
                        // Remove the item from the RecyclerView
                        appointmentRequests.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, appointmentRequests.size());
                    }

                    @Override
                    public void onAppointmentDeleteError(String errorMessage) {
                        // Handle the failure to delete the appointment
                        if (holder.itemView.getContext() != null) {
                            Toast.makeText(holder.itemView.getContext(), "Failed to delete appointment: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return appointmentRequests.size();
    }

    public static class RequestAppViewHolder extends RecyclerView.ViewHolder {
        TextView txtapp_id;
        TextView txtUsername;
        TextView txtKidID;
        TextView txtKidName;
        TextView txtDateAppointment;
        Button btnApprove;
        Button btnDecline;

        public RequestAppViewHolder(@NonNull View itemView) {
            super(itemView);
            txtapp_id = itemView.findViewById(R.id.txtid_appointment);
            txtUsername = itemView.findViewById(R.id.txtusername);
            txtKidID = itemView.findViewById(R.id.txtkidID);
            txtKidName = itemView.findViewById(R.id.txtkidname);
            txtDateAppointment = itemView.findViewById(R.id.txtdateappointment);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnDecline = itemView.findViewById(R.id.btnDecline);
        }
    }




}

