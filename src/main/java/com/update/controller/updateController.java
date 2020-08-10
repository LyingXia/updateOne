package com.update.controller;

import com.alibaba.fastjson.JSONObject;
import com.update.service.updateAllService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/update")
public class updateController {
    private final static Logger logger = LoggerFactory.getLogger(updateController.class);
    private static updateAllService ua = new updateAllService();

    @RequestMapping(value = "/showSVN",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String showSVN(@RequestParam("svn_path") String svn_path){
        return ua.show_svn(svn_path);
    }

    @RequestMapping(value = "/files",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String files(@RequestParam("file_dir") String file_dir){
        return ua.files(file_dir);
    }

    @RequestMapping(value = "/updateFew",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateFew(@RequestParam("wars") String wars, @RequestParam("libs") String libs, @RequestParam("linux") String linux, @RequestParam("svn_path") String svn_path){
        String [] linuxInfo = linux.split("linux_fgf");
        return ua.update_few(wars,libs,linuxInfo,svn_path);
    }

    @RequestMapping(value = "/updateLibsFew",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateLibsFew(@RequestParam("libs") String libs, @RequestParam("linux") String linux, @RequestParam("svn_path") String svn_path){
        String [] linuxInfo = linux.split("linux_fgf");
        return ua.update_lib_few(libs,linuxInfo,svn_path);
    }

    @RequestMapping(value = "/updateWarsFew",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateWarsFew(@RequestParam("wars") String wars,@RequestParam("linux") String linux, @RequestParam("svn_path") String svn_path){
        String [] linuxInfo = linux.split("linux_fgf");
        return ua.update_war_few(wars,linuxInfo,svn_path);
    }

    // SQL页面
    @RequestMapping(value = "/showSqlFile",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String showSqlFile(@RequestParam("svn_path") String svn_path){
        return ua.showSqlFile(svn_path);
    }

    @RequestMapping(value = "/QueryFiles",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String SQLs(@RequestParam("file_dir") String file_dir){
        return ua.QueryFiles(file_dir);
    }

    @RequestMapping(value = "/runSqlFile",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String SQLs(@RequestParam("dbInfo") String dbInfo,@RequestParam("sql_path") String sql_path){
        return ua.run_sql(dbInfo,sql_path);
    }

    @RequestMapping(value = "/readSqlResult",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String readSqlResult(@RequestParam("resultFile") String resultFile){
        return ua.readSqlResult(resultFile);
    }


}
