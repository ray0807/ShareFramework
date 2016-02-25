package com.ray.balloon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;

import com.corelibs.utils.ToastMgr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ryan on 2016/1/13.
 */
public class ImagePickHelper {
    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_GALLERY = 2;
    public static final String EXTRA_CAMERA_FILE_PATH = "EXTRA_CAMERA_FILE_PATH";

    /**
     * 缓存图片文件夹路径
     */
    public static String IMAGE_PATH;

    /**
     * 缓存图片基础文件名, 具体文件名为基础文件名+时间戳
     */
    public static final String IMAGE_BASE_NAME = "tmp";

    /**
     * 缓存图片文件类型
     */
    public static final String IMAGE_BASE_TYPE = ".jpeg";

    /**
     * 图片压缩的大小
     */
    public static final int IMAGE_MAX_SIZE = 720;

    public static void showPickerDialog(final Activity context) {
        IMAGE_PATH = context.getCacheDir() + "/tmpupload/";

        View view = context.getLayoutInflater().inflate(R.layout.dialog_image_pick, null);

        Button btn_takephoto1 = (Button) view.findViewById(R.id.btn_takephoto1);
        Button btn_takephoto2 = (Button) view.findViewById(R.id.btn_takephoto2);
        Button btn_takephoto_cancel = (Button) view.findViewById(R.id.btn_takephoto_cancel);

        final Dialog dialog = new Dialog(context, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = context.getWindowManager().getDefaultDisplay().getHeight();

        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        btn_takephoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    String fileName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    File dirPath = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
                    if (!dirPath.exists()) {
                        dirPath.mkdirs();
                    }
                    File photoFile = new File(dirPath, fileName);
                    // 指定调用相机拍照后的照片存储的路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    intent.putExtra(EXTRA_CAMERA_FILE_PATH, photoFile.getPath());
                    context.startActivityForResult(intent, REQUEST_CAMERA);
                } else {
                    ToastMgr.show("请确认已经插入SD卡");
                }

                dialog.dismiss();
            }
        });
        btn_takephoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                context.startActivityForResult(galleryIntent, REQUEST_GALLERY);
                dialog.dismiss();
            }
        });
        btn_takephoto_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static File bitmapTofile(Bitmap bmp) throws IOException {
        String filename = IMAGE_BASE_NAME + System.currentTimeMillis() + IMAGE_BASE_TYPE;
        File file = new File(IMAGE_PATH + filename);
        if (!file.exists()) {
            File dir = new File(IMAGE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file.createNewFile();
        }
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(IMAGE_PATH + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        boolean result = bmp.compress(format, quality, stream);

        if (result) {
            bmp.recycle();
            return file;
        }

        return null;
    }

    /**
     * 根据传入的maxSize压缩图片
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int maxSize) {
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;
        if (mWidth <= mHeight) {
            scaleWidth = maxSize / (float) mWidth;
            scaleHeight = maxSize / (float) mHeight;
            scaleWidth = scaleHeight;
        } else {
            scaleWidth = maxSize / (float) mWidth;
            scaleHeight = maxSize / (float) mHeight;
            scaleHeight = scaleWidth;
        }
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);

        if(newBitmap != bitmap)
            bitmap.recycle();
        return newBitmap;
    }

    /**
     * 压缩图片, 默认720
     */
    public static Bitmap compressBitmap(Bitmap bitmap) {
        return compressBitmap(bitmap, IMAGE_MAX_SIZE);
    }

    /**
     * 清空图片文件缓存
     */
    public static void clearCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = new File(IMAGE_PATH);
                String[] tmps = dir.list();
                for (int i = 0; i < tmps.length; i++) {
                    File file;
                    if (tmps[i].endsWith(File.separator)) {
                        file = new File(IMAGE_PATH + tmps[i]);
                    } else {
                        file = new File(IMAGE_PATH + File.separator + tmps[i]);
                    }
                    file.delete();
                }
            }
        }).start();
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
