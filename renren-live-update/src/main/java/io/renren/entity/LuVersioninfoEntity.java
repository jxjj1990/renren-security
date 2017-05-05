package io.renren.entity;

import java.io.Serializable;



/**
 * 版本管理表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-03 16:19:54
 */
public class LuVersioninfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//版本号
	private String version;
	//安装包大小
	private String packetsize;
	//通知图标
	private String noticeiconuri;
	//apk图标
	private String apkiconuri;
	//apk地址
	private String installuri;
	//备注
	private String desc;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：版本号
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置：安装包大小
	 */
	public void setPacketsize(String packetsize) {
		this.packetsize = packetsize;
	}
	/**
	 * 获取：安装包大小
	 */
	public String getPacketsize() {
		return packetsize;
	}
	/**
	 * 设置：通知图标
	 */
	public void setNoticeiconuri(String noticeiconuri) {
		this.noticeiconuri = noticeiconuri;
	}
	/**
	 * 获取：通知图标
	 */
	public String getNoticeiconuri() {
		return noticeiconuri;
	}
	/**
	 * 设置：apk图标
	 */
	public void setApkiconuri(String apkiconuri) {
		this.apkiconuri = apkiconuri;
	}
	/**
	 * 获取：apk图标
	 */
	public String getApkiconuri() {
		return apkiconuri;
	}
	/**
	 * 设置：apk地址
	 */
	public void setInstalluri(String installuri) {
		this.installuri = installuri;
	}
	/**
	 * 获取：apk地址
	 */
	public String getInstalluri() {
		return installuri;
	}
	/**
	 * 设置：备注
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 获取：备注
	 */
	public String getDesc() {
		return desc;
	}
}
