package cn.edu.ncut.doubanWebSpider.model;

import javax.persistence.Table;
import java.util.Date;
@Table(name = "tb_userbookcollect")
public class UserBookStatus {
    private Integer id;

    private String userno;

    private String bookno;

    private Date date;

    private String type;

    private Date dbtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public String getBookno() {
        return bookno;
    }

    public void setBookno(String bookno) {
        this.bookno = bookno == null ? null : bookno.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getDbtime() {
        return dbtime;
    }

    public void setDbtime(Date dbtime) {
        this.dbtime = dbtime;
    }

    @Override
    public String toString()
    {
        return "UserBookStatus{" +
                "id=" + id +
                ", userno='" + userno + '\'' +
                ", bookno='" + bookno + '\'' +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", dbtime=" + dbtime +
                '}';
    }
}