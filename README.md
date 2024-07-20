# Add Coil Multiplatform

[Coil Multiplatform support is here](https://github.com/coil-kt/coil/issues/842)! Unfortunately setting it up is not straightforward, especially
for those new to the Kotlin Multiplatform world.

This dependency setups all the complicated wiring for you, using a single dependency.

`NOTE:` This dependency adds no new code to your project other than what Coil provides. We are hoping that when Coil
Multiplatform hits a stable release, you will be able to swap out this dependency with the official one without any
modification in your code base.

## Installation

```
// build.gradle.kts
commonMain.dependencies {
    dependencies {
        implementation("com.alexstyl:addcoilmultiplatform:1.1.0")
    }
}
```

## Documentation

We do not add new any code to your project other than what Coil provides.

You can [find Coil's Compose documentation here](https://coil-kt.github.io/coil/compose/).

## Contributing

We are currently accepting contributions in the form of bug reports and feature requests, in the form of Github issues.