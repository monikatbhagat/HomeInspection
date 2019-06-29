package com.example.bcod2.homeinspection.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.network.CheckNetworkConnection;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.List;

public class PreviewActivity extends AppCompatActivity {
    PDFView pdfView;
    static String mFileName,mDirectory_path;
    static File mFile;
    private Context context;
    Button btn_edit,btn_sendmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        context=PreviewActivity.this;
        pdfView=findViewById(R.id.pdfView);
        btn_sendmail=findViewById(R.id.btn_sendmail);
        btn_edit=findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        btn_sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkNetWork();
            }
        });
        System.out.println("file.exists() = " + mFile.exists());
        Log.d("ttt",String.valueOf(mFile.exists()));
        Log.d("t2222",String.valueOf(mFile));
        pdfView.fromFile(mFile)
                .defaultPage(1)
                .enableSwipe(true)
                .load();
    }

    public static void open(Context context, File file) {
        mFile=file;
        context.startActivity(new Intent(context, PreviewActivity.class));
    }


    private void checkNetWork() {
        if (CheckNetworkConnection.getInstance(context).haveNetworkConnection()) {
            try {
                sendMailWithGmail();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    public void share() {
        try {

            Intent emailIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
//             emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"email@example.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "body text");
//            File root = Environment.getExternalStorageDirectory();
//        String pathToMyAttachedFile = "temp/attachement.xml";
//        File file = new File(root, pathToMyAttachedFile);
            if (!mFile.exists() || !mFile.canRead()) {
                return;
            }
            Uri uri = Uri.fromFile(mFile);
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));

        } catch (Exception e) {

        }
    }

    public void sendMailWithGmail()
    {
        try
        {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
// intent.setType("text/plain");
            final PackageManager pm = getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches) {
                if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                    best = info;
                    break;
                }
            }
            if (best != null) {
                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            }
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "No Subject");
            intent.putExtra(android.content.Intent.EXTRA_TEXT,"Text");
            if (!mFile.exists() || !mFile.canRead()) {
                return;
            }
            Uri uri = Uri.fromFile(mFile);
            intent.putExtra(Intent.EXTRA_STREAM, uri);

            startActivity(Intent.createChooser(intent, "Pick an Email provider"));
        }catch (Exception e)
        {

        }

    }

}
