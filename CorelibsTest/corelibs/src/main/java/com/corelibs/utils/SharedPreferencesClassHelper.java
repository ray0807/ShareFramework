package com.corelibs.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Ryan on 2016/1/8.
 */
public class SharedPreferencesClassHelper {
    private static HashMap<String, SharedPreferencesHolder> preferencesHolders = new HashMap<>();
    private static Context appContext;
    private static SharedPreferencesClassHelper instance = new SharedPreferencesClassHelper();

    public static void init(Context context) {
        appContext = context;
    }

    public static SharedPreferencesClassHelper getInstance() {
        return instance;
    }

    public <T> T getData(Class<T> clz) {
        SharedPreferencesHolder<T> holder = getPreferencesHolder(clz);
        return holder.getData();
    }

    public <T> void saveData(T data) {
        SharedPreferencesHolder<T> holder = getPreferencesHolder((Class<T>) data.getClass());
        holder.saveData(data);
    }

    private <T> SharedPreferencesHolder<T> getPreferencesHolder(Class<T> clz) {
        SharedPreferencesHolder<T> holder = preferencesHolders.get(clz.getName());
        if (holder == null) {
            holder = new SharedPreferencesHolder<>(appContext, clz);
            preferencesHolders.put(clz.getName(), holder);
        }

        return holder;
    }

    class SharedPreferencesHolder<T> {
        private SharedPreferences preferences;
        private Class<T> clz;

        public SharedPreferencesHolder(Context context, Class<T> clz) {
            this.clz = clz;
            preferences = context.getSharedPreferences(clz.getName(), Context.MODE_PRIVATE);
        }

        public T getData() {
            T data = null;
            try {
                data = clz.newInstance();
                Field[] fields = clz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String type = field.getType().toString();
                    if (type.endsWith("int") || type.endsWith("Integer")) {
                        field.set(data, preferences.getInt(field.getName(), 0));
                    } else if (type.endsWith("long") || type.endsWith("Long")) {
                        field.set(data, preferences.getLong(field.getName(), 0));
                    } else if (type.endsWith("boolean") || type.endsWith("Boolean")) {
                        field.set(data, preferences.getBoolean(field.getName(), false));
                    } else {
                        field.set(data, preferences.getString(field.getName(), ""));
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return data;
        }

        public void saveData(T data) {
            Class clz = data.getClass();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String type = field.getType().toString();
                try {
                    if (type.endsWith("int") || type.endsWith("Integer")) {
                        preferences.edit().putInt(field.getName(), field.getInt(data)).apply();
                    } else if (type.endsWith("long") || type.endsWith("Long")) {
                        preferences.edit().putLong(field.getName(), field.getLong(data)).apply();
                    } else if (type.endsWith("boolean") || type.endsWith("Boolean")) {
                        preferences.edit().putBoolean(field.getName(), field.getBoolean(data)).apply();
                    } else {
                        preferences.edit().putString(field.getName(), field.get(data).toString()).apply();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
