package com.m.gis.springboot.po;
/**
 * @Title: GisBaseDict.java
 * @Package com.m.gis.springboot.dict
 * @Description: 从数据查询字典的返回结果
 * @author monkjavaer
 * @date 2017年11月27日 下午6:31:29
 * @version V1.0
 */
public class GisDictItem {

    /**类型编码*/
	private String code;
	/**英语名*/
	private String name;
	/**本地化类型名称*/
	private String nameLocale;

	public GisDictItem() {
		super();
	}

    public GisDictItem(String code, String name, String nameLocale) {
        this.code = code;
        this.name = name;
        this.nameLocale = nameLocale;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getNameLocale() {
        return nameLocale;
    }

    public void setNameLocale(String nameLocale) {
        this.nameLocale = nameLocale;
    }
}

