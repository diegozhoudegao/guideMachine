package com.cssiot.cssutil.common.utils;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import io.vavr.Value;
import io.vavr.collection.Iterator;

/**
 * <p>为空值或为真,抛出异常</p>
 * <p>OptionUtil.of(任意类型).getOrElseThrow(() -> new ResultException(ResultEnum.USERID_NULL,token,loginName,clientType));//空则抛出异常</p>
 * <p>OptionUtil.of(布尔表达式).getOrElseThrow(() -> new ResultException(ResultEnum.USERID_NULL,token,loginName,clientType));//真则抛出异常</p>
 * @ClassName: OptionUtil
 * @Description: 
 * @author 
 * 2018年6月19日  tck 创建
 */
public interface OptionUtil<T> extends Value<T>, Serializable{
	static <T> OptionUtil<T> of(T value) {
        return (ChkUtil.isEmptyAllObject(value)) ? none() : some(value);
    }
	
	@SuppressWarnings("unchecked")
	static <T> OptionUtil<T> of(boolean value) {
		return value ? none() : (OptionUtil<T>) some(value);
	}
	
//	static <T> OptionUtil<T> some() {
//		@SuppressWarnings("unchecked")
//		final Some<T> some = (Some<T>) Some.INSTANCE;
//        return some;
//    }
	static <T> OptionUtil<T> some(T value) {
        return new Some<>(value);
    }
	
	static <T> OptionUtil<T> none() {
        @SuppressWarnings("unchecked")
        final None<T> none = (None<T>) None.INSTANCE;
        return none;
    }
	
	/**
     * Returns the value if this is a {@code Some} or the {@code other} value if this is a {@code None}.
     * <p>
     * Please note, that the other value is eagerly evaluated.
     *
     * @param other An alternative value
     * @return This value, if this Option is defined or the {@code other} value, if this Option is empty.
     */
	@Override
    default T getOrElse(T other) {
        return isEmpty() ? other : get();
    }
	
    /**
     * Returns the value if this is a {@code Some}, otherwise throws an exception.
     *
     * @param exceptionSupplier An exception supplier
     * @param <X>               A throwable
     * @return This value, if this Option is defined, otherwise throws X
     * @throws X a throwable
     */
	@Override
	default <X extends Throwable> T getOrElseThrow(Supplier<X> exceptionSupplier) throws X {
        Objects.requireNonNull(exceptionSupplier, "exceptionSupplier is null");
        if (isEmpty()) {
            throw exceptionSupplier.get();
        } else {
            return get();
        }
    }
	
	/**
     * An {@code Option}'s value is computed synchronously.
     *
     * @return false
     */
    @Override
    default boolean isAsync() {
        return false;
    }

    /**
     * Returns true, if this is {@code Some}, otherwise false, if this is {@code None}.
     * <p>
     * Please note that it is possible to create {@code new Some(null)}, which is defined.
     *
     * @return true, if this {@code Option} has a defined value, false otherwise
     */
    default boolean isDefined() {
        return !isEmpty();
    }

    /**
     * An {@code Option}'s value is computed eagerly.
     *
     * @return false
     */
    @Override
    default boolean isLazy() {
        return false;
    }

    /**
     * An {@code Option} is single-valued.
     *
     * @return {@code true}
     */
    @Override
    default boolean isSingleValued() {
        return true;
    }
    
    /**
     * Applies an action to this value, if this option is defined, otherwise does nothing.
     *
     * @param action An action which can be applied to an optional value
     * @return this {@code Option}
     */
    @Override
    default OptionUtil<T> peek(Consumer<? super T> action) {
        Objects.requireNonNull(action, "action is null");
        if (isDefined()) {
            action.accept(get());
        }
        return this;
    }
    
    @Override
    default Iterator<T> iterator() {
        return isEmpty() ? Iterator.empty() : Iterator.of(get());
    }
    
    /**
     * Maps the value and wraps it in a new {@code Some} if this is a {@code Some}, returns {@code None}.
     *
     * @param mapper A value mapper
     * @param <U>    The new value type
     * @return a new {@code Some} containing the mapped value if this Option is defined, otherwise {@code None}, if this is empty.
     */
    @Override
    default <U> OptionUtil<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return isEmpty() ? none() : some(mapper.apply(get()));
//        return isEmpty() ? none() : some();
    }
	
/*	final class Some<T> implements OptionUtil<T>, Serializable {

        private static final long serialVersionUID = 1L;

//        private final T value;
        
        *//**
         * The singleton instance of Some.
         *//*
        private static final Some<?> INSTANCE = new Some<>();
        
        private Some() {
        }


        @SuppressWarnings("unchecked")
		@Override
        public T get() {
        	return (T) "notEmpty";
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean equals(Object obj) {
        	return obj == this;
        }

        @Override
        public int hashCode() {
        	return 1;
        }

        @Override
        public String stringPrefix() {
            return "Some";
        }

        @Override
        public String toString() {
            return stringPrefix();
        }
        
        private Object readResolve() {
            return INSTANCE;
        }
    }*/
    
    final class Some<T> implements OptionUtil<T>, Serializable {

        private static final long serialVersionUID = 1L;

        private final T value;

        /**
         * Creates a new Some containing the given value.
         *
         * @param value A value, may be null
         */
        private Some(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj == this) || (obj instanceof Some && Objects.equals(value, ((Some<?>) obj).value));
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public String stringPrefix() {
            return "Some";
        }

        @Override
        public String toString() {
            return stringPrefix() + "(" + value + ")";
        }
    }
	
	final class None<T> implements OptionUtil<T>, Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The singleton instance of None.
         */
        private static final None<?> INSTANCE = new None<>();

        /**
         * Hidden constructor.
         */
        private None() {
        }


		@Override
        public T get() {
            throw new NoSuchElementException("No value present");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            return o == this;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String stringPrefix() {
            return "None";
        }

        @Override
        public String toString() {
            return stringPrefix();
        }

        // -- Serializable implementation

        /**
         * Instance control for object serialization.
         *
         * @return The singleton instance of None.
         * @see Serializable
         */
        private Object readResolve() {
            return INSTANCE;
        }
    }
}
