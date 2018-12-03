package com.team26;

import java.util.Scanner;
import java.text.DecimalFormat;
import java.io.IOException;

public class Main
{
    void menu()
    {
        try
        {
        Scanner kb = new Scanner(System.in);
        System.out.println("Choose option, 1-3"
                + "\n\t1. Browse tracks" + "\n\t2. Browse Genre" + "\n\t3. Cancel");
        byte ch = kb.nextByte();

        if (ch == 1)
            Tracks();
        else if (ch == 2)
            Genre();
        else if (ch == 3)
            System.exit(0);
        else if (ch >= 0 || ch > 3)
        {
            System.out.println("Invalid input");
            menu();
        }

    }

    catch(Exception e)
    {
        System.out.println("Invalid input");
        menu();


    }
}

void Tracks()
{

}

void Genre()
{

}


    public static void main(String[] args)
    {
    Main f = new Main ();
    f.menu();
    }
}