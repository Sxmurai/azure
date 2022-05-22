package wtf.azure.core.features;

import lombok.Getter;
import wtf.azure.core.Azure;

@Getter
public class ToggleableFeature extends Feature {
    private boolean toggled;

    public ToggleableFeature(String name) {
        super(name);
    }

    protected void onActivated() {
        Azure.EVENT_BUS.subscribe(this);
    }

    protected void onDeactivated() {
        Azure.EVENT_BUS.unsubscribe(this);
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;

        if (toggled) {
            onActivated();
        } else {
            onDeactivated();
        }
    }
}
