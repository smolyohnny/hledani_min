import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MinyForm extends JFrame {

    int publicSize = 8;
    JButton[][] buttonArray = new JButton[publicSize][publicSize];
    boolean[][] isBomb = new boolean[publicSize][publicSize];
    boolean[][] wasPressed = new boolean[publicSize][publicSize];

    int winCount = 0;
    public MinyForm() {         //IMP!!! ŘÁDKY JSOU SLOUPCE A NAOPAK, NECHCE SE MI TO PŘEDĚLÁVAT
        int size = publicSize;
        InitComponents();
        Container con = getContentPane();
        GridBagLayout gbLayout = new GridBagLayout();
        setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        int bombCount = 0;
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttonArray[row][col] = new JButton("   ");
                buttonArray[row][col].setBackground(Color.gray);
                gbc.gridx = row;
                gbc.gridy = col;
                gbc.ipadx = 10;
                gbc.ipady = 30;
                con.add(buttonArray[row][col], gbc);
                // buttonArray[row][col].addActionListener(this);
                while (bombCount < (publicSize*2)-1) {
                    for (int bRow = 0; bRow < size; bRow++) {
                        for (int bCol = 0; bCol < size; bCol++) {
                            Random random = new Random();
                            int isItABomb = random.nextInt(4);
                            if (isItABomb == 3 && bombCount < publicSize * 2) {
                                isBomb[bRow][bCol] = true;
                                bombCount++;
                            }
                        }
                    }
                }

                buttonArray[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        for (int row = 0; row < size; row++) {
                            for (int col = 0; col < size; col++) {
                                if (e.getSource() == buttonArray[row][col]) {
                                    if (SwingUtilities.isLeftMouseButton(e)) {
                                        if (isBomb[row][col]) {
                                            buttonArray[row][col].setBackground(Color.red);
                                            break;
                                        }
                                        wasPressed[row][col] = true;
                                        winCount++;
                                        if (winCount == (publicSize*publicSize) - (2*publicSize) + 1) {
                                            buttonArray[row][col].setBackground(Color.MAGENTA);
                                            break;
                                        }
                                        buttonArray[row][col].setBackground(Color.green);
                                        buttonArray[row][col].setText(Integer.toString(neighbourDiscovery(row, col)));
                                    }
                                    if (SwingUtilities.isRightMouseButton(e) && !wasPressed[row][col]) {
                                        buttonArray[row][col].setBackground(Color.blue);
                                    }
                                }
                            }
                        }
                    }
                });
                SwingUtilities.updateComponentTreeUI(this);
            }
        }
    }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int neighbourDiscovery(int row, int col) {
        int bombCount = 0;
        if(row != 0 && col != 0) {
            if (isBomb[row-1][col-1]) bombCount++;
        }
        if(row !=0) {
            if (isBomb[row-1][col+0]) bombCount++;
        }
        if(col != 0) {
            if (isBomb[row+0][col-1]) bombCount++;
        }
        if (row != 0 && col != publicSize-1) {
            if (isBomb[row-1][col+1]) bombCount++;
        }
        if(col != publicSize-1) {
            if (isBomb[row+0][col+1]) bombCount++;
        }
        if(row != publicSize-1 && col != 0) {
            if (isBomb[row+1][col-1]) bombCount++;
        }
        if (row != publicSize-1) {
            if (isBomb[row+1][col+0]) bombCount++;
        }
        if (row != publicSize-1 && col != publicSize-1) {
            if (isBomb[row+1][col+1]) bombCount++;
        }


        return bombCount;
    }

    public void InitComponents() {
        setVisible(true);
        setTitle("Hledání min");
        setBackground(Color.lightGray);
        setMinimumSize(new Dimension(600,600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


    public static void main(String[] args) {
        new MinyForm();
    }



}
