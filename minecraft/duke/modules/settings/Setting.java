package duke.modules.settings;

public class Setting {

    public String name;
    public float x;
    public float y;


    public void set(boolean x, boolean y, float val) {
        if(x) this.x = val;
        if(y) this.y = val;
    }

    public float get(boolean x, boolean y) {
        if(x) return this.x;
        if(y) return this.y;
        return 0;
    }
}
