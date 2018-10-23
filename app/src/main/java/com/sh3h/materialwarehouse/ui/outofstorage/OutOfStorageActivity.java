package com.sh3h.materialwarehouse.ui.outofstorage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.sh3h.dataprovider.util.FileUtil;
import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.MainActivity;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;
import com.sh3h.materialwarehouse.util.FileUtils;
import com.sh3h.mobileutil.util.TextUtil;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OutOfStorageActivity extends ParentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_storage);
        ButterKnife.bind(this);
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setTitle("出入库");
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn_add_order, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_order:
//                Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent, 1);

               String url= Environment.getExternalStorageDirectory().toString()+"/sh3h/material.xml";
//                File file=new File(url);//获取父目录  
                analyzeXml(url);
//                File parentFlie=new File(file.getParent());
//
//                Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", parentFlie);
//                Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
////                intent.setDataAndType(Uri.fromFile(parentFlie),"file/*");
//                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
////                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                startActivityForResult(intent, 1);


//                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                    return;
//                }
//                //获取文件下载路径
//
//                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "sh3h" + "/material.xml";
//                File dir = new File(path);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider",dir);
//                //调用系统文件管理器打开指定路径目录
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setDataAndType(uri, "*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent, 1);
                break;
            case R.id.btn_submit:
                finish();
                break;
        }
    }

    String path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                    path = uri.getPath();
                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                    path = FileUtils.getPath(this, uri);
                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                } else {//4.4以下下系统调用方法
                    path = FileUtils.getRealPathFromURI(this, uri);
                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                }
            }
//            analyzeXml(path);
        }
    }
    private static final String INPUT_ENCODING = "UTF-8";
    /**
     * 解析
     */
    private void analyzeXml(String filePath) {
        try {
            InputStream fis = new FileInputStream(filePath);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, INPUT_ENCODING);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("Meter".equals(parser.getName())){
                            String meterNo=parser.getAttributeValue(null,"No");
                            String txm=parser.getAttributeValue(null,"Txm");
                            Toast.makeText(this, meterNo+txm, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
