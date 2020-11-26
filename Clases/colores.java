package Clases;

public class colores {
    // TEXTOS DE COLORES
    //colores.ANSI_BLACK + colores.ANSI_WHITE_BACKGROUND
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_ORANGE = "\033[38;2;255;165;0m";
    //\033[48:2:255:165:0m%s\033[m\n

    // COLORES PARA EL FONDO , USAR EL MISMO RESET QUE EL DE TEXTO
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // BOLD NESS
    public static final String ANSI_WHITE_BOLD = "\033[1;37m";
}

// public class colores {
//     // TEXTOS DE COLORES
//     public static final String ANSI_RESET = "";
//     public static final String ANSI_BLACK = "";
//     public static final String ANSI_RED = "";
//     public static final String ANSI_GREEN = "";
//     public static final String ANSI_YELLOW = "";
//     public static final String ANSI_BLUE = "";
//     public static final String ANSI_PURPLE = "";
//     public static final String ANSI_CYAN = "";
//     public static final String ANSI_WHITE = "";
//     public static final String ANSI_ORANGE = "";

//     // COLORES PARA EL FONDO , USAR EL MISMO RESET QUE EL DE TEXTO
//     public static final String ANSI_BLACK_BACKGROUND = "";
//     public static final String ANSI_RED_BACKGROUND = "";
//     public static final String ANSI_GREEN_BACKGROUND = "";
//     public static final String ANSI_YELLOW_BACKGROUND = "";
//     public static final String ANSI_BLUE_BACKGROUND = "";
//     public static final String ANSI_PURPLE_BACKGROUND = "";
//     public static final String ANSI_CYAN_BACKGROUND = "";
//     public static final String ANSI_WHITE_BACKGROUND = "";

//     // BOLD NESS
//     public static final String ANSI_WHITE_BOLD = "";
// }