package com.hr.pojo;


import com.jayway.jsonpath.internal.function.numeric.Sum;

import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Optional;

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
                "length_of_phase=" + length_of_phase +
                ", paragraphs=" + paragraphs +
                ", similarit=" + similarit +
                '}';
    }

    public Phase cacluate() {
        for (Paragraphs paragraphs : paragraphs) {
            length_of_phase += paragraphs.getLength_of_paragraphs();
        }

        Optional<Double> reduce = paragraphs.stream().map(e -> {
            return e.getLength_of_paragraphs() * 1.0 / length_of_phase * e.getSimilarit();
        }).reduce(Double::sum);

        similarit = reduce.get().intValue();
        return this;
    }
}
