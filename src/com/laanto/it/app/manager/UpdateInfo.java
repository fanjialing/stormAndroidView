package com.laanto.it.app.manager;

import java.io.Serializable;

/**
 * app 更新信息
 * 
 * version : 版本号
 * 
 * descruotuib: 描述
 * 
 * url: 获取app途径
 * 
 * @author fanjialing
 *
 * @date 2015年12月2日 上午11:00:38
 *
 */
public class UpdateInfo implements Serializable {
	private static final long serialVersionUID = 6035389658802621193L;
	private String version;
	private String description;
	private String url;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
