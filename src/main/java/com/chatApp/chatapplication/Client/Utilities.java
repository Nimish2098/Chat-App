package com.chatApp.chatapplication.Client;

import javax.swing.border.EmptyBorder;
import java.awt.*;


public class Utilities {
    public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
    public static final Color PRIMARY_COLOR = Color.decode("#17313E");   // Dark Blue / Primary
    public static final Color SECONDARY_COLOR = Color.decode("#415E72"); // Blue Gray / Accent
    public static final Color ACCENT_COLOR = Color.decode("#C5B0CD");    // Lavender / Secondary
    public static final Color TEXT_COLOR = Color.decode("#F3E2D4");      // Light Beige / Text

    public static EmptyBorder addPadding(int top, int left, int bottom, int right){
        return new EmptyBorder(top, left, bottom, right);
    }
}
