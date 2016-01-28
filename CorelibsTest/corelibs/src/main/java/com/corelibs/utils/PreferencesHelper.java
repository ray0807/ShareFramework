package com.corelibs.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * SharedPreferences工具类, 可以通过传入实体对象保存其至SharedPreferences中,
 * 并通过实体的类型Class将保存的对象取出. 这种方式每一个类只能保存一个实例, 不同实例会被替换.
 * 支持List的形式, 如有多个不同的同类型实例, 可以使用List来保存.
 * <BR/>
 * Created by Ryan on 2016/1/12.
 */
public class PreferencesHelper {

    private static final String LIST_TAG = ".LIST";
    private static SharedPreferences sharedPreferences;
    private static Gson gson;

    /**
     * 使用之前初始化, 可在Application中调用
     * @param context 请传入ApplicationContext避免内存泄漏
     */
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
        gson = new Gson();
    }

    private static void checkInit() {
        if (sharedPreferences == null || gson == null) {
            throw new IllegalStateException("Please call init(context) first.");
        }
    }

    /**
     * 保存对象数据至SharedPreferences, 如
     * <pre>
     * PreferencesHelper.saveData(saveUser);
     * </pre>
     * @param data 不带泛型的任意数据类型实例
     */
    public static <T> void saveData(T data) {
        checkInit();
        if (data == null)
            throw new IllegalStateException("data should not be null.");
        sharedPreferences.edit().putString(data.getClass().getName(), gson.toJson(data)).apply();
    }

    /**
     * 保存List集合数据至SharedPreferences, 请确保List至少含有一个元素, 如
     * <pre>
     * PreferencesHelper.saveData(users);
     * </pre>
     * @param data List类型实例
     */
    public static <T> void saveData(List<T> data) {
        checkInit();
        if (data == null || data.size() <= 0)
            throw new IllegalStateException("List should not be null or at least contains one element.");
        Class returnType = data.get(0).getClass();
        sharedPreferences.edit().putString(returnType.getName() + LIST_TAG,
                gson.toJson(data)).apply();
    }

    /**
     * 将数据从SharedPreferences中取出, 如
     * <pre>
     * User user = PreferencesHelper.getData(User.class)
     * </pre>
     */
    public static <T> T getData(Class<T> clz) {
        checkInit();
        String json = sharedPreferences.getString(clz.getName(), "");
        return gson.fromJson(json, clz);
    }

    /**
     * 将数据从SharedPreferences中取出, 如
     * <pre>List&lt;User&gt; users = PreferencesHelper.getData(List.class, User.class)</pre>
     */
    public static <T> List<T> getData(Class<List> clz, Class<T> gClz) {
        checkInit();
        String json = sharedPreferences.getString(gClz.getName() + LIST_TAG, "");
        return gson.fromJson(json, getClassType(clz, gClz));
    }

    private static ParameterizedType getClassType(final Class<?> raw, final Type... args) {
        return new ParameterizedType() {
            @Override public Type getRawType() {
                return raw;
            }

            @Override public Type[] getActualTypeArguments() {
                return args;
            }

            @Override public Type getOwnerType() {
                return null;
            }
        };
    }
}
