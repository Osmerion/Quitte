### 0.8.0

_Released 2023 Aug 20_

#### Improvements

- Added an explicit module descriptor (`module-info.java`) to
  `quitte-kotlinx-coroutines`.
- Refactored the collection listener API for improved usability.
  - Local updates for lists now contain the old elements. [[GH-5](https://github.com/Osmerion/Quitte/issues/10)]
  - `ObservableMap::entrySet` does now return an `ObservableSet`. [[GH-11](https://github.com/Osmerion/Quitte/issues/11)]
- Various JavaDoc improvements.

#### Breaking Changes

- The collection listener API was refactored for improved usability.
  Consequentially, all deprecated methods were removed and a few additional API
  were made.
- Updated [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) dependency to `1.7.3`.