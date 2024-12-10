package com.car.service;

import java.awt.Color;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.car.response.DashboardCountResponse;

public class GraphUtil {

	public static JFreeChart generateGraph(List<DashboardCountResponse> carRent) {
	    DefaultPieDataset pieDataSet = new DefaultPieDataset();

	    // Aggregate totals
	    int totalMakerCount = 0;
	    int totalCustomerCount = 0;
	    int totalModelCount = 0;

	    // Calculate totals from the provided list
	    for (DashboardCountResponse car : carRent) {
	        totalMakerCount += car.getMakerCount();
	        totalCustomerCount += car.getCustomerCount();
	        totalModelCount +=car.getModelCount();
	    }

	    // Populate the dataset with the aggregated values
	    pieDataSet.setValue("Maker Count: " + totalMakerCount, totalMakerCount);
	    pieDataSet.setValue("Customer Count: " + totalCustomerCount, totalCustomerCount);
	    pieDataSet.setValue("Model Count:" + totalModelCount, totalModelCount);

	    // Create the pie chart
	    JFreeChart chart = ChartFactory.createPieChart(
	        "Total Counts Chart",
	        pieDataSet,
	        true,  // Show legend
	        true,  // Show tooltips
	        false  // No URLs
	    );

	    // Customize the plot
	    PiePlot plot = (PiePlot) chart.getPlot();

	    // Set colors for the two sections
	    plot.setSectionPaint("Maker Count: " + totalMakerCount, Color.BLUE);
	    plot.setSectionPaint("Customer Count: " + totalCustomerCount, Color.GRAY);
	    plot.setSectionPaint("Model Count:" +  totalModelCount, Color.PINK);

	    // Customize labels to show names and values
	    plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
	        "{0}", // {0} = key
	        NumberFormat.getNumberInstance(),
	        NumberFormat.getPercentInstance()
	    ));

	    return chart;
	}

	
	
	
	
	public static JFreeChart generateGraphs(List<DashboardCountResponse> carRent) {
	    DefaultPieDataset pieDataSet = new DefaultPieDataset();

	    // Aggregate totals
	    int totalMakerCount = 0;
	    int totalCustomerCount = 0;

	    // Calculate totals from the provided list
	    for (DashboardCountResponse car : carRent) {
	        totalMakerCount += car.getMakerCount();
	        totalCustomerCount += car.getCustomerCount();
	    }

	    // Populate the dataset with the aggregated values
	    pieDataSet.setValue("Maker Count: " + totalMakerCount, totalMakerCount);
	    pieDataSet.setValue("Customer Count: " + totalCustomerCount, totalCustomerCount);
	    // Create the pie chart
	    JFreeChart chart = ChartFactory.createPieChart(
	        "Total Counts Chart",
	        pieDataSet,
	        true,  // Show legend
	        true,  // Show tooltips
	        false  // No URLs
	    );

	    // Customize the plot
	    PiePlot plot = (PiePlot) chart.getPlot();

	    // Set colors for the two sections
	    plot.setSectionPaint("Maker Count: " + totalMakerCount, Color.BLUE);
	    plot.setSectionPaint("Customer Count: " + totalCustomerCount, Color.GRAY);


	    // Customize labels to show names and values
	    plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
	        "{0}", // {0} = key
	        NumberFormat.getNumberInstance(),
	        NumberFormat.getPercentInstance()
	    ));

	    return chart;
	}

	
	
	
}
