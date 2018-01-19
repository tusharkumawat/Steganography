package STEGANOGRAPHY;

import java.awt.Panel;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.awt.event.ActionEvent;


public class encode extends Panel
{
	
	
	public JTextArea textArea;
	public encode() 
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
	
		setLayout(gridBagLayout);
		
		textArea= new JTextArea();
		GridBagConstraints gbc_pane = new GridBagConstraints();
		gbc_pane.fill = GridBagConstraints.BOTH;
		gbc_pane.anchor=GridBagConstraints.CENTER;
		gbc_pane.insets = new Insets(0, 0, 0, 0);
		gbc_pane.gridx = 0;
		gbc_pane.gridy = 0;
		gbc_pane.gridwidth = 1;
		gbc_pane.gridheight = 1;
		gbc_pane.weightx 	= 1.0; 
		gbc_pane.weighty = 1.0;
		JScrollPane pane=new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		gridBagLayout.setConstraints(pane, gbc_pane);
		pane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
    	
		add(pane);
		JButton btnNewButton = new JButton("ENCODE");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.anchor = GridBagConstraints.CENTER;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		gbc_btnNewButton.gridwidth=1;
		gbc_btnNewButton.gridheight=1;
		gbc_btnNewButton.weightx=1.0;

		
		gridBagLayout.setConstraints(btnNewButton, gbc_btnNewButton);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
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
						String text=textArea.getText();
						
						
						String name=directory.getName();
						String path=directory.getPath();
						
						String opname=JOptionPane.showInputDialog(new encode(), "Enter output file name", "Enter file name", JOptionPane.INFORMATION_MESSAGE);
						int b=encodee(path,opname,text,name);
						path=path.substring(0, path.length()-name.length());
						if(b==1)
						{
							JOptionPane.showMessageDialog(new encode(), "The Image was encoded Successfully!", 
								"Success!", JOptionPane.INFORMATION_MESSAGE);
							
							new steganographyview().setContentPane(new decode());
							
							steganographyview.depath=path+opname+".png";
							decode.label.setIcon(new ImageIcon(ImageIO.read(new File(steganographyview.depath))));
							
						}
						else
						{
							JOptionPane.showMessageDialog(new encode(), "The Image could not be encoded!", 
								"Error!", JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(new encode(), "The File cannot be opened!", 
								"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		
		
	}
	
	private int encodee(String path,String opname,String text,String name)
	{	int flag;
		BufferedImage image=null;
		File file=new File(path);
		try
		{
			image=ImageIO.read(file);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(new encode(), "File cannot be read", "Error!", JOptionPane.ERROR_MESSAGE);
		
		}
		BufferedImage newimage=newimage(image);
		newimage=addtext(newimage,text);
		try
		{
			path=path.substring(0, path.length()-name.length());
			
			ImageIO.write(newimage, "png", new File(path+opname+".png"));
			flag=1;
		}
		catch(Exception f)
		{
			JOptionPane.showMessageDialog(new encode(),	"File could not be saved", "Error!",JOptionPane.ERROR_MESSAGE);
			flag=0;
		}
		return flag;
		
		
	}
	private byte[] bitconversion(int length)
	{
		byte byte3=(byte)((length>>>24) & 0xFF);
		byte byte2=(byte)((length>>>16) & 0xFF);
		byte byte1=(byte)((length>>>8) & 0xFF);
		byte byte0=(byte)((length) & 0xFF);
		return (new byte[]{byte3,byte2,byte1,byte0});
	}
	private byte[] encode_text(byte[] imag,byte[] n,int offset)
	{
		if(n.length+offset>imag.length)
		{
			throw new IllegalArgumentException("Image not long enough!");
		}
		else
		{
			for(int i=0;i<n.length;++i)
			{
				int a=n[i];
				for(int j=7;j>=0;--j,++offset)
				{
					int b=(a>>>j) & 1;
					imag[offset]=(byte)((imag[offset] & 0xFE) | b);
				}
				
			}
		}
		return imag;
	}
	private BufferedImage newimage(BufferedImage image)
	{
		BufferedImage newimage=new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics=newimage.createGraphics();
		graphics.drawRenderedImage(image, null);
		graphics.dispose();
		return newimage;
	}
	private BufferedImage addtext(BufferedImage newimage,String text)
	{
		
		WritableRaster raster=newimage.getRaster();
		DataBufferByte buffer=(DataBufferByte)raster.getDataBuffer();
		byte imag[]=buffer.getData();
		byte msg[]=text.getBytes();
		byte length[]=bitconversion(msg.length);
		try
		{
			encode_text(imag,length,0);
			encode_text(imag,msg,32);
		}
		catch(Exception exx)
		{
			JOptionPane.showMessageDialog(new encode(), "Image cannot hold message", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		return newimage;
	}
	public void textinput(String text)
	{
		textArea.setText(text);
	}
	
}
