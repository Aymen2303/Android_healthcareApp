package com.example.mylogin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylogin.Api_request.ClientFetcher;
import com.example.mylogin.Api_request.KidFetcher_forClient_Entered;
import com.example.mylogin.Api_request.UpdateKid_Api;
import com.example.mylogin.Api_request.VaccineForKid_Api;
import com.example.mylogin.Classes.Client_Class;
import com.example.mylogin.Classes.Vaccine_Class;
import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.adapter.Client_Spinner_Adapter;
import com.example.mylogin.adapter.Kid_Spinner_Adapter;
import com.example.mylogin.adapter.Vaccine_Spinner_Adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class kid_vaccination extends AppCompatActivity {

    private List<Client_Class> clientList;
    private List<kid_class> kidList;
    private List<Vaccine_Class> vaccineList;

    Spinner spinnerClients;
    Spinner spinnerKids;
    Spinner spinnerVaccines;

    EditText editTextKidHeight;

    EditText kidAge_ToVaccinate;
    EditText editTextKidWeight;
    EditText editTextLastVaccinationDate;
    EditText editTextLastVaccination;
    EditText editTextKidAge; // Added EditText for kid's age
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_vaccination);

        /*********** Spinner for Client ***********/
        spinnerClients = findViewById(R.id.Spinner_Clients);
        ClientFetcher clientFetcher = new ClientFetcher(this);

        clientFetcher.fetchClients(new ClientFetcher.ClientCallback() {
            @Override
            public void onClientsReceived(List<Client_Class> clients) {
                // Create an instance of the Client_Spinner_Adapter and pass the retrieved client list
                Client_Spinner_Adapter spinnerAdapter = new Client_Spinner_Adapter(kid_vaccination.this, clients);

                // Set the adapter for the spinner
                spinnerClients.setAdapter(spinnerAdapter);

                // Set the onItemSelectedListener for the spinner to fetch kids for the selected client
                spinnerClients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Get the selected client
                        Client_Class selectedClient = (Client_Class) parent.getItemAtPosition(position);
                        String selectedClientId = String.valueOf(selectedClient.getId());

                        // Fetch kids for the selected client
                        KidFetcher_forClient_Entered.fetchClientKids(kid_vaccination.this, selectedClientId, new KidFetcher_forClient_Entered.OnKidsFetchListener() {
                            @Override
                            public void onKidsFetch(List<kid_class> kids) {
                                // Update the kid list
                                kidList = kids;

                                // Create an instance of the Kid_Spinner_Adapter and pass the retrieved kid list
                                Kid_Spinner_Adapter kidSpinnerAdapter = new Kid_Spinner_Adapter(kid_vaccination.this, kidList);

                                // Set the adapter for the kid spinner
                                spinnerKids.setAdapter(kidSpinnerAdapter);
                            }

                            @Override
                            public void onKidsError(final String errorMessage) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Display the error message on the main UI thread
                                        Toast.makeText(kid_vaccination.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Handle the case where no client is selected
                    }
                });
            }

            @Override
            public void onClientsError(String errorMessage) {
                // Handle the error if the client data retrieval fails
                Toast.makeText(kid_vaccination.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        /*********** Spinner for the kid OF the selected Client ***********/
        spinnerKids = findViewById(R.id.Spinner_kid);
        spinnerVaccines = findViewById(R.id.Spinner_kid_LastVaccine);

        // Initialize the EditText fields
        editTextKidHeight = findViewById(R.id.kidHeight_ToVaccinate);
        editTextKidWeight = findViewById(R.id.kidWeight_ToVaccinate);
        editTextLastVaccinationDate = findViewById(R.id.kidDate_ToVaccinate);
        editTextKidAge = findViewById(R.id.kidAge_ToVaccinate); // Initialize the EditText for kid's age

        // Set the onItemSelectedListener for the kid spinner to handle the selection event
        spinnerKids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected kid
                kid_class selectedKid = (kid_class) parent.getItemAtPosition(position);

                // Update the EditText fields with the selected kid's information
                editTextKidHeight.setText(String.valueOf(selectedKid.getHeight()));
                editTextKidWeight.setText(String.valueOf(selectedKid.getWeight()));
                editTextLastVaccinationDate.setText(selectedKid.getLastVaccinationDate());
                editTextKidAge.setText(String.valueOf(selectedKid.getAge())); // Set the kid's age in the EditText

                // Fetch vaccines for the selected kid
                String selectedKidId = String.valueOf(selectedKid.getKidId());
                VaccineForKid_Api.getVaccinesForKid(kid_vaccination.this, selectedKidId, new VaccineForKid_Api.VaccineCallback() {
                    @Override
                    public void onSuccess(List<Vaccine_Class> vaccines) {
                        // Update the vaccine list
                        vaccineList = vaccines;

                        // Create an instance of the Vaccine_Spinner_Adapter and pass the retrieved vaccine list
                        Vaccine_Spinner_Adapter vaccineSpinnerAdapter = new Vaccine_Spinner_Adapter(kid_vaccination.this, vaccineList);

                        // Set the adapter for the vaccine spinner
                        spinnerVaccines.setAdapter(vaccineSpinnerAdapter);
                    }

                    @Override
                    public void onError(final String errorMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(kid_vaccination.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no kid is selected
            }
        });

        /***********     DatePickerDialog ************/
        editTextLastVaccination = findViewById(R.id.kidDate_ToVaccinate);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        editTextLastVaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        // Inside your onCreate() method

// Find the save button and add a click listener
        Button saveButton = findViewById(R.id.Btn_add_Kid);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the necessary information from the views
                String kidId = String.valueOf(spinnerKids.getSelectedItemId()); // Get the selected kid ID
                String age = kidAge_ToVaccinate.getText().toString();
                String height = editTextKidHeight.getText().toString();
                String weight = editTextKidWeight.getText().toString();
                String lastVaccination = editTextLastVaccinationDate.getText().toString();
                String lastVaccine = spinnerVaccines.getSelectedItem().toString(); // Get the selected vaccine

                // Call the UpdateKid_Api.updateKid() method to trigger the update process
                UpdateKid_Api.updateKid(kid_vaccination.this, kidId, age, height, weight, lastVaccination, lastVaccine, new UpdateKid_Api.KidUpdateCallback() {
                    @Override
                    public void onKidUpdateSuccess(String response) {
                        // Handle the successful update
                        Toast.makeText(kid_vaccination.this, "Kid's information updated successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onKidUpdateError(String errorMessage) {
                        // Handle the error during update
                        Toast.makeText(kid_vaccination.this, "Error updating kid's information: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    /********* Outside OnCreate  **********/

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, monthOfYear, dayOfMonth);

                String selectedDate = dateFormatter.format(selectedCalendar.getTime());
                editTextLastVaccination.setText(selectedDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
