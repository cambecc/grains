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
        given(operator.invoke(any(Type.class))).willCallRealMethod();

        operator.invoke((Type)Object.class);
        operator.invoke((Type)new LateParameterizedType(Set.class, null, Integer.class));
        operator.invoke((Type)new LateGenericArrayType(new LateTypeVariable<Class>("E", Set.class)));
        operator.invoke((Type)new LateWildcardType("?"));
        operator.invoke((Type)new LateTypeVariable<Class>("E", Set.class));
        assertNull(operator.invoke((Type)null));

        verify(operator, times(1)).invoke(any(Class.class));
        verify(operator, times(1)).invoke(any(ParameterizedType.class));
        verify(operator, times(1)).invoke(any(GenericArrayType.class));
        verify(operator, times(1)).invoke(any(WildcardType.class));
        verify(operator, times(1)).invoke(any(TypeVariable.class));
        verify(operator, times(6)).invoke(any(Type.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invoke_unknown() {
        TypeOperator<?> operator = mock(AbstractTypeOperator.class);
        given(operator.invoke(any(Type.class))).willCallRealMethod();
        operator.invoke(new Type() {});
    }
}
