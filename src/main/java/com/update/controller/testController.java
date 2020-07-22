//package com.update.controller;
//
//import com.test.service.*;
//import net.sf.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import java.util.List;
//
//
///**
// * created by xiapf on 2018/6/25
// */
//@Controller
//@RequestMapping("/test")
//public class testController {
//    private final static Logger logger = LoggerFactory.getLogger(testController.class);
//    private saveFileService sf = new saveFileService();
//    private runTestService rt = new runTestService();
//    private cryptService cs = new cryptService();
//    private toPostService tp = new toPostService();
//    private autoGetService ag = new autoGetService();
//    private AppServletService ass = new AppServletService();
//    private lotserverService lot = new lotserverService();
//    private String savePath = sf.getLinPath();
//    private String os = sf.winOrLin();
//
//    /**
//     * 上传单个文件操作
//     * @param file 括号中的参数名file和表单的input节点的name属性值一致
//     * @return doneCode 是否上传成功
//     */
//    @RequestMapping(value = "/uploadFile",method=RequestMethod.POST)
//    @ResponseBody
//    public String saveFile(@RequestParam("file") MultipartFile file){
//        if (os.equals("windows")){
//            savePath = sf.getWinPath();
//        }
//        logger.info("新上传文件: "+file);
////       String doneCode = sf.saveFile(file,savePath);
////       return doneCode;  //上传成功则跳转至此success.jsp页面
//        return sf.saveFile(file,savePath);
//    }
//
//    @RequestMapping(value="/runTest",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String runTest(@RequestParam("url") String url,@RequestParam("contentType") String contentType,@RequestParam("filename")String filename) throws Exception {
//        logger.info("runTest--------url="+url+"&contentType"+contentType+"&filename"+filename);
//        if (os.equals("windows")){
//            logger.info("OS:WINDOWS");
//            savePath = sf.getWinPath();
//        }
//        List<String> message;
//        String data;
//        JSONObject responseList = new JSONObject();
//        /*switch (contentType){
//            case "text":
//                message = rt.textMessage(savePath,filename);
//                break;
//            case "json":
//                message = rt.JsonMessage(savePath,filename);
//                break;
//            default:
//            //貌似是因为switch...case  default语句中不能有return 所以不好使
//                message = rt.textMessage(savePath,filename);
//                return "请输入正确的contentType";
//        }*/
//        if (contentType.equals("text")){
////            contentType = "application/x-www-form-urlencoded";
//            message = rt.textMessage(savePath,filename);
//        }
//        else if (contentType.equals("json")){
////            contentType = "application/x-www-form-urlencoded";
//            message = rt.JsonMessage(savePath,filename);
//        }
//        else {
//            return "请输入正确的contentType";
//        }
//        for (int i=0;i<message.size();i++){
//            if (message.get(i).contains("请检查您的测试EXCEL表中数据")){
//                data = "请检查您的测试EXCEL表中数据";
//                responseList.put("request"+i+data,"");
//            }
//            else if(url.contains("lotserver/test/sendRequest")){
//                String resp = ag.lotserverRunOne(url,message.get(i));
//                String urlAddress =ag.getURLaddress(url);
//                data = "content="+resp+"&urlAddress="+urlAddress;
//                String response = tp.doPost(url,data);
//                responseList.put("request"+i+data,response);
//            }else if(url.contains("lotserver/appServlet")){
//                data = message.get(i);
//                byte[] request = ass.encryptToByte(message.get(i));
//                byte[] response = ass.post(url,request);
//                String  s = new String(ass.decrtptForByte(response).getBytes(),"UTF-8");
//                responseList.put("request"+i+data,s);
//            }else if(url.contains("/lotserver/lotserverServlet")){
//                data = message.get(i);
//                String  request = lot.zt_decrypt(message.get(i));
//                String  response = lot.zt_decrypt(tp.doPost(url,request));
//                responseList.put("request"+i+data,response);
//            }else{
//                data = message.get(i);
//                String response = tp.doPost(url,data);
//                responseList.put("request"+i+data,response);
////                responseList.put("request "+i+ "SomeThing ERROR","SomeThing ERROR");
//            }
//        }
//        return responseList.toString();
//    }
//
//
//
//    @RequestMapping(value="/files",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String files(){
//        if (os.equals("windows")){
//            savePath = sf.getWinPath();
//        }
//        List<String> files =  sf.getAllfiles(savePath);
//        JSONObject file = new JSONObject();
//        file.put("files",files);
//        return file.toString();
//    }
//
//    @RequestMapping(value="/onceRun",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String onceRun(@RequestParam("url") String url, String data) throws Exception {
//        logger.info("url="+url+"&request="+data);
//        String request;
//        String response;
//        JSONObject responseList = new JSONObject();
//        if (url.contains("lotserver/appServlet")){
//            /**这里还没有验证！！！*/
//            byte[] requestByte = ass.encryptToByte(data);
//            byte[] responseByte = ass.post(url,requestByte);
//            response = new String(ass.decrtptForByte(responseByte).getBytes(),"utf-8");
//            System.out.println(response);
//        }
//        else if (url.contains("/lotserver/lotserverServlet")&&cs.isJSON(data)){
//            logger.info("");
//            request = cs.loten(data,"zt");
//            String responseString = tp.doPost(url,request);
//            /**这里有问题在！！！*/
//            if (cs.isJSON(responseString)){
//                response = responseString;
//            }else {
//                response = cs.lotresponse(responseString,"zt");
//            }
//        }else{
//            request = data;
//            response = tp.doPost(url,request);
//        }
//        responseList.put("request",data);
//        responseList.put("response",response);
//        return responseList.toString();
//    }
//
//    @RequestMapping(value="/stressRun",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String stressRun(@RequestParam("url") String url,@RequestParam("count") Integer count,@RequestParam("contentType") String contentType,@RequestParam("data") String data){
//        /**想不明白还需要完善**/
//
//        return "";
//    }
//
//    public static void main(String[] args) throws Exception {
//        testController tc = new testController();
////        String url = "http://192.168.1.31:8080/lotserver/test/sendRequest";
//        String APPurl = "http://192.168.1.50:8080/lotserver/appServlet/register";
////        String url = "http://192.168.1.35:8080/lottery-account//user  /bangding";
////        String url ="5555";
//        String contentType="json";
//        String filename = "test.xlsx";
//        String APPfilename = "APPServlet.xlsx";
//        String data = "{\"version\": \"2.3.0\", \"userName\": \"houtaiml\", \"machineId\": \"HTC Desire\", \"password\": \"houtaiml\", \"platform\": \"android\", \"imei\": \"3.43E+15\", \"channel\": \"1\", \"productName\": \"xshl\"}";
//        tc.onceRun(APPurl,data);
////        tc.runTest(url,contentType,filename);
//        tc.runTest(APPurl,contentType,APPfilename);
//
//    }
//
//
//}