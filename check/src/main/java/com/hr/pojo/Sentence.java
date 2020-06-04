package com.hr.pojo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;

import java.util.HashSet;
import java.util.List;
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

    private int length_of_sentence;

    public int getLength_of_sentence() {
        return length_of_sentence;
    }

    public void setLength_of_sentence(int length_of_sentence) {
        this.length_of_sentence = length_of_sentence;
    }

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

    @Override
    public String toString() {
        return "Sentence{" +
                "rawSentence='" + rawSentence + '\'' +
                ", similarit=" + similarit +
                '}';
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
        if (length_of_sentence == 0) {
            if (rawSentence.length() > 38) {
                length_of_sentence = rawSentence.substring(0, 38).replaceAll("[\\pP‘’“”]", "").replaceAll(" ", "").length();
            } else {
                length_of_sentence = rawSentence.replaceAll("[\\pP‘’“”]", "").replaceAll(" ", "").length();
            }
        }

        /**
         * 句子小于13个字，不计入重复率
         */
        if (length_of_sentence < 13) {
            length_of_sentence = 0;
            similarit = 0;
        }

        infomations.forEach(new Consumer<Infomation>() {
                                @Override
                                public void accept(Infomation infomation) {
                                    final Elements em = Jsoup.parse(infomation.getRawContent()).select("em");
                                    Set set = new HashSet();
                                    for (Element element : em) {
                                        set.add(element.text().replaceAll("[\\pP‘’“”]", "").replaceAll(" ", ""));
                                    }
                                    //相似的标记总计有多长
                                    int length = 0;
                                    for (Object o : set) {
                                        length += o.toString().length();
                                    }
                                    if (rawSentence.startsWith("不然《冰与火之歌》的关键剧情，简单讲起来不就是第")) {
                                        System.out.println(set);
                                    }

                                    int temp = (int) Math.round(length * 1.0 / length_of_sentence * 100);
                                    if (temp > similarit) {
                                        similarit = temp;
                                        int i = infomation.getMostSim() + 1;
                                        infomation.setMostSim(i);
                                    }
                                }
                            }

        );

        /**
         * 后置校验
         */
        if (length_of_sentence >= 34 && similarit >= 94) {
            similarit = 100;
        }

        if (similarit > 100) {
            similarit = 100;
        }

        if (similarit <= 30) {
            similarit = 0;
        }
        return this;
    }
}
