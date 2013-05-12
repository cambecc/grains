package net.nullschool.grains.generate;

import java.util.*;

/**
 * 2013-03-24<p/>
 *
 * A symbol that represents the property structure of a grain.
 *
 * @author Cameron Beccario
 */
final class GrainSymbol implements Symbol {
    
    private final List<? extends PropertySymbol> properties;
    private final List<? extends TypeTokenSymbol> typeTokens;
    private final Symbol constPolicyLoadExpression;

    GrainSymbol(
        List<? extends PropertySymbol> properties,
        Collection<? extends TypeTokenSymbol> typeTokens,
        Symbol constPolicyLoadExpression) {

        this.properties = Collections.unmodifiableList(new ArrayList<>(properties));
        this.typeTokens = Collections.unmodifiableList(new ArrayList<>(typeTokens));
        this.constPolicyLoadExpression = constPolicyLoadExpression;
    }

    public List<? extends PropertySymbol> getProperties() {
        return properties;
    }

    public List<? extends TypeTokenSymbol> getTypeTokens() {
        return typeTokens;
    }

    public Symbol getConstPolicy() {
        return constPolicyLoadExpression;
    }
}
