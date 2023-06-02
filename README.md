# Quitte

[![License](https://img.shields.io/badge/license-BSD-blue.svg?style=flat-square&label=License)](https://github.com/Osmerion/Quitte/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.osmerion.quitte/quitte.svg?style=flat-square&label=Maven%20Central)](https://maven-badges.herokuapp.com/maven-central/com.osmerion.quitte/quitte)
[![JavaDoc](https://img.shields.io/maven-central/v/com.osmerion.quitte/quitte.svg?style=flat-square&label=JavaDoc&color=blue)](https://javadoc.io/doc/com.osmerion.quitte/quitte)
![Java](https://img.shields.io/badge/Java-17-green.svg?style=flat-square&color=b07219&logo=java)

Quitte provides specialized observable properties and expressions with lazy
variants, and observable collections.

Quitte is compatible with Java 17 or later.


## Usage

### Quitte

The core module provides the basic set of observable building blocks.


#### Properties

Properties are mutable wrappers for values which support listening for changes.
_Simple_ properties are the most basic kind of observable. They are simple
wrappers for values with set- and get-access.

_Lazy_ properties provide setter overloads to support lazy updates of their
values. When, for example, `LazyIntProperty#set(IntSupplier)` is used, the
properties value is not updated. Instead, the property is marked as invalid and
only invalidation listeners are notified. The new value is only computed when
the property's value is queried.

> **Tip:** While properties are fundamentally mutable, it is generally
> recommended using read-only views of properties to reduce the risk of unwanted
> modifications. (See `ReadableProperty#asReadOnlyProperty()`)

#### Expressions

Expressions are observables which are derived from one or more observables using
some processing step. They do not have a mutable value, but instead _yield_ the
value of the calculation they represent. Similar to properties, expressions come
in two flavours:

- _Simple_ expressions which recompute their result eagerly when the value of an
  input is changed, and
- _Lazy_ expressions which avoid recomputing until their result is queried.


#### Observable collections

In addition to single-value observables, Quitte also supports observable
collections. To support a variety of collection implementations, Quitte's
observable collections are wrappers that delegate to a concrete implementation.

```java
List<String> strings = new ArrayList<>();
ObservableList<String> observableStrings = ObservableList.of(strings);
observableStrings.addChangeListener(change -> System.out.println("The content of observableStrings has been changed."));

observableStrings.add("foo");

/*
 * WARNING: The call below does not notify the listeners since it accesses the
 * list implementation directly. To avoid running into issues, holding a
 * reference to the collection implementation is discouraged. Instead, use:
 * 
 * ObservableList<String> observableStrings = ObservableList.of(new ArrayList());
 */
strings.add("bar");
```


### Quitte I18n

The `quitte-i18n` module provides a basic API for using Quitte observables for
localization.

> **Tip:** [Billi](https://github.com/Osmerion/Billi) provides complete
> localization and pluralization support on top of Quitte.


### Quitte extensions for Jetpack Compose

The `quitte-compose` module provides extensions for better interoperability with
[Jetpack Compose](https://developer.android.com/jetpack/compose).

```kotlin
interface MyModel {
    val text: ObservableObjectValue<String>
}

@Composable
fun MyComposable(model: MyModel) {
    val text by textFlow.observeAsState()
    
    Text(text)
}
```

> **Note**: The `quitte-compose` module targets Java 17 bytecode and is
> currently only supported for [Compose for Desktop](https://www.jetbrains.com/lp/compose-mpp/).


### Quitte extensions for kotlinx.coroutines

The `quitte-kotlinx-coroutines` module provides extensions for better
interoperability with [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines).

Simply convert observables to flows using any of the built-in conversion
functions. Flow conversions are available for `ObservableValue`,
`ObservableList`, `ObservableMap`, and `ObservableSet`.


## Building from source

### Setup

This project uses [Gradle's toolchain support](https://docs.gradle.org/8.1.1/userguide/toolchains.html)
to detect and select the JDKs required to run the build. Please refer to the
build scripts to find out which toolchains are requested.

An installed JDK 1.8 (or later) is required to use Gradle.

### Building

Once the setup is complete, invoke the respective Gradle tasks using the
following command on Unix/macOS:

    ./gradlew <tasks>

or the following command on Windows:

    gradlew <tasks>

Important Gradle tasks to remember are:
- `clean`                   - clean build results
- `build`                   - assemble and test the Java library
- `generate`                - generates all code from the templates (see the
                              "Editing" section below for details)
- `publishToMavenLocal`     - build and install all public artifacts to the
                              local maven repository

Additionally `tasks` may be used to print a list of all available tasks.


## Editing

To reduce the amount duplication when generating specialized types, Quitte uses
a simple code-generation mechanism. The templates are simple Gradle Kotlin DSL
scripts which are located under `src/{module}/{site}-templates` and discovered
automatically.

The generated code is published to the repository to make it easier to follow
changes using the git history.


## License

Quitte is available under the terms of the [3-Clause BSD license](https://spdx.org/licenses/BSD-3-Clause.html).

```
Copyright (c) 2018-2023 Leon Linhart,
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
```