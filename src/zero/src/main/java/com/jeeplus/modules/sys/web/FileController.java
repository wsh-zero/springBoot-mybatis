/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.OkHttpUtil;
import com.jeeplus.common.utils.ResultUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.core.web.Servlets;
import com.jeeplus.modules.sys.entity.FileData;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 文件管理Controller
 *
 * @author liugf
 * @version 2018-01-21
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "${adminPath}/sys/file")
public class FileController extends BaseController {


	public void init() {
		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) UserUtils.getPrincipal();

		FileUtils.createDirectory(Global.getAttachmentDir());
		FileUtils.createDirectory(Global.getMyDocDir());
		FileUtils.createDirectory(Global.getShareBaseDir());
	}

	/**
	 * 文件管理列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"list", ""})
	public String list() {
		init();
		return "modules/sys/file/fileManager";
	}

	/**
	 * 文件管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "data")
	public List<FileData> data(HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) UserUtils.getPrincipal();
		if (principal == null) {
			return null;
		}
		List<File> targetFiles = Lists.newArrayList();
//		targetFiles.add(new File(Global.getAttachmentDir()));
		targetFiles.add(new File(Global.getMyDocDir()));
		targetFiles.add(new File(Global.getShareBaseDir()));
		return FileUtils.getFileList("files", Lists.newArrayList(targetFiles));
	}


	/**
	 * 移动文件或文件夹
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "move")
	public List move(@Param("source") String source, @Param("target") String target) throws IOException {
		//source = getFileWebUrl(source);
		//target = getFileWebUrl(target);
		List list = Lists.newArrayList();
		String[] sourceArra = source.split(",");
		for (String s : sourceArra) {
			String fileName = StringUtils.substringAfterLast(s.replace("\\", "/"), "/");
				if (FileUtils.isFolder(s)) {
					File targetFolder = FileUtils.getAvailableFolder(target + "/" + fileName, 0);
					FileUtils.moveDirectory(new File(s), targetFolder);
					Map map = new HashMap();
					map.put("id", targetFolder.getAbsolutePath());
					map.put("value", targetFolder.getName());
					list.add(map);
				} else {
					File targetFile = FileUtils.getAvailableFile(target + "/" + fileName, 0);
					FileUtils.moveFile(new File(s), targetFile);
					new File(s).deleteOnExit();
					Map map = new HashMap();
					map.put("id", targetFile.getAbsolutePath());
					map.put("value", targetFile.getName());
				list.add(map);
			}

		}

		return list;
	}

	/**
	 * copy文件文件夹
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "copy")
	public List copy(@Param("source") String source, @Param("target") String target) {
		/*source = getFileWebUrl(source);
		target = getFileWebUrl(target);*/
		List list = Lists.newArrayList();
		String[] sourceArra = source.split(",");
		for (String s : sourceArra) {
			String fileName = StringUtils.substringAfterLast(s.replace("\\", "/"), "/");
			if (FileUtils.isFolder(s)) {
				File targetFolder = FileUtils.getAvailableFolder(target + "/" + fileName, 0);
				if (FileUtils.copyDirectory(s, targetFolder.getAbsolutePath())) {
					Map map = new HashMap();
					map.put("id", targetFolder.getAbsolutePath());
					map.put("value", targetFolder.getName());
					list.add(map);
				}
			} else {
				File targetFile = FileUtils.getAvailableFile(target + "/" + fileName, 0);
				if (FileUtils.copyFile(s, targetFile.getAbsolutePath())) {
					Map map = new HashMap();
					map.put("id", targetFile.getAbsolutePath());
					map.put("value", targetFile.getName());
					list.add(map);
				}
			}

		}

		return list;
	}

	/**
	 * 删除文件管理
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "download")
	public void download(@Param("source") String source, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AjaxJson j = new AjaxJson();
		File file = new File(source);
		if (file == null || !file.exists()) {
			request.setAttribute("exception", new FileNotFoundException("请求的文件不存在"));
			request.getRequestDispatcher("/webpage/error/404.jsp").forward(request, response);
		}
		OutputStream out = null;
		try {
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			String agent = (String) request.getHeader("USER-AGENT");
			String fileName = file.getName();
			if (agent != null && agent.indexOf("MSIE") == -1) {
// FF
				String enableFileName = "=?UTF-8?B?" + (new String(Base64.getEncoder().encode(fileName.getBytes("UTF-8")))) + "?=";
				response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
			} else {
// IE
				String enableFileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
			}
//			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			out = response.getOutputStream();
			out.write(FileUtils.readFileToByteArray(file));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 删除文件管理
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "remove")
	public List delete(@Param("source") String source) {
		List list = Lists.newArrayList();
		//先删除文件
		String[] sourceArra = source.split(",");
		for (String s : sourceArra) {
			//s = getFileWebUrl(s);
			OkHttpUtil.delFile(s);
			Map map = new HashMap();
			map.put("id", s);
			map.put("value", StringUtils.substringAfterLast(s.replace("\\", "/"), "/"));
			list.add(map);
		}

		return list;
	}

	/**
	 * 创建本地文件夹
	 * @param source
	 * @param target
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "createFolder")
	public Map create(@Param("source") String source, @Param("target") String target) {
		Map map = new HashMap();
		String targetFolderPath = target + "/" + source;
		File targetFolder = FileUtils.getAvailableFolder(targetFolderPath, 0);
		boolean result = FileUtils.createDirectory(targetFolder.getAbsolutePath());
		if (result) {
			map.put("id", targetFolder.getAbsolutePath());
			map.put("value", targetFolder.getName());

		}
		return map;
	}

	/**
	 * 创建网络文件夹(文件服务器)
	 * @param source
	 * @param target
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "createWebFolder")
	public Map createFolder(@Param("source") String source, @Param("target") String target) {
		Map map = new HashMap();
		String targetFolderPath = target + "/" + source;
		File targetFolder = FileUtils.getAvailableFolder(targetFolderPath, 0);
		ResultUtil folder = OkHttpUtil.createFolder(targetFolderPath);
		//File targetFolder = FileUtils.getAvailableFolder(targetFolderPath, 0);
		//boolean result = FileUtils.createDirectory(targetFolder.getAbsolutePath());
		if (folder.getCode() == 0) {
			map.put("id", targetFolder.getAbsolutePath());
			map.put("value", targetFolder.getName());
		}
		return map;
	}

	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "rename")
	public Map rename(@Param("source") String source, @Param("target") String target) {
		Map map = new HashMap();
		File sourceFile = new File(source);
		File targetFile = new File(sourceFile.getParent() + "/" + target);
		if (sourceFile.isDirectory()) {
			targetFile = FileUtils.getAvailableFolder(targetFile.getAbsolutePath(), 0);
		} else {
			targetFile = FileUtils.getAvailableFile(targetFile.getAbsolutePath(), 0);
		}
		boolean result = sourceFile.renameTo(targetFile);
		if (result) {
			map.put("id", targetFile.getAbsolutePath());
			map.put("value", targetFile.getName());

		}
		return map;
	}

	/**
	 * 获取文件网络地址
	 *
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "getUrl")
	@ResponseBody
	public AjaxJson getUrl(@Param("dir") String dir) throws IllegalStateException, IOException {
		AjaxJson j = new AjaxJson();

		String url = getFileWebUrl(dir);

		String type = FileUtils.getFileType(dir);
		// 判断文件是否为空
		j.put("url", url);
		j.put("type", type);
		return j;
	}

	/**
	 * 对前端传过来的路径处理为文件网络路径
	 * @return
	 */
	private String getFileWebUrl(String dir){
		return Global.getFileServePath() + Servlets.getRequest().getContextPath()+dir.replace("\\","/").substring(2);
	}
	/**
	 * 上传文件
	 *
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "webupload/upload")
	@ResponseBody
	public AjaxJson webupload(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws IllegalStateException, IOException {
		AjaxJson j = new AjaxJson();

		User currentUser = UserUtils.getUser();
		String uploadPath = request.getParameter("uploadPath");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) UserUtils.getPrincipal();
		String fileUrl = Global.getAttachmentUrl() + uploadPath + "/" + year + "/" + month + "/";
		String fileDir = Global.getAttachmentDir() + uploadPath + "/" + year + "/" + month + "/";
		// 判断文件是否为空
		if (!file.isEmpty()) {
			ResultUtil resultUtil = OkHttpUtil.fileUpload(file, fileUrl, fileDir);
			if (resultUtil.getCode() == 0) {
				String data = resultUtil.getData().toString();
				Gson gson = new Gson();
				HashMap map = gson.fromJson(data, HashMap.class);
				j.put("id", map.get("id"));
				j.put("url", map.get("url"));
			}
		}
		return j;
	}

    /**
     * 文件管理 上传文件
     *
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "upload")
    @ResponseBody
    public Map upload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) throws IllegalStateException, IOException {
        User currentUser = UserUtils.getUser();
        String realPath = request.getParameter("target").substring(2) + "/";
		String fileUrl = Servlets.getRequest().getContextPath() + realPath;
        Map map = new HashMap();
        // 判断文件是否为空
        if (!upload.isEmpty()) {
			ResultUtil resultUtil = OkHttpUtil.fileUpload(upload, fileUrl, realPath);
			if (resultUtil.getCode() == 0) {
				// 文件保存路径
				// 转存文件
				String data = resultUtil.getData().toString();
				Gson gson = new Gson();
				 map = gson.fromJson(data, HashMap.class);
				String url = (String) map.get("url");
				String[] type = url.split("\\.");
				String[] value = url.split("\\/");
				String id = (String) map.get("id");
				 map.put("type",type[type.length-1]);
				 map.put("value",value[value.length-1]);
				 map.put("id",Global.getFileServePath()+url);
			}
        }
        return map;
    }

	/**
	 * 批量删除文件管理
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "webupload/delete")
	public AjaxJson delFile(String id) {
		AjaxJson j = new AjaxJson();
		ResultUtil resultUtil = OkHttpUtil.delFile(id);
		if (resultUtil.getCode() == 0) {
			j.setSuccess(true);
			j.setMsg("删除文件成功");
		} else {
			j.setSuccess(false);
			j.setMsg("删除文件失败");
		}

		return j;
	}

}
