package bln.fin.entity.enums;

public enum ForecastTypeEnum {
    OPTIMISTIC,
    REALISTIC,
    PESSIMISTIC;

    public String toShortString() {
        switch (this) {
            case OPTIMISTIC: return "O";
            case REALISTIC: return "R";
            case PESSIMISTIC: return "P";
        }
        return this.name();
    }
}
