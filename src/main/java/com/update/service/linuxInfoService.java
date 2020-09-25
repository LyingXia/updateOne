package com.update.service;

import com.alibaba.fastjson.JSONObject;
import com.update.util.linuxConnectionInfoUtil;
import java.util.List;

public class linuxInfoService {
    private  static linuxConnectionInfoUtil  li = new linuxConnectionInfoUtil();
    public static String linux_ip(){
        JSONObject file_json = new JSONObject();
        List<String> linuxConnectionIp = li.linuxConnectionIp();
        file_json.put("linux_ips",linuxConnectionIp);
        return file_json.toJSONString();
    }

}
