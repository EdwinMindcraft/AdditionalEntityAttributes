# Additional Entity Attributes Forge

### Depending on this project
To depend on AEA Forge, please use the MerchantPug maven.
`build.gradle`
```gradle
repositories {
    ...
    maven {
        url "https://maven.merchantpug.net"
    }
}

dependencies {
    ...
    // Forge
    implementation(jarJar("de.dafuqs:additionalentityattributes-forge:${aea_forge_version}+${minecraft_version}")) {
        jarJar.ranged(it, "[${aea_forge_version},)")
    }
}
```

`Example gradle.properties`
```properties
aea_forge_version=1.4.0.5
minecraft_version=1.20.2
```