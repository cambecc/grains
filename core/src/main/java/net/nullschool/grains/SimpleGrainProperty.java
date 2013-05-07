package net.nullschool.grains;

import net.nullschool.collect.basic.BasicConstSet;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;


/**
 * 2013-04-10<p/>
 *
 * @author Cameron Beccario
 */
public class SimpleGrainProperty implements GrainProperty {

    private final String name;
    private final Type type;
    private final Set<Flag> flags;

    public SimpleGrainProperty(String name, Type type, Set<Flag> flags) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.flags = BasicConstSet.asSet(flags);
    }

    public SimpleGrainProperty(String name, Type type, Flag... flags) {
        this(name, type, BasicConstSet.asSet(flags));
    }

    @Override public String getName() {
        return name;
    }

    @Override public Type getType() {
        return type;
    }

    @Override public Set<Flag> getFlags() {
        return flags;
    }
}
