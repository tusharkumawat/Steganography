package STEGANOGRAPHY;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class decode extends Panel
{
	
	
	static JLabel label;
	public decode() 
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		
		setLayout(gridBagLayout);
		
		label = new JLabel();
		GridBagConstraints gbc_pane = new GridBagConstraints();
		gbc_pane.fill = GridBagConstraints.BOTH;
		gbc_pane.insets = new Insets(0, 0, 0, 0);
		gbc_pane.gridx = 0;
		gbc_pane.gridy = 0;
		gbc_pane.gridwidth = 1;
		gbc_pane.gridheight = 1;
		gbc_pane.weightx 	= 1.0; 
		gbc_pane.weighty = 1.0;
		JScrollPane pane=new JScrollPane(label,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		gridBagLayout.setConstraints(pane, gbc_pane);
		pane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		add(pane);
		JButton btnNewButton = new JButton("DECODE");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		add(btnNewButton, gbc_btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) 
			{
				if(steganographyview.depath==null)
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
							
							steganographyview.depath=directory.getPath();
							
							decode.label.setIcon(new ImageIcon(ImageIO.read(new File(steganographyview.depath))));
						}
						catch(Exception f)
						{
							JOptionPane.showMessageDialog(new decode(), "The file cannot be opened", "Error!",JOptionPane.ERROR_MESSAGE);
						}
					}	
				    try
				    {
						String texta=decodee(steganographyview.depath);
						JOptionPane.showMessageDialog(new encode(), "Image decoded!!","Success",JOptionPane.INFORMATION_MESSAGE);
						encode e=new encode();
				
						new steganographyview().setContentPane(e);
				
						e.textinput(texta);
				    }
				    catch(Exception e)
				    {
					
				    }
				}
				else
				{
					String texta=decodee(steganographyview.depath);
					JOptionPane.showMessageDialog(new encode(), "Image decoded!!","Success",JOptionPane.INFORMATION_MESSAGE);
					encode e=new encode();
			
					new steganographyview().setContentPane(e);
			
					e.textinput(texta);
			
				}
			}
				
		});
		
		
		
	}
	private String decodee(String path)
	{
		BufferedImage image=null;
		File file=new File(path);
		try
		{
			image=ImageIO.read(file);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(new decode(), "Image cannot be opened", "Error!", JOptionPane.ERROR_MESSAGE);
			
		}
		WritableRaster raster=image.getRaster();
		DataBufferByte buffer=(DataBufferByte)raster.getDataBuffer();
		byte imag[]=buffer.getData();
		int len=0;
		int offset=32;
		
		for(int i=0;i<32;i++)
		{
			len=(len<<1) | (imag[i] & 1);
		}
		byte[] text=new byte[len];
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<8;j++,offset++)
			{
				text[i]=(byte)((text[i]<<1) | (imag[offset] & 1));
			}
		}
		String data=new String(text);
		
		return (data);
	}

}
