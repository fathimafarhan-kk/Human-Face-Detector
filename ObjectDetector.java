package objectdet.org;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.Imgproc;
import java.util.List;

public class ObjectDetector {

    private CascadeClassifier faceCascade;

    public ObjectDetector() {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the pre-trained Haar cascade for face detection
        faceCascade = new CascadeClassifier("haarcascade_frontalface_default.xml");
    }

    public Mat detectAndDisplay(String imagePath) {
        // Read input image
        Mat image = Imgcodecs.imread(imagePath);

        // Detect faces in the image
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(image, faces);

        // Convert MatOfRect to list of Rectangles
        List<Rect> facesList = faces.toList();

        // Draw bounding boxes around detected faces
        for (Rect rect : facesList) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }

        // Resize the image to a specific width and height
        Mat resizedImage = new Mat();
        Size size = new Size(1000, 800);
        Imgproc.resize(image, resizedImage, size);

        return resizedImage;
    }
}

