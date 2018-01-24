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

public class MedicalHistory extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;
    TextView showMedicalHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        showMedicalHistory = (TextView) findViewById(R.id.logHistory);

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

    }




    public void readHistory(View view){

        File interpath = getApplicationContext().getFilesDir();
        File outerpath = getApplicationContext().getExternalFilesDir(null);
        File file = new File(outerpath,"tracking.txt");




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
