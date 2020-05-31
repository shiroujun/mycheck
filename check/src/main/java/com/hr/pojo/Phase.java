package com.hr.pojo;


import java.util.List;

/**
 * 文章
 */
public class Phase {
    public List<Paragraphs> getParagraphs() {
        return paragraphs;
    }

    int length_of_phase;

    public void setParagraphs(List<Paragraphs> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public int getSimilarit() {
        return similarit;
    }

    public int getLength_of_phase() {
        return length_of_phase;
    }

    public void setLength_of_phase(int length_of_phase) {
        this.length_of_phase = length_of_phase;
    }

    public void setSimilarit(int similarit) {
        this.similarit = similarit;
    }

    //该文章的段落集合
    private List<Paragraphs> paragraphs;
    //该文章的综合重复率
    private int similarit;

    public Phase(List<Paragraphs> paragraphs) {
        this.paragraphs = paragraphs;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "paragraphs=" + paragraphs +
                ", similarit=" + similarit +
                '}';
    }

    public Phase cacluate() {
        for (Paragraphs paragraphs : paragraphs) {
            length_of_phase = paragraphs.getLength_of_paragraphs();
        }

        for (Paragraphs paragraphs : paragraphs) {
            //获取权重
            similarit += (paragraphs.getLength_of_paragraphs() * 1.0 / length_of_phase) * paragraphs.getSimilarit();
        }
        return this;
    }
}
