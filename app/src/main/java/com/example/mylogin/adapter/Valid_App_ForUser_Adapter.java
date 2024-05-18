package com.example.mylogin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylogin.Classes.Appointment_Class;
import com.example.mylogin.R;

import java.util.List;

public class Valid_App_ForUser_Adapter extends RecyclerView.Adapter<Valid_App_ForUser_Adapter.ViewHolder> {

    private Context context;
    private List<Appointment_Class> appointmentList;

    public Valid_App_ForUser_Adapter(Context context) {
        this.context = context;
    }

    public void setAppointments(List<Appointment_Class> appointments) {
        this.appointmentList = appointments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.valid_app_item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment_Class appointment = appointmentList.get(position);

        String kidname = "Kid Name : " + appointment.getKidName();
        String nurseName = "Nurse Name : " + appointment.getNurseName();
        String date = "Appointment Date : " + appointment.getAppointmentDate();

        holder.txtKidName.setText(kidname);
        holder.txtNurseName.setText(nurseName);
        holder.txtDate.setText(date);

        holder.btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle reminder button click
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList != null ? appointmentList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtKidName;
        TextView txtNurseName;
        TextView txtDate;
        Button btnReminder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtKidName = itemView.findViewById(R.id.kid_name_forUser);
            txtNurseName = itemView.findViewById(R.id.Nurse_name_forUser);
            txtDate = itemView.findViewById(R.id.Date_forUser);
            btnReminder = itemView.findViewById(R.id.btn_reminder);
        }
    }
}
