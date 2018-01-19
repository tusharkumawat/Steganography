package STEGANOGRAPHY;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class steganographyview extends JFrame
{
	
	static String depath;
	public steganographyview() 
	{
		super("STEGANOGRAPHY");
		setSize(500,400);
		setLocationRelativeTo(this);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmEncode = new JMenuItem("Encode");
		mntmEncode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
				new steganographyview().setContentPane(new encode());
			}
		});
		mnFile.add(mntmEncode);
		
		JMenuItem mntmDecode = new JMenuItem("Decode");
		mntmDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				steganographyview.depath=null;
				dispose();
				new steganographyview().setContentPane(new decode());
				FileNameExtensionFilter filter=new FileNameExtensionFilter("Image Files","jpg","png");
				JFileChooser chooser=new JFileChooser("./");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setFileFilter(filter);
				int value=chooser.showOpenDialog(new encode());
				if(value==JFileChooser.APPROVE_OPTION)
				{
					File directory=chooser.getSelectedFile();
					try
					{
						
						depath=directory.getPath();
						
						decode.label.setIcon(new ImageIcon(ImageIO.read(new File(depath))));
											}
					catch(Exception f)
					{
						JOptionPane.showMessageDialog(new decode(), "The file cannot be opened", "Error!",JOptionPane.ERROR_MESSAGE);
					}
				}	
			}
		});
		mnFile.add(mntmDecode);
		mnFile.addSeparator();
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		setContentPane(new encode());
	}
	
	public static void main(String args[])
	{
		steganographyview s=new steganographyview();
		s.setVisible(true);
	}
	}
