package cn.nineSeven.service.impl;

import cn.nineSeven.constant.SystemConstant;
import cn.nineSeven.entity.Result;
import cn.nineSeven.handler.exception.SystemException;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import cn.nineSeven.constant.AppHttpCodeEnum;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class OssUploadService {
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.secretKey}")
    private String secretKey;
    @Value("${oss.bucket}")
    private String bucket;

    public Result uploadImg(MultipartFile img) {
        String originalFilename = img.getOriginalFilename();
        if(!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filePath =  new StringBuilder().append(uuid).append(fileType).toString();
        String url = uploadOss(img, filePath);
        return Result.okResult(url);
    }



    private String uploadOss(MultipartFile imgFile, String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken,null, null);
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return SystemConstant.OSS_URL + key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return null;
    }
}
