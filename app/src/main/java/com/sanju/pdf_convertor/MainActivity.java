package com.sanju.pdf_convertor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

//    private String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//            +"/myCamera/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getImagesFromPDF(File pdfFilePath, File DestinationFolder) throws IOException {

        // Getting images from Test.pdf file.
        File source = new File(Environment.getExternalStorageDirectory() + "/" + "Test" + ".pdf");

        // Images will be saved in Test folder.
        File destination = new File(Environment.getExternalStorageDirectory() + "/Test");

        // Getting images from pdf in png format.
        try {
            getImagesFromPDF(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if destination already exists then delete destination folder.
        if(DestinationFolder.exists()){
            DestinationFolder.delete();
        }

        // Create empty directory where images will be saved.
        DestinationFolder.mkdirs();

        // Reading pdf in READ Only mode.
        ParcelFileDescriptor fileDescriptor = ParcelFileDescriptor.open(pdfFilePath, ParcelFileDescriptor.MODE_READ_ONLY);

        // Initializing PDFRenderer object.
        PdfRenderer renderer = new PdfRenderer(fileDescriptor);

        // Getting total pages count.
        final int pageCount = renderer.getPageCount();

        // Iterating pages
        for (int i = 0; i < pageCount; i++) {

            // Getting Page object by opening page.
            PdfRenderer.Page page = renderer.openPage(i);

            // Creating empty bitmap. Bitmap.Config can be changed.
            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(),Bitmap.Config.ARGB_8888);

            // Creating Canvas from bitmap.
            Canvas canvas = new Canvas(bitmap);

            // Set White background color.
            canvas.drawColor(Color.WHITE);

            // Draw bitmap.
            canvas.drawBitmap(bitmap, 0, 0, null);

            // Rednder bitmap and can change mode too.
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            // closing page
            page.close();

            // saving image into sdcard.
            File file = new File(DestinationFolder.getAbsolutePath(), "image"+i + ".png");

            // check if file already exists, then delete it.
            if (file.exists()) file.delete();

            // Saving image in PNG format with 100% quality.
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                Log.v("Saved Image - ", file.getAbsolutePath());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void convertButton(View view){
//
//        String directory = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File( "/myCamera");
//        myDir.mkdirs();
//
//        String file = directory + "first.jpg";
//        Bitmap bitmap = BitmapFactory.decodeFile(file);
//
//        PdfDocument pdfDocument = new PdfDocument();
//        PdfDocument.PageInfo myPageInfo = new PdfDocument.
//                PageInfo.Builder(960,1280,1).create();
//        PdfDocument.Page page = pdfDocument.startPage(myPageInfo);
//
//        page.getCanvas().drawBitmap(bitmap,0,0, null);
//        pdfDocument.finishPage(page);
//
//        String pdfFile = directory + "/myPDFFile_3.pdf";
//        File myPDFFile = new File(pdfFile);
//
//        try {
//            pdfDocument.writeTo(new FileOutputStream(myPDFFile));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        pdfDocument.close();
//
//    }
}