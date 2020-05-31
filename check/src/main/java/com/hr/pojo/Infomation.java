package com.hr.pojo;


import org.jsoup.Jsoup;
import org.jsoup.select.Elements;


/**
 * 在百度一页上爬取到的数据
 * <p>
 * 被百度用<em></em>标记的是关键字
 */
public class Infomation {
    private String href;//网站链接
    private String rawTitle;//网站的title html格式  --eg:内向的人,告诉你个秘密--外向的人其实很<em>羡慕你</em>的性格…进来<em>听我说</em>…
    private String rawContent;//检索能呈现的内容 html格式  --eg:<span class="m">最佳答案: </span>《<em>你家</em>在哪里》 作词:李准 作曲:朱超伦 演唱:李娜 歌词: <em>你家</em>在哪里,<em>我家</em>氓山头 吃过百家饭,走过千村路 学过百灵叫,...<br><a href='http://zhidao.baidu.com/q?ct=17&pn=0&tn=ikaslist&rn=10&word=%E4%BD%A0%E5%AE%B6' target='_blank' class='c'>更多关于你家的问题&gt;&gt;</a>
    private String title;//网站的标题   --eg:内向的人,告诉你个秘密--外向的人其实很羡慕你的性格…进来听我说…
    private String content;//网站的内容  --eg:《你家在哪里》 作词:李准 作曲:朱超伦 演唱:李娜 歌词: 你家在哪里,我家氓山头 吃过百家饭,走过千村路 学过百灵叫,...
    private int pieceTogether;//拼凑程度  ,与改网页的文本内容多个片段的关键字相似程度
    private String moreInfomation;//更多关于xx的内容  展示给前端，我们不做处理


    public String getRawTitle() {
        return rawTitle;
    }

    public void setRawTitle(String rawTitle) {
        this.rawTitle = rawTitle;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoreInfomation() {
        return moreInfomation;
    }

    public void setMoreInfomation(String moreInfomation) {
        this.moreInfomation = moreInfomation;
    }

    public int getPieceTogether() {
        return pieceTogether;
    }

    public void setPieceTogether(int pieceTogether) {
        this.pieceTogether = pieceTogether;
    }

    public Infomation(String href, String rowTitle, String rotContent) {
        this.href = href;
        this.rawTitle = rowTitle;
        this.rawContent = rotContent;
    }

    /**
     * 通过rawContent 获取content
     */
    public Infomation ContentFromRaw() {
        Elements span = Jsoup.parse(rawContent).select("span").remove();//找到可能存在的span标签及其内容
        Elements a = Jsoup.parse(rawContent).select("a").remove();//找到可能存在的a标签及其内容
        this.content = rawContent.replaceAll(span.toString(), "").replaceAll(a.toString(), "").replaceAll("<[^>]+>", "").replaceAll("\r", "").replaceAll("\n", "");
        return this;
    }

    /**
     * 通过rawTitle 获取title
     */
    public Infomation TitleFromRaw() {
        this.title = this.rawTitle.replaceAll("<[^>]+>", "").replaceAll("\r", "").replaceAll("\n", "");
        return this;
    }

    @Override
    public String toString() {
        return "Infomation{" +
                "href='" + href + '\'' +
                ", rowTitle='" + rawTitle + '\'' +
                ", rotContent='" + rawContent + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", moreInfomation='" + moreInfomation + '\'' +
                ", pieceTogether=" + pieceTogether +
                '}';
    }
}
