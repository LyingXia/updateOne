package com.update.util;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class linuxConnectionInfoUtil {
    private  static PropertiesUtil pu = new PropertiesUtil();

    public String linuxConnectionInfo(){
        JSONObject result_json = new JSONObject();
        String str ;
        String[] linuxInfo ;
        File f = new File(pu.getLinuxConnectionPath()+"linux_connection.csv");
        try {
            BufferedReader in = null;
            in = new BufferedReader(new FileReader(f));
            while ((str = in.readLine()) != null) {
                linuxInfo = str.split(",linuxConnection,");
                if (linuxInfo.length == 2) {
                    result_json.put(linuxInfo[0], linuxInfo[1]);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result_json.toJSONString();
    }

    public List<String> linuxConnectionIp() {
        String str;
        List<String> linux_ip = new ArrayList();
        String[] arr;
        File f = new File(pu.getLinuxConnectionPath() + "linux_connection.csv");
        try {
            BufferedReader in = null;
            in = new BufferedReader(new FileReader(f));
            long a = f.length();
            String[] b = new String[(int) a];
            while ((str = in.readLine()) != null) {
                arr = str.split(",linuxConnection,");
                if (arr.length == 4) {
                    linux_ip.add(arr[0]);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  linux_ip;
    }

    public String[] getLinuxConnectionInfo(String linux_ip) {
        String str;
        String[] arr;
        File f = new File(pu.getLinuxConnectionPath() + "linux_connection.csv");
        try {
            BufferedReader in = null;
            in = new BufferedReader(new FileReader(f));
            while ((str = in.readLine()) != null) {
                arr = str.split(",linuxConnection,");
                if (arr.length == 4 && arr[0].equals(linux_ip)) {
                   return arr;
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  new String[4];
    }

    public static void main(String[] args) {
         linuxConnectionInfoUtil li   = new linuxConnectionInfoUtil();
         String linuxInfo[] = li.getLinuxConnectionInfo("172.31.2.204");
         for (int i=0;i<linuxInfo.length;i++) {
             System.out.println(linuxInfo[i]);
         }
    }
}
