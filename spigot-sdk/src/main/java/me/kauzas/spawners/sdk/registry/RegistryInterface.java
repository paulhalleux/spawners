package me.kauzas.spawners.sdk.registry;

import java.util.List;

/**
 * Interface that represents a registerer.
 */
public interface RegistryInterface<T> {
    /**
     * Registers an object.
     *
     * @param object Object to register.
     */
    RegisterResult register(T object);

    /**
     * Gets all registered items.
     *
     * @return List of registered items.
     */
    List<T> getRegisteredItems();

    /**
     * Called when an object is registered.
     *
     * @param object Registered object.
     */
    default void onRegister(Class<? extends T> object, RegisterResult result) {
    }

    /**
     * Called when the registerer is registered.
     */
    default void register() {
    }

    /**
     * Determines if the object should be skipped.
     * If true, the object will not be registered.
     */
    default boolean skip(Class<? extends T> object) {
        return false;
    }
}
