package com.ray.balloon.constants;

/**
 * Created by Administrator on 2016/2/25.
 */
public class Constant {

    public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";
    // 用户信息
    public final static String USER_INFO = "user_info";

    // 是否已登录
    public final static String IS_LOGIN = "is_login";

    // 保存着上传图片的路径
    public final static String UPLOAD_PICTURE_PATH = "/imageUpload/";

    // json格式数据缓存的路径
    public final static String JSON_DATA_CACHE_PATH = "/jsonCache/";

    // 保存报错信息文件的路径
    public final static String CRASH_ERROR_FILE_PATH = "/crash/";

    // 下载文件的路径
    public final static String DOWNLOAD_FILE_PATH = "/download/";

    // 图片缓存的路径
    public final static String IMAGE_CACHE_PATH = "/imageCache/";

    // 系统可用RAM内存大小
    public static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    // 磁盘缓存大小限制
    public static final int MAX_DISK_CACHE_SIZE = 40 * 1024 * 1024; // 40M

    // RAM内存缓存大小限制
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
}
