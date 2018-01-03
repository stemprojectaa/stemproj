package aa_stem.finallogscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

public class OptionsScreen extends AppCompatActivity {

    String medicine_name = "";
    String start_Date = "";
    String start_Time = "";
    String cell_phone = "";
    String home_phone = "";
    String dose_amt = "";
    UserSessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        session = new UserSessionManagement(getApplicationContext());
        HashMap<String,String> medicalDetails = session.getMedicalDetails();
        medicine_name = medicalDetails.get(UserSessionManagement.KEY_MEDICINE_NAME);
        Log.v("MedicalName: ", medicine_name);

        final Button button = findViewById(R.id.input);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BasicSetup.class));
            }
        });

        final Button button2 = findViewById(R.id.edit);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(medicine_name=="$$$$$"){
                    Toast.makeText(getApplicationContext(),"Please add Medication details before editing..",Toast.LENGTH_LONG).show();
                }else {
                    startActivity(new Intent(getApplicationContext(), EditMedicineData.class));
                }
            }
        });



    }
}
