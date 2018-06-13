package com.liudongcai.liuclan.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * 项目名称：LiuClan<br>
 * 类描述：工具类<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/13 16:09<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class ViewUtil {

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/6/13 16:10<br>
     * 方法描述：dp转px<br>
     */
    public static int dipToPix(Context context,float dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }



}
