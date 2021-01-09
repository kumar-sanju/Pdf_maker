package com.sanju.pdf_convertor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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

public class TextPdf4Activity extends AppCompatActivity {

    EditText edit2,edit1,name,phone;
    Spinner spinner1,spinner2;
    Button createBtn,shareBtn;

    Bitmap bitmap, scaleBitmap;
    int pageWidth = 1200;
    Date dateObj;
    DateFormat dateFormat;
    float[] prices = new float[]{0,200,300,450,325,500};
    private String  stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "FirstPDF.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_pdf4);

        edit2 = findViewById(R.id.edit2);
        edit1 = findViewById(R.id.edit1);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        createBtn = findViewById(R.id.btn);
        shareBtn = findViewById(R.id.shareBtn);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pdf);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap,100,100,false);

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

                if(name.getText().toString().length() == 0 ||
                phone.getText().toString().length() == 0 ||
                edit1.getText().toString().length() == 0 ||
                edit2.getText().toString().length() == 0 ){
                    Toast.makeText(TextPdf4Activity.this,"Fields are empty",Toast.LENGTH_LONG).show();
                }

                PdfDocument myPDFDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint titlePaint = new Paint();

                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
                PdfDocument.Page myPage = myPDFDocument.startPage(myPageInfo);
                Canvas canvas = myPage.getCanvas();

                canvas.drawBitmap(scaleBitmap,0,0,myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(70f);
                canvas.drawText("Diamond Pizza", pageWidth/2, 270, titlePaint);

                myPaint.setColor(Color.rgb(0,113,188));
                myPaint.setTextSize(30f);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("Call: 022-414212313",1160,40, myPaint);
                canvas.drawText("022-121313414", 1160,80, myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                titlePaint.setTextSize(70f);
                canvas.drawText("Invoice", pageWidth/2, 500, titlePaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(35f);
                myPaint.setColor(Color.BLACK);
                canvas.drawText("Customer Name: "+name.getText(),20,590, myPaint);
                canvas.drawText("Contact No.: "+phone.getText(),20,640, myPaint);

                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("Invoice No.:" +"223223", pageWidth-20, 590, myPaint);

                dateFormat = new SimpleDateFormat("dd/mm/yy");
                canvas.drawText("Date: "+dateFormat.format(dateObj), pageWidth-20,640, myPaint);

                dateFormat = new SimpleDateFormat("HH:mm:ss");
                canvas.drawText("Time: "+dateFormat.format(dateObj), pageWidth-20,690, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                canvas.drawRect(20,780,pageWidth-20,860,myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                canvas.drawText("Sl. No. ",40,830, myPaint);
                canvas.drawText("Item Description ",200,830, myPaint);
                canvas.drawText("Price ",700,830, myPaint);
                canvas.drawText("Qty ",900,830, myPaint);
                canvas.drawText("Total ",1050,830, myPaint);

                canvas.drawLine(180,790,180,840, myPaint);
                canvas.drawLine(680,790,680,840, myPaint);
                canvas.drawLine(880,790,880,840, myPaint);
                canvas.drawLine(1030,790,1030,840, myPaint);

                float totle = 0, total2=0;
                if(spinner1.getSelectedItemPosition() != 0){
                    canvas.drawText("1. ",40,950, myPaint);
                    canvas.drawText(spinner1.getSelectedItem().toString(), 200,950, myPaint);
                    canvas.drawText(String.valueOf(prices[spinner1.getSelectedItemPosition()]), 700,950, myPaint);
                    canvas.drawText(edit1.getText().toString(),900,950,myPaint);
                    totle = Float.parseFloat(edit1.getText().toString())*prices[spinner1.getSelectedItemPosition()];
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(totle),pageWidth-40,950,myPaint);
                    myPaint.setTextAlign(Paint.Align.LEFT);
                }

                if(spinner2.getSelectedItemPosition() !=0){
                    canvas.drawText("2. ",40,1050, myPaint);
                    canvas.drawText(spinner2.getSelectedItem().toString(), 200,1050, myPaint);
                    canvas.drawText(String.valueOf(prices[spinner2.getSelectedItemPosition()]), 700,1050, myPaint);
                    canvas.drawText(edit2.getText().toString(),900,1050,myPaint);
                    total2 = Float.parseFloat(edit2.getText().toString())*prices[spinner2.getSelectedItemPosition()];
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(totle),pageWidth-40,1050,myPaint);
                    myPaint.setTextAlign(Paint.Align.LEFT);
                }

                float subTotla = totle + total2;
                canvas.drawLine(680, 1200, pageWidth-40, 1200, myPaint);
                canvas.drawText("Sub total", 700, 1250, myPaint);
                canvas.drawText(":",900,1250, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(subTotla),pageWidth-40,1250, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Tax (%10)",700,1300,myPaint);
                canvas.drawText(":",900,1300, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(subTotla*12/100),pageWidth-40,1300, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);

                myPaint.setColor(Color.rgb(247,147,30));
                canvas.drawRect(680,1350,pageWidth-20,1450,myPaint);

                myPaint.setColor(Color.BLACK);
                myPaint.setTextSize(50f);
                myPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Total", 700, 1415, myPaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(subTotla +(subTotla*12/100)),pageWidth-40,1415, myPaint);

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