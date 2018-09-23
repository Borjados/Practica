import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelCoche extends JLabel {
	private static final long serialVersionUID = 1L;
	public static final int TAMANYO_COCHE = 100;
	
	public JLabelCoche() {
		try {
			setIcon( new ImageIcon( JLabelCoche.class.getResource( "coche.png" ).toURI().toURL() ) );
		} catch (Exception e) {
			System.err.println( "ERROR: coche.png no encontrado" );
			e.printStackTrace();
		}
		setBounds( 0, 0, TAMANYO_COCHE, TAMANYO_COCHE );
	}
	
	private double miGiro = Math.PI/2;
	public void setGiro( double gradosGiro ) {
		miGiro = gradosGiro/180*Math.PI;
		miGiro = -miGiro;
		miGiro = miGiro + Math.PI/2;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Image img = ((ImageIcon)getIcon()).getImage();
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
        g2.rotate( miGiro, 50, 50 );
        g2.drawImage( img, 0, 0, TAMANYO_COCHE, TAMANYO_COCHE, null );
	}
}
