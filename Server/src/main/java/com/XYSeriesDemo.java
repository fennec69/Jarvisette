package com;

import com.fhacktory.utils.SignalUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by farid on 13/05/2017.
 */
public class XYSeriesDemo extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series containing a null value.
     *
     * @param title the frame title.
     */
    public XYSeriesDemo(final String title) {

        super(title);
        final XYSeries series = new XYSeries("Random Data");
        String fileName = "./sample1.wav";
        Path path = Paths.get(fileName);
        System.out.println("Reading file");
        try {
            short[] data = SignalUtils.byteArrayToShortArray(Files.readAllBytes(path));
            for(int i = 0; i < data.length; i++) {
                series.add(i, data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Series Demo",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
}