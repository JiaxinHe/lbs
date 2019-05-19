package app.lbs.com.lbsapp.bean;

public class LimitBean {

    /**
     * limitSuffix : 6
     * limited : false
     */

    private String limitSuffix;
    private boolean limited;

    public String getLimitSuffix() {
        return limitSuffix;
    }

    public void setLimitSuffix(String limitSuffix) {
        this.limitSuffix = limitSuffix;
    }

    public boolean isLimited() {
        return limited;
    }

    public void setLimited(boolean limited) {
        this.limited = limited;
    }
}
