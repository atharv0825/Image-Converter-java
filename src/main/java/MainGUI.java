

//AWT graphic functions 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//function used to convert the file
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.ImageIcon;
//java swing 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainGUI {
    private static JFrame frame;
    private static JTextField urlField;
    private static JButton urlButton;
    private static JButton browseButton;
    private static JButton convertButton;
    private static JLabel statusLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        // ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Athar\\OneDrive\\Desktop\\sem 4 project final\\resource\\2023-Porsche-911-GT3-RS-004-1080.jpg");
        frame.setLayout(new GridBagLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Enter the URL:");
        JTextField urlField = new JTextField(20);
        JButton urlButton = new JButton("Convert from URL");
        topPanel.add(label);
        topPanel.add(urlField);
        topPanel.add(urlButton);

        JPanel middlePanel = new JPanel(new FlowLayout());
        JButton browseButton = new JButton("Browse Files");
        middlePanel.add(browseButton);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton convertButton = new JButton("Convert");
        bottomPanel.add(convertButton);
        JLabel statusLabel = new JLabel("");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(topPanel, gbc);

        gbc.gridy = 1;
        frame.add(middlePanel, gbc);

        gbc.gridy = 2;
        frame.add(bottomPanel, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.PAGE_END;
        frame.add(statusLabel, gbc);

        frame.setVisible(true);

        urlButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String urlString = urlField.getText();
                convertImageFromURL(urlString);
            }
        });

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<File> selectedFiles = browseDeviceFiles();
                if (!selectedFiles.isEmpty()) {
                    convertImage(selectedFiles);
                } else {
                    statusLabel.setText("No file is Selected");
                }
            }
        });

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Conversion successful!");
            }
        });
    }

    public static void convertImageFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedImage image = ImageIO.read(url);
            convertImage(image);
        } catch (MalformedURLException e) {
            statusLabel.setText("Error while reading the URL: " + e.getMessage());
        } catch (IOException e) {
            statusLabel.setText("Error in reading the URL");
        }
    }

    public static List<File> browseDeviceFiles() {
        List<File> selectedFiles = new ArrayList<>();
        JFileChooser choosefile = new JFileChooser();
        choosefile.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif", "bmp");
        choosefile.setFileFilter(filter);

        int returnVal = choosefile.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            for (File file : choosefile.getSelectedFiles()) {
                selectedFiles.add(file);
            }
        }
        return selectedFiles;
    }

    public static void convertImage(List<File> files) {
        JFrame conversionFrame = new JFrame("Conversion Format");
        conversionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conversionFrame.setSize(600, 600);
        conversionFrame.setLayout(new GridBagLayout());

        JLabel formatLabel = new JLabel("Select the conversion format:");
        JButton jpegButton = new JButton("JPEG");
        JButton gifButton = new JButton("GIF");
        JButton pngButton = new JButton("PNG");
        JButton bmpButton = new JButton("BMP");
        JButton pdfButton = new JButton("PDF");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(5, 5, 5, 5);
        
        conversionFrame.add(formatLabel, gbc);
        gbc.gridy++; // Move to the next row
        conversionFrame.add(jpegButton, gbc);

        gbc.gridy++;
        conversionFrame.add(gifButton, gbc);

        gbc.gridy++;
        conversionFrame.add(pngButton, gbc);

        gbc.gridy++;
        conversionFrame.add(bmpButton, gbc);

        gbc.gridy++;
        conversionFrame.add(pdfButton, gbc);

        conversionFrame.setVisible(true);

        jpegButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveimage(files, "jpg");
                conversionFrame.dispose();
            }
        });

        gifButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveimage(files, "gif");
                conversionFrame.dispose();
            }
        });

        pngButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveimage(files, "png");
                conversionFrame.dispose();
            }
        });

        bmpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveimage(files, "bmp");
                conversionFrame.dispose();
            }
        });

        pdfButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pdfFileName = JOptionPane.showInputDialog(frame, "Enter the name of the PDF file (without extension):");
                if (pdfFileName != null && !pdfFileName.trim().isEmpty()) {
                    combineImagestoPdf(files, pdfFileName.trim());
                    conversionFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid file name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void convertImage(BufferedImage image) {
        // Implement conversion logic
        JFrame conversionFrame = new JFrame("Conversion Format");
        conversionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conversionFrame.setSize(600, 600);
        conversionFrame.setLayout(new FlowLayout());

        JLabel formatLabel = new JLabel("Select the conversion format:");
        JButton jpegButton = new JButton("JPEG");
        JButton gifButton = new JButton("GIF");
        JButton pngButton = new JButton("PNG");
        JButton bmpButton = new JButton("BMP");
        JButton pdfButton = new JButton("PDF");

        conversionFrame.add(formatLabel);
        conversionFrame.add(jpegButton);
        conversionFrame.add(gifButton);
        conversionFrame.add(pngButton);
        conversionFrame.add(bmpButton);
        conversionFrame.add(pdfButton);

        conversionFrame.setVisible(true);

        jpegButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveimage(image, "jpg");
                conversionFrame.dispose();
            }
        });

        gifButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveimage(image, "gif");
                conversionFrame.dispose();
            }
        });

        pngButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveimage(image, "png");
                conversionFrame.dispose();
            }
        });

        bmpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveimage(image, "bmp");
                conversionFrame.dispose();
            }
        });

        pdfButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cannot convert single image to PDF. Please select 'file' and then 'PDF' to combine multiple images into a single PDF.");
                conversionFrame.dispose();
            }
        });
    }

    public static void saveimage(List<File> files, String format) {
        for (File file : files) {
            try {
                BufferedImage image = ImageIO.read(file);
                saveimage(image, format);
            } catch (IOException e) {
                System.err.println("Error while reading the image : " + file.getName());
            }
        }
    }

    public static void saveimage(BufferedImage image, String format) {
        try {
            String Filename = JOptionPane.showInputDialog(frame, "Enter the name of the image file (without extension):");
            if (Filename != null && !Filename.trim().isEmpty()) {
                String filePathBase = "C:\\Users\\Athar\\OneDrive\\Desktop\\sem 4 project final\\Converted files\\";
                File outFile = new File(filePathBase + Filename.trim() + "." + format);
                ImageIO.write(image, format, outFile); //to save the images at the specified path
                JOptionPane.showMessageDialog(frame, "Image saved at " + outFile.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid file name!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            System.err.println("Error in saving image");
        }
    }
     

    public static void combineImagestoPdf(List<File> files, String pdfFileName) {
        try (PDDocument doc = new PDDocument()) {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    System.err.println("Unable to read the image file: " + file.getName());
                    continue;
                }
                PDPage page = new PDPage();
                doc.addPage(page);
                try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                    PDImageXObject pdfImage = PDImageXObject.createFromFile(file.getPath(), doc);
                    float desiredWidth = 1200;
                    float desiredHeight = 800;
                    float x = (page.getMediaBox().getWidth() - desiredWidth) / 2;
                    float y = (page.getMediaBox().getHeight() - desiredHeight) / 2;
                    float scale = Math.min(desiredWidth / image.getWidth(), desiredHeight / image.getHeight());
                    float width = image.getWidth() * scale;
                    float height = image.getHeight() * scale;
                    contentStream.drawImage(pdfImage, x, y, width, height);
                }
            }
            String pdfFilePath = "C:\\Users\\Athar\\OneDrive\\Desktop\\sem 4 project final\\Converted files\\" + pdfFileName + ".pdf";
            doc.save(pdfFilePath);
            JOptionPane.showMessageDialog(frame, "PDF file created: " + pdfFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("Error in creating PDF: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error in creating PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
