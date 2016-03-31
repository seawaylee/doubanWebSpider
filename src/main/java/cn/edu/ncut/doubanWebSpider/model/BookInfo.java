package cn.edu.ncut.doubanWebSpider.model;

import java.util.Date;

import javax.persistence.Table;
@Table(name = "tb_bookinfo")
public class BookInfo {
    private Integer id;

    private String no;

    private String title;

    private String authorname;

    private String press;

    private String orititle;

    private String translator;

    private Date publishtime;

    private Integer pages;

    private Double price;

    private String binding;

    private String series;

    private String isbn;

    private Double rating;

    private Integer comments;

    private String contentinfo;

    private String authorinfo;

    private String usertags;

    private String alsolikeebook;

    private String alsolikebook;

    private Integer shortcommentsnum;

    private Integer bookcommentsnum;

    private Integer readingnotesnum;

    private String bookurl;

    private Integer discussnum;

    private Integer readingsnum;

    private Integer hasreadnum;

    private Integer wantreadnum;

    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname == null ? null : authorname.trim();
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press == null ? null : press.trim();
    }

    public String getOrititle() {
        return orititle;
    }

    public void setOrititle(String orititle) {
        this.orititle = orititle == null ? null : orititle.trim();
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator == null ? null : translator.trim();
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding == null ? null : binding.trim();
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series == null ? null : series.trim();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim();
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getContentinfo() {
        return contentinfo;
    }

    public void setContentinfo(String contentinfo) {
        this.contentinfo = contentinfo == null ? null : contentinfo.trim();
    }

    public String getAuthorinfo() {
        return authorinfo;
    }

    public void setAuthorinfo(String authorinfo) {
        this.authorinfo = authorinfo == null ? null : authorinfo.trim();
    }

    public String getUsertags() {
        return usertags;
    }

    public void setUsertags(String usertags) {
        this.usertags = usertags == null ? null : usertags.trim();
    }

    public String getAlsolikeebook() {
        return alsolikeebook;
    }

    public void setAlsolikeebook(String alsolikeebook) {
        this.alsolikeebook = alsolikeebook == null ? null : alsolikeebook.trim();
    }

    public String getAlsolikebook() {
        return alsolikebook;
    }

    public void setAlsolikebook(String alsolikebook) {
        this.alsolikebook = alsolikebook == null ? null : alsolikebook.trim();
    }

    public Integer getShortcommentsnum() {
        return shortcommentsnum;
    }

    public void setShortcommentsnum(Integer shortcommentsnum) {
        this.shortcommentsnum = shortcommentsnum;
    }

    public Integer getBookcommentsnum() {
        return bookcommentsnum;
    }

    public void setBookcommentsnum(Integer bookcommentsnum) {
        this.bookcommentsnum = bookcommentsnum;
    }

    public Integer getReadingnotesnum() {
        return readingnotesnum;
    }

    public void setReadingnotesnum(Integer readingnotesnum) {
        this.readingnotesnum = readingnotesnum;
    }

    public String getBookurl() {
        return bookurl;
    }

    public void setBookurl(String bookurl) {
        this.bookurl = bookurl == null ? null : bookurl.trim();
    }

    public Integer getDiscussnum() {
        return discussnum;
    }

    public void setDiscussnum(Integer discussnum) {
        this.discussnum = discussnum;
    }

    public Integer getReadingsnum() {
        return readingsnum;
    }

    public void setReadingsnum(Integer readingsnum) {
        this.readingsnum = readingsnum;
    }

    public Integer getHasreadnum() {
        return hasreadnum;
    }

    public void setHasreadnum(Integer hasreadnum) {
        this.hasreadnum = hasreadnum;
    }

    public Integer getWantreadnum() {
        return wantreadnum;
    }

    public void setWantreadnum(Integer wantreadnum) {
        this.wantreadnum = wantreadnum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
}