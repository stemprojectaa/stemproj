package aa_stem.finallogscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MedicalHistory extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;
    TextView showMedicalHistory;
    String str;
    DatabaseManagement databaseManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        showMedicalHistory = (TextView) findViewById(R.id.logHistory);

        databaseManagement = new DatabaseManagement(this);

        final Button buttonHome = findViewById(R.id.btnHome);

        //button click event
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicalHistory.this);

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


        List<UserDetailsAndMedicalDetails> userDetailsAndMedicalDetails = databaseManagement.getAllMedicalHistory();

        for (UserDetailsAndMedicalDetails cn : userDetailsAndMedicalDetails) {
            str =  " MedicineName: " + cn.getMedicinename()
                        +"\n "
                        + " Dosage: " + cn.getDosageamount()
                        +"\n "
                        + " StartDate: " + cn.getStartdate()
                        +"\n "
                        + " StartTime: " + cn.getStarttime()
                        +"\n "
                        + " CellPhone: " + cn.getCellphone()
                        +"\n "
                        + " HomePhone: " + cn.getHomePhone()
                        +"\n "
                        ;

            // Writing Contacts to log
            Log.d("MedicineDetailsAre: ", str);
        }
        showMedicalHistory.setText(str);

        //File interpath = getApplicationContext().getFilesDir();
        //File file = new File(interpath,"trackhistory.txt");
        //File outerpath = getApplicationContext().getExternalFilesDir(null);
        //File file = new File(outerpath,"trackhistory.txt");


        //Log.d("filepath_outer:",file1.getAbsolutePath().toString());
        //Log.d("filepath_inner:",file.getAbsolutePath().toString());


        /*try{
            //FileOutputStream stream = openFileOutput(file.getAbsolutePath().toString(),MODE_PRIVATE);
            FileOutputStream stream = new FileOutputStream(new File("trackhistory.txt"));
            Log.d("filepath_inner:",stream.getFD().toString());
            OutputStreamWriter outputwriter = new OutputStreamWriter(stream);

            outputwriter.write("Username: ");
            outputwriter.write(("Password: "));
        }catch (Exception e){
            e.printStackTrace();
        }*/


        /*try{
            FileInputStream fileInputStream = openFileInput(file.getAbsolutePath().toString());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String str = "Writing Medical History:";
            int charRead;

            while ((charRead=inputStreamReader.read(inputBuffer))>0){

                String strRead = String.copyValueOf(inputBuffer,0,charRead);
                str += strRead;

                Log.v("Stringcontents: ",str);

            }

            inputStreamReader.close();
            showMedicalHistory.setText(str);

        }catch(Exception e){
            e.printStackTrace();
        }*/


    }




    public void Save(View view){

        File interpath = getApplicationContext().getFilesDir();
        File outerpath = getApplicationContext().getExternalFilesDir(null);
        File file1 = new File(outerpath,"tracking.txt");
        File file2 = new File(interpath,"tracking.txt");

        Log.d("filepath_outer:",file1.getAbsolutePath().toString());

        Log.d("filepath_inner:",file2.getAbsolutePath().toString());



        /*try{
            FileInputStream fileInputStream = openFileInput("tracking.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String str = "";
            int charRead;

            while ((charRead=inputStreamReader.read(inputBuffer))>0){

                String strRead = String.copyValueOf(inputBuffer,0,charRead);
                str += strRead;

                Log.v("Stringcontents: ",str);

            }

            inputStreamReader.close();
            showMedicalHistory.setText(str);

        }catch(Exception e){
            e.printStackTrace();
        }*/
    }



}
