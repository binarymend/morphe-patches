# Binarymend Patches

![License](https://img.shields.io/github/license/binarymend/morphe-patches)
![Last Commit](https://img.shields.io/github/last-commit/binarymend/morphe-patches)

Custom Android patches for the **Morphe** framework, maintained by Binarymend.

> [!IMPORTANT]
> **Suggestions for new apps and patches are welcome in [GitHub Issues](https://github.com/binarymend/morphe-patches/issues).**
>
> However, please keep in mind that this is a hobby project maintained in my spare time — I have a job and a life outside of coding. I cannot fulfill every request or release patches on demand; doing so would quickly lead to burnout.
>
> I generally prioritize popular applications. **If you need frequent updates or have specific requests, that kind of dedicated work requires compensation — my time is not unlimited and not free.** Consider [supporting the project](.donate/donate.md) if you'd like to see faster progress.

## How to use

1. Open **Morphe Manager** on your device.
2. Go to **Repositories**.
3. Add this repository:

   ```text
   https://github.com/binarymend/morphe-patches
   ```

4. Enable **Use pre-release patches** only if you want preview builds from the `dev` channel.

## Available patches

> [!NOTE]
> All patches are tested against **APK installs only** on **arm64-v8a** architecture. Other architectures or split APKs are not guaranteed to work.

| App | Package | Patches |
| :--- | :--- | :--- |
| Quick Cursor | `com.quickcursor` | Unlock PRO |
| Truecaller | `com.truecaller` | Disable Telemetry, Remove Ads |
| Pinterest | `com.pinterest` | Disable Bugsnag Telemetry, Disable General Telemetry, Remove Promoted Pins |
| aCalendar | `org.withouthat.acalendar` | Unlock aCalendar+ |

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
