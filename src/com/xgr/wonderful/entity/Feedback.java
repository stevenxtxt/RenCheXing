/*
 * Copyright (C), 2014-2015, 联创车盟汽车服务有限公司
 * FileName: Feedback.java
 * Author:   xutework
 * Date:     2015-5-8 下午2:38:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.xgr.wonderful.entity;

import cn.bmob.v3.BmobObject;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author xutework
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Feedback extends BmobObject {

    private String content;

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    
}
