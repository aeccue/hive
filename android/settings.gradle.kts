dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "aeccue"

include(
    ":foundation:android",
    ":foundation:core",
    ":foundation:dagger",
    ":hub",
    ":modules:bluetooth",
    ":modules:led",
    ":modules:pi",
    ":modules:profile"
)
