package nz.org.geonet.gradle.build

import org.gradle.api.GradleScriptException

class BuildVersionExtension {

    String releaseTagPattern = "^release-(\\d+\\.\\d+\\.\\d)"
    String matchGroup = "\$1"
    String versionSplitter = "."
    String snapShotQuantifier = "-SNAPSHOT"
    boolean isRelease = false

    private final GitVersion gitVersion

    public BuildVersionExtension() {
        gitVersion = new GitVersion()
    }

    String getVersion() {
        String version = null

        try {
            version = gitVersion.getBuildVersion(
                    releaseTagPattern,
                    matchGroup,
                    versionSplitter,
                    snapShotQuantifier,
                    isRelease
            )
        } catch (Exception e) {
            throw new GradleScriptException("Cannot suss a build version for you.\n\n" +
                    "Using:\n\n" +
                    "releaseTagPattern: \"" + releaseTagPattern + "\"\n" +
                    "matchGroup: \"" + matchGroup + "\"\n" +
                    "versionSplitter: \"" + versionSplitter + "\"\n" +
                    "snapShotQuantifier: \"" + snapShotQuantifier + "\"\n" +
                    "isRelease: \"" + isRelease + "\"\n" +
                    "\n" +
                    "Probable causes are:\n" +
                    "\n" +
                    "1. The directory is not a Git repo.\n" +
                    "2. There are no annotated tags matching the releaseTagPattern.\n" +
                    "3. The matchGroup and versionSplitter don't allow for the extraction of integers\n" +
                    "\n" +
                    "More info below\n\n"
                    , e)
        }

        return version
    }

}