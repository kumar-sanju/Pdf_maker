package com.sanju.pdf_convertor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextPdfActivity extends AppCompatActivity {

    TextView nameText,calibrationText,dobText,heightText,wightText;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_pdf);

//        nameText = findViewById(R.id.nameText);
//        calibrationText = findViewById(R.id.calibrationText);
//        dobText = findViewById(R.id.dobText);
//        heightText = findViewById(R.id.heightText);
//        wightText = findViewById(R.id.wightText);
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
                canvas.drawText("Welcome",40,50,myPaint);
                myPDFDocument.finishPage(myPage);

                // for page 2 you should add a new line
//                PdfDocument.PageInfo myPageInfo2 = new PdfDocument.PageInfo.Builder(250,400,1).create();
//                PdfDocument.Page myPage2 = myPDFDocument.startPage(myPageInfo2);
//                Canvas canvas2 = myPage2.getCanvas();
//                canvas2.drawText("Welcome",40,50,myPaint);
//                myPDFDocument.finishPage(myPage2);

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