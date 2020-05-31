package com.hr.pojo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 句子
 */
public class Sentence {

    public int getSimilarit() {
        return similarit;
    }

    public void setSimilarit(int similarit) {
        this.similarit = similarit;
    }

    //一个句子原始的内容
    private String rawSentence;

    //该句子在百度爬取到的信息
    private List<Infomation> infomations;

    //综合后的相似率  ...对infomations里的查询到的多个内容与rawSentence做对比获取
    private int similarit;

    public String getRawSentence() {
        return rawSentence;
    }

    public void setRawSentence(String rawSentence) {
        this.rawSentence = rawSentence;
    }

    public List<Infomation> getInfomations() {
        return infomations;
    }

    public void setInfomations(List<Infomation> infomations) {
        this.infomations = infomations;
    }

    public Sentence(String rawSentence, List<Infomation> infomations) {
        this.rawSentence = rawSentence;
        this.infomations = infomations;
    }

    /**
     * 根据这两个字段的内容计算句子的相似度
     * private String rawSentence;
     * private List<Infomation> infomations;
     */
    public Sentence cacluate() {
        infomations.forEach(new Consumer<Infomation>() {
            @Override
            public void accept(Infomation infomation) {
                final Elements em = Jsoup.parse(infomation.getRawContent()).select("em");
                Set set = new HashSet();
                for (Element element : em) {
                    set.add(element.text());
                }
                //相似的标记总计有多长
                int length = 0;
                for (Object o : set) {
                    length += o.toString().length();
                }
                int temp = (int) Math.round(length * 1.0 / rawSentence.length() * 100);
                if (temp > similarit) {
                    similarit = temp;
                }
            }
        });
        return this;
    }
}
