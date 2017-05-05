package io.renren.controller;

import io.renren.entity.LuVersioninfoEntity;
import io.renren.service.LuVersioninfoService;
import io.renren.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 版本管理表
 *
 * @author zhangjiasheng
 * @email 13802885227@139.com
 * @date 2017-05-03 16:19:54
 */
@RestController
@RequestMapping("luversioninfo")
public class LuVersioninfoController {
    @Autowired
    private LuVersioninfoService luVersioninfoService;
    

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("luversioninfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<LuVersioninfoEntity> luVersioninfoList = luVersioninfoService.queryList(query);
        int total = luVersioninfoService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(luVersioninfoList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("luversioninfo:info")
    public R info(@PathVariable("id") Long id) {
        LuVersioninfoEntity luVersioninfo = luVersioninfoService.queryObject(id);

        return R.ok().put("luVersioninfo", luVersioninfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("luversioninfo:save")
    public R save(@RequestBody LuVersioninfoEntity luVersioninfo) {
        luVersioninfoService.save(luVersioninfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("luversioninfo:update")
    public R update(@RequestBody LuVersioninfoEntity luVersioninfo) {
        luVersioninfoService.update(luVersioninfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("luversioninfo:delete")
    public R delete(@RequestBody Long[] ids) {
        luVersioninfoService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 上传图片
     */
    @RequestMapping("/upload")
    @RequiresPermissions("luversioninfo:upload")
    public R upload(@RequestParam("file") MultipartFile file, HttpSession session) throws IllegalStateException, IOException {
        String fileName = null;
        //判断这个文件不为空

        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        //服务端的imges目录需要手动创建好
        String path = session.getServletContext().getRealPath("/Images");

        //获取原始文件名
        fileName = file.getOriginalFilename();

        Random rd = new Random();
        int y,m,d,h,mi,s;
        Calendar cal=Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH) + 1;
        d=cal.get(Calendar.DATE);
        h=cal.get(Calendar.HOUR_OF_DAY);
        mi=cal.get(Calendar.MINUTE);
        s=cal.get(Calendar.SECOND);

        String newFileName = Integer.toString(y)+ Integer.toString(m)+ Integer.toString(d) + Integer.toString(h) + Integer.toString(mi) + Integer.toString(s) + rd.nextInt(1000000) + fileName;


        //限制上传类型

        if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif") || fileName.endsWith(".bmp")) {

            File file1 = new File(path, newFileName);

            //完成文件上传
            file.transferTo(file1);
        } else {
            throw new RRException("只支持jpg、gif、png、bmp格式的图片");
        }

        return R.ok().put("fileName", newFileName);
    }

    /**
     * 上传文件
     */
    @RequestMapping("/uploadfile")
    @RequiresPermissions("luversioninfo:uploadfile")
    public R uploadfile(@RequestParam("file") MultipartFile file, HttpSession session) throws IllegalStateException, IOException {
        String fileName = null;
        //判断这个文件不为空

        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        //服务端的imges目录需要手动创建好
        String path = session.getServletContext().getRealPath("/Images");

        //获取原始文件名
        fileName = file.getOriginalFilename();

        Random rd = new Random();
        int y,m,d,h,mi,s;
        Calendar cal=Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH) + 1;
        d=cal.get(Calendar.DATE);
        h=cal.get(Calendar.HOUR_OF_DAY);
        mi=cal.get(Calendar.MINUTE);
        s=cal.get(Calendar.SECOND);

        String newFileName = Integer.toString(y)+ Integer.toString(m)+ Integer.toString(d) + Integer.toString(h) + Integer.toString(mi) + Integer.toString(s) + rd.nextInt(1000000) + fileName;


        //限制上传类型

        if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif") || fileName.endsWith(".bmp")) {

            File file1 = new File(path, newFileName);

            //完成文件上传
            file.transferTo(file1);
        } else {
            throw new RRException("只支持jpg、gif、png、bmp格式的图片");
        }

        return R.ok().put("fileName", newFileName);
    }

    // 上传APK
    @RequestMapping("/uploadApk")
    @RequiresPermissions("luversioninfo:uploadApk")
    public R uploadApk(@RequestParam("file") MultipartFile file, HttpSession session) throws IllegalStateException, IOException {
        String fileName = null;
        //判断这个文件不为空

        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        //服务端的imges目录需要手动创建好
        String path = session.getServletContext().getRealPath("/apk");

        //获取原始文件名
        fileName = file.getOriginalFilename();

        //路径名


        Map<String,Object> mapApkInfo = new HashMap<String, Object>();
        //限制上传类型
        String filename1 =  path + "\\" + fileName;

        if (fileName.endsWith(".apk")) {

            File file1 = new File(path, fileName);

            //获取文件大小
            long fileSize = file.getSize();

            //完成文件上传
            file.transferTo(file1);

            try {
                mapApkInfo = ExtractAPK.getApkVersion(filename1);
            }catch (Exception e){

            }

        } else {
            throw new RRException("非法的apk文件");
        }

        return R.ok().put("fileName", fileName);
    }

}
