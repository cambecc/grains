package net.nullschool.reflect;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.Set;

import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.*;


/**
 * 2013-05-02<p/>
 *
 * @author Cameron Beccario
 */
public class AbstractTypeOperatorTest {

    @Test
    public void test_invoke() {
        TypeOperator<?> operator = mock(AbstractTypeOperator.class);
        given(operator.apply(any(Type.class))).willCallRealMethod();

        operator.apply((Type)Object.class);
        operator.apply((Type)new LateParameterizedType(Set.class, null, Integer.class));
        operator.apply((Type)new LateGenericArrayType(new LateTypeVariable<Class>("E", Set.class)));
        operator.apply((Type)new LateWildcardType("?"));
        operator.apply((Type)new LateTypeVariable<Class>("E", Set.class));
        assertNull(operator.apply((Type)null));

        verify(operator, times(1)).apply(any(Class.class));
        verify(operator, times(1)).apply(any(ParameterizedType.class));
        verify(operator, times(1)).apply(any(GenericArrayType.class));
        verify(operator, times(1)).apply(any(WildcardType.class));
        verify(operator, times(1)).apply(any(TypeVariable.class));
        verify(operator, times(6)).apply(any(Type.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invoke_unknown() {
        TypeOperator<?> operator = mock(AbstractTypeOperator.class);
        given(operator.apply(any(Type.class))).willCallRealMethod();
        operator.apply(new Type(){});
    }
}
