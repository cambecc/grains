/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainProperty;
import net.nullschool.reflect.TypeTools;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
public final class GrainAsBeanProperty implements BeanProperty {

    private final GrainProperty property;
    private final JavaType type;
    private final Class<?> grainClass;

    public GrainAsBeanProperty(GrainProperty property, JavaType type, Class<?> grainClass) {
        this.property = property;
        this.type = type;
        this.grainClass = grainClass;
    }

    @Override public String getName() {
        return property.getName();
    }

    @Override public JavaType getType() {
        return type;
    }

    @Override public PropertyName getWrapperName() {
        return null;
    }

    @Override public boolean isRequired() {
        return true;
    }

    @Override public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return null;
    }

    @Override public <A extends Annotation> A getContextAnnotation(Class<A> acls) {
        return null;
    }

    @Override public AnnotatedMember getMember() {
        return new AnnotatedMember(new AnnotationMap()) {
            private static final long serialVersionUID = 1;

            @Override public Class<?> getDeclaringClass() {
                return grainClass;
            }

            @Override public Member getMember() {
                throw new UnsupportedOperationException();
            }

            @Override public void setValue(Object pojo, Object value) {
                throw new UnsupportedOperationException();
            }

            @Override public Object getValue(Object pojo) {
                return ((Grain)pojo).get(property.getName());
            }

            @Override public <A extends Annotation> A getAnnotation(Class<A> acls) {
                return null;
            }

            @Override public Annotated withAnnotations(AnnotationMap fallback) {
                throw new UnsupportedOperationException();
            }

            @Override public AnnotatedElement getAnnotated() {
                throw new UnsupportedOperationException();
            }

            @Override protected int getModifiers() {
                throw new UnsupportedOperationException();
            }

            @Override public String getName() {
                return property.getName();
            }

            @Override public Type getGenericType() {
                return property.getType();
            }

            @Override public Class<?> getRawType() {
                return TypeTools.erase(property.getType());
            }
        };
    }

    @Override public void depositSchemaProperty(JsonObjectFormatVisitor objectVisitor) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
