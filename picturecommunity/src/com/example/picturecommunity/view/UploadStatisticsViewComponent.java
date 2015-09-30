package com.example.picturecommunity.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.Painter;

import javafx.scene.paint.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.vaadin.addon.JFreeChartWrapper;

import com.example.picturecommunity.controller.AdminController;
import com.example.picturecommunity.model.User;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@PreserveOnRefresh
public class UploadStatisticsViewComponent extends CustomComponent {
	AdminController model = new AdminController();
	JFreeChartWrapperContainer jfcwc;
	
	public UploadStatisticsViewComponent() {
		VerticalLayout layout = new VerticalLayout();
		
		ComboBox numOfUsers = new ComboBox("Number of top uploaders:");
		numOfUsers.setInvalidAllowed(false);
		numOfUsers.setNullSelectionAllowed(false);
		numOfUsers.addItem("5");
		numOfUsers.addItem("10");
		numOfUsers.addItem("15");
		numOfUsers.select("5");
		
		//Notification.show(controller.getUploadStats(1).get(0).getUserName());
		//Notification.show(controller.getUploadStats(5).toString());
		/*Button TESTUPSTATS = new Button("TEST UPSTATS");
		TESTUPSTATS.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String stats = "";
				for (User u : controller.getUploadStats(10)) {
					stats += "[user: " + u.getUserName() + "|uploads: " + u.getUploads() + "]\n";
				}
				Notification.show(stats);
			}
		});
		layout.addComponent(TESTUPSTATS);*/
		
		layout.addComponent(numOfUsers);
		jfcwc = new JFreeChartWrapperContainer(Integer.parseInt((String) numOfUsers.getValue()));
        layout.addComponent(jfcwc);	
		
		// Recreate the chart every time a different value is selected from the ComboBox
		ValueChangeListener listener = new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				//layout.removeComponent(jfcwc);
				layout.removeComponent(jfcwc);
				jfcwc = new JFreeChartWrapperContainer(Integer.parseInt((String) numOfUsers.getValue()));
		        layout.addComponent(jfcwc);	
			}
		};
		/*JFreeChartWrapperContainer jfcwc = new JFreeChartWrapperContainer(Integer.parseInt((String) numOfUsers.getValue()));
		ValueChangeListener listener = new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
		        Notification.show("Selected value " + numOfUsers.getValue().toString());
		        
		        layout.removeComponent(jfcwc);
		        jfcwc = new JFreeChartWrapperContainer(Integer.parseInt((String) numOfUsers.getValue()));
		        layout.addComponent(jfcwc);
		    }
		};*/
		numOfUsers.addValueChangeListener(listener);
		
		//layout.addComponent(new JFreeChartWrapperContainer()); // add listener for the combobox, which updates automatically the JFCWcontainer with the selected value (5, 10 or 15)
		
		setSizeUndefined();
		setCompositionRoot(layout);
	}
	
	public class JFreeChartWrapperContainer extends Panel {
		
		private int numOfUploads;
		
		public JFreeChartWrapperContainer(int numOfUploads) {
			this.numOfUploads = numOfUploads;
			setContent(createJFCW());
		}
		
		private CategoryDataset createDataset() {
			//ArrayList<User> users = new ArrayList<User>();
			// fill the list with users from the database limiting the output to only the top 15 (the maximum for the numOfUsers combobox) uploaders
			// query has to sort by number of uploads and use the LIMIT 15 expression at the end
			Vector<User> users = (Vector<User>) model.getUsers(numOfUploads);
			
			// create the dataset
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			for (User user : users) {
				dataset.addValue(user.getUploads(), user.getUserName(), "");
			}
			
			return dataset;
		}
		
		public JFreeChartWrapper createJFCW() {
			JFreeChart chart = createChart(createDataset());
			JFreeChartWrapper jfcw = new JFreeChartWrapper(chart) {
				@Override
				public void attach() {
					super.attach();
					setResource("src", getSource());
				}
			};
			
			return jfcw;
		}
		
		private JFreeChart createChart(CategoryDataset dataset) {
			JFreeChart chart = ChartFactory.createBarChart("Top uploaders",
					"Users",
					"Number of uploads",
					dataset,
					PlotOrientation.HORIZONTAL,
					true,
					true,
					false);
			
			// additional customization
			chart.setBackgroundPaint(Color.white);
			
			// get a reference to the plot for further customization
			CategoryPlot plot = (CategoryPlot)chart.getPlot();
			plot.setBackgroundPaint(Color.white);
			plot.setDomainGridlinePaint(Color.black);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinePaint(Color.black);
			
			// set the range axis to display integers only
			final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			
			// disable bar outlines
			BarRenderer renderer = (BarRenderer)plot.getRenderer();
			renderer.setDrawBarOutline(false);
			renderer.setShadowVisible(false);
			
			// set gradient paints for series
			//GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,  0.0f, 0.0f, new Color(0, 0, 64));
			//renderer.setSeriesPaint(0, gp0);
			
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
			
			return chart;
		}
	}
}
