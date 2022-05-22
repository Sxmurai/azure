package wtf.azure.core.features.module.impl.visual;

import org.lwjgl.glfw.GLFW;
import wtf.azure.core.features.module.Module;
import wtf.azure.core.features.module.ModuleCategory;
import wtf.azure.core.ui.click.ClickGUIScreen;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", ModuleCategory.VISUAL, "Opens a Clickable GUI");
        bind.setValue(GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @Override
    protected void onActivated() {
        super.onActivated();

        mc.setScreen(ClickGUIScreen.getInstance());
        setToggled(false);
    }
}
