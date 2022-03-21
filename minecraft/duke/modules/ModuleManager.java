package duke.modules;



import duke.modules.impl.combat.KillAura;
import duke.modules.impl.movement.Flight;
import duke.modules.impl.movement.Sprint;
import duke.modules.impl.player.Disabler;
import duke.modules.impl.visual.ClickGUI;
import duke.modules.impl.visual.HUD;
import duke.modules.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {

    public ArrayList<Module> modules = new ArrayList<Module>();

    public void ModuleManager() {
        modules.addAll(Arrays.asList(new Flight(), new Sprint(), new KillAura(), new ClickGUI(),new Disabler(), new HUD()));
    }
    public static ModuleManager moduleManager = new ModuleManager();
    public static ModuleManager getModuleManager(){
        return  moduleManager;
    }

    public ArrayList<Setting> getSetting() {
        ArrayList<Setting> setting = new ArrayList<>();
        for (Module module : getModules()) {
            for (Setting option : module.settings) {
                setting.add(option);
            }
        }
        return setting;
    }

    public Setting getSettingByName(String name) {
        for (Setting set : getSetting()) {
            if (set.name.equalsIgnoreCase(name)) {
                return set;
            }
        }
        return null;
    }

    public Module getModuleByName(String name) {
        for (Module m : getModules()) {
            if (m.name.equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public List<Setting> getSettingsByModule(Module m) {
        List<Setting> list = new ArrayList<>();
        for (Setting s : m.settings) {
            list.add(s);
        }
        return list;
    }

    public ArrayList<Module> getEnabledModules(){
        ArrayList<Module> enabled = new ArrayList<>();
        for(Module m : modules){

            if(m.toggled){
                if (!m.name.equals("ClickGUI") && !m.name.equals("HUD")) {
                    enabled.add(m);
                }
            }else {
                enabled.remove(m);
            }
        }
        return enabled;
    }


    public CopyOnWriteArrayList<Module> getModulesByCategory(Module.Category c) {
        CopyOnWriteArrayList<Module> modulesByCategory = new CopyOnWriteArrayList<>();
        getModules2().forEach(module -> {
            if(module.category == c) {
                modulesByCategory.add(module);
            }
        });
        return modulesByCategory;
    }
    public ArrayList<Module> getModules() {
        return modules;
    }
    public ArrayList<Module> getModules2() {
        return modules;
    }
    public void keyPress(int key){
        for(Module m : modules){
            if(m.getKey() == key){
                m.toggle();
            }
        }
    }

}