### 0.6.1

_Released 2022 Dec 19_

#### Fixes

- Adjusted the JavaDoc for property binding methods to reflect the actual
  intended behavior.


---

### 0.6.0

_Released 2022 Dec 13_

#### Improvements

- `AbstractExpression` does now define a `public` constructor. Consequentially,
  it may now be extended by consumers. [[GH-4](https://github.com/Osmerion/Quitte/issues/4)]
- Various JavaDoc improvements.

#### Fixes

- Fixed a bug that caused `ObservableList#setAll` to skip some elements. [[GH-5](https://github.com/Osmerion/Quitte/issues/5)]
  (Thanks to [SkytAsul](https://github.com/SkytAsul))
- Reduced the visibility of some accidentally exposed expression members.

#### Breaking Changes

- Several members of simple and lazy expressions had their visibility reduced
  and may no longer be accessible. These methods were considered unsafe and
  should not have been exposed in the first place. Proper alternatives are
  available.


---

### 0.5.0

_Released 2022 Nov 10_

#### Improvements

- Changed the license information in the POM to use a valid [SPDX identifier](https://spdx.org/licenses/).

#### Fixes

- Fixed a bug that caused expression dependencies to be incorrectly tracked.


---

### 0.4.0

_Released 2022 Sep 24_

#### Improvements

- New `quitte-compose` module with Jetpack Compose extensions.
- Renamed listener methods for clarity and simpler use.
  - Renamed `Observable#addListener(InvalidationListener)` to `addInvalidationListener`.
  - Renamed `Observable#removeListener(InvalidationListener)` to `removeInvalidationListener`.
  - Renamed `ObservableValue#addBoxedListener` to `addBoxedChangeListener`.
  - Renamed `ObservableValue#removeBoxedListener` to `removeBoxedChangeListener`.
  - Renamed `Observable${Type}Value#addListener(${Type}ChangeListener)` to `addChangeListener`.
  - Renamed `Observable${Type}Value#removeListener(${Type}ChangeListener)` to `removeChangeListener`.
  - Renamed `ObservableCollection#addListener(ChangeListener)` to `addChangeListener`.
  - Renamed `ObservableCollection#removeListener(ChangeListener)` to `removeChangeListener`.

#### Breaking Changes

- Renamed listener related methods.


---

### 0.3.0

_Released 2022 Jun 02_

#### Improvements

- New `quitte-kotlinx-coroutines` module with `Flow` conversions.
- Added version information to the module descriptor.
- Updated collection change classes to make use of records and sealed classes.
- Modified `quitte.i18n` to use the more general type `Format` instead of
  `MessageFormat`.

#### Fixes

- `ObservableList#unmodifiableViewOf` does no longer yield results with
  incorrect `RandomAccess` hints.

#### Breaking Changes

- Changed the return type of `I18nContext#getMessageFormat` from `MessageFormat` to
  `Format` and renamed the method accordingly.


---

### 0.2.0

_Released 2021 Jul 30_

#### Improvements

- Type names now use `To` instead of `2` (e.g. `ObjectToBoolFunction` instead of `Object2BoolFunction`).

#### Fixes

- `WeakChangeListener`s are now properly marked as garbage collected.

#### Breaking Changes

- Type names now use `To` instead of `2` (e.g. `ObjectToBoolFunction` instead of `Object2BoolFunction`).


---

### 0.1.0

_Released 2021 May 01_

#### Overview

Quitte provides specialized observable properties and expressions with lazy
variants, and observable collections.

This release is compatible with Java 16 or later.