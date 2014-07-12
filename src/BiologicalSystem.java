import java.awt.Color;
import java.awt.Point;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.*;

//Code by Ray Alfano
//Significant theoretical contributions by Phillip Bryant until 7/15/12 7:49pm

public class BiologicalSystem
{

    // Initialization of experimental tool, occurs before the "self-recognition"
    // portion

    public static Boolean invader1Exists = true;
    public static Boolean invader2Exists = false;
    public static Boolean invader3Exists = false;
    public static Boolean invader4Exists = false;
    public static Boolean invader5Exists = false;
    public static int initialAntibodyCount;
    public static Color initialAntibodyColor;
    public static int minGridY = 0;
    public static int minGridX = 0;
    public static int simulationSpeed;
    public static int crossReactivity;
    public static int antibodyCount;
    public static ConcurrentHashMap<String, CellObject> simulationSpace;
    public static ArrayList<Invader> invaders;
    public static int gridSize;
    public static ArrayList<Invader> allInvasionInfo;
    public static boolean keepRunning = true;
    public static boolean sleep = false;

    // Import the configuration file

    public static void main(String[] args)
    {
        Properties prop = new Properties();
        int waveUnderway = 1;
        try
        {
            prop.load(new FileInputStream("./src/configFile.properties"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        // populate list of x-y coordinates to be reserved such that antibodies
        // do not overwrite invaders and vice versa
        allInvasionInfo = new ArrayList<Invader>();
        // read in the configuration file with parameters

        // System.out.print("Number of self objects: ");
        // System.out.println(prop.getProperty("SELF_OBJECTS"));
        System.out.print("Length of axis of grid: ");
        System.out.println(prop.getProperty("GRID_SIZE"));
        int maxGridY = Integer.parseInt(prop.getProperty("GRID_SIZE"));
        int maxGridX = Integer.parseInt(prop.getProperty("GRID_SIZE"));
        System.out.print("Number of antibodies: ");
        System.out.println(prop.getProperty("ANTIBODY_COUNT"));
        // initialAntibodyCount =
        // Integer.parseInt(prop.getProperty("ANTIBODY_COUNT"));
        System.out.print("Speed of simulation: ");
        System.out.println(prop.getProperty("SIM_SPEED"));
        System.out.print("Cross-reactivity value: ");
        System.out.println(prop.getProperty("CROSS_REACTIVITY"));
        System.out.print("Number antibodies at initialization: ");
        System.out.println(prop.getProperty("ANTIBODY_COUNT"));
        System.out.print("X position of invader 1: ");
        System.out.println(prop.getProperty("INVADER1X"));
        System.out.print("Y position of invader 1: ");
        System.out.println(prop.getProperty("INVADER1Y"));
        System.out.print("RGB values for invader 1: ");
        System.out.println(prop.getProperty("INVADER1R") + "," + prop.getProperty("INVADER1G") + ","
                + prop.getProperty("INVADER1B"));
        Invader invader1 = new Invader(Integer.parseInt(prop.getProperty("INVADER1X")), Integer.parseInt(prop
                .getProperty("INVADER1Y")), Integer.parseInt(prop.getProperty("INVADER1R")), Integer.parseInt(prop
                .getProperty("INVADER1G")), Integer.parseInt(prop.getProperty("INVADER1B")));

        Invader invader2 = null;
        Invader invader3 = null;
        Invader invader4 = null;
        Invader invader5 = null;
        allInvasionInfo.add(invader1);

        if (prop.getProperty("INVADER2_ACTIVE").matches("enabled"))
        {
            System.out.print("X position of invader 2: ");
            System.out.println(prop.getProperty("INVADER2X"));
            System.out.print("Y position of invader 2: ");
            System.out.println(prop.getProperty("INVADER2Y"));
            System.out.print("RGB values for invader 2: ");
            System.out.println(prop.getProperty("INVADER2R") + "," + prop.getProperty("INVADER2G") + ","
                    + prop.getProperty("INVADER2B"));
            invader2 = new Invader(Integer.parseInt(prop.getProperty("INVADER2X")), Integer.parseInt(prop
                    .getProperty("INVADER2Y")), Integer.parseInt(prop.getProperty("INVADER2R")), Integer.parseInt(prop
                    .getProperty("INVADER2G")), Integer.parseInt(prop.getProperty("INVADER2B")));
            invader2Exists = true;
            allInvasionInfo.add(invader2);
        }

        if (prop.getProperty("INVADER3_ACTIVE").matches("enabled"))
        {
            System.out.print("X position of invader 3: ");
            System.out.println(prop.getProperty("INVADER3X"));
            System.out.print("Y position of invader 3: ");
            System.out.println(prop.getProperty("INVADER3Y"));
            System.out.print("RGB values for invader 3: ");
            System.out.println(prop.getProperty("INVADER3R") + "," + prop.getProperty("INVADER3G") + ","
                    + prop.getProperty("INVADER3B"));
            invader3 = new Invader(Integer.parseInt(prop.getProperty("INVADER3X")), Integer.parseInt(prop
                    .getProperty("INVADER3Y")), Integer.parseInt(prop.getProperty("INVADER3R")), Integer.parseInt(prop
                    .getProperty("INVADER3G")), Integer.parseInt(prop.getProperty("INVADER3B")));
            invader3Exists = true;
            allInvasionInfo.add(invader3);

        }

        if (prop.getProperty("INVADER4_ACTIVE").matches("enabled"))
        {
            System.out.print("X position of invader 4: ");
            System.out.println(prop.getProperty("INVADER4X"));
            System.out.print("Y position of invader 4: ");
            System.out.println(prop.getProperty("INVADER4Y"));
            System.out.print("RGB values for invader 4: ");
            System.out.println(prop.getProperty("INVADER4R") + "," + prop.getProperty("INVADER4G") + ","
                    + prop.getProperty("INVADER4B"));
            invader4 = new Invader(Integer.parseInt(prop.getProperty("INVADER4X")), Integer.parseInt(prop
                    .getProperty("INVADER4Y")), Integer.parseInt(prop.getProperty("INVADER4R")), Integer.parseInt(prop
                    .getProperty("INVADER4G")), Integer.parseInt(prop.getProperty("INVADER4B")));
            invader4Exists = true;
            allInvasionInfo.add(invader4);

        }

        if (prop.getProperty("INVADER5_ACTIVE").matches("enabled"))
        {
            System.out.print("X position of invader 5: ");
            System.out.println(prop.getProperty("INVADER5X"));
            System.out.print("Y position of invader 5: ");
            System.out.println(prop.getProperty("INVADER5Y"));
            System.out.print("RGB values for invader 2: ");
            System.out.println(prop.getProperty("INVADER5R") + "," + prop.getProperty("INVADER5G") + ","
                    + prop.getProperty("INVADER5B"));
            invader5 = new Invader(Integer.parseInt(prop.getProperty("INVADER5X")), Integer.parseInt(prop
                    .getProperty("INVADER5Y")), Integer.parseInt(prop.getProperty("INVADER5R")), Integer.parseInt(prop
                    .getProperty("INVADER5G")), Integer.parseInt(prop.getProperty("INVADER5B")));
            invader5Exists = true;
            allInvasionInfo.add(invader5);

        }

        if (prop.getProperty("MANUAL_ANTIBODY1").matches("enabled"))
        {
            // System.out.println(prop.getProperty("MANUAL_ANTIBODY1"));
            System.out.print("X position of manually-added antibody 1: ");
            System.out.println(prop.getProperty("ANTIBODY1X"));
            System.out.print("Y position of manually-added antibody 1: ");
            System.out.println(prop.getProperty("ANTIBODY1Y"));
            Color initialColor1 = new Color(Integer.parseInt("ANTIBODY1R"), Integer.parseInt("ANTIBODY1G"),
                    Integer.parseInt("ANTIBODY1B"));
            CellObject manualAntibody1 = new CellObject(Integer.parseInt(prop.getProperty("ANTIBODY1X")),
                    Integer.parseInt(prop.getProperty("ANTIBODY1Y")), 1, 1, initialColor1);
        }

        if (prop.getProperty("MANUAL_ANTIBODY2").matches("enabled"))
        {
            System.out.print("X position of manually-added antibody 2: ");
            System.out.println(prop.getProperty("ANTIBODY2X"));
            System.out.print("Y position of manually-added antibody 2: ");
            System.out.println(prop.getProperty("ANTIBODY2Y"));
            Color initialColor2 = new Color(Integer.parseInt("ANTIBODY2R"), Integer.parseInt("ANTIBODY2G"),
                    Integer.parseInt("ANTIBODY2B"));
            CellObject manualAntibody2 = new CellObject(Integer.parseInt(prop.getProperty("ANTIBODY2X")),
                    Integer.parseInt(prop.getProperty("ANTIBODY2Y")), 1, 1, initialColor2);

        }

        // Create the hash map to represent a grid and the locations of the
        // antibodies and the invading objects
        gridSize = Integer.parseInt(prop.getProperty("GRID_SIZE"));
        int antibodyStart = Integer.parseInt(prop.getProperty("ANTIBODY_COUNT"));
        int initialSelfR = Integer.parseInt(prop.getProperty("ANTIBODY_R"));
        int initialSelfG = Integer.parseInt(prop.getProperty("ANTIBODY_G"));
        int initialSelfB = Integer.parseInt(prop.getProperty("ANTIBODY_B"));
        Color initialSelfColor = new Color(initialSelfR, initialSelfG, initialSelfB);
        ArrayList<Color> knownFriendlyColors = new ArrayList<Color>();
        knownFriendlyColors.add(initialSelfColor);

        // ConcurrentHashMap<String, CellObject>
        simulationSpace = new ConcurrentHashMap<String, CellObject>();

        // Initialize the arraylist of invader objects here to permit checking
        // against their x/y coords in antibody instantiation
        final double recognitionDistance = 1.5;
        // final ArrayList<Invader> invaders = new ArrayList<Invader>();
        invaders = new ArrayList<Invader>();
        if (invader1Exists) invaders.add(invader1);
        // if (invader2Exists) invaders.add(invader2);
        // if (invader3Exists) invaders.add(invader3);
        // if (invader4Exists) invaders.add(invader4);
        // if (invader5Exists) invaders.add(invader5);

        // create a conceptual representation of the grid
        // char[][] worldState = new char[maxGridX][maxGridY];

        // create the number of antibodies, as evenly distributed as possible
        Random rnd = new Random();
        final int RGB_variance = 10;
        final int antibody_size = 1;
        int i = 0;
        for (i = 0; i < antibodyStart; i++)
        {
            int noiseR = 255;
            int noiseG = 255;
            int noiseB = 255;

            while (noiseR + initialSelfR > 255 || noiseR + initialSelfR < 0)
                noiseR = rnd.nextInt(RGB_variance * 2) - RGB_variance;
            while (noiseG + initialSelfG > 255 || noiseG + initialSelfG < 0)
                noiseG = rnd.nextInt(RGB_variance * 2) - RGB_variance;
            while (noiseB + initialSelfB > 255 || noiseB + initialSelfB < 0)
                noiseB = rnd.nextInt(RGB_variance * 2) - RGB_variance;

            int positionX = rnd.nextInt(maxGridX);
            int positionY = rnd.nextInt(maxGridY);

            // do not allow for overlap of antibodies and the invading cells:
            for (Invader localInv : allInvasionInfo)
            {
                // if(positionX == localInv.intX && positionY == localInv.intY)
                // {
                // if(positionX < maxGridX && positionY < maxGridY){
                // positionX+=1;
                // positionY+=1;
                // }
                // else if(positionX < maxGridX && positionY==maxGridY){
                // positionX+=1;
                // }
                // else if(positionX == maxGridX && positionY==maxGridY){
                // positionX-=1;
                // positionY-=1;
                // }
                // else if(positionX == 0 && positionY==maxGridY){
                // positionX+=1;
                // }
                // else if(positionX == 0 && positionY == 0){
                // positionY+=1;
                // positionX+=1;
                // }
                // else if(positionX == maxGridX && positionY == 0){
                // positionY+=1;
                // }
                // else
                // {
                //
                // }
                // }

                // populating the world state, some redundancy
                // worldState[positionX][positionY] = 'X';
                // worldState[localInv.intX][localInv.intY] = 'o';
            }

            simulationSpace.put("ANTIBODY_ag_" + i, new CellObject(positionX, positionY, antibody_size, antibody_size,
                    new Color(initialSelfR + noiseR, initialSelfG + noiseG, initialSelfB + noiseB)));
            System.out.println("New antibody formed at x: " + positionX + ", y: " + positionY);
        }

        // programmatic display to console of the world state array
        // columns (y) are outer loop to allow for fixed-width row-by-row
        // display
        // for(int y=0; y<maxGridY; y++)
        // {
        //
        // System.out.println("");
        // for(int x=0; x<maxGridX; x++){
        // if( worldState[x][y] == 'X'){
        // System.out.print(" X ");
        // }
        // else if( worldState[x][y] == 'o'){
        // System.out.print(" o ");
        // }
        // else{
        // System.out.print("  ");
        // }
        // }
        // System.out.println("");
        // }

        final ArrayList<CellObject> cloneNextCycle = new ArrayList<CellObject>();
        final ArrayList<Invader> diesNextCycle = new ArrayList<Invader>();
        final ArrayList<Color> knownInvaders = new ArrayList<Color>();

        while (keepRunning)
        {

            // UI display
            try
            {
                if (sleep) Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.err.println("stopped");
                return;
            }
            // end UI display

            // antibodies act
            for (String abn : simulationSpace.keySet())
            {
                final CellObject ab = simulationSpace.get(abn);

                // per invader, if within range, make clone
                for (Invader invader : invaders)
                {
                    final double dist = ab.distanceToInvader(invader);
                    // do not allow local overlap

                    if (dist <= recognitionDistance)
                    {
                        int r = ab.colorValueCurrent.getRed() + (ab.colorValueCurrent.getRed() < invader.R ? 1 : -1);
                        int g = ab.colorValueCurrent.getGreen()
                                + (ab.colorValueCurrent.getGreen() < invader.G ? 1 : -1);
                        int b = ab.colorValueCurrent.getBlue() + (ab.colorValueCurrent.getBlue() < invader.B ? 1 : -1);
                        if (r > 255) r = 255;
                        if (g > 255) g = 255;
                        if (b > 255) b = 255;
                        if (r < 0) r = 0;
                        if (g < 0) g = 0;
                        if (b < 0) b = 0;
                        cloneNextCycle.add(new CellObject(ab.posX, ab.posY, antibody_size, antibody_size, new Color(r,
                                g, b)));
                    }
                    // else if(dist > recognitionDistance*2){
                    // if(invader.intX-ab.posX<=dist){
                    // ab.posX=invader.intX-ab.posX;
                    // }
                    // if(invader.intY-ab.posY<=dist){
                    // ab.posY=invader.intY-ab.posY;
                    // }

                    // give a random color value based on the set of known
                    // memory values
                    // Random colorPos = new Random(1);
                    // int colorPossibility = colorPos.nextInt();
                    //
                    // if(knownFriendlyColors.size()<1){
                    // ab.colorValueCurrent = knownFriendlyColors.get(0);
                    // }
                    //
                    // if(ab.colorValueCurrent==null){
                    // ab.colorValueCurrent=knownFriendlyColors.get(0);
                    // }
                    // if(knownFriendlyColors.get(1) != null){
                    // ab.colorValueCurrent=knownFriendlyColors.get(1);
                    // }
                    // System.out.println("Redundant protection antibody removed, memory cell activated in infection area");
                    // System.out.println("Antibody reforms to combat infection at X: "
                    // + ab.posX + ", Y: " + ab.posY);
                    // System.out.println("New antibody retains memory information for color: "
                    // + ab.colorValueCurrent);
                    // }
                }
            }

            // invaders die if within overwhelmed area
            final int threshold = 10;
            final int hammingThreshold = 30;

            // create a new local arraylist that tracks the antibodies that are
            // effective
            // if the threshold is sufficient to destroy an invading object,
            // select the best antibody and add to repetoire
            // if the threshold is not met, clear the arraylist at the end of
            // the for loop

            // for each invader
            for (Invader invader : invaders)
            {
                int danger = 0;

                // check each hamming distance to a cell if within recognition
                // distance
                for (CellObject ab : simulationSpace.values())
                {
                    if (ab.posX == invader.intX && ab.posY == invader.intY)
                    {
                        switch (rnd.nextInt(4))
                        {
                        case 0:
                            ab.posX--;
                            break;
                        case 1:
                            ab.posX++;
                            break;
                        case 2:
                            ab.posY--;
                            break;
                        case 3:
                            ab.posY++;
                            break;
                        }
                        if (ab.posX < 0) ab.posX = 0;
                        if (ab.posY < 0) ab.posY = 0;
                        if (ab.posX >= gridSize) ab.posX = gridSize - 1;
                        if (ab.posY >= gridSize) ab.posY = gridSize - 1;
                    }
                    
                    if (ab.distanceToInvader(invader) <= recognitionDistance)
                    {
                        int ham = hammingThreshold - ab.hammingDistanceToInvader(invader);
                        if (ham > 0)
                        {
                            danger += ham;
                        }
                        else
                        {
                            int dx = invader.intX - ab.posX;
                            int dy = invader.intY - ab.posY;
                            
                            ab.posX -= Math.signum(dx);
                            ab.posY -= Math.signum(dy);
                            
                            if (ab.posX < 0) ab.posX = 0;
                            if (ab.posY < 0) ab.posY = 0;
                            if (ab.posX >= gridSize) ab.posX = gridSize - 1;
                            if (ab.posY >= gridSize) ab.posY = gridSize - 1;
                        }
                    }
                }

                // if this invader is overwhelmed, die at the end of this cycle.
                if (danger > threshold)
                {
                    diesNextCycle.add(invader);

                    // examine the referenced hashcodes for antibodies at this
                    // juncture
                    // select the top 3 and add them to the repetoire from which
                    // successive antibodies will be created
                    // then clear the arraylist for reuse in the future
//                    Color bestLocalColor = new Color(0, 0, 0);
//                    int bestLocalEffectiveness = 0;
//
//                    for (CellObject effectiveAntibody : locallyEffectiveAbs)
//                    {
//                        int effectivenessAgainstInvader = hammingThreshold
//                                - effectiveAntibody.hammingDistanceToInvader(invader);
//
//                        if (effectivenessAgainstInvader > bestLocalEffectiveness)
//                        {
//                            bestLocalEffectiveness = effectivenessAgainstInvader;
//                            bestLocalColor = effectiveAntibody.colorValueCurrent;
//                        }
//                        else
//                        {
//                            simulationSpace.remove(effectiveAntibody.toString(), effectiveAntibody);
//                        }
//                    }

                    // now that the most effective antibody is known, add that
                    // color to the list of known colors
                    // when antibodies are "retired" after a certain period of
                    // time, they should be replaced by known colors
                    // Given an even distribution of known-color antibodies the
                    // antibodies should be highly effective against repeat
                    // invasions
//                    knownFriendlyColors.add(bestLocalColor);
                }
            }

            // end of cycle, clone all antibodies that wanted to this cycle
            for (CellObject ab : cloneNextCycle)
            {
                simulationSpace.put("ANTIBODY_c_" + i, ab);
                i++;
            }

            // all invaders who died, remove from collection
            for (Invader inv : diesNextCycle)
            {
                System.out.println("invader removal triggered. X: " + inv.intX + ", Y: " + inv.intY);
                invaders.remove(inv);
                System.out.println(i + " cloning steps taken so far.");
            }

            // reset clone/clear lists
            cloneNextCycle.clear();
            diesNextCycle.clear();

            // check if all invaders are dead
            if (invaders.size() == 0)
            {

                Boolean waveInProgress = false;

                if (invader2Exists && waveUnderway == 1)
                {
                    // System.out.println("Invader 1 destroyed. Enter no to terminate or anything else to begin a second wave: ");
                    // Scanner scan = new Scanner(System.in);
                    // if(scan.hasNext("no")){
                    // break;
                    // }
                    // else{
                    invaders.add(invader2);
                    waveUnderway = 2;
                    waveInProgress = true;
                    // }
                }

                if (invader3Exists && waveUnderway == 2 && !waveInProgress)
                {
                    // System.out.println("Invader 2 destroyed. Enter no to terminate or anything else to begin a third wave: ");
                    // Scanner scan = new Scanner(System.in);
                    // if(scan.hasNext("no")){
                    // break;
                    // }
                    // else{
                    invaders.add(invader3);
                    waveUnderway = 3;
                    waveInProgress = true;
                    // }
                }

                if (invader4Exists && waveUnderway == 3 && !waveInProgress)
                {
                    // System.out.println("Invader 3 destroyed. Enter no to terminate or anything else to begin a fourth wave: ");
                    // Scanner scan = new Scanner(System.in);
                    // if(scan.hasNext("no")){
                    // break;
                    // }
                    // else{
                    invaders.add(invader4);
                    waveUnderway = 4;
                    waveInProgress = true;
                    // }
                }

                if (invader5Exists && waveUnderway == 4 && !waveInProgress)
                {
                    // System.out.println("Invader 4 destroyed. Enter no to terminate or anything else to begin a fifth wave: ");
                    // Scanner scan = new Scanner(System.in);
                    // if(scan.hasNext("no")){
                    // break;
                    // }
                    // else{
                    invaders.add(invader5);
                    waveUnderway = 5;
                    waveInProgress = true;
                    // }
                }

                if (waveUnderway == 5 && waveInProgress == false)
                {
                    System.exit(0);
                }

            }
        }
    }

    // code for recalling main method:
    //

}
