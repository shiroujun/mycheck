package com.hr.service;

import com.hr.pojo.Infomation;

import java.util.List;

/**
 * html解析器
 */
public interface HtmlParseService {

    public List<Infomation> parse(String html);

}
