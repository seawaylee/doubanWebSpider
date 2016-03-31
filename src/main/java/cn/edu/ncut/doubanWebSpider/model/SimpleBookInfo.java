package cn.edu.ncut.doubanWebSpider.model;

import javax.persistence.Table;

@Table(name = "tb_simplebookinfo")
public class SimpleBookInfo {
    private Integer id;

    private String title;

    private String author;

    private String translator;

    private String press;

    private String publishtime;

    private String price;

    private Double rating;

    private String url;

    private String tag;

    private String img;

    /**
     * limit  查询参数
     */
    
    private Integer selectLimitAmount;

    /**
     * 查询结果数量
     */
    private Integer itemResultAmount;
    
    
    
    public Integer getSelectLimitAmount()
	{
		return selectLimitAmount;
	}

	public void setSelectLimitAmount(Integer selectLimitAmount)
	{
		this.selectLimitAmount = selectLimitAmount;
	}

	public Integer getItemResultAmount()
	{
		return itemResultAmount;
	}

	public void setItemResultAmount(Integer itemResultAmount)
	{
		this.itemResultAmount = itemResultAmount;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator == null ? null : translator.trim();
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press == null ? null : press.trim();
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime == null ? null : publishtime.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

	@Override
	public String toString()
	{
		return "书名:" + title + "</br>"
				+ "作者:" + author+ "</br>"
				+ "出版社:" + press+ "</br>"
				+ "出版时间:" + publishtime+ "</br>"
				+ "价格:" + price+ "</br>"
				+ "评分:" + rating+ "</br>"
				+ "地址:" + url+ "</br>"
				+ "标签:" + tag+ "</br>"
				+ "<br><br>";
	}
    
}