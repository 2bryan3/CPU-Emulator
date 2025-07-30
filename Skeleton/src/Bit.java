public class Bit {
    public enum boolValues { FALSE, TRUE }
    private boolValues val;

    public Bit(boolean value) {
        if (value) {
            assign(boolValues.TRUE);
        } else {
            assign(boolValues.FALSE);
        }
    }

    public boolValues getValue() {
        return val;
    }

    public void assign(boolValues value) {
        this.val = value;
    }

    public void and(Bit b2, Bit result) {
        and(this, b2, result);
    }

    public static void and(Bit b1, Bit b2, Bit result) {
        if (b1.getValue() == boolValues.TRUE) {
            if (b2.getValue() == boolValues.TRUE) {
                result.assign(boolValues.TRUE);
            }
        } else {
            result.assign(boolValues.FALSE);
        }
    }

    public void or(Bit b2, Bit result) {
        or(this, b2, result);
    }

    public static void or(Bit b1, Bit b2, Bit result) {
        if (b1.getValue() == boolValues.TRUE){
            result.assign(boolValues.TRUE);
        } else if (b2.getValue() == boolValues.TRUE){
            result.assign(boolValues.TRUE);
        } else {
            result.assign(boolValues.FALSE);
        }
    }

    public void xor(Bit b2, Bit result) {
        xor(this, b2, result);
    }

    public static void xor(Bit b1, Bit b2, Bit result) {
        if (b1.getValue() == b2.getValue()) {
            result.assign(boolValues.FALSE);
        } else {
            result.assign(boolValues.TRUE);
        }
    }

    public static void not(Bit b2, Bit result) {
        if (b2.getValue() == boolValues.TRUE) {
            result.assign(boolValues.FALSE);
        } else {
            result.assign(boolValues.TRUE);
        }
    }

    public void not(Bit result) {
        not(this, result);
    }

    public String toString() {
        if (this.getValue() == boolValues.TRUE) {
            return "t";
        } else {
            return "f";
        }
    }
}
