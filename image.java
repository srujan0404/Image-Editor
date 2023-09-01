import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class image {
    // convert to grey
    // function.............................................................
    public static BufferedImage converttogrey(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                outputImage.setRGB(j, i, inputImage.getRGB(j, i));
            }
        }
        return outputImage;
    }

    // increase
    // brightness..................................................................
    public static BufferedImage increasebrightness(BufferedImage inputImage, int increase) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Color pixel = new Color(inputImage.getRGB(x, y));
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();
                g = g + (increase * g / 100);
                b = b + (increase * b / 100);
                r = r + (increase * r / 100);
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
                Color newpixel = new Color(r, g, b);
                outImage.setRGB(x, y, newpixel.getRGB());
            }
        }
        return outImage;
    }

    // decrease
    // brightness................................................................
    public static BufferedImage decreasebrightness(BufferedImage inputImage, int decrease) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Color pixel = new Color(inputImage.getRGB(x, y));
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();
                g = g - (decrease * g / 100);
                b = b - (decrease * b / 100);
                r = r - (decrease * r / 100);
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
                Color newpixel = new Color(r, g, b);
                outImage.setRGB(x, y, newpixel.getRGB());
            }
        }
        return outImage;
    }

    // mirror image horizontally
    public static BufferedImage mirrorHorizontally(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage mirroredImage = new BufferedImage(width, height, inputImage.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int mirroredX = width - x - 1; // Compute the mirrored X-coordinate
                int pixel = inputImage.getRGB(x, y);
                mirroredImage.setRGB(mirroredX, y, pixel);
            }
        }

        return mirroredImage;
    }

    // mirror vertically
    public static BufferedImage mirrorVertically(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage mirroredImage = new BufferedImage(width, height, inputImage.getType());

        for (int y = 0; y < height; y++) {
            int mirroredY = height - y - 1; // Compute the mirrored Y-coordinate
            mirroredImage.setRGB(0, y, inputImage.getRGB(0, mirroredY));
        }

        return mirroredImage;
    }

    // rotate function..........................................................
    public static BufferedImage rotate(BufferedImage inputImage, int degrees) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(degrees), inputImage.getWidth() / 2, inputImage.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage outputImage = new BufferedImage(inputImage.getHeight(), inputImage.getWidth(),
                inputImage.getType());

        op.filter(inputImage, outputImage);

        return outputImage;
    }

    // blurrr function..........................................................
    public static BufferedImage Blur(BufferedImage inputImage, int radius) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage blurredImage = new BufferedImage(width, height, inputImage.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int avgRed = 0;
                int avgGreen = 0;
                int avgBlue = 0;
                int numPixels = 0;

                for (int offsetY = -radius; offsetY <= radius; offsetY++) {
                    for (int offsetX = -radius; offsetX <= radius; offsetX++) {
                        int posX = x + offsetX;
                        int posY = y + offsetY;

                        if (posX >= 0 && posX < width && posY >= 0 && posY < height) {
                            int pixel = inputImage.getRGB(posX, posY);
                            avgRed += (pixel >> 16) & 0xFF;
                            avgGreen += (pixel >> 8) & 0xFF;
                            avgBlue += pixel & 0xFF;
                            numPixels++;
                        }
                    }
                }
                avgRed /= numPixels;
                avgGreen /= numPixels;
                avgBlue /= numPixels;
                int blurredPixel = (avgRed << 16) | (avgGreen << 8) | avgBlue;
                blurredImage.setRGB(x, y, blurredPixel);
            }
        }
        return blurredImage;
    }

    private static void saveImage(BufferedImage image, String outputPath) {
        try {
            File output = new File(outputPath);
            ImageIO.write(image, "jpg", output);
            System.out.println("Image saved as " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // main........................................................................
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

            System.out.println("1. Increase Brightness");
            System.out.println("2. Decrease Brightness");
            System.out.println("3. Grayscale");
            System.out.println("4. Rotate");
            System.out.println("5. Blur");
            System.out.println("6. Mirror Horizontally");
            System.out.println("7. Mirror Vertically");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    image = increasebrightness(image, 10);
                    break;
                case 2:
                    image = decreasebrightness(image, 10);
                    break;
                case 3:
                    image = converttogrey(image);
                    break;
                case 4:
                    image = rotate(image, 90);
                    break;
                case 5:
                    image = Blur(image, 5);
                    break;
                case 6:
                    image = mirrorHorizontally(image);
                    break;
                case 7:
                    image = mirrorVertically(image);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (choice >= 1 && choice <= 7) {
                saveImage(image, outputImagePath);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}