import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;

//Code by Ray Alfano
//Significant theoretical contributions by Phillip Bryant until 7/15/12 7:49pm
//Also comparisons of relative success in implementations with code comparison for reference

public class BiologicalSystemUI extends JFrame implements Runnable
{
    private static final long serialVersionUID = -8096743205112918191L;

    @Override
    public void paint(Graphics g)
    {
        if (BiologicalSystem.invaders == null || BiologicalSystem.simulationSpace == null
                || BiologicalSystem.gridSize == 0)
        {
            return;
        }

        final int gridSize = BiologicalSystem.gridSize;
        final int drawSize = (this.getWidth() < this.getHeight() ? this.getWidth() : this.getHeight()) / gridSize;
        final int drawOffset = drawSize / 4;
        final Collection<CellObject> antibodies = BiologicalSystem.simulationSpace.values();
        final List<Invader> invaders = BiologicalSystem.invaders;
        
        int[][] cellCount = new int[gridSize][gridSize];

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Invader invader : invaders)
        {
            g.setColor(invader.colorValueCurrent);
            g.drawOval(invader.intX * drawSize, invader.intY * drawSize, drawSize, drawSize);
            g.fillOval(invader.intX * drawSize, invader.intY * drawSize, drawSize, drawSize);
        }
        
        for (CellObject ab : antibodies)
        {
            g.setColor(ab.colorValueCurrent);
            g.fillOval(ab.posX * drawSize + drawSize/4, ab.posY * drawSize + drawSize/4, drawSize/2, drawSize/2);
            cellCount[ab.posX][ab.posY]++; 
        }
        
        g.setColor(Color.BLACK);
        for (int x = 0; x < gridSize; x++)
        {
            for (int y = 0; y < gridSize; y++)
            {
                final String countStr = "" + cellCount[x][y];
                g.drawChars(countStr.toCharArray(), 0, countStr.length(), x*drawSize + drawOffset, y*drawSize + drawOffset);
            }
        }
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException ie)
            {
                break;
            }
            this.repaint();
        }

        this.repaint();
    }

    @Override
    public void dispose()
    {
        this.system.interrupt();
        super.dispose();
    }

    private Thread system = new Thread(new BiologicalSystemRunner());
    private Thread display = new Thread(this);

    public BiologicalSystemUI()
    {
        this.setSize(1000, 1000);
        this.setVisible(true);
        this.display.start();
        this.system.start();
    }

    public static void main(String[] args)
    {
        final BiologicalSystemUI ui = new BiologicalSystemUI();
    }

    public class BiologicalSystemRunner implements Runnable
    {
        @Override
        public void run()
        {
            BiologicalSystem.sleep = true;
            BiologicalSystem.main(new String[0]);
        }
    }
}
