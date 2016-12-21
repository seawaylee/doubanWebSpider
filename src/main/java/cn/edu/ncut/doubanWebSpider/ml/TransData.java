package cn.edu.ncut.doubanWebSpider.ml;

import cn.edu.ncut.doubanWebSpider.dao.BookCommentMapper;
import cn.edu.ncut.doubanWebSpider.dao.SimpleBookInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.BookComment;
import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.FileUtils;

import java.util.List;

@Component
@Transactional
public class TransData
{
	@Autowired
	private BookCommentMapper commentDao;
	@Autowired
	private SimpleBookInfoMapper bookDao;
//	public void doTransComment(String destFilePath)
//	{
//		List<BookComment> commentList = commentDao.selectAll();
//		int rowCount = 0;
//		for (BookComment comment : commentList)
//		{
//			// userHashCode,userNo,bookNo
//			String data = comment.getUserno() + "," + comment.getUserno().hashCode() + "," + comment.getBookno() + ","
//					+ comment.getRating();
//			FileUtils.writeStrToFile(data + "\r\n", destFilePath);
//			rowCount++;
//		}
//		System.out.println("成功转换 " + rowCount + "  条评论");
//	}

	/**
	 *  将有评分的评论记录存储到文件 Format: UserNo,BookNo,Rating
	 * 
	 * @author 李熙伟 2016年3月2日
	 */
	public void doTransCommentHasRating(String destFilePath)
	{
//		防止内存溢出   一次取100W
		int rowCount = 0;
		List<BookComment> commentList;
		while (true)
		{
			commentList = commentDao.selectAllHasRatings(rowCount);
			if (commentList.size() <= 0)
				break;

			for (BookComment comment : commentList)
			{
				String data = comment.getUserno() + "," + comment.getBookno() + "," + comment.getRating();
				FileUtils.writeStrToFile(data + "\r\n", destFilePath);
				rowCount++;
			}
			System.out.println("成功转换 " + rowCount + "  条有评分评论");
		}
	}

	/**
	 * 将没有评分的记录单独存储 Format: UserNo,BookNo,Content
	 * 
	 * @author 李熙伟 2016年3月2日
	 */
	public void doTransCommentNoRating(String destFilePath)
	{
		List<BookComment> commentList = commentDao.selectAllNoRatings();
		int rowCount = 0;
		for (BookComment comment : commentList)
		{
			String data = comment.getUserno() + "##*##" + comment.getBookno() + "##*##"
					+ comment.getContent().trim();
			FileUtils.writeStrToFile(data + "\r\n", destFilePath);
			rowCount++;
		}
		System.out.println("成功转换 " + rowCount + "  条无评分评论");
	}

	/**
	 * 抽取有评分的评论数据作为朴素贝叶斯训练语料
	 * @Author NikoBelic
	 * @Date 19/12/2016 21:04
	 */
	public void doTransCommentPNRating(String destFilePath)
	{
		List<BookComment> commentList;
		int rowCount = 0;
		String data;
		//while (true)
		//{
			commentList = commentDao.selectByRatings(5,rowCount);
			//if (commentList.size() <= 0)
				//break;
			for (BookComment comment : commentList)
			{
				data = comment.getUserno() + "##*##" + comment.getBookno() + "##*##" + comment.getRating() + "##*##"
						+ comment.getContent().trim();
				FileUtils.writeStrToFile(data + "\r\n", destFilePath);
				rowCount++;
			}
			System.out.println("成功转换 " + rowCount + "  条有PN极性评论");
		//}
	}

	/**
	 * 转换 BookNo,BookTilte,BookTag
	 * @author 李熙伟
	 * 2016年3月3日
	 */
	public void doTransBookNoWithName(String destFilePath)
	{
		List<SimpleBookInfo> bookList = bookDao.selectAllNames();
		int rowCount = 0;
		for (SimpleBookInfo book : bookList)
		{
			String data = book.getUrl().split("/")[4] + "," + book.getTitle() + "," + book.getTag();
			FileUtils.writeStrToFile(data + "\r\n", destFilePath);
			rowCount++;
		}
		System.out.println("成功转换 " + rowCount + "  本书");
	}

//	public void doTransBook(String destFilePath)
//	{
//		List<SimpleBookInfo> bookList = bookDao.selectAll();
//		int rowCount = 0;
//		int lose = 0;
//		for (SimpleBookInfo book : bookList)
//		{
//			String data = book.getUrl().split("/")[4] + "," + book.getRating() + "," + book.getTitle();
//			if (!data.contains("null"))
//			{
//				FileUtils.writeStrToFile(data + "\r\n", destFilePath);
//				rowCount++;
//			} else
//			{
//				System.out.println(data);
//				lose++;
//			}
//		}
//		System.out.println("成功转换 " + rowCount + "  本书," + "放弃无评分 " + lose + " 本书");
//	}

//	public void doTransContentByRating(String destFilePath)
//	{
//		List<BookComment> commentList = commentDao.selectAll();
//		int rowCount = 0;
//		for (BookComment comment : commentList)
//		{
//			String rating = comment.getRating().toString();
//			String data = comment.getContent().trim();// 存储数据集
//			FileUtils.writeStrToFile(data + "\r\n", destFilePath + rating + ".txt");
//			rowCount++;
//		}
//		System.out.println("成功转换 " + rowCount + "  条评论");
//	}

	public void addBookno()
	{
		List<SimpleBookInfo> books = bookDao.selectAll();
		int count = 1;
		for (SimpleBookInfo book : books)
		{
			String url = book.getUrl();
			String bookno = url.split("/")[4];
			book.setBookno(bookno);
			bookDao.update(book);
			System.out.println("已成功转换" + (count++ + "/" + books.size()));
		}
	}

	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:ssm/spring-*.xml");
		//applicationContext.getBean(TransData.class).doTransCommentHasRating("/Users/lixiwei-mac/Documents/DataSet/recommend/UserComment.txt");
		//applicationContext.getBean(TransData.class).doTransCommentNoRating(
//				"/Users/lixiwei-mac/Documents/DataSet/doubanReading/rating/UserComment.txt");
//		applicationContext.getBean(TransData.class).doTransBookNoWithName(
//				"/Users/lixiwei-mac/Documents/DataSet/recommend/BookName.txt");
		applicationContext.getBean(TransData.class).doTransCommentPNRating("/Users/lixiwei-mac/Documents/DataSet/recommend/EqNumUserComment.txt");
	}
}
