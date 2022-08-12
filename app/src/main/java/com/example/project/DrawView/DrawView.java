package com.example.project.DrawView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.project.EndingActivity;
import com.example.project.MainActivity;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Random;

class Case {
    public boolean isDrawed = false;
    public int top;
    public int bottom;
    public int left;
    public int right;
    public int c;

    public String drawType;
    public int centerX;
    public int centerY;

    //  Constructor
    Case(
            //boolean isDrawed,
            int top,
            int bottom,
            int left,
            int right,
            String drawType
        ) {
        //this.isDrawed = isDrawed;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.drawType = drawType;
        this.centerX = left + ((right - left) / 2);
        this.centerY = top + ((bottom - top) / 2 );
    }
}

public class DrawView extends View {

    int CASE_NUMBER;
    int screenWidth;
    int isXFound = 1;
    int isYFound = 2;
    int isDiagFound = 3;
    int isNotFound = 0;
    boolean flag = false;
    Paint paint = new Paint();
    Paint obj = new Paint();

    //  Global indexes
    int indexX = -1;
    int indexY = -1;

    int touchX = -1, touchY = -1;

    ArrayList<Integer> tabX = new ArrayList<Integer>()
            , tabY = new ArrayList<Integer>()
            , tabIndexX = new ArrayList<Integer>()
            , tabIndexY = new ArrayList<Integer>();

    ArrayList<Case> TabCases = new ArrayList<Case>();

    public Case[][] matrice;

    public DrawView(Context context) {
        super(context);
    }
    public DrawView(
            Context context,
            @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       // paint.setColor(Color.rgb(204, 172, 0));
        paint.setColor(Color.rgb(240, 117, 15 ));
        obj.setColor(Color.GREEN);


        int left = 10;
        int initialleft = 10;
        int top = 10;
        int boardsize = CASE_NUMBER;
        int margin = 10;

        if (indexX == -1 && indexY == -1)
        {
            //  Initialiser la taille de la matrice
            matrice = new Case[boardsize][boardsize];
        }

        int x=0;
        // calcul taille du pixel
        int casePixel = ((screenWidth - margin) / CASE_NUMBER) ;

        int bottom = casePixel;
        int right = casePixel;

        //  Boucle de création des cases
        System.out.println("CasePixel: " + casePixel);
        System.out.println("Screen Width: " + screenWidth);
        for (int i=0;i<(boardsize*boardsize);i++){
            while (x< boardsize){
                for (int y=0; y < boardsize; y++){

                    //  Draw Case
                    canvas.drawRect(
                            left,
                            top,
                            right,
                            bottom,
                            paint);

                    if (TabCases.size() < boardsize*boardsize) // boardsize value
                    {
                        //  Instantiation de l'objet Case
                        Case c = new Case(top, bottom, left, right, "");

                        //  Ajouter dans le tableau
                        TabCases.add(c);

                        //  Ajouter le case dans la matrice
                        matrice[x][y] = c;
                    }
                    //paint.setStyle(Paint.Style.STROKE);
                    // paint.setStrokeWidth(20);
                    // paint.setColor(Color.rgb(204, 172, 0));
                    paint.setColor(Color.rgb(240, 117, 15 ));
                    left = left + casePixel;
                    right = right + casePixel;
                }

                x=x+1;
                left = initialleft;
                top = top + casePixel;
                right= casePixel;
                bottom= bottom + casePixel;
            }
        }

        if (touchX != -1 && touchY != -1)
        {
            //  Itération sur les tableaux
            for (int tX = 0; tX < tabX.size(); tX++)
            {
                System.out.println("Tx nombre : "+ tX);
                obj.setStyle(Paint.Style.STROKE);
                obj.setStrokeWidth(15);
                //  if(tabX.size() == (CASE_NUMBER * CASE_NUMBER) ){
                //      showEndingScreen("MATCH NULL");
                //  }
                if ((tX & 1) == 0) {
                    obj.setColor(Color.GREEN);
                    obj.setTextSize(18);
                    canvas.drawText("X",
                            tabX.get(tX),
                            tabY.get(tX),
                            obj );

                    matrice[tabIndexX.get(tX)][tabIndexY.get(tX)].drawType = "X";

                    if (checkGoal(indexX, indexY, "X") == isXFound)
                    {
                        setVictory(tX, canvas, Color.GREEN, isXFound);

                        //  Afficher l'activité final
                        showEndingScreen("Bravo ! Vous avez gagné !");
                        break;
                    }
                    else if (checkGoal(indexX, indexY, "X") == isYFound)
                    {
                        setVictory(tX, canvas, Color.GREEN, isYFound);

                        //  Afficher l'activité final
                        showEndingScreen("Bravo ! Vous avez gagné !");
                        break;
                    }
                    else if (checkGoal(indexX, indexY, "X") == isDiagFound)
                    {
                        showEndingScreen("Bravo ! Vous avez gagné !");
                        break;
                    }
                }
                else
                {
                    obj.setColor(Color.RED);
                    canvas.drawCircle(
                            tabX.get(tX),
                            tabY.get(tX),
                            50,
                            obj
                    );

                    matrice[tabIndexX.get(tX)][tabIndexY.get(tX)].drawType = "O";

                    /*if (checkGoal(indexX, indexY, "O") == isXFound)
                    {
                        setVictory(tX, canvas, Color.RED, isXFound);

                        //  Afficher l'activité final
                        showEndingScreen("Bravo ! Vous avez gagné !");
                        break;
                    }
                    else if (checkGoal(indexX, indexY, "O") == isYFound)
                    {
                        setVictory(tX, canvas, Color.RED, isYFound);

                        //  Afficher l'activité final
                        showEndingScreen("Bravo ! Vous avez gagné !");
                        break;
                    }
                    else if (checkGoal(indexX, indexY, "O") == isDiagFound)
                    {
                        showEndingScreen("Bravo ! Vous avez gagné !");
                        break;
                    }*/
                }

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touchX = (int)event.getX();
            touchY = (int)event.getY();

            System.out.println("TouchX = " + touchX);
            System.out.println("TouchY = " + touchY);

            System.out.println("Nombre de case : " + TabCases.size());

            for (int x=0; x<matrice.length; x++)
            {
                for (int y=0;y< matrice[x].length; y++)
                {
                    if (
                            touchX <= matrice[x][y].right
                            && touchX >= matrice[x][y].left
                            && touchY <= matrice[x][y].bottom
                            && touchY >= matrice[x][y].top
                            && matrice[x][y].drawType.equals("")
                    )
                    {
                        //  Index de la matrice
                        indexX = x;
                        indexY = y;
                        System.out.println("Index X: "+ indexX);
                        System.out.println("Index Y: "+ indexY);
                        matrice[x][y].drawType = "X";

                        tabX.add(matrice[x][y].centerX);
                        tabY.add(matrice[x][y].centerY);
                        tabIndexX.add(indexX);
                        tabIndexY.add(indexY);

                        /*int goal = checkGoal(x, y, "X");
                        if (goal == isXFound)
                        {
                            System.out.println("Victoire X verte !");
                            showEndingScreen("VERT X Found");
                        }
                        else if (goal == isYFound)
                        {
                            System.out.println("Victoire X verte !");
                            showEndingScreen("VERT Y Found");
                        }*/

                        invalidate();
                        flag = true;
                        return true;
                    }
                }
            }

            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            System.out.println(flag);
            if (flag)
            {
                int randomX = -1;
                int randomY = -1;

                randomX = new Random().nextInt(CASE_NUMBER);
                randomY = new Random().nextInt(CASE_NUMBER);
                boolean caseIsEmpty = false;
                while(!caseIsEmpty){
                    randomX = new Random().nextInt(CASE_NUMBER);
                    randomY = new Random().nextInt(CASE_NUMBER);
                    if(matrice[randomX][randomY].drawType.equals("")){
                        tabX.add(matrice[randomX][randomY].centerX);
                        tabY.add(matrice[randomX][randomY].centerY);
                        tabIndexX.add(randomX);
                        tabIndexY.add(randomY);

                        matrice[randomX][randomY].drawType = "O";

                        int goal = checkGoal(randomX, randomY, "O");
                        if (goal == isXFound)
                        {
                            System.out.println("Victoire O rouge !");
                            showEndingScreen("Vous avez perdu !");
                        }
                        else if (goal == isYFound)
                        {
                            System.out.println("Victoire O rouge !");
                            showEndingScreen("Vous avez perdu !");
                        }

                        break;
                    }
                    else{
                        caseIsEmpty = false;
                    }
                }

                System.out.println("RANDOM X => " + randomX);
                System.out.println("RANDOM Y => " + randomY);
                flag = false;
                invalidate();
            }
        }

        return true;
    }

    public int checkGoal(int indexX, int indexY, String drawType)
    {
        int nbCheckedX = 0;
        int nbCheckedY = 0;
        int nbCheckedDiag = 0;
        int nbCheckedDiagReverse = 0;

        if(indexX == indexY){
            for (int x=0; x < CASE_NUMBER; x++)
            {
                if (matrice[x][x].drawType.equals(drawType))
                {
                    nbCheckedDiag++;
                }
            }
        }

        if(indexX + indexY == CASE_NUMBER-1){
            for (int x=CASE_NUMBER-1; x >= 0; x--)
            {
                int iY = (CASE_NUMBER - 1) - x;
                if (matrice[x][iY].drawType.equals(drawType))
                {
                    nbCheckedDiagReverse++;
                }
            }
        }

        //  Itération sur X
        for (int x=0; x < CASE_NUMBER; x++)
        {
            if (matrice[indexX][x].drawType.equals(drawType))
            {
                nbCheckedX++;
            }
        }

        //  Itération sur Y
        for (int y=0; y < CASE_NUMBER; y++)
        {
            if (matrice[y][indexY].drawType.equals(drawType))
            {
                nbCheckedY++;
            }
        }

        if (nbCheckedX == CASE_NUMBER)
        {
            return isXFound;
        }

        if (nbCheckedY == CASE_NUMBER)
        {
            return isYFound;
        }

        if (nbCheckedDiag == CASE_NUMBER)
        {
            return isDiagFound;
        }

        if (nbCheckedDiagReverse == CASE_NUMBER)
        {
            return isDiagFound;
        }

        return isNotFound;
    }

    public void setCASE_NUMBER(int M_CASE_NUMBER)
    {
        this.CASE_NUMBER = M_CASE_NUMBER;
    }
    public void setScreenWidth(int sw)
    {
        this.screenWidth = sw;
    }

    public void showEndingScreen(String userGoalName)
    {
        //  Afficher l'activité final
        Context mContext = getContext();
        Intent endingView = new Intent(mContext, EndingActivity.class);
        endingView.putExtra("goalUsernane", userGoalName);
        mContext.startActivity(endingView);
    }

    public void setVictory(int tX, Canvas canvas, int color, int foundType)
    {
        //System.out.println("Case number = "+ CASE_NUMBER);

        if (foundType == isXFound)
        {
            System.out.println("VICTORY : X FOUND");

            //  Itération pour vérification
            for (int v = 0; v < CASE_NUMBER; v++)
            {
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(
                        matrice[tabIndexY.get(tX)][v].left,
                        matrice[tabIndexY.get(tX)][v].top,
                        matrice[tabIndexY.get(tX)][v].right,
                        matrice[tabIndexY.get(tX)][v].bottom,
                        p
                );
            }
        }
        else if (foundType == isYFound)
        {
            System.out.println("VICTORY : Y FOUND");

            //  Itération pour vérification
            for (int v = 0; v < CASE_NUMBER; v++)
            {
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(
                        matrice[v][tabIndexY.get(tX)].left,
                        matrice[v][tabIndexY.get(tX)].top,
                        matrice[v][tabIndexY.get(tX)].right,
                        matrice[v][tabIndexY.get(tX)].bottom,
                        p
                );
            }
        }
    }
}
