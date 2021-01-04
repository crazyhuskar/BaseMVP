package com.crazyhuskar.basesdk.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import static com.crazyhuskar.basesdk.SystemConfig.SP_FILE_NAME;


/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilSharedPreferences {
    private static MyUtilSharedPreferences instance;
    private Activity activity;
    private boolean isGet = false;

    public static MyUtilSharedPreferences getInstance(Activity activity) {
        if (null == instance) {
            instance = new MyUtilSharedPreferences(activity);
        }
        return instance;
    }

    public MyUtilSharedPreferences(Activity activity) {
        this.activity = activity;
        MyUtilPermission.getInstance().init(activity, new MyUtilPermission.PermissionCallback() {
            @Override
            public void onSucceed() {
                isGet = true;
            }

            @Override
            public void onFail() {
                isGet = false;
            }
        }).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        if (isGet) {
            SharedPreferences sp = activity.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            if (object instanceof String) {
                editor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else {
                editor.putString(key, object.toString());
            }

            SharedPreferencesCompat.apply(editor);
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(String key, Object defaultObject) {
        if (isGet) {
            SharedPreferences sp = activity.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);

            if (defaultObject instanceof String) {
                return sp.getString(key, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(key, (Long) defaultObject);
            }
        }
        return "权限不足";
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        if (isGet) {
            SharedPreferences sp = activity.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            SharedPreferencesCompat.apply(editor);
        }
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        if (isGet) {
            SharedPreferences sp = activity.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            SharedPreferencesCompat.apply(editor);
        }
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        if (isGet) {
            SharedPreferences sp = activity.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
            return sp.contains(key);
        } else {
            return false;
        }
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        if (isGet) {
            SharedPreferences sp = activity.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
            return sp.getAll();
        } else {
            return new HashMap<>();
        }
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
