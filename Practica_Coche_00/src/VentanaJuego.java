import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;


public class VentanaJuego extends JFrame {
	private static final long serialVersionUID = 1L; 
	JPanel pPrincipal;        
	CocheJuego miCoche;     
	MiRunnable miHilo = null;  	

	public VentanaJuego() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		pPrincipal = new JPanel();
		JPanel pBotonera = new JPanel();
		JButton bAcelerar = new JButton( "Acelerar" );
		JButton bFrenar = new JButton( "Frenar" );
		JButton bGiraIzq = new JButton( "Girar Izquierda" );
		JButton bGiraDer = new JButton( "Girar Derecha" );
		pPrincipal.setLayout( null );
		add( pPrincipal, BorderLayout.CENTER );
		pBotonera.add( bAcelerar );
		pBotonera.add( bFrenar );
		pBotonera.add( bGiraIzq );
		pBotonera.add( bGiraDer );
		add( pBotonera, BorderLayout.SOUTH );
		setSize( 720, 480 );
		bAcelerar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (miCoche.getVelocidad()==0)
					miCoche.acelera( +5 );
				else 
					miCoche.acelera( +5 );
				System.out.println( "Nueva velocidad de coche: " + miCoche.getVelocidad() );
			}
		});
		bFrenar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miCoche.acelera( -5 );
				System.out.println( "Velocidad del coche: " + miCoche.getVelocidad() );
			}
		});
		bGiraIzq.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miCoche.gira( +10 );
				System.out.println( "Dirección del coche: " + miCoche.getDireccionActual() );
			}
		});
		bGiraDer.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miCoche.gira( -10 );
				System.out.println( "Dirección del coche: " + miCoche.getDireccionActual() );
			}
		});
		pPrincipal.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						miCoche.acelera( +5 );
						break;
					}
					case KeyEvent.VK_DOWN: {
						miCoche.acelera( -5 );
						break;
					}
					case KeyEvent.VK_LEFT: {
						miCoche.gira( +10 );
						break;
					}
					case KeyEvent.VK_RIGHT: {
						miCoche.gira( -10 );
						break;
					}
				}
			}
		});
		pPrincipal.setFocusable(true);
		pPrincipal.requestFocus();
		pPrincipal.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pPrincipal.requestFocus();
			}
		});
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (miHilo!=null) miHilo.acaba();
			}
		});
	}
	public void creaCoche( int posX, int posY ) {
		miCoche = new CocheJuego();
		miCoche.setPosicion( posX, posY );
		pPrincipal.add( miCoche.getGrafico() );
	}
	
	public static void main(String[] args) {
		VentanaJuego miVentana = new VentanaJuego();
		miVentana.creaCoche( 150, 100 );
		miVentana.setVisible( true );
		miVentana.miCoche.setPiloto( "Robert" );
		miVentana.miHilo = miVentana.new MiRunnable();
		Thread nuevoHilo = new Thread( miVentana.miHilo );
		nuevoHilo.start();
	}
	
	class MiRunnable implements Runnable {
		boolean sigo = true;
		@Override
		public void run() {
			while (sigo) {
				miCoche.mueve( 0.050 );
				if (miCoche.getPosX() < -JLabelCoche.TAMANYO_COCHE/2 || miCoche.getPosX()>pPrincipal.getWidth()-JLabelCoche.TAMANYO_COCHE/2 ) {
					System.out.println( "Choca X");
					double dir = miCoche.getDireccionActual();
					dir = 180-dir; 
					if (dir < 0) dir = 360+dir; 
					miCoche.setDireccionActual( dir );
				}
				if (miCoche.getPosY() < -JLabelCoche.TAMANYO_COCHE/2 || miCoche.getPosY()>pPrincipal.getHeight()-JLabelCoche.TAMANYO_COCHE/2 ) {
					System.out.println( "Choca Y");
					double dir = miCoche.getDireccionActual();
					dir = 360 - dir;
					miCoche.setDireccionActual( dir );
				}
				try {
					Thread.sleep( 40);
				} catch (Exception e) {
				}
			}
		}
		public void acaba() {
			sigo = false;
		}
	};
	
}
