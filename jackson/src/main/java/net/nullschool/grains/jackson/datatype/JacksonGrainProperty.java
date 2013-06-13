package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.nullschool.grains.GrainProperty;

import java.lang.annotation.Annotation;

import static net.nullschool.grains.GrainProperty.Flag.IS_PROPERTY;
import static net.nullschool.util.StringTools.capitalize;


/**
 * 2013-06-10<p/>
 *
 * @author Cameron Beccario
 */
final class JacksonGrainProperty implements BeanProperty {  // UNDONE: Serializable

    private static final AnnotationMap[] EMPTY = new AnnotationMap[0];


    private final String name;
    private final JavaType type;
    private final AnnotatedMember member;

    JacksonGrainProperty(GrainProperty prop, TypeFactory typeFactory, Class<?> grainClass) {
        this.name = prop.getName();
        this.type = typeFactory.constructType(prop.getType());
        try {
            String getterName = (prop.getFlags().contains(IS_PROPERTY) ? "is" : "get") + capitalize(prop.getName());
            // UNDONE: annotations
            this.member = new AnnotatedMethod(grainClass.getMethod(getterName), new AnnotationMap(), EMPTY);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override public String getName() {
        return name;
    }

    @Override public JavaType getType() {
        return type;
    }

    @Override public PropertyName getWrapperName() {
        return null;
    }

    @Override public boolean isRequired() {
        return false;
    }

    @Override public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return member.getAnnotation(acls);
    }

    @Override public <A extends Annotation> A getContextAnnotation(Class<A> acls) {
        return member.getDeclaringClass().getAnnotation(acls);
    }

    @Override public AnnotatedMember getMember() {
        return member;
    }

    @Override public void depositSchemaProperty(JsonObjectFormatVisitor objectVisitor) throws JsonMappingException {
        // UNDONE
        throw new UnsupportedOperationException("NYI");
    }

    @Override public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), name, type);
    }
}
