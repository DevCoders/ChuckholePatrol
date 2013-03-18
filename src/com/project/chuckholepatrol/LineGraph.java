package com.project.chuckholepatrol;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
//import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class LineGraph{

	private GraphicalView view;

	TimeSeries series1 = new TimeSeries("X"); 
	TimeSeries series2 = new TimeSeries("Y"); 
	TimeSeries series3 = new TimeSeries("Z");
	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	
	XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
	XYSeriesRenderer renderer1 = new XYSeriesRenderer(); // This will be used to customize line 1
	XYSeriesRenderer renderer2 = new XYSeriesRenderer(); // This will be used to customize line 2
	XYSeriesRenderer renderer3 = new XYSeriesRenderer();
	
	public LineGraph() {
		
		// Our first data
		//int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		//int[] y =  { 30, 34, 45, 57, 77, 89, 100, 111 ,123 ,145 }; // y values!
		//TimeSeries series = new TimeSeries("X"); 
		/*for( int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}*/
		
		// Our second data
		//int[] x2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		//int[] y2 =  { 145, 123, 111, 100, 89, 77, 57, 45, 34, 30}; // y values!
		//TimeSeries series2 = new TimeSeries("Y"); 
		/*for( int i = 0; i < x2.length; i++)
		{
			series2.add(x2[i], y2[i]);
		}*/
		
		//XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		
		//XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
		//XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
		//XYSeriesRenderer renderer2 = new XYSeriesRenderer(); // This will be used to customize line 2
		mRenderer.setXTitle("Time (ms)");
		mRenderer.setYTitle("Acceleration (g)");
		//mRenderer.setScale(1);
		mRenderer.setYLabelsAlign(Align.CENTER);
		//mRenderer.setLabelsColor(Color.RED);
		mRenderer.setYLabelsColor(0, Color.RED);
		mRenderer.setXLabelsColor(Color.RED);
		//mRenderer.setLabelsTextSize(10);
		//mRenderer.setYAxisMin(-1);
		//mRenderer.setYAxisMax(10);
		//mRenderer.setPanEnabled(true, false);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setYAxisMax(5);
		mRenderer.setYAxisMin(-5);
		mRenderer.addSeriesRenderer(renderer1);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.addSeriesRenderer(renderer3);
		
		// Customization time for line 1!
		renderer1.setColor(Color.rgb(0, 255, 0));
		renderer1.setLineWidth(1);
		renderer1.setPointStyle(PointStyle.CIRCLE);
		renderer1.setFillPoints(true);
		
		// Customization time for line 2!
		renderer2.setColor(Color.CYAN);
		renderer2.setLineWidth(1);
		renderer2.setPointStyle(PointStyle.CIRCLE);
		renderer2.setFillPoints(true);
		
		renderer3.setColor(Color.MAGENTA);
		renderer3.setLineWidth(1);
		renderer3.setPointStyle(PointStyle.CIRCLE);
		renderer3.setFillPoints(true);
		
		//Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Line Graph Title");
		//return intent;
		
	}
	
	public GraphicalView getView(Context context) 
	{
		
		view =  ChartFactory.getLineChartView(context, dataset, mRenderer);
		return view;
		
	}
	
	public void addNewPoints(AccelData data, long t)
	{
			
		series1.add(data.getTimestamp()-t, data.getX());
		series2.add(data.getTimestamp()-t, data.getY());
		series3.add(data.getTimestamp()-t, data.getZ());
		
	}


}
