package aa_stem.finallogscreen;

import android.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CaptureImage extends AppCompatActivity {

    UserSessionManagement session;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    String medicine_name = "";
    String dose_amt = "";
    String start_Date = "";
    String start_Time = "";
    Date dateTime;
    String combineTime;
    String home_phone;
    String cell_phone;
    String phoneNo;
    String homePhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        imageView = (ImageView) this.findViewById(R.id.imageView);
        Button btnImage = (Button) this.findViewById(R.id.btnTakeImage);
        session = new UserSessionManagement(getApplicationContext());

        HashMap<String,String> medicalDetails = session.getMedicalDetails();
        medicine_name = medicalDetails.get(UserSessionManagement.KEY_MEDICINE_NAME);
        dose_amt = medicalDetails.get(UserSessionManagement.KEY_DOSAGE_AMOUNT);
        start_Date = medicalDetails.get((UserSessionManagement.KEY_START_DATE));
        start_Time = medicalDetails.get(UserSessionManagement.KEY_START_TIME);
        home_phone = medicalDetails.get(UserSessionManagement.KEY_HOME_PHONE);
        cell_phone = medicalDetails.get(UserSessionManagement.KEY_CELL_PHONE);

        Button btnHome = (Button) findViewById(R.id.btnHome);

        //button click event
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CaptureImage.this);

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


        Button btnSchedule = (Button) findViewById(R.id.btnSchedule);
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.SEND_SMS},1);

        btnSchedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Log.d("phone:",cell_phone);
                String sms = "Your schedule has now been set. You will be reminded when you are ready to start taking medication.";


                //Validating AlarmService

                String[] fields = start_Time.split(":");
                String hour = fields[0];
                String min = fields[1];
                Log.v("Hour:",hour);
                Log.v("Minute:",min);
                combineTime = start_Date + " " + start_Time;
                Log.v("combineTime",combineTime);
                try{
                    dateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm").parse(combineTime.toString());
                    Log.v("DateTime:",dateTime.toString());
                }catch (ParseException e){
                    Log.v("Exception Parsing Time",e.getMessage());
                }

                Long time = dateTime.getTime();
                Log.v("TimeAlert: ",time.toString());


                /*alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Log.v("TimeAlert1: ",time.toString());
                Intent a_intent = new Intent(context,AlarmReceiver.class);
                Log.v("TimeAlert2: ",time.toString());
                alarmIntent = PendingIntent.getBroadcast(context,0,a_intent,0);
                Log.v("TimeAlert3: ",time.toString());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);
                calendar.set(Calendar.HOUR_OF_DAY,new Integer(hour).intValue());
                calendar.set(Calendar.MINUTE,new Integer(min).intValue());
                Log.v("TimeAlert4: ",time.toString());
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,time,1000*60*1,alarmIntent);
                */


                //End of AlarmService

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(cell_phone, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();

                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), notification);
                    mediaPlayer.start();

                    Log.d("Timestamp::::",String.valueOf(mediaPlayer.getTimestamp().getAnchorMediaTimeUs()));
                    Log.d("Timestamp::::",String.valueOf(mediaPlayer.getTimestamp().getAnchorSytemNanoTime()));
                    Log.d("Timestamp::::",String.valueOf(mediaPlayer.getTimestamp().getMediaClockRate()));


                    //Log.dmediaPlayer.getTimestamp().getAnchorMediaTimeUs());


                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(5000);
                                if(mediaPlayer.isPlaying()){
                                    mediaPlayer.stop();
                                }
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    });
                    th.start();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CaptureImage.this);
                alertDialog.setTitle("SMS Alert");
                alertDialog.setMessage("Alarm Set. Please continue..");

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i = new Intent(getApplicationContext(),SentSMSActivity.class);
                        startActivity(i);
                    }
                });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"You Clicked NO, Please validate Input and Continue.",Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        });
                alertDialog.show();


            }
        });


    }

    public void takeImage(View view){
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageIntent,CAMERA_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap imagePhoto = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(imagePhoto);
            //session.saveMedValues(null,null,null,null,null,imagePhoto);
        }
    }
}
