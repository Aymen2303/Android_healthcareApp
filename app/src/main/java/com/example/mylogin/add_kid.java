package com.example.mylogin;

import static java.lang.Float.parseFloat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylogin.Api_request.ClientFetcher;
import com.example.mylogin.Api_request.Kid_PostApi;
import com.example.mylogin.Classes.Client_Class;
import com.example.mylogin.Classes.kid_class;
import com.example.mylogin.adapter.Client_Spinner_Adapter;

import java.util.Calendar;
import java.util.List;

public class add_kid extends AppCompatActivity {
    private EditText kidNameEditText;
    private EditText kidAgeEditText;
    private EditText kidHeightEditText;
    private EditText kidWeightEditText;

    private Spinner clientSpinner;
    private Button addKidButton;

    private Calendar myCalendar;

    private List<Client_Class> clientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kid);

        // Find views by their IDs
        kidNameEditText = findViewById(R.id.kidName);
        kidAgeEditText = findViewById(R.id.kidAge);
        kidHeightEditText = findViewById(R.id.kidHeight);
        kidWeightEditText = findViewById(R.id.kidWeight);
        clientSpinner = findViewById(R.id.Spinner_Clients);
        addKidButton = findViewById(R.id.Btn_add_Kid);

        /*********  Spinner of Vaccine list

         Vaccine vaccineApi = new Vaccine(this);
         vaccineApi.getVaccines(new Vaccine.VaccineCallback() {
        @Override
        public void onVaccineReceived(List<Vaccine_Class> vaccines) {
        // Create the adapter and set it to the spinner
        Vaccine_Spinner_Adapter adapter = new Vaccine_Spinner_Adapter(add_kid.this, vaccines);
        vaccineSpinner.setAdapter(adapter);
        }

        @Override
        public void onVaccineError(String errorMessage) {
        // Handle error case
        }
        }); ***********/

        /*********  Spinner of Clients list    ***********/

        ClientFetcher clientFetcher = new ClientFetcher(this);
        clientFetcher.fetchClients(new ClientFetcher.ClientCallback() {
            @Override
            public void onClientsReceived(List<Client_Class> clients) {
                // Create an instance of the Client_Spinner_Adapter and pass the client list to it
                Client_Spinner_Adapter adapter2 = new Client_Spinner_Adapter(add_kid.this, clients);

                // Set the adapter on the clientSpinner
                clientSpinner.setAdapter(adapter2);
            }

            @Override
            public void onClientsError(String errorMessage) {
                // Handle the error case
            }
        });

        // Set click listener for the button
        addKidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client_Class selectedClient = (Client_Class) clientSpinner.getSelectedItem();

                // Retrieve the values entered by the user
                String kidName = kidNameEditText.getText().toString();
                int kidAge = Integer.parseInt(kidAgeEditText.getText().toString());
                float kidHeight = parseFloat(kidHeightEditText.getText().toString());
                float kidWeight = parseFloat(kidWeightEditText.getText().toString());

                String selectedClientId = String.valueOf(selectedClient.getId());

                // Create a kid_class object with the retrieved values
                kid_class newKid = new kid_class("", kidName, kidAge, kidHeight, kidWeight, null, null, selectedClientId);

                // Perform further operations with the newKid object as needed
                sendToTable(newKid);

                kidNameEditText.setText("");
                kidAgeEditText.setText("");
                kidHeightEditText.setText("");
                kidWeightEditText.setText("");

                clientSpinner.setSelection(0);
            }
        });


    }

    // Method to send the kid_class object to the table
    private void sendToTable(kid_class newKid) {

        Kid_PostApi kidPostApi = new Kid_PostApi();


        String kidName = newKid.getKidName();
        int age = newKid.getAge();
        float height = newKid.getHeight();
        float weight = newKid.getWeight();
        String clientId = newKid.getClientId();

        // Call the addKid method of Kid_PostApi to send the data to the table
        kidPostApi.addKid(kidName, age, height, weight, clientId);
    }

}
