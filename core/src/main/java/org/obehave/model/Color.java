package org.obehave.model;

import org.obehave.exceptions.Validate;

/**
 * @author Markus Möslinger
 */
public class Color {
    // memory footprint of this class could be reduced by a factor of 4, if only one int would be used
    // see {@link java.awt.Color} for example

    private final int red;
    private final int green;
    private final int blue;
    private final int opacity;

    public Color(int color) {
        this(color, color, color, 255);
    }

    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    public Color(int red, int green, int blue, int opacity) {
        Validate.isBetween(red, 0, 255);
        Validate.isBetween(green, 0, 255);
        Validate.isBetween(blue, 0, 255);
        Validate.isBetween(opacity, 0, 255);

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getOpacity() {
        return opacity;
    }

    @Override
    public String toString() {
        return String.format("%02X%02X%02X%02X", red, green, blue, opacity);
    }

    public static Color valueOf(String s) {
        if (s.startsWith("#")) {
            s = s.substring(1, s.length());
        }

        // validation is done after removal of an optional hash sign at the beginning
        Validate.isOneOf(s.length(), 6, 8);


        final int red = Integer.parseInt(s.substring(0, 2), 16);
        final int green = Integer.parseInt(s.substring(2, 4), 16);
        final int blue = Integer.parseInt(s.substring(4, 6), 16);
        final int opacity = s.length() == 8 ? Integer.parseInt(s.substring(6, 8), 16) : 255;

        return new Color(red, green, blue, opacity);
    }

}
