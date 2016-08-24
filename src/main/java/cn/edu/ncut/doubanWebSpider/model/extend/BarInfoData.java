package cn.edu.ncut.doubanWebSpider.model.extend;

public  class BarInfoData{
	
	String[] yAxis;
	
	String[]  xAxis;

	
	
	public BarInfoData(String[] yAxis, String[] xAxis)
	{
		super();
		this.yAxis = yAxis;
		this.xAxis = xAxis;
	}

	public String[] getyAxis()
	{
		return yAxis;
	}

	public void setyAxis(String[] yAxis)
	{
		this.yAxis = yAxis;
	}

	public String[] getxAxis()
	{
		return xAxis;
	}

	public void setxAxis(String[] xAxis)
	{
		this.xAxis = xAxis;
	}

	
}