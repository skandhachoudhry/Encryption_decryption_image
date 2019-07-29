package ImageEncryption;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class image
{	
	private static Scanner in;

	public static void main(String[] args) throws IOException
   {	
   			in = new Scanner(System.in);
    	  	double pixelData[][];
 	      
			JFileChooser chooser = new JFileChooser();
			FileFilter filter1 = new ExtensionFileFilter("JPG,JPEG,PNG", new String[] { "JPG", "JPEG", "PNG" });
			chooser.setFileFilter(filter1);			
			

			int val=chooser.showOpenDialog(null);
			
			File f = chooser.getSelectedFile();
			
			if(val==JFileChooser.CANCEL_OPTION ||val==JFileChooser.ERROR_OPTION )
			{
				System.exit(0);
			}
			
			String img = f.getName();
			String path=f.getPath();
			String folder=f.getParent();
			folder=folder+"\\";

			
			BufferedImage cat = ImageIO.read(new File(path));
			imageExtract i=new imageExtract(cat,path,img,folder);
			boolean check =i.checkmatorder();
			
			pixelData=i.convert_img_mat();
			
			System.out.println("Original image is ");
			Runtime runtime=Runtime.getRuntime();
			try 
			{
				Process process=runtime.exec("C:\\Windows\\explorer.exe "+folder+img);
			}
			catch(IOException h)
			{
				h.printStackTrace();
			}


			System.out.println(i.getrow()+"::"+i.getcolumn());
			gaussian g=new gaussian(pixelData,i.getrow(),i.getcolumn());
			long gus_start=System.currentTimeMillis();
			g.init_key(); 
			g.encrypt();
			long gus_end = System.currentTimeMillis();
			System.out.println("Encryption took " + (gus_end - gus_start) + " milliseconds \n");	
			
						
			double U[][]=g.getencryptedmatrix();
			i. gen_encrypted_image(U);
			
			try 
			{
				Process process=runtime.exec("C:\\Windows\\explorer.exe "+folder+"Encrypted_"+img);
			}
			catch(IOException h)
			{
				h.printStackTrace();
			}
			
			System.out.println("DO YOU WISH TO DECRYPT THE ENCRYPTED IMAGE?::Yes/No");
			String decide_decode=in.next();	
			if(decide_decode.toLowerCase().equals("yes"))
			{
				long strgrey=System.currentTimeMillis();
				double V[][]=g.decrypt();
				System.out.println("\nDecryption successful..Image saved..");
				long endgrey = System.currentTimeMillis();	
				System.out.println("Decryption took " + (endgrey - strgrey) + " milliseconds");
				i.gen_decrypted_image(V);			
				try 
				{
					Process process=runtime.exec("C:\\Windows\\explorer.exe "+folder+"Decrypted_"+img);
				}
				catch(IOException h)
				{
					h.printStackTrace();
				}
		   }	
			else
			{
				System.exit(0);
			}	
	}
}
