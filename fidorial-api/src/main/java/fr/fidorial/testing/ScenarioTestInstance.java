package fr.fidorial.testing;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Method;

@ApiStatus.Internal
public final class ScenarioTestInstance {

    private final Method method;
    private final String group;
    private final int timeoutTicks;
    private final boolean required;

    public ScenarioTestInstance(final Method method, final String group, final int timeoutTicks, final boolean required) {
        this.method = method;
        this.group = group;
        this.timeoutTicks = timeoutTicks;
        this.required = required;
    }

    public String id() {
        return method.getDeclaringClass().getSimpleName() + "/" + method.getName();
    }

    public String group() {
        return group;
    }

    public int timeoutTicks() {
        return timeoutTicks;
    }

    public boolean required() {
        return required;
    }

    void invoke(final ScenarioTestHelper helper) throws ReflectiveOperationException {
        method.invoke(null, helper);
    }
}
