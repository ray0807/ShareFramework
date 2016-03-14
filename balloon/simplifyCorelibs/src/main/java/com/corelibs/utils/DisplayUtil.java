package com.corelibs.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.WindowManager;

/**
 * 屏幕尺寸转换的工具类；
 *
 * @author 赵成龙
 */
public class DisplayUtil {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕分辨率
     */
    @SuppressWarnings("deprecation")
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }


    /**
     * byte数组转换成16进制字符数组
     *
     * @param src
     * @return
     */
    public static String[] bytesToHexStrings(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        String[] str = new String[src.length];

        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                str[i] = "0";
            }
            str[i] = hv;
        }
        return str;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int Height = wm.getDefaultDisplay().getHeight();
        return Height;
    }

    public static float getNetImageWidthBIHeight() {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/test.jpg", options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        Log.e("wanglei", "Bitmap Height == " + options.outHeight);
        Log.e("wanglei", "Bitmap width == " + options.outWidth);
        Log.e("wanglei", "Bitmap  ==null " + (bitmap==null));
        return (options.outWidth * 1f) / (options.outHeight * 1f);
    }
}