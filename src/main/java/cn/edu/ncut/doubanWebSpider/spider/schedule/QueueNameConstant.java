package cn.edu.ncut.doubanWebSpider.spider.schedule;

//redis 中 爬虫待抓取队列的常量
public interface QueueNameConstant {
	  static final String QUEUE_BOOK_INFO="queue_book_info_";
	  static final String QUEUE_SIMPLE_BOOK_INFO="queue_simple_book_info_";
	  static final String QUEUE_BOOK_COMMNENT="queue_book_comment_";
	  static final String QUEUE_USER_INFO = "queue_user_info_";
	  
	  static final String QUEUE_BOOK_ERROR = "queue_book_error";
	  static final String QUEUE_SIMPLE_BOOK_ERROR = "queue_book_error";
	  static final String QUEUE_COMMENT_ERROR = "queue_comment_error";
}
