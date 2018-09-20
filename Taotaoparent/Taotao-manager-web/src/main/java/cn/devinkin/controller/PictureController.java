package cn.devinkin.controller;

import cn.devinkin.common.fileupload.FastDFSClient;
import cn.devinkin.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


/**
 * @author devinkin
 * <p>Title: PictureController</p>
 * <p>Description: 图片上传Controller</p>
 * @version 1.0
 * @see
 * @since 18:56 2018/9/20
 */
@RequestMapping("/pic")
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/upload")
    @ResponseBody
    public String picUpload(MultipartFile uploadFile) {
        Map result = null;
        try {
            // 接收上传文件
            // 取扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            url =  IMAGE_SERVER_URL + url;
            // 响应上传图片的url
            result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return JsonUtils.objectToJson(result);
        }
    }
}
