package objectdet.org;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObjectDetectorGUI extends JFrame {
    private JButton chooseImageButton;
    private JLabel imageLabel;
    private ObjectDetector objectDetector;
    private JLabel titleLabel;

    public ObjectDetectorGUI() {
        setTitle("Object Detector");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize ObjectDetector
        objectDetector = new ObjectDetector();

        // Create components
        chooseImageButton = new JButton("Choose Image");
        imageLabel = new JLabel();
        titleLabel = new JLabel("Face Detector", JLabel.CENTER);

        // Set styles for the components
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titleLabel.setForeground(Color.WHITE);

        chooseImageButton.setFont(new Font("Arial", Font.PLAIN, 18));
        chooseImageButton.setFocusPainted(false);
        chooseImageButton.setBackground(Color.DARK_GRAY);
        chooseImageButton.setForeground(Color.BLUE);
        chooseImageButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // Create a panel for the title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(new Color(45, 45, 45)); // Dark background color
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(60, 63, 65)); // Darker background color
        buttonPanel.add(chooseImageButton);

        // Add components to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        // Add action listener to choose image button
        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Images", "jpg", "png", "jpeg");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Get selected file
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    // Call detectAndDisplay method from ObjectDetector
                    Mat resultImage = objectDetector.detectAndDisplay(selectedFilePath);
                    displayImage(resultImage);
                }
            }
        });
    }

    public void displayImage(Mat image) {
        // Convert Mat to BufferedImage
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        ImageIcon icon = new ImageIcon(byteArray);
        Image img = icon.getImage();
        // Resize image to fit JLabel
        Image scaledImage = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        // Update imageLabel
        imageLabel.setIcon(scaledIcon);
    }

    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ObjectDetectorGUI objectDetectorGUI = new ObjectDetectorGUI();
                objectDetectorGUI.setVisible(true);
            }
        });
    }
}
