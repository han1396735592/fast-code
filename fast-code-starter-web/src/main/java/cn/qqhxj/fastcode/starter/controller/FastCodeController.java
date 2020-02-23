package cn.qqhxj.fastcode.starter.controller;

import cn.qqhxj.fastcode.core.config.FastCodeHelper;
import cn.qqhxj.fastcode.core.db.DbDao;
import cn.qqhxj.fastcode.core.model.Result;
import cn.qqhxj.fastcode.core.model.TableInfo;
import cn.qqhxj.fastcode.core.service.FreemarkerService;
import cn.qqhxj.fastcode.core.util.FastCodeTool;
import cn.qqhxj.fastcode.core.vo.ProjectConfig;
import cn.qqhxj.fastcode.core.vo.TableConfig;
import cn.qqhxj.fastcode.core.vo.TemplateConfig;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@ConditionalOnWebApplication
@Controller
@RequestMapping("/fastCode")
public class FastCodeController {
    @Autowired
    private FastCodeHelper fastCodeHelper;
    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private FastCodeTool tool;
    @Autowired
    private DbDao dbDao;

    @GetMapping()
    public String index() {
        return "/webjars/fastCode/index.html";
    }


    @ResponseBody
    @GetMapping("/db/list")
    public Result<List<String>> info() {
        return Result.ok(dbDao.getTableList());
    }

    @ResponseBody
    @GetMapping("/db/table/{table}")
    public Result<TableInfo> tableInfo(@PathVariable String table) {
        return Result.ok(dbDao.getTableInfo(table));
    }

    @ResponseBody
    @PostMapping("/db/table/{table}/save")
    public Result<TableInfo> saveExt(@PathVariable String table, @RequestBody JSONObject data) {
        String path = fastCodeHelper.getTableDataPath();
        tool.writeFile(path + File.separator + table + ".json", data.toJSONString());
        return Result.ok();
    }

    @ResponseBody
    @PostMapping("/generate/project")
    public Result generate(@RequestBody ProjectConfig projectConfig) {
        freemarkerService.generate(projectConfig);
        return Result.ok();
    }

    @ResponseBody
    @PostMapping("/generate/table")
    public Result generate(@RequestBody TableConfig tableConfig) {
        freemarkerService.generate(tableConfig);
        return Result.ok();
    }

    @ResponseBody
    @PostMapping("/generate/template")
    public Result generate(@RequestBody TemplateConfig templateConfig) {
        freemarkerService.generate(templateConfig);
        return Result.ok();
    }

    @ResponseBody
    @PostMapping("/preview/template")
    public String preview(@RequestBody TemplateConfig templateConfig) {
        return freemarkerService.preview(templateConfig);
    }

    @ResponseBody
    @GetMapping("/metaData")
    public Result<JSONObject> metaData() {
        String path = fastCodeHelper.getConfigPath() + File.separator + "metadata.json";
        String s = tool.readFile(path);
        return Result.ok(JSONObject.parseObject(s));
    }

    @ResponseBody
    @GetMapping("/tableConfig/get")
    public Result<JSONObject> tableInfo() {
        String path = fastCodeHelper.getConfigPath() + File.separator + "tableConfig.json";
        String s = tool.readFile(path);
        return Result.ok(JSONObject.parseObject(s));
    }

    @ResponseBody
    @PostMapping("/tableConfig/save")
    public Result<JSONObject> tableInfo(@RequestParam JSONObject jsonObject) {
        String path = fastCodeHelper.getConfigPath() + File.separator + "tableConfig.json";
        tool.writeFile(path, jsonObject.toJSONString());
        return Result.ok();
    }

    @ResponseBody
    @PostMapping("/columnConfig/save")
    public Result<JSONObject> columnConfig(@RequestParam JSONObject jsonObject) {
        String path = fastCodeHelper.getConfigPath() + File.separator + "columnConfig.json";
        tool.writeFile(path, jsonObject.toJSONString());
        return Result.ok();
    }

    @ResponseBody
    @GetMapping("/columnConfig/get")
    public Result<JSONObject> columnConfig() {
        String path = fastCodeHelper.getConfigPath() + File.separator + "columnConfig.json";
        String s = tool.readFile(path);
        return Result.ok(JSONObject.parseObject(s));
    }

    @ResponseBody
    @GetMapping("/getTemplates")
    public Result<String[]> templateList() {
        String path = fastCodeHelper.getConfigPath() + File.separator + "templates";
        String[] list = new File(path).list();
        if (list == null) {
            return Result.ok(new String[0]);
        }
        String[] strings = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            strings[i] = list[i].substring(0, list[i].lastIndexOf("."));
        }
        return Result.ok(strings);
    }


}
