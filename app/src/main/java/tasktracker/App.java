/*
 * This source file was generated by the Gradle 'init' task
 */
package tasktracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class App {
    

    public static void main(String[] args) {
           App app = new App();
    }

    private JTable calanderTable;
    private JLabel monthlabel;
    private int currentMonth;
    private int currentYear;

    public App() {
         SwingUtilities.invokeLater(() -> createWindow());
    }

    private void createWindow() { 
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);

        
        currentMonth = LocalDate.now().getMonthValue();
        currentYear = LocalDate.now().getYear();

        monthlabel = new JLabel();
        monthlabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");

        prevButton.addActionListener(e -> {
            currentMonth--;
            if (currentMonth < 1) {
                currentMonth = 12;
                currentYear--;
            }
            updateCalendar();
        });

        nextButton.addActionListener(e -> {
            currentMonth++;
            if (currentMonth > 12) {
                currentMonth = 1;
                currentYear++;
            }
            updateCalendar();
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(monthlabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        calanderTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(calanderTable);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        updateCalendar();
    }

    
    private void updateCalendar() {
        String[] columns = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        DefaultTableModel model = new DefaultTableModel(null, columns);
        calanderTable.setModel(model);

        YearMonth yearMonth = YearMonth.of(currentYear, currentMonth);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = yearMonth.lengthOfMonth();

        monthlabel.setText(yearMonth.getMonth() + " " + currentYear);

        Object[] week = new Object[7];
        int day = 1;

        for (int i = 0; i < firstDayOfWeek; i++) {
            week[i] = "";
        }

        for (int i = firstDayOfWeek; i < 7; i++) {
            week[i] = day++;
        }
        model.addRow(week);

        while (day <= daysInMonth) {
            week = new Object[7];
            for (int i = 0; i < 7 && day <= daysInMonth; i++) {
                week[i] = day++;
            }
            model.addRow(week);
        }
    }

}
