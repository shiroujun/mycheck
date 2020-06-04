package com.hr.pojo;


import java.util.List;
import java.util.Optional;

/**
 * 段落
 */
public class Paragraphs {


    public Paragraphs(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    //该段落里句子的集合
    private List<Sentence> sentences;

    //改段落的综合重复率
    private int similarit;

    private int length_of_paragraphs;

    public int getLength_of_paragraphs() {
        return length_of_paragraphs;
    }

    public void setLength_of_paragraphs(int length_of_paragraphs) {
        this.length_of_paragraphs = length_of_paragraphs;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public int getSimilarit() {
        return similarit;
    }

    public void setSimilarit(int similarit) {
        this.similarit = similarit;
    }

    @Override
    public String toString() {
        return "Paragraphs{" +
                "sentences=" + sentences +
                ", similarit=" + similarit +
                ", length_of_paragraphs=" + length_of_paragraphs +
                '}';
    }

    public Paragraphs cacluate() {
        for (Sentence sentence : sentences) {
            length_of_paragraphs += sentence.getLength_of_sentence();
        }

       /* for (Sentence sentence : sentences) {
            //获取权重
            similarit = similarit + sentence.getLength_of_sentence() * 1.0 / length_of_paragraphs * sentence.getSimilarit();
        }*/

        Optional<Double> reduce = sentences.stream().map(e -> e.getLength_of_sentence() * 1.0 / length_of_paragraphs * e.getSimilarit()).reduce(Double::sum);
        similarit = reduce.get().intValue();
        return this;
    }
}
