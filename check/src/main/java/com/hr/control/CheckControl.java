package com.hr.control;

public class CheckControl {


    public String check(String phase){

        String[] Paragraphs = phase.split("\r\n");
        for (String paragraph : Paragraphs) {
            paragraph = paragraph.trim().replaceAll(" ","");//对段落除去所有空格
            String[] sentences  = paragraph.split("。");//根据中文句号分

            for (String sentence : sentences) {
                /**
                 * 对百度发起http请求,获取检索内容 参数为sentence
                 */

            }



        }
        return null;
    }
}
