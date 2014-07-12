
//Code by Ray Alfano
//Significant theoretical contributions by Phillip Bryant until 7/15/12 7:49pm

import java.awt.Color;

class CellObject
{
	public int posX, posY, width, height, age;
//	public Color colorValueInitial;
    public Color colorValueCurrent;
	public double affinity;
	private int representation;
	
	public int hammingDistanceToInvader(Invader i)
	{
	    int r = Math.abs(colorValueCurrent.getRed() - i.colorValueCurrent.getRed());
	    r += Math.abs(colorValueCurrent.getGreen() - i.colorValueCurrent.getGreen());
	    r += Math.abs(colorValueCurrent.getBlue() - i.colorValueCurrent.getBlue());
	    return r;
	}
	
	public void destroy()
	{
		BiologicalSystem.simulationSpace.remove(this.toString(), this);
	}
	
	//pythagorean theorem - distance formula
	public double distanceToInvader(Invader i)
	{
	    return Math.sqrt(Math.pow(this.posX - i.intX, 2) + Math.pow(this.posY - i.intY, 2));
	}

	/**
	* Creates a cell of specified shape and position.
	* @param posX The x value at which the cell is placed.
	* @param posY The y value at which the cell is placed.
	* @param width The width of the cell.
	* @param height The height of the cell.
	*/
	public CellObject(int posX, int posY, int width, int height, Color colorValueInitial)
	{
		this.posX = posX;
		this.posY = posY;
		//in my implementation width and height are 1 to create squares
		this.width = width;
		this.height = height;
		//this.colorValueInitial = colorValueInitial;
		this.colorValueCurrent = colorValueInitial;
		this.age=0;
		representation = 0;
		affinity = 0;
	}

	

}

