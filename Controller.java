

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//clasa contine elemente generate de SceneBuilder
public class Controller {

    @FXML
    private Button uploadButton; //buton incarcare imagine
    
    @FXML
    private Button saveButton;  //buton salvare imagine finala

    @FXML
    private Button deleteButton; //butonul de reset

    @FXML
    private Button exitButton;  //butonul de iesire din aplicatie

    @FXML
    private ImageView originalImageView; //elementul din panou pe care se va pozitiona imaginea originala

    @FXML
    private ImageView changedImageView; //elementul din panou pe care se va pozitiona imaginea modificata dupa rotire

    @FXML
    private Button rotate_90_Button; //butonul de rotire cu 90 de grade dreapta

    @FXML
    private Button rotate_180_Button;  //butonul de rotire cu 180 de grade

    @FXML
    private Button rotate_270_Button;//butonul de rotire cu 270 de grade

    private BufferedImage originalImage;  //imaginea originala, plasata la stanga 
    private BufferedImage rotatedImage;  //imaginea rotita, plasata la dreapta

    @FXML
    public void buttonUploadAction(ActionEvent event) {
        FileChooser fileChooser = getFileFromFolder();
        File file = fileChooser.showOpenDialog(null);//deschidem fereastra de dialog
        try {
            setImagesInView(file);
        } catch (IOException ex) {  //in cazul in avem probleme cu citirea imaginii se va arunca o exceptie IOException
            // si cu ajutorul blocului try-catch o vom prinde
            ex.printStackTrace();
        }
    }

    @FXML
    public void buttonClearAction(ActionEvent event) {
        changedImageView.setImage(SwingFXUtils.toFXImage(originalImage, null));
        rotatedImage = originalImage; //cand apasam butonul delete vom reveni la imaginea initiala
    }

    @FXML
    public void buttonExitAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close(); //inchidem aplicatia
    }

    @FXML
    public void saveNewImage(ActionEvent event) {

        FileChooser fileChooser = getFileFromFolder();
        fileChooser.setTitle("Save image");
        File file = fileChooser.showSaveDialog(null);
        //se afiseaza fereastra de alegere a  fisierului unde dorim sa salvam noua imagine
        if (file.exists()) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(changedImageView.getImage(), null),
                        "bmp", file);
            } catch (IOException ex) { //folosim din nou blocul try-catch pentru situatia de FileNotFoundException
                // subclasa a IOException
                ex.printStackTrace();
            }
        }
    }

    private FileChooser getFileFromFolder() {
        FileChooser fileChooser = new FileChooser(); //navigam dupa fisier
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.BMP");
        fileChooser.getExtensionFilters().add(extFilterBMP); //setam extensia dorita
        return fileChooser;
    }

    @FXML
    public void rotate_with_90(Event event) {
        //construim un obiect de tip RotationImage cu ajutorul datelor provenite de la imaginea ce se doreste a fi rotita
        RotationImage rotationImage = new RotationImage(rotatedImage.getHeight(), rotatedImage.getWidth(), rotatedImage);
        performRotation(rotationImage.rotate_with_90_degrees());//aplicam rotatia propriu-zisa
    }

    @FXML
    public void rotate_with_180(Event event) {
        RotationImage im = new RotationImage(rotatedImage.getHeight(), rotatedImage.getWidth(), rotatedImage);
        performRotation(im.rotate_with_180_degrees());
    }

    @FXML
    public void rotate_with_270(Event event) {
        RotationImage im = new RotationImage(rotatedImage.getHeight(), rotatedImage.getWidth(), rotatedImage);
        performRotation(im.rotate_with_270_degrees());
    }

    private void performRotation(BufferedImage image) {
        rotatedImage = image;//updatam imaginea sa efectuam cate rotatii dorim
        Image newImage = SwingFXUtils.toFXImage(rotatedImage, null);
        changedImageView.setImage(newImage); //afisare imaginea rotita


    }

    private void setImagesInView(File file) throws IOException {
        this.originalImage = ImageIO.read(file); //citim imaginea
        this.rotatedImage = ImageIO.read(file); //in view-ul imaginii rotite vom plasa imaginea originala la inceput,
        // urmand sa se modifice dupa rotatii
        originalImageView.setImage(SwingFXUtils.toFXImage(originalImage, null));//pozitionam corect imaginile pe suportul corespunzator
        changedImageView.setImage(SwingFXUtils.toFXImage(rotatedImage, null));
    }
}
