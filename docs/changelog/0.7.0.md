### 0.7.0

_Released 2023 Feb 06_

#### Improvements

- Flow-conversion utilities provided by `quitte-kotlinx-coroutines` are no
  longer marked with `@ExperimentalCoroutinesApi`.
- Replaced `java.text.format` with a more general `I18nFormat` API.

#### Fixes

- Removed the publicly available constructor for the `QuitteCollections` utility
  class.
- Fixed expression JavaDoc referring to the expression as "property".
- Fixed a bug that caused `ObservableMap.get` to throw an
  `UnsupportedOperationException` for unmodifiable views.

#### Breaking Changes

- Kotlin `1.8` or later is now required for `quitte-compose` and
  `quitte-kotlinx-coroutines`.
- The signature of `I18nContext#getFormat(String)` was changed to return the new
  `I18nFormat` type.