package com.liudongcai.liuclan.util;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：LiuClan<br>
 * 类描述：<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/20 14:35<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class JsonAnalyze {

    public static JSONObject getJSONObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/4/25 10:58<br>
     * 方法描述：获取JSON值<br>
     */
    public static String getJSONValue(JSONObject json, String key) {
        String text = "";
        try {
            if (json != null && !TextUtils.isEmpty(key)) {
                text = json.isNull(key) ? "" : json.getString(key);
            }
        } catch (JSONException e) {}
        return text;
    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/4/25 10:57<br>
     * 方法描述：获取JSON值<br>
     */
    public static String getJSONValue(JSONArray array, int index) {
        String text = "";
        try {
            if (array != null && index >= 0 && index < array.length()) {
                text = array.isNull(index) ? "" : array.getString(index);
            }
        } catch (JSONException e) {}
        return text;
    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/4/25 10:57<br>
     * 方法描述：获取一个JSONObject<br>
     */
    public static JSONObject getJSONObject(JSONObject json, String key) {
        try {
            if (json != null && !TextUtils.isEmpty(key)) {
                return json.isNull(key) ? null : json.getJSONObject(key);
            }
        } catch (JSONException e) {}
        return null;
    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/4/25 10:57<br>
     * 方法描述：获取一个JSONObject<br>
     */
    public static JSONObject getJSONObject(JSONArray array, int index) {
        JSONObject json = null;
        try {
            if (array != null && index >= 0 && index < array.length()) {
                json = array.isNull(index) ? null : array.getJSONObject(index);
            }
        } catch (JSONException e) {}
        return json;
    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/4/25 10:57<br>
     * 方法描述：获取JSON数组<br>
     */
    public static JSONArray getJSONArray(JSONObject json, String key) {
        JSONArray array = null;
        try {
            if (json != null && !TextUtils.isEmpty(key)) {
                array = json.isNull(key) ? null : json.getJSONArray(key);
            }
        } catch (JSONException e) {}
        return array;
    }

}
