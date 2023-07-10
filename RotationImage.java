import java.awt.image.BufferedImage;


public class RotationImage {
    private int height;  //latimea imaginii
    private int width;//inaltimea imaginii
    private BufferedImage image;// imaginea

    //constructor cu parametrii
    public RotationImage(int height, int width, BufferedImage image){
        this.height = height;
        this.width = width;
        this.image = image;
    }

    //rotirea cu 90 de grade la dreapta
    public BufferedImage rotate_with_90_degrees() {
        BufferedImage newRotatedImage = new BufferedImage(this.height, this.width,this.image.getType());
        //imaginea ce urmeaza a fi rotita cu 90 de grade
        for(int i=0; i<height; i++ ) {
            for(int j=0; j<width; j++) {
                newRotatedImage.setRGB((height - 1)-i, j, image.getRGB(j, i));
            }
        }
        return newRotatedImage;
    }

    //rotirea cu 180 de grade
    public BufferedImage rotate_with_180_degrees() {
        BufferedImage newRotatedImage = new BufferedImage(this.width, this.height, this.image.getType());
        //imaginea ce urmeaza a fi rotita cu 180 de grade
        for(int i=0; i<height; i++ ) {
            for(int j=0; j<width; j++) {
                newRotatedImage.setRGB(width - 1 - j, height - 1 - i, image.getRGB(j, i));
            }
        }
        return newRotatedImage;
    }
    
   //rotirea cu 270 de grade
    public BufferedImage rotate_with_270_degrees() {
        BufferedImage newRotatedImage = new BufferedImage(this.height, this.width, this.image.getType());
        //imaginea ce urmeaza a fi rotita cu 270 de grade(cu 90 de grade la stanga)
        for(int i=0; i<height; i++ ) {
            for(int j=0; j<width; j++) {
                newRotatedImage.setRGB(i, (width - 1)-j, image.getRGB(j, i)); //o roteste cu 270 de grade
            }
        }
        return newRotatedImage;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
