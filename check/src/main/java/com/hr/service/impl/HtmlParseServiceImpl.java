package com.hr.service.impl;

import com.hr.pojo.Infomation;
import com.hr.service.HtmlParseService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HtmlParseServiceImpl implements HtmlParseService {
    @Override
    public List<Infomation> parse(String html) {
        Document document = Jsoup.parse(html);

        /**
         * 空格 父子关系的子全部子元素
         * >    父子关系的直接子元素
         *
         */
        Elements select = document.select("div.result.c-container");
        ArrayList<Infomation> list = new ArrayList<Infomation>();
        int i = 0;
        for (Element element : select) {
            Element a = element.getElementsByTag("a").first();
            String href = a.attr("href");//获取链接
            String rowTitle = a.html();//内向的人,告诉你个秘密--外向的人其实很<em>羡慕你</em>的性格…进来<em>听我说</em>
            Element div = element.select(".c-abstract").first();
            if (div == null) {
                System.out.println("搜索出来的内容是视频...跳过");
                i--;
                continue;
            }
            String rowContent = div.html();
            list.add(new Infomation(href, rowTitle, rowContent).ContentFromRaw().TitleFromRaw());
        }
        return list;
    }
}
