package com.sanju.pdf_convertor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PrantaeActivity extends AppCompatActivity {

    TextView name,phone,edit2,edit1,spinner1,spinner2,spinner3,reading,edit5,blood;
//    Spinner spinner2,spinner3;
    Button createBtn,shareBtn;

    Bitmap bitmap, scaleBitmap,bitmap2,scaleBitmap2;
    int pageWidth = 1200;
    Date dateObj;
    DateFormat dateFormat;
    float[] prices = new float[]{0,158,56,450,325,580};
    String[] gender = new String[]{"Male","Female","O+"};
    private String  stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "FirstPDF.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prantae);

        edit2 = findViewById(R.id.edit2);
        edit1 = findViewById(R.id.edit1);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        createBtn = findViewById(R.id.btn);
        shareBtn = findViewById(R.id.shareBtn);
        reading = findViewById(R.id.reading);
        edit5 = findViewById(R.id.edit5);
        blood = findViewById(R.id.blood);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo_orange);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap,100,100,false);

        bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.prantae);
        scaleBitmap2 = Bitmap.createScaledBitmap(bitmap2,100,100,false);

        ActivityCompat.requestPermissions(this, new String[]{
                WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this, new String[]{
                READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        createPDF();

    }

    private void createPDF() {
        createBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                dateObj = new Date();

//                if(name.getText().toString().length() == 0 ||
//                        phone.getText().toString().length() == 0 ||
//                        edit1.getText().toString().length() == 0 ||
//                        edit2.getText().toString().length() == 0 ){
//                    Toast.makeText(PrantaeActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
//                }

                PdfDocument myPDFDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint titlePaint = new Paint();

                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200,1700,1).create();
                PdfDocument.Page myPage = myPDFDocument.startPage(myPageInfo);
                Canvas canvas = myPage.getCanvas();

                canvas.drawBitmap(scaleBitmap,550,110,myPaint);
                canvas.drawBitmap(scaleBitmap2,550,1350,myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(70f);
                canvas.drawText("PROFLO-U", pageWidth/2, 270, titlePaint);

                myPaint.setColor(Color.rgb(0,113,188));
                myPaint.setTextSize(30f);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("Call: 022-414212313",1160,40, myPaint);
                canvas.drawText("022-121313414", 1160,80, myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                titlePaint.setTextSize(70f);
                canvas.drawText("Your Report", pageWidth/2, 500, titlePaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                myPaint.setColor(Color.BLACK);
                canvas.drawRect(20,540,pageWidth-20,860,myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setColor(Color.BLACK);
                canvas.drawText("",40,830, myPaint);
                canvas.drawText("",200,830, myPaint);
                canvas.drawText("",700,830, myPaint);

                canvas.drawLine(180,790,180,840, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(35f);
                myPaint.setColor(Color.BLACK);
                canvas.drawText("User Name: "+name.getText(),40,590, myPaint);
                canvas.drawText("Contact No: "+phone.getText(),40,640, myPaint);

                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("Report No.:" +"223223", pageWidth-40, 590, myPaint);

                dateFormat = new SimpleDateFormat("dd/mm/yy");
                canvas.drawText("Date: "+dateFormat.format(dateObj), pageWidth-40,640, myPaint);

                dateFormat = new SimpleDateFormat("HH:mm:ss");
                canvas.drawText("Time: "+dateFormat.format(dateObj), pageWidth-40,690, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                myPaint.setColor(Color.BLACK);
                canvas.drawRect(20,780,pageWidth-20,1200,myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                canvas.drawText("Sl. No. ",40,830, myPaint);
                canvas.drawText("Item Description ",200,830, myPaint);
                canvas.drawText("Values ",700,830, myPaint);

                canvas.drawLine(180,790,180,840, myPaint);
                canvas.drawLine(680,790,680,840, myPaint);

                canvas.drawText("1. ",40,950, myPaint);
                canvas.drawText(spinner1.getText().toString(), 200,950, myPaint);
                canvas.drawText(String.valueOf(prices[1]), 700,950, myPaint);

                canvas.drawText("2. ",40,1000, myPaint);
                canvas.drawText(spinner2.getText().toString(), 200,1000, myPaint);
                canvas.drawText(String.valueOf(prices[2]), 700,1000, myPaint);

                canvas.drawText("3. ",40,1050, myPaint);
                canvas.drawText(spinner3.getText().toString(), 200,1050, myPaint);
                canvas.drawText(gender[0], 700,1050, myPaint);

                canvas.drawText("4. ",40,1100, myPaint);
                canvas.drawText(blood.getText().toString(), 200,1100, myPaint);
                canvas.drawText(gender[2], 700,1100, myPaint);

                canvas.drawText("5. ",40,1150, myPaint);
                canvas.drawText(reading.getText().toString(), 200,1150, myPaint);
                canvas.drawText(String.valueOf(prices[5]), 700,1150, myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(30f);
                titlePaint.setColor(Color.rgb(255,99,71));
                canvas.drawText("Prantae Solutions", pageWidth/2, 1500, titlePaint);
                titlePaint.setTextSize(25f);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                titlePaint.setColor(Color.rgb(0,113,188));
                canvas.drawText("#312, Campus 11, KIIT, Patia, Bhubaneswar, Odisha, India. 751024", pageWidth/2, 1550, titlePaint);

//                titlePaint.setTextAlign(Paint.Align.LEFT);
//                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//                titlePaint.setTextSize(30f);
//                canvas.drawText("Company", 80, 1500, titlePaint);
//                canvas.drawText("Signature", 80, 1550, titlePaint);
//
//                titlePaint.setTextAlign(Paint.Align.RIGHT);
//                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//                titlePaint.setTextSize(30f);
//                canvas.drawText("Your", 1057,1500, titlePaint);
//                canvas.drawText("Signature", 1100,1550, titlePaint);

                myPDFDocument.finishPage(myPage);

                File file = new File(Environment.getExternalStorageDirectory(),"/FirstPDF.pdf");

                try {
                    myPDFDocument.writeTo(new FileOutputStream(file));
                }catch (IOException e){
                    e.printStackTrace();
                }
                myPDFDocument.close();
            }
        });
    }

    public void buttonShare(View view) {
        File file = new File(stringFile);
        if (!file.exists()){
            Toast.makeText(this, "File doesn't exists", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("application/pdf");
        intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));
        startActivity(Intent.createChooser(intentShare, "Share the file ..."));
    }

//    public void buttonShareText(View view){
//        Intent intentShare = new Intent(Intent.ACTION_SEND);
//        intentShare.setType("text/plain");
//        intentShare.putExtra(Intent.EXTRA_SUBJECT,"My Subject Here ... ");
//        intentShare.putExtra(Intent.EXTRA_TEXT,"My Text of the message goes here ... write anything what you want");
//
//        startActivity(Intent.createChooser(intentShare, "Shared the text ..."));
//    }
}