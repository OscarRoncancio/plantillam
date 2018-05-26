/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crunchify.jsp.servlet;

import edu.co.sergio.mundo.dao.ColmenaDAO;
import edu.co.sergio.mundo.vo.Colmena;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ChartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        JFreeChart chart = getChart();
        int width = 500;
        int height = 350;
        ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);

    }

    public JFreeChart getChart() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        ColmenaDAO dAO = new ColmenaDAO();
        //Crear la capa de servicios que se enlace con el DAO
        ArrayList<Colmena> arrayList = (ArrayList<Colmena>) dAO.findAll();

        for (int i = 0; i < arrayList.size(); i++) {
            double porcentaje = (arrayList.get(i).getPaneles_con_alimento() / 10) * 100;
            if (arrayList.get(i).getPaneles_con_alimento() != 0) {
                dataset.setValue("colmena 1", porcentaje);
            } else {
                dataset.setValue("otros", 1 - porcentaje);
            }
        }

        boolean legend = true;
        boolean tooltips = false;
        boolean urls = false;

        JFreeChart chart = ChartFactory.createPieChart("Obras", dataset, legend, tooltips, urls);

        chart.setBorderPaint(Color.GREEN);

        chart.setBorderStroke(
                new BasicStroke(5.0f));
        chart.setBorderVisible(
                true);

        return chart;
    }

}
