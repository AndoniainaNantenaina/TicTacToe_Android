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
    int isDiagReverseFound = 4;
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

                if ((tX & 1) == 0) {
                    obj.setColor(Color.GREEN);
                    obj.setTextSize(18);
                    canvas.drawText("X",
                            tabX.get(tX),
                            tabY.get(tX),
                            obj );

                    matrice[tabIndexX.get(tX)][tabIndexY.get(tX)].drawType = "X";

                    if(checkGoalX(indexX, "X")){
                        setVictory(indexX, canvas, Color.GREEN, isXFound);
                        showEndingScreen("Bravo ! Vous avez gagné !");
                        break;
                    }

                    else if(checkGoalY(indexY, "X")){
                        setVictory(indexY, canvas, Color.GREEN, isYFound);
                        showEndingScreen("Bravo ! Vous avez gagné !");
                        break;
                    }
                    else if(indexX == indexY){
                        if (checkGoalDiag(indexX,indexY , "X"))
                        {
                            setVictory(indexX, canvas, Color.GREEN, isDiagFound);
                            showEndingScreen("Bravo ! Vous avez gagné !");
                            break;
                        }
                    }
                    else if(indexX + indexY == CASE_NUMBER-1){
                        if (checkGoalDiagReverse(indexX,indexY , "X"))
                        {
                            setVictory(indexX, canvas, Color.GREEN, isDiagReverseFound);
                            showEndingScreen("Bravo ! Vous avez gagné !");
                            break;
                        }
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

                    if (checkGoalX(tabIndexX.get(tX), "O"))
                    {
                        setVictory(tabIndexX.get(tX), canvas, Color.RED, isXFound);
                        showEndingScreen("Vous avez perdu ! Gagnant : Rouge");
                    }

                    else if(checkGoalY(tabIndexY.get(tX), "O")){
                        setVictory(tabIndexY.get(tX), canvas, Color.RED, isYFound);
                        showEndingScreen("Vous avez perdu ! Gagnant : Rouge");
                    }

                    else if(tabIndexX.get(tX) == tabIndexY.get(tX)){
                        if (checkGoalDiag(tabIndexX.get(tX),tabIndexY.get(tX) , "O"))
                        {
                            setVictory(tabIndexX.get(tX), canvas, Color.RED, isDiagFound);
                            showEndingScreen("Vous avez perdu ! Gagnant : Rouge");
                            break;
                        }
                    }
                    else if(tabIndexX.get(tX) + tabIndexY.get(tX) == CASE_NUMBER-1){
                        if (checkGoalDiagReverse(tabIndexX.get(tX),tabIndexY.get(tX) , "O"))
                        {
                            setVictory(tabIndexX.get(tX), canvas, Color.RED, isDiagReverseFound);
                            showEndingScreen("Vous avez perdu ! Gagnant : Rouge");
                            break;
                        }
                    }
                }
                if(tabX.size() == (CASE_NUMBER * CASE_NUMBER) ){
                    showEndingScreen("MATCH NULL");
                    break;
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

    public boolean checkGoalX(int indexX, String drawType){
        int nbCheckedX = 0;
        //  Itération sur X
        for (int x=0; x < CASE_NUMBER; x++)
        {
            if (matrice[indexX][x].drawType.equals(drawType))
            {
                nbCheckedX++;
            }
        }
        if(nbCheckedX == CASE_NUMBER){
            return true;
        }
        return false;
    }

    public boolean checkGoalY(int indexY, String drawType){
        int nbCheckedY = 0;

        //  Itération sur Y
        for (int y=0; y < CASE_NUMBER; y++)
        {
            if (matrice[y][indexY].drawType.equals(drawType))
            {
                nbCheckedY++;
            }
        }
        if(nbCheckedY == CASE_NUMBER){
            return true;
        }
        return false;
    }

    public boolean checkGoalDiagReverse(int indexX, int indexY, String drawType){
        int nbCheckedDiagReverse = 0;
        for (int x=CASE_NUMBER-1; x >= 0; x--)
        {
            int iY = (CASE_NUMBER - 1) - x;
            if (matrice[x][iY].drawType.equals(drawType))
            {
                nbCheckedDiagReverse++;
            }
        }
        if (nbCheckedDiagReverse == CASE_NUMBER){
            return true;
        }
        return false;
    }

    public boolean checkGoalDiag(int indexX, int indexY, String drawType)
    {
        int nbCheckedDiag = 0;

        for (int x=0; x < CASE_NUMBER; x++)
        {
            if (matrice[x][x].drawType.equals(drawType))
            {
                nbCheckedDiag++;
            }
        }
        if(nbCheckedDiag == CASE_NUMBER){
            return true;
        }
        return false;
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
                        matrice[tX][v].left,
                        matrice[tX][v].top,
                        matrice[tX][v].right,
                        matrice[tX][v].bottom,
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
                        matrice[v][tX].left,
                        matrice[v][tX].top,
                        matrice[v][tX].right,
                        matrice[v][tX].bottom,
                        p
                );
            }
        }
        else if(foundType == isDiagFound)
        {
            for (int v = 0; v < CASE_NUMBER; v++)
            {
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(
                        matrice[v][v].left,
                        matrice[v][v].top,
                        matrice[v][v].right,
                        matrice[v][v].bottom,
                        p
                );
            }
        }
        else if(foundType == isDiagReverseFound)
        {

            for (int v = CASE_NUMBER-1; v >= 0; v--)
            {
                int y = (CASE_NUMBER -1) -v;
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(
                        matrice[v][y].left,
                        matrice[v][y].top,
                        matrice[v][y].right,
                        matrice[v][y].bottom,
                        p
                );
            }
        }
    }
}
