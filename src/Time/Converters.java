package Time;

public class Converters {

    /**
     * Convert entrance value of Hours to Minutes
     * @param value int of hours
     * @return int value of minutes
     */
    public static int hourToMinute(int value) {
        return value * 60;
    }

    /**
     * Convert entrance value of Hours to Seconds
     * @param value int of hours
     * @return int value of seconds
     */
    public static int hourToSecond(int value) {
        return (value * 60) * 60;
    }

    /**
     * Convert entrace value of Hours to Milliseconds
     * @param value int of hours
     * @return int value of Milliseconds
     */
    public static int hourToMillisecond(int value) {
        return value * 3600000;
    }

    /**
     * Convert entrace value of Minutes to Seconds
     * @param value int of Minutes
     * @return int value of Seconds
     */
    public static int minuteToSecond(int value) {
        return value * 60;
    }

    /**
     * Convert entrace value of Minutes to Milliseconds
     * @param value int of Minutes
     * @return int value of Milliseconds
     */
    public static int minuteToMilliseconds(int value) {
        return value * 60000;
    }

    /**
     * Convert entrace value of Minutes to Hours
     * @param value int of Minutes
     * @return int value of Hours
     */
    public static int minuteToHour(int value) {
        return value / 60;
    }

    /**
     * Convert entrace value of Seconds to Milliseconds
     * @param value int of Seconds
     * @return int value of Milliseconds
     */
    public static int secondToMilliseconds(int value) {
        return value * 1000;
    }

    /**
     * Convert entrance value of second to Minute
     * @param value int of Seconds
     * @return int value of Minutes
     */
    public static int secondToMinute(int value) {
        return value / 60;
    }

    /**
     * Convert entrace value of Second to Hour
     * @param value int value of Seconds
     * @return int value of Hours
     */
    public static int secondToHour(int value) {
        return secondToMinute(value) / 60;
    }


}
