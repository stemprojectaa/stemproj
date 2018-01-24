package aa_stem.finallogscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Registration extends AppCompatActivity {

    EditText inputusername;
    EditText inputpassword;
    EditText inputemail;
    EditText inputgender;
    EditText inputdateofbirth;

    String username;
    String password;
    String email;

    UserSessionManagement session;
    static final int READ_BLOCK_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        session = new UserSessionManagement(getApplicationContext());

        inputusername = (EditText) findViewById(R.id.username) ;
        inputpassword = (EditText) findViewById(R.id.password) ;
        inputemail = (EditText) findViewById(R.id.email) ;
        inputgender = (EditText) findViewById(R.id.gender) ;
        inputdateofbirth = (EditText) findViewById(R.id.dateofbirth) ;

        Button btnRegister = (Button) findViewById(R.id.registration);
        Button btnHome = (Button) findViewById(R.id.btnHome);

        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        //button click event
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Registration.this);

                alertDialog.setTitle("Create Alert");
                alertDialog.setMessage("Are you sure you want to continue..");


                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i = new Intent(getApplicationContext(),OptionsScreen.class);
                        startActivity(i);
                    }
                });

                alertDialog.show();
            }
        });

    }

    public void Save(View view) {

        username = inputusername.getText().toString();
        password = inputpassword.getText().toString();
        email = inputemail.getText().toString();

        session.createUserLoginSession(username,password,email);

        writeToFile(view);

        Toast.makeText(getApplicationContext(),"User Login session created.",Toast.LENGTH_LONG).show();
    }

    public void writeToFile(View view){

        File innerpath = getApplicationContext().getFilesDir();
        File outerpath = getApplicationContext().getExternalFilesDir(null);

        Log.d("Innerpath: ",innerpath.getName().toString());
        Log.d("Outer: ",outerpath.getName().toString());
        File file = new File(outerpath,"tracking.txt");


        try{

            FileOutputStream stream = new FileOutputStream(file);
            OutputStreamWriter outputwriter = new OutputStreamWriter(stream);

            outputwriter.write("Username: "+ username);
            outputwriter.write(("Password: "+ password));
            outputwriter.write("Email: "+email);
        }catch (Exception e){
            e.printStackTrace();
        }


        /*try{
            FileOutputStream fileout = openFileOutput("tracking.txt",MODE_PRIVATE);
            OutputStreamWriter outputwriter = new OutputStreamWriter(fileout);
            outputwriter.write("Username: "+ username);
            outputwriter.write(("Password: "+ password));
            outputwriter.write("Email: "+email);
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
