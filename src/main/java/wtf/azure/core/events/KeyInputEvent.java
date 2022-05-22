package wtf.azure.core.events;

import me.bush.eventbus.event.Event;

public class KeyInputEvent extends Event {
    private final int keyCode, action, modifiers;
    private final long window;

    public KeyInputEvent(int keyCode, int action, int modifiers, long window) {
        this.keyCode = keyCode;
        this.action = action;
        this.modifiers = modifiers;
        this.window = window;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public int getAction() {
        return action;
    }

    public int getModifiers() {
        return modifiers;
    }

    public long getWindow() {
        return window;
    }

    @Override
    protected boolean isCancellable() {
        return false;
    }
}
