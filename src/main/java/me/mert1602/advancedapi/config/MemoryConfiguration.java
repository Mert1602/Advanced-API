package me.mert1602.advancedapi.config;

import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * This is a {@link Configuration} implementation that does not save or load
 * from any source, and stores all values in memory only.
 * This is useful for temporary Configurations for providing defaults.
 */
public class MemoryConfiguration extends MemorySection implements Configuration {
    protected Configuration defaults;
    protected MemoryConfigurationOptions options;

    /**
     * Creates an empty {@link MemoryConfiguration} with no default values.
     */
    public MemoryConfiguration() {}

    /**
     * Creates an empty {@link MemoryConfiguration} using the specified {@link
     * Configuration} as a source for all default values.
     *
     * @param defaults Default value provider
     * @throws IllegalArgumentException Thrown if defaults is null
     */
    public MemoryConfiguration(Configuration defaults) {
        this.defaults = defaults;
    }

    @Override
    public void addDefault(String path, Object value) {
        Validate.notNull(path, "Path may not be null");

        if (this.defaults == null) {
            this.defaults = new MemoryConfiguration();
        }

        this.defaults.set(path, value);
    }

    @Override
	public void addDefaults(Map<String, Object> defaults) {
        Validate.notNull(defaults, "Defaults may not be null");

        for (Map.Entry<String, Object> entry : defaults.entrySet()) {
            this.addDefault(entry.getKey(), entry.getValue());
        }
    }

    @Override
	public void addDefaults(Configuration defaults) {
        Validate.notNull(defaults, "Defaults may not be null");

        this.addDefaults(defaults.getValues(true));
    }

    @Override
	public void setDefaults(Configuration defaults) {
        Validate.notNull(defaults, "Defaults may not be null");

        this.defaults = defaults;
    }

    @Override
	public Configuration getDefaults() {
        return this.defaults;
    }

    @Override
    public ConfigurationSection getParent() {
        return null;
    }

    @Override
	public MemoryConfigurationOptions options() {
        if (this.options == null) {
            this.options = new MemoryConfigurationOptions(this);
        }

        return this.options;
    }
}
