package aa_stem.finallogscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MedicineNotTakenConfirmation extends AppCompatActivity {

    TextView viewpromptMedName;
    String medicine_name = "";
    String dose_amt = "";
    String start_Date = "";
    String start_Time = "";
    String cell_phone = "";
    String home_phone = "";
    UserSessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_not_taken_confirmation);

        session = new UserSessionManagement(getApplicationContext());
        viewpromptMedName = (TextView) findViewById(R.id.medicineName);

        HashMap<String,String> medicalDetails = session.getMedicalDetails();
        medicine_name = medicalDetails.get(UserSessionManagement.KEY_MEDICINE_NAME);
        dose_amt = medicalDetails.get(UserSessionManagement.KEY_DOSAGE_AMOUNT);
        start_Date = medicalDetails.get((UserSessionManagement.KEY_START_DATE));
        start_Time = medicalDetails.get(UserSessionManagement.KEY_START_TIME);
        home_phone = medicalDetails.get(UserSessionManagement.KEY_HOME_PHONE);
        cell_phone = medicalDetails.get(UserSessionManagement.KEY_CELL_PHONE);

        viewpromptMedName.setText("Medicine Name: "+ medicine_name
                + "\r\nfor the dose amount of: " + dose_amt
                + " \r\nwas NOT taken on :"+ start_Date
                + " \r\nat time: "+ start_Time+"."
                + "\r\nInformation is now logged and SMS has been sent to " + cell_phone);


        Button btnHome = (Button) findViewById(R.id.btnHome);

        //button click event
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicineNotTakenConfirmation.this);

                alertDialog.setTitle("Home Alert");
                alertDialog.setMessage("Are you sure you want to continue..");


                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i = new Intent(getApplicationContext(),OptionsScreen.class);
                        startActivity(i);
                    }
                });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"You Clicked NO, Please review and Continue.",Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        });

                alertDialog.show();
            }
        });

    }
}
