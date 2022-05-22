package wtf.azure.core.features.module.impl.other;

import wtf.azure.core.features.module.Module;
import wtf.azure.core.features.module.ModuleCategory;

public class UnfocusedCPU extends Module {
    public UnfocusedCPU() {
        super("Unfocused CPU", ModuleCategory.OTHER, "Stops the game from doing anything while unfocused");
    }
}
