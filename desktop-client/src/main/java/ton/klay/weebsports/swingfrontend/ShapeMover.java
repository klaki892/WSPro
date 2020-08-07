/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShapeMover {

    public ShapeMover() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Shape Mover");

        initComponents(frame);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String s[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShapeMover();
            }
        });

    }

    private void initComponents(JFrame frame) {
        frame.getContentPane().add(new DragPanel());
    }
}

class DragPanel extends JPanel {

    Rectangle rect = new Rectangle(0, 0, 100, 50);
    int preX, preY;
    boolean isFirstTime = true;
    Rectangle area;
    boolean pressOut = false;
    private Dimension dim = new Dimension(400, 300);

    public DragPanel() {
        setBackground(Color.white);
        addMouseMotionListener(new MyMouseAdapter());
        addMouseListener(new MyMouseAdapter());
    }

    @Override
    public Dimension getPreferredSize() {
        return dim;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        if (isFirstTime) {
            area = new Rectangle(dim);
            rect.setLocation(50, 50);
            isFirstTime = false;
        }

        g2d.setColor(Color.black);
        g2d.fill(rect);
    }

    boolean checkRect() {
        if (area == null) {
            return false;
        }

        if (area.contains(rect.x, rect.y, rect.getWidth(), rect.getHeight())) {
            return true;
        }

        int new_x = rect.x;
        int new_y = rect.y;

        if ((rect.x + rect.getWidth()) > area.getWidth()) {
            new_x = (int) area.getWidth() - (int) (rect.getWidth() - 1);
        }
        if (rect.x < 0) {
            new_x = -1;
        }
        if ((rect.y + rect.getHeight()) > area.getHeight()) {
            new_y = (int) area.getHeight() - (int) (rect.getHeight() - 1);
        }
        if (rect.y < 0) {
            new_y = -1;
        }
        rect.setLocation(new_x, new_y);
        return false;
    }

    private class MyMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            preX = rect.x - e.getX();
            preY = rect.y - e.getY();

            if (rect.contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = true;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!pressOut) {
                updateLocation(e);
            } else {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (rect.contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = false;
            }
        }

        public void updateLocation(MouseEvent e) {
            rect.setLocation(preX + e.getX(), preY + e.getY());
            checkRect();

            repaint();
        }
    }
}