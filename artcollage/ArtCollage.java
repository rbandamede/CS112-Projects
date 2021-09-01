/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage Flo2.jpeg
 *
 *  @author: Rishika Bandamede rb1173 rb1173@scarletmail.rutgers.edu
 *
 *************************************************************************/

import java.awt.Color;

public class ArtCollage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename) {

        // WRITE YOUR CODE HERE
        this.collageDimension = 4;
        this.tileDimension = 100;
        this.original = new Picture(filename);
        this.collage = new Picture(tileDimension*collageDimension, tileDimension*collageDimension);
        
        //scaling filter, which essentially makes one tile of the collage
        for(int collageCol = 0; collageCol < this.collage.width(); collageCol++){
            for(int collageRow = 0; collageRow < this.collage.height(); collageRow++){
                
                //getting pixels from the original picture
                int originalCol = collageCol * this.original.width() / this.collage.width();
                int originalRow = collageRow * this.original.height() / this.collage.height();
                //getting the actual color from those pixels
                Color color = this.original.get(originalCol, originalRow);
                //setting the collage pixels to the original pixels
                this.collage.set(collageCol, collageRow, color);
                //System.out.println(color);
            }
        }
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename, int td, int cd) {

        // WRITE YOUR CODE HERE
        this.collageDimension = cd;
        this.tileDimension = td;
        this.original = new Picture(filename);
        this.collage = new Picture(tileDimension*collageDimension, tileDimension*collageDimension);
        
        //scaling filter, which essentially makes one tile of the collage
        for(int targetCol = 0; targetCol < this.collage.width(); targetCol++){
            for(int targetRow = 0; targetRow < this.collage.height(); targetRow++){
                int originalCol = targetCol * this.original.width() / this.collage.width();
                int originalRow = targetRow * this.original.height() / this.collage.height();
                Color color = this.original.get(originalCol, originalRow);
                this.collage.set(targetCol, targetRow, color);
                //System.out.println(color);
            }
        }
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {

        // WRITE YOUR CODE HERE
        return this.collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {

        // WRITE YOUR CODE HERE
        return this.tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() {

        // WRITE YOUR CODE HERE
        return this.original;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() {

        // WRITE YOUR CODE HERE
        return this.collage;
    }
    
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {

        // WRITE YOUR CODE HERE
        this.original.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {

        // WRITE YOUR CODE HERE
        this.collage.show();
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        int pixCol = collageCol * this.tileDimension;
        int pixRow = collageRow * this.tileDimension;

        //holding the new image
        Picture newImage = new Picture(filename);
        //will hold the scaled image
        Picture newTile = new Picture(this.tileDimension, this.tileDimension);

        //scale the new image to fit it into the tile size/ fit into the newTile object
        for(int col = 0; col < newTile.width(); col++){
            for(int row = 0; row < newTile.height(); row++){
                int scaledCol = col * newImage.width() / newTile.width();
                int scaledRow = row * newImage.height() / newTile.height();
                //getting the pixels, in other words colors from random tiles to scale it
                Color color = newImage.get(scaledCol, scaledRow);
                newTile.set(col, row, color);

            }
        }
       

        //replacing the tile
        int tileCol = 0; 
        int tileRow = 0;
        for(int row = pixRow; row < pixRow + this.tileDimension; row++){
            for(int col = pixCol; col < pixCol + this.tileDimension; col++){
                //System.out.println(row + " " + col + "\t" + tileRow + " " + tileCol);
                Color color = newTile.get(tileCol++, tileRow);
                this.collage.set(col, row, color);
            }
            tileRow++;
            tileCol = 0;
        }

    }
    
    /*
     * Makes a collage of tiles from original Picture
     * original will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () {

        // WRITE YOUR CODE HERE

        // //scaling filter, which essentially makes one tile of the collage
        //making one tile
        Picture tile = new Picture(this.tileDimension, this.tileDimension);
        for(int collageCol = 0; collageCol < tile.width(); collageCol++){
            for(int collageRow = 0; collageRow < tile.height(); collageRow++){
                
                //getting pixels from the original picture
                int originalCol = collageCol * this.original.width() / this.tileDimension;
                int originalRow = collageRow * this.original.height() / this.tileDimension;
                //getting the actual color from those pixels
                Color color = this.original.get(originalCol, originalRow);
                //setting the tile pixels to the original pixels
                tile.set(collageCol, collageRow, color);
                //System.out.println(color);
            }
        }
        
        int tileRow = 0;
        int tileCol = 0;
        for(int row = 0; row < this.collage.width(); row++){
            if(row % this.tileDimension == 0){
                tileRow = 0;
            }
            for(int c = 0; c < this.collage.height(); c++){
                if(c % this.tileDimension == 0){
                    tileCol = 0;
                }
                Color color = tile.get(tileCol++, tileRow);
                this.collage.set(c, row, color);
            }
            tileRow++;
            tileCol = 0;
        }
        
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see CS111 Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        int pixCol = collageCol * this.tileDimension;
        int pixRow = collageRow * this.tileDimension;
        //separates color 
        for (int col = pixCol; col < pixCol + this.tileDimension; col++) {
            for (int row = pixRow; row < pixRow + this.tileDimension; row++) {
                Color color = this.collage.get(col, row);
                if(component == "red"){
                    int r = color.getRed();
                    this.collage.set(col, row, new Color(r, 0, 0));
                }else if(component == "green"){
                    int g = color.getGreen();
                    this.collage.set(col, row, new Color(0, g, 0));
                }else if(component == "blue"){
                    int b = color.getBlue();
                    this.collage.set(col, row, new Color(0, 0, b));
                }
                
            }
        }

    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     * (see CS111 Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */

    public void grayscaleTile (int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        int pixCol = collageCol * this.tileDimension;
        int pixRow = collageRow * this.tileDimension;
        // convert to grayscale
        for (int col = pixCol; col < pixCol + this.tileDimension; col++) {
            for (int row = pixRow; row < pixRow + this.tileDimension; row++) {
                Color color = this.collage.get(col, row);
                Color gray = Luminance.toGray(color);
                this.collage.set(col, row, gray);
            }
        }
    }


    /*
     *
     *  Test client: use the examples given on the assignment description to test your ArtCollage
     */
    public static void main (String[] args) {

        ArtCollage art = new ArtCollage(args[0],200,3); 
        art.makeCollage();
        art.replaceTile(args[1],1,1);

        // art.replaceTile(args[1], 3, 3);
        //art.colorizeTile("green",0,0);
        //art.replaceTile(args[1], 0, 1);
        //art.colorizeTile("red",2,2);
        //art.colorizeTile("blue",2,1);

        art.showCollagePicture();
        //art.showOriginalPicture();
       
        
        // art.showCollagePicture();
        // //art.showOriginalPicture();

        // Replace 3 tiles 
        // art.makeCollage();
        // art.replaceTile(args[1],0,1);
        // art.replaceTile(args[2],1,0);
        // art.replaceTile(args[3],1,1);
        
        // art.showCollagePicture();
    }
}
