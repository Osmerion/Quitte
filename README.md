# Quitte
[![License](https://img.shields.io/badge/license-BSD-blue.svg?style=flat-square)](https://github.com/Osmerion/Quitte/blob/master/LICENSE)

Specialized, observable properties and observable collections for the JVM.

Quitte was originally designed as a replacement for JavaFX's properties for [Osmerion](https://github.com/Osmerion/Osmerion).
This library does not provide any advanced functionality on its own. However, it can be useful when observable values,
properties, or collections are required.


## Usage

Quitte only provides a set of building blocks that can be used in applications
and libraries.

Simple observable properties are available and can be easily created and used as
follows:

```java
SimpleIntProperty myIntProperty = new SimpleIntProperty(0);
int initialValue = myIntProperty.get();

myIntProperty.addChangeListener((observable, oldValue, newValue) -> {
    /*
     * In this example this lambda will be invoked exactly once, with the following values:
     * observable == myIntProperty
     * oldValue == initialValue == 0
     * newValue == 42
     */
    System.out.println(String.format("Value of myIntProperty changed! (Old Value: %s, New Value: %s)", oldValue, newValue));
});

myIntProperty.set(42);
```

Observable collections provided by Quitte wrap around existing collection types
for best compatibility with other libraries. They are created as follows:

```
List<String> someStrings = ...;

ObservableList<String> observableStrings = ObservableList.of(someStrings);
observableStrings.addChangeListener((change) -> {
    System.out.println("The content of observableStrings has been changed.")
});

observableStrings.add("foo");

/*
 * Note that the call below does not notify the listeners but the content of the
 * list is still modified.
 * This is in line with Collections.unmodifiableList() since only an _observable
 * view_ which serves as proxy to someStrings is returned.
 */
someStrings.add("bar");
```

It is generally recommended to always pass a new list instance directly to
`ObservableList.of` (e.g. `ObservableList.of(new ArrayList())`).

For further information on how to use Quitte, please refer to the JavaDoc.


## Building from source

### Setup

A complete build of Quitte only requires an installation of JDK 14 or later.
This JDK should either be used to invoke Gradle (i.e. sit in `JAVA_HOME`), or be
made visible to Gradle by setting up environment variables (or [Gradle properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties))
as follows:
```
JDK_14="path to JDK 14"
```

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
scripts which are located under `src/templates` and discovered automatically.

The generated code is published to the repository to make it easier to follow
changes using the git history.


## License

```
Copyright (c) 2018-2020 Leon Linhart,
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