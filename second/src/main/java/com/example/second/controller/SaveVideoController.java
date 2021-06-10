package com.example.second.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/saveVideo")
@CrossOrigin
public class  SaveVideoController {
    @PostMapping(value = "/saveTest")
    public Map<String,String> saveTest(@RequestParam("file") MultipartFile file, @RequestParam String SavePath)
            throws IllegalStateException {
        Map<String,String> resultMap = new HashMap<>();
        try{
            //获取文件后缀，因此此后端代码可接收一切文件，上传格式前端限定
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            // 重构文件名称
            System.out.println("前端传递的保存路径："+SavePath);
            String pikId = UUID.randomUUID().toString().replaceAll("-", "");
            String newVideoName = pikId + "." + fileExt;
            System.out.println("重构文件名防止上传同名文件："+newVideoName);
            //保存视频
            File fileSave = new File(SavePath, newVideoName);
            file.transferTo(fileSave);
            //构造Map将视频信息返回给前端
            //视频名称重构后的名称
            resultMap.put("newVideoName",newVideoName);
            //正确保存视频则设置返回码为200
            resultMap.put("resCode","200");
            //返回视频保存路径
            resultMap.put("VideoUrl",SavePath + "/" + newVideoName);
            return  resultMap;

        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
            //保存视频错误则设置返回码为400
            resultMap.put("resCode","400");
            return  resultMap ;

        }
    }
}
