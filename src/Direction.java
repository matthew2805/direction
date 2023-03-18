import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public enum Direction {
    N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);
    private final int degrees;

    Direction(final int degrees) {
        this.degrees = degrees;
    }

    public static Direction ofDegrees(int degrees) {
        int n = castingDegrees(degrees);
        for (Direction direction : Direction.values())
            if (direction.degrees == n) return direction;
        return null;
    }

    public static Direction closestToDegrees(int degrees) {
        int n = castingDegrees(degrees);
        return Arrays.stream(Direction.values())
                .min(Comparator.comparingInt(i -> Math.abs(i.degrees - n)))
                .orElseThrow(NoSuchElementException::new);
    }

    public Direction opposite() {
        var n = this.degrees + 180;
        for (Direction direction : Direction.values())
            if ((n >= 360 ? n - 360 : n) == direction.degrees) return direction;
        return null;
    }

    public int differenceDegreesTo(Direction direction) {
        if (isBeginningAndEnd(this, direction))
            return 360 - direction.degrees;
        else if (isBeginningAndEnd(direction, this))
            return 360 - this.degrees;
        else return Math.abs(direction.degrees - this.degrees);
    }

    private static boolean isBeginningAndEnd(Direction n, Direction m) {
        return n.degrees == 0 && m.degrees > 180 && m.degrees <= 360;
    }

    private static int castingDegrees(int degrees) {
        while (degrees >= 360 || degrees < 0)
            degrees = degrees >= 0 ? degrees - 360 : degrees + 360;
        return degrees;
    }
}