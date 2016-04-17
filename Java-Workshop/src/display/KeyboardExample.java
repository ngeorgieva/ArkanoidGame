package display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class KeyboardExample extends JPanel {

    public KeyboardExample() {
        KeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        setFocusable(true);
    }


    public class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if ( KeyEvent.getKeyText(e.getKeyCode()).equals("Space")){

            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            KeyEvent.getKeyText(e.getKeyCode());
        }
    }
}