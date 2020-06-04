package com.hr.control;

import com.hr.pojo.Infomation;
import com.hr.pojo.Paragraphs;
import com.hr.pojo.Phase;
import com.hr.pojo.Sentence;
import com.hr.service.HtmlParseService;
import com.hr.util.HttpUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.text.normalizer.Replaceable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CheckControl {

    @Autowired
    private HtmlParseService htmlParseService;


    @RequestMapping(value = "/crawling",method =RequestMethod.POST)
    @ResponseBody
    public Phase checkBaidu(String phase) throws URISyntaxException {
        //把英文逗号句号换成中文逗号句号
        phase = phase.replaceAll("\\.","。")
                .replaceAll(",","，")
                .replaceAll("\\?","？")
                .replaceAll("!","！")
                .replaceAll(";","；")
                .replaceAll(" ","");
        ArrayList<Paragraphs> returnParagraphs = new ArrayList<>();
        String[] Paragraphs = phase.split("\r\n");
        for (String paragraph : Paragraphs) {
            paragraph = paragraph.trim().replaceAll(" ","");//对段落除去所有空格
            String[] sentences  = paragraph.split("[。？！.?!;；]");//根据中文句号分
            ArrayList<Sentence> returnSentences = new ArrayList<>();
            for (String sentence : sentences) {
                /**
                 * 对百度发起http请求,获取检索内容 参数为sentence
                 */
                URIBuilder uriBuilder = new URIBuilder("http://www.baidu.com/s");
                //https://www.baidu.com/s?wd=关键字&pn=0
                uriBuilder.setParameter("wd",sentence);//搜索的内容
                if (sentence.length()>38) {
                    uriBuilder.setParameter("bs", sentence.substring(0, 38));
                }else {
                    uriBuilder.setParameter("bs", sentence);
                }
                System.out.println(sentence);
                uriBuilder.setParameter("pn","0");//第几条数据开始查
                uriBuilder.setParameter("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9,User-Agent");
                uriBuilder.setParameter("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.16 Safari/537.36");
                uriBuilder.setParameter("rqlang","cn");
                //uriBuilder.setParameter("rn","20");//查询多少条
                URI uri = uriBuilder.build();
                String html = HttpUtils.doGet(uri);


                /**
                 * 将查询到的信息解析成 单个infomation
                 */
                List<Infomation> list = htmlParseService.parse(html);
                returnSentences.add(new Sentence(sentence,list).cacluate());
            }
            returnParagraphs.add(new Paragraphs(returnSentences).cacluate());
        }

        Phase returnPhase = new Phase(returnParagraphs).cacluate();
        System.out.println(returnPhase);
        return returnPhase;
    }

}
