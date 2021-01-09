package com.sanju.pdf_convertor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextPdf2Activity extends AppCompatActivity {

    Button createButton;
    String[] infoArray = new String[]{"Name", "Company Name", "Address", "Phone", "Email"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_pdf2);

        createButton = findViewById(R.id.createButton);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }

    private void createPDF() {
        createButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                PdfDocument myPDFDocument = new PdfDocument();
                Paint myPaint = new Paint();

                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,400,1).create();
                PdfDocument.Page myPage = myPDFDocument.startPage(myPageInfo);
                Canvas canvas = myPage.getCanvas();

                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setTextSize(12.0f);
                canvas.drawText("Enterprises",myPageInfo.getPageWidth()/2,30,myPaint);

                myPaint.setTextSize(6.0f);
                myPaint.setTextScaleX(1.5f);
                myPaint.setColor(Color.rgb(122,119,119));
                canvas.drawText("Street no.15, Kanan Vihar, Odisha",myPageInfo.getPageWidth()/2,40,myPaint);
                myPaint.setTextScaleX(1f);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(9.0f);
                myPaint.setColor(Color.rgb(122,119,119));
                canvas.drawText("Customer Information",10,70,myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(8.0f);
                myPaint.setColor(Color.BLACK);

                int startXPosition = 10;
                int endXPosition = myPageInfo.getPageWidth()-10;
                int startYPosition = 100;

                for(int i=0; i<5; i++){
                    canvas.drawText(infoArray[i],startXPosition,startYPosition,myPaint);
                    canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3, myPaint);
                    startYPosition+=20;
                }

                canvas.drawLine(80,92,80,190,myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                canvas.drawRect(10,200,myPageInfo.getPageWidth()-10,300,myPaint);
                canvas.drawLine(85,200,85,300,myPaint);
                canvas.drawLine(163,200,163,300,myPaint);
                myPaint.setStrokeWidth(0);
                myPaint.setStyle(Paint.Style.FILL);

                canvas.drawText("Photo",35,250,myPaint);
                canvas.drawText("Photo",110,250,myPaint);
                canvas.drawText("Photo",190,250,myPaint);

                canvas.drawText("Note",10,320,myPaint);
                canvas.drawLine(35,325,myPageInfo.getPageWidth()-10,325,myPaint);
                canvas.drawLine(10,345,myPageInfo.getPageWidth()-10,345,myPaint);
                canvas.drawLine(10,365,myPageInfo.getPageWidth()-10,365,myPaint);

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
}