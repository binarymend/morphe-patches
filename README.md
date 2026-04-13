# Binarymend Patches

![License](https://img.shields.io/github/license/binarymend/morphe-patches)
![Last Commit](https://img.shields.io/github/last-commit/binarymend/morphe-patches)

Custom Android patches for the **Morphe** framework, maintained by Binarymend.

Patch requests and bug reports are welcome in [GitHub Issues](https://github.com/binarymend/morphe-patches/issues).

## How to use

1. Open **Morphe Manager** on your device.
2. Go to **Repositories**.
3. Add this repository:

   ```text
   https://github.com/binarymend/morphe-patches
   ```

4. Enable **Use pre-release patches** only if you want preview builds from the `dev` channel.

## Available patches

| App | Package | Patches |
| :--- | :--- | :--- |
| Quick Cursor | `com.quickcursor` | Unlock PRO |
| Truecaller | `com.truecaller` | Disable Telemetry, Remove Ads |
| Pinterest | `com.pinterest` | Disable Bugsnag Telemetry, Disable General Telemetry, Remove Promoted Pins |

## Building from source

1. Clone the repo: `git clone https://github.com/binarymend/morphe-patches.git`
2. Create `local.properties` with your Android SDK path.
3. Add GitHub Packages credentials to `gradle.properties` or environment variables.
4. Run `./gradlew clean build`.

## ❤️ Support

If you want to support development, see [donate](.donate/donate.md).

## Legal

Use this project at your own risk.

1. This repository is for educational and research purposes.
2. The software is provided without warranty.
3. This repository does not distribute modified APKs or proprietary source code.
4. App names, trademarks, and logos belong to their respective owners.
5. If you are a rights holder and need content reviewed or removed, open an issue first so it can be handled promptly.

## Credits

- [MorpheApp](https://github.com/MorpheApp)
