package men.brakh.kasiski;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

public class KasiskiFrame extends JFrame {

    static private int BOR = 10;
    private static final JProgressBar progressBar2 = new JProgressBar();
    private static JLabel lblCurrentFile = new JLabel("output.txt");
    private static String currentPath = "output.txt";

    public static void createGUI() throws IOException {
        final JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(BOR, BOR, BOR, BOR));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton btnSelectFile = new JButton("Select input file");
        btnSelectFile.addActionListener(new SelectFile());

        panel.add(lblCurrentFile);
        panel.add(btnSelectFile);

        panel.add(Box.createVerticalGlue());


        progressBar2.setStringPainted(true);
        progressBar2.setMinimum(0);
        progressBar2.setMaximum(100);
        panel.add(progressBar2);

        panel.add(Box.createVerticalGlue());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        JButton button = new JButton("Analyze");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new StartKasiski(currentPath);
            }
        });
        buttonsPanel.add(button);

        buttonsPanel.add(Box.createHorizontalGlue());



        panel.add(buttonsPanel);

        panel.add(Box.createVerticalGlue());

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(260, 225));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                try {
                    createGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    static void dialogMSG(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }


    private  static class StartKasiski extends Thread {
        String filename;
        StartKasiski(String filename) {
            start();
            this.filename = filename;
        }
        @Override
        public void run() {
            String mem = Kasiski.shiftStrings("АБВ");
            String text = null;
            try {
                text = new String(Files.readAllBytes(Paths.get(filename)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Kasiski kasiski = new Kasiski(text, 3);
            int key = kasiski.progressiveTest(progressBar2);
            progressBar2.setValue(100);
            dialogMSG("Cryptotext analysis was performed.\nEstimated key length = " + key, "Success");
        }
    }

    static class SelectFile implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileopen = new JFileChooser();
            fileopen.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int ret = fileopen.showDialog(null, "OpenFile");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                currentPath = file.getAbsolutePath();
                lblCurrentFile.setText(file.getAbsolutePath());
            }
            progressBar2.setValue(0);
        }

    }



}
