/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Benjamin Weber
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package oop.simulation.beans;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Public interface to get/set properties using C# style syntax.
 * @param <T> Property type
 *
 * @author Benjamin Weber
 * @author Kevin Dai
 */
public class Property<T>
{
    private Supplier<T> getter;
    private Function<T, T> setter;
    private T value;

    protected Property(T initial)
    {
        this.value = initial;
        this.getter = () -> value;
        this.setter = v -> value = v;
    }

    protected Property(Supplier<T> getter, Function<T, T> setter)
    {
        this.value = null;
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * Base factory to build properties.
     * For usage, see {@link Property#get(Supplier)}
     *
     * @param <T> Type of property.
     */
    public interface PropertyBuilder<T>
    {
        Property<T> set(Function<T, T> setter);

        Readonly<T> readonly();
    }

    /**
     * Base factory to build inferred properties.
     * For usage, see {@link Property#inferred(Object)}
     *
     * @param <T> Type of property.
     */
    public interface InferredPropertyBuilder<T>
    {
        Property<T> set();

        Readonly<T> readonly();
    }

    /**
     * Create property getter/setter pair. Basic usage example:
     * <pre>
     * T backing;
     * Property &lt;T&gt; MyProperty = Property
     *     .get(() -&gt; backing)
     *     .set(value -&gt; backing = value);
     * </pre>
     * You may also wish to set the property to readonly() instead of specifying
     * a mutator.
     *
     * @param getter A lambda/supplier expression to return a value of type T.
     * @param <T>    Type of property
     * @return Resultant PropertyBuilder so you can chain get and set.
     */
    public static <T> PropertyBuilder<T> get(Supplier<T> getter)
    {
        return new PropertyBuilder<>()
        {
            public Property<T> set(Function<T, T> setter)
            {
                return new Property<>(getter, setter);
            }

            public Readonly<T> readonly()
            {
                return new Property<>(getter, Function.identity())::get;
            }
        };
    }

    /**
     * @param initial
     * @param <T>
     * @return
     */
    public static <T> InferredPropertyBuilder<T> inferred(T initial)
    {
        return new InferredPropertyBuilder<T>()
        {
            public Property<T> set()
            {
                return new Property<>(initial);
            }

            public Readonly<T> readonly()
            {
                return new Property<>(initial)::get;
            }
        };
    }

    /**
     * Sets the value of the property
     *
     * @param value Value to set
     */
    public void set(T value)
    {
        setter.apply(value);
    }

    /**
     * Gets the value of the property
     *
     * @return Value of property
     */
    public T get()
    {
        return getter.get();
    }

    /**
     * Overrides current property with supplied property.
     *
     * @param p Property to override this one with.
     */
    public void rebind(Property<T> p)
    {
        this.getter = p.getter;
        this.setter = p.setter;
    }

    /**
     * Overrides current property with getter and setter.
     *
     * @param getter New getter
     * @param setter New setter
     */
    public void rebind(Supplier<T> getter, Function<T, T> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }
}
