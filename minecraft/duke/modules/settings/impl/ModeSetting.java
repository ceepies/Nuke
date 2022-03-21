package duke.modules.settings.impl;

import duke.modules.settings.Setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {
    public int index;
    public List<String> list;

    public ModeSetting(String name, String defaultMode, String... list) {
        this.name = name;
        this.list = Arrays.asList(list);
        this.index = this.list.indexOf(defaultMode);
    }

    public void cycle() {
        if(index < list.size() -1) {
            index++;
        }
        else {
            index = 0;
        }
    }

    public boolean is(String mode){
        return index == list.indexOf(mode);
    }

    public String name() {
        return list.get(index);
    }
    public void setName(String name) {
        list.set(index, name);
    }
}
