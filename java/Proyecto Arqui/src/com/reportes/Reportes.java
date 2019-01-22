
package com.reportes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author admin
 */
public class Reportes 
{
    public void reportePaciente() throws SQLException, JRException
    {
        Connection conect;
        conect = DriverManager.getConnection("jdbc:mysql://localhost:3306/arqui2018?user=root&password=");
        JasperReport report =null;
        report = (JasperReport) JRLoader.loadObjectFromFile("src\\com\\reportes\\Pacientes.jasper");
        JasperPrint im = JasperFillManager.fillReport(report,null,conect);
        JasperViewer ver = new JasperViewer(im);
        ver.setTitle("Pacientes");
        ver.setVisible(true);
    }
    
    
    
}
