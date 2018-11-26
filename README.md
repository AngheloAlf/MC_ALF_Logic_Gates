# MC_ALF_Logic_Gates
Logic Gates mod for Minecraft


## How to build

### Pre-requisites

- [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) (or [OpenJDK](https://openjdk.java.net/install/)) 8 or later.
- [Gradle build tool](https://gradle.org/install/).
- Possibly something else that I'm missing...

### Building

- `git clone https://github.com/AngheloAlf/MC_ALF_Logic_Gates.git`
- `cd MC_ALF_Logic_Gates`
- `gradle build` (the first run will take a lot of time.)

The generated mod will be located at `build/libs/ALF_Logic_Gates-X.Y.Z.jar`. You can just copy this jar into the mod folder in your minecraft installation.


## Version compatibility

The mod has been tested to work in the following setup:

- Minecraft 1.12.2 & Forge 14.23.5.2768

It can probably work with other setups, but it has not been officially tested.



## TODOs

- [ ] Write a little guide for the mod. It should have:
  - The block recipes.
  - Block functionality explanation.
- [ ] Less ugly textures. 
  - The actual textures are very ugly.
  - It is hard to distinguish between blocks in the inventory.
- [ ] Improve the blocks recipes. 
  - I'm not satisfied with the actual recipes, I think they could be improved, but don't know how.
- [ ] Improve the `README.md`.
- [ ] Document the code.
  - It would be difficult to other people to contribute to the project without documentation.
  - If I revisit the code after some time, probably I will not remember what thing does what.
- [ ] Look for stuff in the project that violates the _Forge_ way to do things.
  - I'm not a Forge expert, so a lot of things could be probably be improved, or changed to the latest version way to do it.
