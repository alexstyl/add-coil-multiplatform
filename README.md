# Add Coil Multiplatform

[Coil Multiplatform support is here](https://github.com/coil-kt/coil/issues/842)! Unfortunately setting it up is not straightforward, especially
for those new to the Kotlin Multiplatform world.

This dependency setups all the complicated wiring for you, using a single dependency.

`NOTE:` This dependency adds Ktor to your project and uses it as the HTTP engine for request. We do not support other engines.

## Installation

```
// build.gradle.kts
commonMain.dependencies {
    dependencies {
        // version matches the version of coil used
        implementation("com.alexstyl:addcoilmultiplatform:3.0.0-rc01")
    }
}
```

## Documentation

We do not add new any code to your project other than what Coil provides.

You can [find Coil's Compose documentation here](https://coil-kt.github.io/coil/compose/).

## Contributing

We are currently accepting contributions in the form of bug reports and feature requests, in the form of Github issues.