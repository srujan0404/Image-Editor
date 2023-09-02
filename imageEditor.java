import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class imageEditor {
    /**
     * This method converts a given color BufferedImage to grayscale.
     *
     * @param inputImage The input color BufferedImage to convert to grayscale.
     * @return A grayscale BufferedImage representing the input image.
     */
    public static BufferedImage convertToGray(BufferedImage inputImage) {
        // Get the height and width of the input image.
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        // Create a new BufferedImage with grayscale type.
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Iterate through each pixel of the input image.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Get the RGB color value of the pixel from the input image.
                int rgb = inputImage.getRGB(j, i);

                // Set the grayscale value to the output image at the same location.
                outputImage.setRGB(j, i, rgb);
            }
        }

        // Return the grayscale BufferedImage.
        return outputImage;
    }

    /**
     * This method increases the brightness of a given BufferedImage.
     *
     * @param inputImage The input BufferedImage.
     * @param increase   The percentage increase in brightness (0 to 100).
     * @return A new BufferedImage with increased brightness.
     */
    public static BufferedImage increaseBrightness(BufferedImage inputImage, int increase) {
        // Get the height and width of the input image.
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        // Create a new BufferedImage with 3-byte BGR type.
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        // Iterate through each pixel of the input image.
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                // Get the color of the current pixel in the input image.
                Color pixel = new Color(inputImage.getRGB(x, y));

                // Extract the red, green, and blue components.
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                // Increase the brightness based on the given percentage.
                g = g + (increase * g / 100);
                b = b + (increase * b / 100);
                r = r + (increase * r / 100);

                // Ensure that the color values stay within the valid range [0, 255].
                if (r > 255)
                    r = 255;
                if (b > 255)
                    b = 255;
                if (g > 255)
                    g = 255;
                if (r < 0)
                    r = 0;
                if (b < 0)
                    b = 0;
                if (g < 0)
                    g = 0;

                // Create a new Color object with the adjusted values.
                Color newPixel = new Color(r, g, b);

                // Set the new color in the output image.
                outImage.setRGB(x, y, newPixel.getRGB());
            }
        }

        // Return the BufferedImage with increased brightness.
        return outImage;
    }

    /**
     * This method decreases the brightness of a given BufferedImage.
     *
     * @param inputImage The input BufferedImage.
     * @param decrease   The percentage decrease in brightness (0 to 100).
     * @return A new BufferedImage with decreased brightness.
     */
    public static BufferedImage decreaseBrightness(BufferedImage inputImage, int decrease) {
        // Get the height and width of the input image.
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        // Create a new BufferedImage with 3-byte BGR type.
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        // Iterate through each pixel of the input image.
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                // Get the color of the current pixel in the input image.
                Color pixel = new Color(inputImage.getRGB(x, y));

                // Extract the red, green, and blue components.
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                // Decrease the brightness based on the given percentage.
                g = g - (decrease * g / 100);
                b = b - (decrease * b / 100);
                r = r - (decrease * r / 100);

                // Ensure that the color values stay within the valid range [0, 255].
                if (r > 255)
                    r = 255;
                if (b > 255)
                    b = 255;
                if (g > 255)
                    g = 255;
                if (r < 0)
                    r = 0;
                if (b < 0)
                    b = 0;
                if (g < 0)
                    g = 0;

                // Create a new Color object with the adjusted values.
                Color newPixel = new Color(r, g, b);

                // Set the new color in the output image.
                outImage.setRGB(x, y, newPixel.getRGB());
            }
        }

        // Return the BufferedImage with decreased brightness.
        return outImage;
    }

    /**
     * This method horizontally mirrors a given BufferedImage.
     *
     * @param inputImage The input BufferedImage to be mirrored horizontally.
     * @return A new BufferedImage with horizontal mirroring applied.
     */
    public static BufferedImage mirrorHorizontally(BufferedImage inputImage) {
        // Get the width and height of the input image.
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage with the same type as the input image.
        BufferedImage mirroredImage = new BufferedImage(width, height, inputImage.getType());

        // Iterate through each pixel in the input image.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compute the mirrored X-coordinate.
                int mirroredX = width - x - 1;

                // Get the color of the current pixel in the input image.
                int pixel = inputImage.getRGB(x, y);

                // Set the pixel at the mirrored position in the output image.
                mirroredImage.setRGB(mirroredX, y, pixel);
            }
        }

        // Return the horizontally mirrored BufferedImage.
        return mirroredImage;
    }

    /**
     * Mirrors a given BufferedImage vertically.
     * This method creates a new BufferedImage with the same type as the input
     * image,
     * then copies the pixel colors from the input image to the output image with a
     * vertical flip.
     *
     * @param inputImage The input BufferedImage to be vertically mirrored.
     * @return A new BufferedImage with vertical mirroring applied.
     */
    public static BufferedImage mirrorVertically(BufferedImage inputImage) {
        // Get the width and height of the input image.
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage with the same type as the input image.
        BufferedImage mirroredImage = new BufferedImage(width, height, inputImage.getType());

        // Iterate through each row of pixels in the input image.
        for (int y = 0; y < height; y++) {
            // Compute the mirrored Y-coordinate.
            int mirroredY = height - y - 1;

            // Iterate through each column of pixels in the row.
            for (int x = 0; x < width; x++) {
                // Get the color of the pixel from the mirrored row in the input image.
                int pixel = inputImage.getRGB(x, mirroredY);

                // Set the pixel at the same X and Y coordinates in the output image.
                mirroredImage.setRGB(x, y, pixel);
            }
        }

        // Return the vertically mirrored BufferedImage.
        return mirroredImage;
    }

    /**
     * This method rotates a given BufferedImage by a specified angle in degrees.
     *
     * @param inputImage The input BufferedImage to be rotated.
     * @param degrees    The angle in degrees by which to rotate the image.
     * @return A new BufferedImage with the specified rotation applied.
     */
    public static BufferedImage rotateclockwise(BufferedImage inputImage, int degrees) {
        // Create an AffineTransform to perform the rotation.
        AffineTransform transform = new AffineTransform();

        // Calculate the rotation center as the image's center.
        double centerX = inputImage.getWidth() / 2.0;
        double centerY = inputImage.getHeight() / 2.0;

        // Set the rotation angle in radians.
        double radians = Math.toRadians(degrees);

        // Apply the rotation to the transform.
        transform.rotate(radians, centerX, centerY);

        // Create an AffineTransformOp with bilinear interpolation.
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        // Create a new BufferedImage with swapped width and height.
        BufferedImage outputImage = new BufferedImage(inputImage.getHeight(), inputImage.getWidth(),
                inputImage.getType());

        // Apply the rotation to the input image and store the result in the output
        // image.
        op.filter(inputImage, outputImage);

        // Return the rotated BufferedImage.
        return outputImage;
    }

    /**
     * This method rotates a given BufferedImage by a specified angle in degrees.
     *
     * @param inputImage The input BufferedImage to be rotated.
     * @param degrees    The angle in degrees by which to rotate the image.
     * @return A new BufferedImage with the specified rotation applied.
     */
    public static BufferedImage rotateanticlockwise(BufferedImage inputImage, int degrees) {
        // Create an AffineTransform to perform the rotation.
        AffineTransform transform = new AffineTransform();

        // Calculate the rotation center as the image's center.
        double centerX = inputImage.getWidth() / 2.0;
        double centerY = inputImage.getHeight() / 2.0;

        // Set the rotation angle in radians.
        double radians = Math.toRadians(degrees);

        // Apply the rotation to the transform.
        transform.rotate(radians, centerX, centerY);

        // Create an AffineTransformOp with bilinear interpolation.
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        // Create a new BufferedImage with swapped width and height.
        BufferedImage outputImage = new BufferedImage(inputImage.getHeight(), inputImage.getWidth(),
                inputImage.getType());

        // Apply the rotation to the input image and store the result in the output
        // image.
        op.filter(inputImage, outputImage);

        // Return the rotated BufferedImage.
        return outputImage;
    }

    /**
     * This method applies a blur effect to a given BufferedImage with a specified
     * radius.
     *
     * @param inputImage The input BufferedImage to be blurred.
     * @param radius     The radius of the blur effect (determines the blurriness
     *                   level).
     * @return A new BufferedImage with the blur effect applied.
     */
    public static BufferedImage blur(BufferedImage inputImage, int radius) {
        // Get the width and height of the input image.
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create a new BufferedImage with the same type as the input image.
        BufferedImage blurredImage = new BufferedImage(width, height, inputImage.getType());

        // Iterate through each pixel in the input image.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int avgRed = 0;
                int avgGreen = 0;
                int avgBlue = 0;
                int numPixels = 0;

                // Iterate over a square neighborhood with the given radius.
                for (int offsetY = -radius; offsetY <= radius; offsetY++) {
                    for (int offsetX = -radius; offsetX <= radius; offsetX++) {
                        int posX = x + offsetX;
                        int posY = y + offsetY;

                        // Check if the neighbor pixel is within bounds.
                        if (posX >= 0 && posX < width && posY >= 0 && posY < height) {
                            int pixel = inputImage.getRGB(posX, posY);
                            avgRed += (pixel >> 16) & 0xFF;
                            avgGreen += (pixel >> 8) & 0xFF;
                            avgBlue += pixel & 0xFF;
                            numPixels++;
                        }
                    }
                }

                // Calculate the average color values for the neighborhood.
                avgRed /= numPixels;
                avgGreen /= numPixels;
                avgBlue /= numPixels;

                // Create a new pixel with the average color values.
                int blurredPixel = (avgRed << 16) | (avgGreen << 8) | avgBlue;

                // Set the blurred pixel in the output image.
                blurredImage.setRGB(x, y, blurredPixel);
            }
        }

        // Return the BufferedImage with the blur effect applied.
        return blurredImage;
    }

    /**
     * This method saves a BufferedImage to a specified file path.
     *
     * @param image      The BufferedImage to be saved.
     * @param outputPath The file path where the image should be saved.
     */
    private static void saveImage(BufferedImage image, String outputPath) {
        try {
            // Create a File object for the output path.
            File output = new File(outputPath);

            // Write the image to the specified file path in JPG format.
            ImageIO.write(image, "jpg", output);

            // Print a message indicating that the image has been saved.
            System.out.println("Image saved as " + outputPath);
        } catch (IOException e) {
            // If an error occurs during saving, print the exception stack trace.
            e.printStackTrace();
        }
    }

    /**
     * This is the main method for image manipulation.
     * It allows the user to load an image, choose an operation, and save the
     * result.
     * 
     * @author Srujan Reddy Dharma
     * @param args The command-line arguments provided by the user (not directly
     *             used for this application).
     *
     */
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            // Input and output file paths
            System.out.print("Enter the path of the input image: ");
            String inputImagePath = scanner.nextLine();
            BufferedImage image = ImageIO.read(new File(inputImagePath));

            if (image == null) {
                System.out.println("Could not read the input image.");
                return;
            }

            System.out.print("Enter the path for the output image: ");
            String outputImagePath = scanner.nextLine();

            System.out.println("Image loaded successfully!");

            // Display menu of image manipulation options
            System.out.println("1. Increase Brightness");
            System.out.println("2. Decrease Brightness");
            System.out.println("3. Grayscale");
            System.out.println("4. Rotate Clockwise");
            System.out.println("5. Rotate Anticlockwise"); // New option for anticlockwise rotation
            System.out.println("6. Blur");
            System.out.println("7. Mirror Horizontally");
            System.out.println("8. Mirror Vertically");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    image = increaseBrightness(image, 10);
                    break;
                case 2:
                    image = decreaseBrightness(image, 10);
                    break;
                case 3:
                    image = convertToGray(image);
                    break;
                case 4:
                    image = rotateclockwise(image, 90);
                    break;
                case 5:
                    image = rotateanticlockwise(image, -90); // Rotate Anticlockwise option
                    break;
                case 6:
                    image = blur(image, 5);
                    break;
                case 7:
                    image = mirrorHorizontally(image);
                    break;
                case 8:
                    image = mirrorVertically(image);
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            // Save the modified image if the choice is valid
            if (choice >= 1 && choice <= 8) { // Adjust the range to include the new option
                saveImage(image, outputImagePath);
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}