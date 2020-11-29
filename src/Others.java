import java.util.Random;

public class Others {

    /**
     * @return random Letter
     */
    public static char generateRandomLetter() {
        return (char)(new Random().nextInt(26) + 97);
    }
}
