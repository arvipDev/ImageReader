import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class IllustReader implements ActionListener {

	private JFrame frame;
	private JButton choose_btn, convert_btn;
	JTextArea browse_text;
	ArrayList<String> pngs = new ArrayList<String>();
	ArrayList<String> pngs_path = new ArrayList<String>();
	String parent_direc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IllustReader window = new IllustReader();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IllustReader() {
		initialize();
	}
	
	/*
	public String testOCR(String path1, String path2) {
		Tesseract tesseract = new Tesseract(); 
		String text = null;
        try { 
  
            tesseract.setDatapath(path1); 
            text 
                = tesseract.doOCR(new File(path2)); 
  
            System.out.print(text); 
        } 
        catch (TesseractException e) { 
            e.printStackTrace(); 
        }
		return text; 
	}
	*/
	
	
	public String testOCR() {
		Tesseract tesseract = new Tesseract(); 
		String text = null ;
        try { 
  
        	 tesseract.setDatapath("D:/Temp/Tess4J-3.4.8-src/Tess4J/tessdata"); 
        	  
             // the path of your tess data folder 
             // inside the extracted file 
             text 
                 = tesseract.doOCR(new File("C:/Users/L027954/Desktop/test.png")); 
   
             // path of your image file 
             System.out.print(text); 
        } 
        catch (TesseractException e) { 
            e.printStackTrace(); 
        }
		return text; 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("PDF to TXT Converter Version 1");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		choose_btn = new JButton("Choose Image");
		choose_btn.setBounds(167, 193, 124, 23);
		desktopPane.add(choose_btn);
		choose_btn.addActionListener(this);
		
		convert_btn = new JButton("Convert");
		convert_btn.setBounds(167, 227, 124, 23);
		desktopPane.add(convert_btn);
		convert_btn.addActionListener(this);
		
		browse_text = new JTextArea();
		browse_text.setBackground(Color.LIGHT_GRAY);
		browse_text.setBounds(10, 11, 414, 171);
		desktopPane.add(browse_text);
	}
	
	private static void writeUsingBufferedWriter(String data, int noOfLines, String abso_path) {
        File file = new File(abso_path);
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine=data+System.getProperty("line.separator");
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = noOfLines; i>0; i--){
                br.write(dataWithNewLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == choose_btn) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Multiple file and directory selection:");
			jfc.setMultiSelectionEnabled(true);
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File[] files = jfc.getSelectedFiles();
				System.out.println("Directories found\n");
				Arrays.asList(files).forEach(x -> {
					if (x.isDirectory()) {
						System.out.println(x.getName());
					}
				});
				System.out.println("\n- - - - - - - - - - -\n");
				System.out.println("Files Found\n");
				Arrays.asList(files).forEach(x -> {
					if (x.isFile()) {
						System.out.println(x.getName());
						pngs.add(x.getName());
						pngs_path.add(x.getAbsolutePath());
						//parent_direc = x.getAbsoluteFile().getParent();
						System.out.println("test " + x.getAbsoluteFile().getParent() + "\n");						
						System.out.println("in list " + pngs.get(pngs.size() - 1));
						System.out.println("in list abso path " + pngs_path.get(pngs.size() - 1));
					}
				});
			}
			/*
			String pdf_names = null;
			for (String pdf_name : pngs) {
				if(pdf_names == null) { pdf_names = pdf_name; }
				else { pdf_names = pdf_names + "\r\n" + pdf_name; }
			} browse_text.setText(pdf_names);
		}if(e.getSource() == convert_btn) {
			for (String png_path : pngs_path) {
				//txt = pdfs.get(counter++);
				//parent_direc = parent_direc + txt.substring(0, txt.length()-3) + "txt";		   
				parent_direc = png_path.substring(0, png_path.length() - 4) + ".txt";
				System.out.println("\n text " + parent_direc);
				
				String text = testOCR(png_path, parent_direc);
				writeUsingBufferedWriter(text, 999, text);
				System.out.println(text);
			}
			*/
		} 
		if (e.getSource() == convert_btn) {
			String data = testOCR();
			writeUsingBufferedWriter(data, 999, "C:/Users/L027954/Desktop/testTXT.txt" );
			//testOCR();
		}
		
	}

}
