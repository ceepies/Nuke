package duke;

import duke.modules.Module;
import duke.modules.ModuleManager;
import duke.modules.events.impl.Event;
import org.lwjgl.opengl.Display;

public class Duke {

    public String version = "1.0", clientName, clientBuild = "Dev";
    public static Duke nova = new Duke();
    public String getVersion(){
        return version;
    }
    public void setClientName(String newClientName){
        clientName = newClientName;
    }
    public String getClientVersion(){
        return version;
    }
    public String getClientName(){
        return clientName;
    }
    public String getClientBuild(){
        return clientBuild;
    }
    public static ModuleManager moduleManager = new ModuleManager();
    public void onStartup(){
        Display.setTitle("Duke");
        moduleManager.ModuleManager();
    }
    public ModuleManager getModuleManager(){
        return moduleManager;
    }
    public static Duke get(){
        return nova;
    }
    public static void onEvent(Event e){
        for(Module m : Duke.moduleManager.modules){
            if(!m.toggled)
                continue;
            m.onEvent(e);
        }
    }
}
