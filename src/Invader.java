import java.awt.Color;


//Code by Ray Alfano
//Significant theoretical and practical support from Phillip Bryant until 7/15/12 7:52pm

class Invader
{
    public int intX, intY;
    public int R, G, B;
    public Color colorValueCurrent;
    
    public Invader(int intX, int intY)
    {
        this(intX, intY, 80, 80, 80);
    }

    public Invader(int intX, int intY, int R, int G, int B)
    {
        this.intX = intX;
        this.intY = intY;
        this.R = R;
        this.G = G;
        this.B = B;
        this.colorValueCurrent=new Color(R,G,B);
    }
    
    public int hammingDistanceToInvader(Invader i)
    {
        int r = Math.abs(colorValueCurrent.getRed() - i.R);
        r += Math.abs(colorValueCurrent.getGreen() - i.G);
        r += Math.abs(colorValueCurrent.getBlue() - i.B);
        return r;
    }
    
}
