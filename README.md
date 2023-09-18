# Additional Entity Attributes Multiloader
This is technically just an Additional Entity Attributes Forge port, but it's multiloader for the sake of supporting Common classes.

### Depending on this project
To depend on AEA Common or AEA Forge, please use the MerchantPug maven.
Please use the Modrinth Maven for the official AEA if you are on Fabric or Quilt.
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
    // Common
    compileOnly "de.dafuqs:additionalentityattributes-common:${aea_forge_version}+${minecraft_version}"
    // Forge
    implementation(jarJar("de.dafuqs:additionalentityattributes-forge:${aea_forge_version}+${minecraft_version}")) {
        jarJar.ranged(it, "[${aea_forge_version},)")
    }
}
```

`Example gradle.properties`
```properties
aea_forge_version=1.4.0.5
minecraft_version=1.20.1
```