package com.luteng.qiubai.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拼接功能
 * Created by John on 2015/12/29.
 */
public class Enjoiner {
    public static String getImageURL(String image){
        String url = "http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern =Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher = pattern.matcher(image);
        matcher.find();
        return String.format(url,matcher.group(1),matcher.group(),"small",image);
    }
    public static String getIconURL(long id,String icon){
        String url = "http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";
        return String.format(url,id/10000,id,icon);
    }
}
