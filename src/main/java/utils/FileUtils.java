package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;

public class FileUtils
{
	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 */
	public static void readFileByBytes(String fileName)
	{
		File file = new File(fileName);
		InputStream in = null;
		try
		{
			System.out.println("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1)
			{
				System.out.write(tempbyte);
			}
			in.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		try
		{
			System.out.println("以字节为单位读取文件内容，一次读多个字节：");
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			in = new FileInputStream(fileName);
			FileUtils.showAvailableBytes(in);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1)
			{
				System.out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e1)
		{
			e1.printStackTrace();
		} finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e1)
				{
				}
			}
		}
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 */
	public static String readFileByChars(String fileName,String coder)
	{
		File file = new File(fileName);
		Reader reader = null;
		String s = "";
		try
		{
			System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file),coder);
			int tempchar;
			while ((tempchar = reader.read()) != -1)
			{
				s += (char) tempchar;
			}
			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFileByLines(String fileName)
	{
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String temp;
		try
		{
			reader = new BufferedReader(new FileReader(file));

			// 一次读入一行，直到读入null为文件结束
			while ((temp = reader.readLine()) != null)
			{
				sb.append(temp);
			}
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e1)
				{
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 随机读取文件内容
	 */
	public static void readFileByRandomAccess(String fileName)
	{
		RandomAccessFile randomFile = null;
		try
		{
			System.out.println("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1)
			{
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (randomFile != null)
			{
				try
				{
					randomFile.close();
				} catch (IOException e1)
				{
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 */
	private static void showAvailableBytes(InputStream in)
	{
		try
		{
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 写入文件
	 */
	public static void writeStrToFile(String str, String filename)
	{
		try
		{
			File file = new File(filename);
			//如果文件所在文件夹不存在 则 创建文件夹
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			try
			{
				file.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			FileOutputStream fos = new FileOutputStream(file, true);
			Writer os = new OutputStreamWriter(fos, "UTF-8");
			os.write(str);
			os.flush();
			fos.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void writeStrToFileReward(String str, String filename)
	{
		try
		{
			// FileOutputStream fos = new FileOutputStream(new
			// File("D:\\DwcrTmpFile\\" + filename),true);
			File file = new File(filename);
			//如果文件所在文件夹不存在 则 创建文件夹
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			try
			{
				file.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			FileOutputStream fos = new FileOutputStream(file, false);
			Writer os = new OutputStreamWriter(fos, "UTF-8");
			os.write(str);
			os.flush();
			fos.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeFileByByte(String path,byte[] contents)
	{
		try
		{
			FileOutputStream fio = new FileOutputStream(path);
			fio.write(contents);
			fio.flush();
			fio.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static boolean deleteFile(String filePath)
	{
		if(filePath != null && !"".equals(filePath))
		{
			try
			{
				File f = new File(filePath); // 输入要删除的文件位置
				if(f.exists())
					return f.delete();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static byte[] readfile(String filePath)
	{
		try
		{
		File one = new File(filePath);
		if (!one.exists()) {
			
			//"文件不存在:" + one.getName();
			return null;
		}
		long len = one.length();
		byte[] onebytes = new byte[(int) len];
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(one));
		bufferedInputStream.read(onebytes);
		bufferedInputStream.close();
		bufferedInputStream = null;
		one = null;
		return onebytes;
		}
		catch(Exception e)
		{
			return null;
		}
		
	}
}
