package net.nullschool.grains.generate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
final class BeanSymbol {
    
    private final List<? extends PropertySymbol> properties;
    private final List<? extends TypeTokenDecl> typeTokens;

    BeanSymbol(List<? extends PropertySymbol> properties, Collection<? extends TypeTokenDecl> typeTokens) {
        this.properties = Collections.unmodifiableList(new ArrayList<>(properties));
        this.typeTokens = Collections.unmodifiableList(new ArrayList<>(typeTokens));
    }

    public List<? extends PropertySymbol> getProperties() {
        return properties;
    }

    public List<? extends TypeTokenDecl> getTypeTokens() {
        return typeTokens;
    }
}
