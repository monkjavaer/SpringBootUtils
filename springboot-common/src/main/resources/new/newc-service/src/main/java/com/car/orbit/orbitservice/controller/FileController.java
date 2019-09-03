package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.vo.FileVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.FastDFSClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Title: FileController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 文件上传Controller
 * @Author: monkjavaer
 * @Date: 2019/03/11 17:26
 * @Version: V1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传，客户端需设置Content-Type为application/x-www-form-urlencoded
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageList(@RequestParam("file") MultipartFile file) {
        String filePath;

        try {
            byte[] bytes = file.getBytes();
            String name = file.getOriginalFilename();
            filePath = FastDFSClient.uploadFile(bytes, name, true);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error(1003, e.getMessage());
        }

        return ResultUtil.success(new FileVO(filePath));
    }


    /**
     *  下载网络文件
     * @param response HttpServletResponse
     * @param url 网络文件路径
     * @param token 网关验证用
     * @return
     */
    @GetMapping(value = "/download")
    @ResponseBody
    public OrbitResult download(HttpServletResponse response, String url,String token) {
        try {
            downloadFile(response,url);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(1000,"download error!");
        }
        return ResultUtil.success(true);
    }


    /**
     * 下载文件并输出到response
     * @param response HttpServletResponse
     * @param urlPath 网络文件路径
     * @throws Exception
     */
    private static void downloadFile(HttpServletResponse response, String urlPath) throws Exception{
        InputStream is = null;
        OutputStream out = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            is = conn.getInputStream();
            String fileName = urlPath.substring(urlPath.lastIndexOf("/")+1,urlPath.length());
            response.setHeader("content-disposition", "attachment;filename="+fileName);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            if (null != is) {
                out = response.getOutputStream();
                byte[] b = new byte[512];
                int len;
                while ((len = is.read(b)) > 0) {
                    out.write(b, 0, len);
                }
                out.flush();
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert conn != null;
            conn.disconnect();
        }
    }

}
