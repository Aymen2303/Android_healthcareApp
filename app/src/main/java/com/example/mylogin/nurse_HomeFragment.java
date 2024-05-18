package com.example.mylogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mylogin.Api_request.KidsFetcher_ID;
import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.adapter.Valid_Appointment_Adapter;

import java.util.List;

public class nurse_HomeFragment extends Fragment implements Valid_Appointment_Adapter.OnApproveButtonClickListener {

    private static final String ARG_KID_ID = "kidId";
    private static final String ARG_KID_NAME = "kidName";

    private String kidId;
    private String kidName;

    private TextView nameTextView;
    private TextView ageTextView;
    private TextView heightEditText, weightEditText, lastVaccinationDateEditText, lastVaccineEditText;

    public nurse_HomeFragment() {
        // Required empty public constructor
    }

    public static nurse_HomeFragment newInstance(String kidId, String kidName) {
        nurse_HomeFragment fragment = new nurse_HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KID_ID, kidId);
        args.putString(ARG_KID_NAME, kidName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            kidId = getArguments().getString(ARG_KID_ID);
            kidName = getArguments().getString(ARG_KID_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_container, container, false);

        nameTextView = rootView.findViewById(R.id.kidName_text_vaccine);
        ageTextView = rootView.findViewById(R.id.kidAge_txt_vaccine);
        heightEditText = rootView.findViewById(R.id.kidHeight_txt_vaccine);
        weightEditText = rootView.findViewById(R.id.kidWeight_txt_vaccine);
        lastVaccinationDateEditText = rootView.findViewById(R.id.kidDate_txt_vaccine);
        lastVaccineEditText = rootView.findViewById(R.id.kidVaccine_txt_Name);

        // Call the method to fetch kid's information using the kidId
        fetchKidInformation(kidId);

        return rootView;
    }

    private void fetchKidInformation(String kidId) {
        KidsFetcher_ID.fetchKid_ID(kidId, new KidsFetcher_ID.OnKidsFetchListener() {
            @Override
            public void onKidsFetch(List<kid_class> kids) {
                if (!kids.isEmpty()) {
                    kid_class kid = kids.get(0);

                    // Update the UI with the kid's information in the fragment_container
                    nameTextView.setText(kid.getKidName());
                    ageTextView.setText(String.valueOf(kid.getAge()));
                    heightEditText.setText(String.valueOf(kid.getHeight()));
                    weightEditText.setText(String.valueOf(kid.getWeight()));
                    lastVaccinationDateEditText.setText(kid.getLastVaccinationDate());
                    lastVaccineEditText.setText(kid.getLastVaccination());
                } else {
                    // Handle the case when there are no approved appointments
                    nameTextView.setText("No approved appointments");
                    ageTextView.setText("");
                    heightEditText.setText("");
                    weightEditText.setText("");
                    lastVaccinationDateEditText.setText("");
                    lastVaccineEditText.setText("");
                }
            }
        });
    }


    @Override
    public void onApproveButtonClick(String kidId) {
        KidsFetcher_ID.fetchKid_ID(kidId, new KidsFetcher_ID.OnKidsFetchListener() {
            @Override
            public void onKidsFetch(List<kid_class> kids) {
                if (!kids.isEmpty()) {
                    kid_class kid = kids.get(0);

                    String id = kid.getKidId();
                    String name = kid.getKidName();
                    int age = kid.getAge();
                    double height = kid.getHeight();
                    double weight = kid.getWeight();
                    String lastVaccination = kid.getLastVaccinationDate();
                    String lastVaccine = kid.getLastVaccination();
                    String clientId = kid.getClientId();

                    // Update the UI with the extracted information
                    nameTextView.setText(name);
                    ageTextView.setText(String.valueOf(age));
                    heightEditText.setText(String.valueOf(height));
                    weightEditText.setText(String.valueOf(weight));
                    lastVaccinationDateEditText.setText(lastVaccination);
                    lastVaccineEditText.setText(lastVaccine);


                }
            }
        });
    }



}
