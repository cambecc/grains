///*
// * Copyright 2013 Cameron Beccario
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package net.nullschool.transform;
//
//import net.nullschool.collect.IterableMap;
//import net.nullschool.collect.MapIterator;
//
//import java.util.*;
//
//
///**
// * 2013-05-13<p/>
// *
// * @author Cameron Beccario
// */
//class DefaultTransformFactory implements TransformFactory {
//
//    private static class SimpleBuilder<T> implements Builder<T> {
//        private final Class<T> clazz;
//
//        private SimpleBuilder(Class<T> clazz) {
//            this.clazz = clazz;
//        }
//
//        @Override public Class<? super T> getType() {
//            return clazz;
//        }
//
//        @Override public Builder<T> setArgumentCasts(Transform<?>[] argCasts) {
//            throw new UnsupportedOperationException("unexpected casts");
//        }
//
//        @Override public Transform<T> build() {
//            return new Transform<T>() {
//                @Override public T apply(Object o) {
//                    return clazz.cast(o);
//                }
//            };
//        }
//    }
//
//    private static class ArrayBuilder<T> implements Builder<T> {
//        private final Class<T> clazz;
//        private Transform<?>[] argCasts;
//
//        private ArrayBuilder(Class<T> clazz) {
//            this.clazz = clazz;
//        }
//
//        @Override public Class<? super T> getType() {
//            return clazz;
//        }
//
//        @Override public Builder<T> setArgumentCasts(Transform<?>[] argCasts) {
//            this.argCasts = argCasts.clone();
//            return this;
//        }
//
//        @Override public Transform<T> build() {
//            if (argCasts == null || argCasts.length == 0) {
//                return new SimpleBuilder<>(clazz).build();
//            }
//            final Transform<?> elementCast = argCasts[0];
//            return new Transform<T>() {
//
//                private boolean checkElements(Object[] array) {
//                    for (Object e : array) {
//                        elementCast.apply(e);  // UNDONE: should be assertion error
//                    }
//                    return true;
//                }
//
//                @Override public T apply(Object o) {
//                    T t = clazz.cast(o);
//                    assert t == null || checkElements((Object[])t);
//                    return t;
//                }
//            };
//        }
//    }
//
//    private static class CollectionFunctionBuilder<T extends Collection<?>> implements Builder<T> {
//
//        private final Class<T> clazz;
//        private Transform<?>[] argCasts;
//
//        private CollectionFunctionBuilder(Class<T> clazz) {
//            this.clazz = clazz;
//        }
//
//        @Override public Class<? super T> getType() {
//            return Collection.class;
//        }
//
//        @Override public Builder<T> setArgumentCasts(Transform<?>[] argCasts) {
//            this.argCasts = argCasts.clone();
//            return this;
//        }
//
//        @Override public Transform<T> build() {
//            if (argCasts == null || argCasts.length == 0) {
//                return new SimpleBuilder<>(clazz).build();
//            }
//            final Transform<?> elementCast = argCasts[0];
//            return new Transform<T>() {
//
//                private boolean checkElements(Collection<?> c) {
//                    for (Object e : c) {
//                        elementCast.apply(e);  // UNDONE: should be assertion failure
//                    }
//                    return true;
//                }
//
//                @Override public T apply(Object o) {
//                    T t = clazz.cast(o);
//                    assert t == null || checkElements(t);
//                    return t;
//                }
//            };
//        }
//    }
//
//    private static class MapFunctionBuilder<T extends Map<?, ?>> implements Builder<T> {
//
//        private final Class<T> clazz;
//        private Transform<?>[] argCasts;
//
//        private MapFunctionBuilder(Class<T> clazz) {
//            this.clazz = clazz;
//        }
//
//        @Override public Class<? super T> getType() {
//            return Map.class;
//        }
//
//        @Override public Builder<T> setArgumentCasts(Transform<?>[] argCasts) {
//            this.argCasts = argCasts.clone();
//            return this;
//        }
//
//        @Override public Transform<T> build() {
//            if (argCasts == null || argCasts.length < 2) {
//                return new SimpleBuilder<>(clazz).build();
//            }
//            final Transform<?> keyCast = argCasts[0];
//            final Transform<?> valueCast = argCasts[1];
//            return new Transform<T>() {
//
//                private boolean checkEntries(Map<?, ?> map) {
//                    if (map instanceof IterableMap) {
//                        for (MapIterator<?, ?> iter = ((IterableMap<?, ?>)map).iterator(); iter.hasNext();) {
//                            keyCast.apply(iter.next());      // UNDONE: should be assertion error
//                            valueCast.apply(iter.value());
//                        }
//                    }
//                    else {
//                        for (Map.Entry entry : map.entrySet()) {
//                            keyCast.apply(entry.getKey());      // UNDONE: should be assertion error
//                            valueCast.apply(entry.getValue());
//                        }
//                    }
//                    return true;
//                }
//
//                @Override public T apply(Object o) {
//                    T t = clazz.cast(o);
//                    assert t == null || checkEntries(t);
//                    return t;
//                }
//            };
//        }
//    }
//
//    @Override public <T> Builder<T> newBuilder(Class<T> clazz) {
//        if (clazz.getTypeParameters().length == 0) {
//            if (clazz.isArray()) {
//                return new ArrayBuilder<>(clazz);
//            }
//            return new SimpleBuilder<>(clazz);
//        }
//        if (Collection.class.isAssignableFrom(clazz)) {
//            @SuppressWarnings("unchecked") Builder<T> builder =
//                (Builder<T>)new CollectionFunctionBuilder<>(clazz.asSubclass(Collection.class));
//            return builder;
//        }
//        if (Map.class.isAssignableFrom(clazz)) {
//            @SuppressWarnings("unchecked") Builder<T> builder =
//                (Builder<T>)new MapFunctionBuilder<>(clazz.asSubclass(Map.class));
//            return builder;
//        }
//        // WARN -- but how?
//        return new SimpleBuilder<>(clazz);
//    }
//}
